import React, {Component} from 'react';
import {FormBinder, FormError} from '@icedesign/form-binder';
import {
    Button, Grid, Input
}
    from "@icedesign/base";
import IceContainer from '@icedesign/container';
import {injectIntl, FormattedMessage} from 'react-intl';

const {Row, Col} = Grid;

class Targets extends Component {

    inputValidator = (rule, value, callback) => {
        const errors = [];
        console.log(value);
        if (!value) {
            callback(this.props.intl.messages['gateway.route.createWeightingPolicy.configs.weightWarn.require']);
        }else if(isNaN(value)){
            callback(this.props.intl.messages['gateway.route.createWeightingPolicy.configs.weightWarn.number']);
        }else if (value < 0) {
            callback(this.props.intl.messages['gateway.route.createWeightingPolicy.configs.weightWarn.less']);
        } else if (value > 1000) {
            callback(this.props.intl.messages['gateway.route.createWeightingPolicy.configs.weightWarn.more']);
        } else {
            callback();
        }
    };

    render() {
        return (
            <IceContainer>
                {this.props.targets.map((target, index) => {
                    return (
                        <Row key={index}>
                            <Col xxs="6" s="2" l="3">
                            </Col>
                            <Col>
                                <FormBinder required
                                            message={this.props.intl.messages['gateway.route.createWeightingPolicy.configs.hostWarn']}
                                            name={`targets[${index}].targetHost`}>
                                    <Input
                                        placeholder={this.props.intl.messages['gateway.route.createWeightingPolicy.configs.host']}
                                        style={{width: '100%'}}/>
                                </FormBinder>
                                <FormError name={`targets[${index}].targetHost`} style={styles.formItemError}/>
                            </Col>
                            &nbsp;
                            <Col>
                                <FormBinder name={`targets[${index}].targetPort`} required
                                            message={this.props.intl.messages['gateway.route.createWeightingPolicy.configs.portWarn']}>
                                    <Input
                                        placeholder={this.props.intl.messages['gateway.route.createWeightingPolicy.configs.port']}
                                        style={{width: '100%'}}/>
                                    {/*<TextArea/>*/}
                                </FormBinder>
                                <FormError name={`targets[${index}].targetPort`} style={styles.formItemError}/>
                            </Col>
                            <Col>
                                <FormBinder name={`targets[${index}].weights`}
                                            required
                                            validator={this.inputValidator}>
                                    <Input
                                        placeholder={this.props.intl.messages['gateway.route.createWeightingPolicy.configs.weight']}
                                        style={{width: '100%'}}/>
                                    {/*<TextArea/>*/}
                                </FormBinder>
                                <FormError name={`targets[${index}].weights`} style={styles.formItemError}/>
                            </Col>
                            <Button type="secondary" style={{margin: 'auto', marginLeft: '10px'}}
                                    onClick={this.props.removeItem.bind(this, index)}>{this.props.intl.messages['gateway.route.createWeightingPolicy.configs.delete']}</Button>
                        </Row>
                    );
                })}
                <Row>
                    <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                    </Col>
                    <div style={styles.btns}>
                        <Button type="secondary" onClick={this.props.addItem}>
                            {this.props.intl.messages['gateway.route.createWeightingPolicy.configs.add']}
                        </Button>
                    </div>
                </Row>
            </IceContainer>
        );
    }
}

const styles = {
    formLabel: {
        textAlign: 'right',
    },
    formItem: {
        marginBottom: '20px',
        display: 'flex',
        alignItems: 'center',
    },
    formItemLabel: {
        width: '70px',
        marginRight: '10px',
        display: 'inline-block',
        textAlign: 'right',
    },
    formItemError: {
        marginLeft: '10px',
    },
    preview: {
        border: '1px solid #eee',
        marginTop: 20,
        padding: 10
    },
    btns: {
        margin: '25px 0',
    },
};
export default injectIntl(Targets);

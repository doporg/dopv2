import React, {Component} from 'react';
import IceContainer from '@icedesign/container';
import {
    Input,
    Button,
    Grid, Feedback,
} from '@icedesign/base';
import Axios from "axios";
import {Link, withRouter} from "react-router-dom";
import API from "../../../API";
import {FormBinder, FormBinderWrapper, FormError} from '@icedesign/form-binder';
import {injectIntl} from "react-intl";

const {Row, Col} = Grid;


const Toast = Feedback.toast;

class CreateCurrentLimitPolicyForm extends Component {
    static displayName = 'CreateCurrentLimitPolicyForm';

    static defaultProps = {};

    constructor(props) {
        super(props);
        this.state = {
            value: {
                name: '',
                description: '',
                second: '',
                minute: '',
                hour: '',
                day: '',
            },
        };
    }

    formChange = value => {
        // 说明：
        //  1. 表单是双向通行的，所有表单的响应数据都会同步更新 value
        //  2. 这里 setState 只是为了实时展示当前表单数据的演示使用
        this.setState({value});
    };

    reset = () => {
        this.setState({
            value: {
                name: '',
                description: '',
                second: '',
                minute: '',
                hour: '',
                day: '',
            }
        });
    };

    back = () => {
        this.props.history.push('/gateway/currentLimit');
    };

    checkValue = () => {
        console.log(this.state.value.second !== ''|| this.state.value.minute !== '' || this.state.value.hour !== '' || this.state.value.day !== '');
        return this.state.value.second !== ''|| this.state.value.minute !== '' || this.state.value.hour !== '' || this.state.value.day !== ''
    };

    submit = () => {
        let _this = this;
        let noError = true;


        this.refs.form.validateAll((error, value) => {
            if (error != null||!this.checkValue()) {
                noError = false;
            }
        });

        if (noError) {
            let url = API.gateway + '/policy/flowControl/currentLimit';
            Axios.post(url, this.state.value)
                .then(function (response) {
                    if (response.data.code === 0) {
                        Toast.success(_this.props.intl.messages['gateway.currentLimit.createCurrentLimitPolicy.successInfo']);
                        let route = '/gateway/currentLimit';
                        _this.props.history.push(route);
                    } else {
                        Toast.error(_this.props.intl.messages['gateway.currentLimit.createCurrentLimitPolicy.errorInfo']);
                    }
                }).catch(function (error) {
                console.log(error);
                Toast.error(_this.props.intl.messages['gateway.currentLimit.createCurrentLimitPolicy.errorInfo']);
            });
        }else {
            Toast.error(_this.props.intl.messages['gateway.currentLimit.createCurrentLimitPolicy.warn.must']);
        }
    };

    inputValidator = (rule, value, callback) => {
        console.log(value);
        if (value) {
            if (isNaN(value)) {
                callback(this.props.intl.messages['gateway.currentLimit.createCurrentLimitPolicy.warn.number']);
            } else if (value <= 0) {
                callback(this.props.intl.messages['gateway.currentLimit.createCurrentLimitPolicy.warn.less']);
            } else {
                callback();
            }
        } else {
            callback();
        }
    };

    render() {
        return (
            <div className="create-currentLimitPolicy-form">
                <IceContainer title={this.props.intl.messages['gateway.currentLimit.createCurrentLimitPolicy.title']}
                              style={styles.container}>
                    <FormBinderWrapper
                        value={this.state.value}
                        onChange={this.formChange}
                        ref="form"
                    >
                        <div>
                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.currentLimit.createCurrentLimitPolicy.name']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder
                                        name="name"
                                        required
                                        message={this.props.intl.messages['gateway.currentLimit.createCurrentLimitPolicy.nameWarn']}
                                    >
                                        <Input style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="name"/>
                                </Col>
                            </Row>

                            <Row>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.currentLimit.createCurrentLimitPolicy.description']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="description">
                                        <Input multiple style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="description"/>
                                </Col>
                            </Row>


                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.currentLimit.createCurrentLimitPolicy.second']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="second"
                                                validator={this.inputValidator}>
                                        <Input style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="second"/>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.currentLimit.createCurrentLimitPolicy.minute']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="minute"
                                                validator={this.inputValidator}>
                                        <Input style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="minute"/>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.currentLimit.createCurrentLimitPolicy.hour']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="hour"
                                                validator={this.inputValidator}>
                                        <Input style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="hour"/>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.currentLimit.createCurrentLimitPolicy.day']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="day"
                                                validator={this.inputValidator}>
                                        <Input style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="day"/>
                                </Col>
                            </Row>


                            <Row style={styles.btns}>
                                <Col xxs="6" s="2" l="2" style={styles.formLabel}>
                                    {' '}
                                </Col>
                                <Col s="12" l="10">
                                    <Button style={{marginRight: '20px'}} onClick={this.back}>
                                        {this.props.intl.messages['gateway.route.createServiceDiscoveryPolicy.cancel']}
                                    </Button>

                                    <Button onClick={this.submit} type='secondary'>
                                        {this.props.intl.messages['gateway.route.createServiceDiscoveryPolicy.yes']}
                                    </Button>
                                    <Button style={styles.resetBtn} onClick={this.reset}>
                                        {this.props.intl.messages['gateway.route.createServiceDiscoveryPolicy.reset']}
                                    </Button>
                                </Col>
                            </Row>
                        </div>
                    </FormBinderWrapper>
                </IceContainer>
            </div>
        );
    }
}

const styles = {
    container: {
        paddingBottom: 0,
    },
    formItem: {
        height: '38px',
        lineHeight: '28px',
        marginBottom: '15px',
    },
    formLabel: {
        textAlign: 'right',
    },
    btns: {
        margin: '25px 0',
    },
    resetBtn: {
        marginLeft: '20px',
    },
};

export default injectIntl(withRouter(CreateCurrentLimitPolicyForm));

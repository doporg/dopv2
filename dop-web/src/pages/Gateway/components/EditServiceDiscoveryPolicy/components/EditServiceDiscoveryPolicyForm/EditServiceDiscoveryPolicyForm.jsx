import React, {Component} from 'react';
import IceContainer from '@icedesign/container';
import {
    Input,
    Button,
    Checkbox,
    Select,
    DatePicker,
    Switch,
    Grid, Feedback,
} from '@icedesign/base';
import Axios from "axios";
import API from "../../../../../API";
import {FormBinder, FormBinderWrapper, FormError} from '@icedesign/form-binder';
import {FormattedMessage, injectIntl} from "react-intl";

const {Row, Col} = Grid;
const styles = {
    container: {
        paddingBottom: 0,
    },
    formItem: {
        height: '28px',
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
const Toast = Feedback.toast;

class EditServiceDiscoveryPolicyForm extends Component {

    constructor(props) {
        super(props);
        const policy_Id = this.props.policyId;
        this.state = {
            policyId: policy_Id,
            value: {
                name: '',
                description: '',
                targetPath: '',
                targetHost:'',
                targetPort:'',
            },
        };
        this.doGetPolicy(policy_Id);
    }

    formChange = value => {
        // 说明：
        //  1. 表单是双向通行的，所有表单的响应数据都会同步更新 value
        //  2. 这里 setState 只是为了实时展示当前表单数据的演示使用
        this.setState({value});
    };

    reset = () => {
        this.doGetPolicy(this.state.policyId)
    };

    doGetPolicy(policyId) {
        let url = API.gateway + '/policy/route/' + policyId;
        let _this = this;
        Axios.get(url).then(function (response) {
            let policyDto = response.data.body;
            if (policyDto) {
                let policyInfo = EditServiceDiscoveryPolicyForm.doTrans(policyDto);
                _this.setState(policyInfo);
            } else {
                Toast.error("No policy info!");
            }
        }).catch(function (error) {
            Toast.error(error);
        });
    }

    static doTrans(policyDto) {
        return {
            value: {
                name: policyDto.name,
                description: policyDto.description,
                targetPath: policyDto.targetPath,
                targetHost: policyDto.targetHost,
                targetPort: policyDto.targetPort,
            },
        };
    };

    back = () => {
        this.props.history.push('/gateway/route');
    };

    submit = () => {
        let _this = this;
        let noError = true;

        this.refs.form.validateAll((error, value) => {
            if (error != null) {
                noError = false;
            }
        });

        if (noError) {
            Toast.success(<FormattedMessage
                id='gateway.route.editWeightingPolicy.waiting'
                defaultMessage={_this.props.intl.messages['gateway.route.editWeightingPolicy.waiting']}
            />);
            let url = API.gateway + '/policy/route/serviceDiscoveryPolicy/'+_this.state.policyId;
            Axios.patch(url, this.state.value)
                .then(function (response) {
                    if (response.data.code===0){
                        Toast.success(_this.props.intl.messages['gateway.route.editServiceDiscoveryPolicy.successInfo']);
                        let route = '/gateway/route';
                        _this.props.history.push(route);
                    } else {
                        Toast.error(_this.props.intl.messages['gateway.route.editServiceDiscoveryPolicy.errorInfo']);
                    }
                }).catch(function (error) {
                console.log(error);
                Toast.error(_this.props.intl.messages['gateway.route.editServiceDiscoveryPolicy.errorInfo']);
            });
        }
    };

    render() {
        return (
            <div className="create-activity-form">
                <IceContainer title={this.props.intl.messages['gateway.route.editServiceDiscoveryPolicy.title']} style={styles.container}>
                    <FormBinderWrapper
                        value={this.state.value}
                        onChange={this.formChange}
                        ref="form"
                    >
                        <div>
                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.route.createServiceDiscoveryPolicy.name']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder
                                        name="name"
                                        required
                                        message={this.props.intl.messages['gateway.route.createServiceDiscoveryPolicy.nameWarn']}
                                    >
                                        <Input style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="name"/>
                                </Col>
                            </Row>

                            <Row>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.route.createServiceDiscoveryPolicy.description']}
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
                                    {this.props.intl.messages['gateway.route.createServiceDiscoveryPolicy.path']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="targetPath" required
                                                message={this.props.intl.messages['gateway.route.createServiceDiscoveryPolicy.pathWarn']}>
                                        <Input style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="targetPath"/>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.route.createServiceDiscoveryPolicy.host']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="targetHost" required
                                                message={this.props.intl.messages['gateway.route.createServiceDiscoveryPolicy.hostWarn']}>
                                        <Input style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="targetHost"/>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.route.createServiceDiscoveryPolicy.port']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="targetPort" required
                                                message={this.props.intl.messages['gateway.route.createServiceDiscoveryPolicy.portWarn']}>
                                        <Input style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="targetPort"/>
                                </Col>
                            </Row>

                            <Row style={styles.btns}>
                                <Col xxs="6" s="2" l="2" style={styles.formLabel}>
                                    {' '}
                                </Col>
                                <Col s="12" l="10">
                                    <Button style={{marginRight: '20px'}} onClick={this.back}>
                                        {this.props.intl.messages['gateway.route.editServiceDiscoveryPolicy.cancel']}
                                    </Button>

                                    <Button onClick={this.submit} type='secondary'>
                                        {this.props.intl.messages['gateway.route.editServiceDiscoveryPolicy.yes']}
                                        {/* <Link to='/test/createInterfaceScripts'>
                    </Link>*/}
                                    </Button>
                                    <Button style={styles.resetBtn} onClick={this.reset}>
                                        {this.props.intl.messages['gateway.route.editServiceDiscoveryPolicy.reset']}
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

export default injectIntl(EditServiceDiscoveryPolicyForm);

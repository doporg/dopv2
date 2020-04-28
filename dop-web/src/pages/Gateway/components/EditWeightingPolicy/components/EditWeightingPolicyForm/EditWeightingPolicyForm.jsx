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
import {Link, withRouter} from "react-router-dom";
import API from "../../../../../API";
import {FormBinder, FormBinderWrapper, FormError} from '@icedesign/form-binder';
import {FormattedMessage, injectIntl} from "react-intl";
import Targets from "../../../CreateWeightingPolicy/Targets";

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

class EditWeightingPolicyForm extends Component {

    constructor(props) {
        super(props);
        const policy_Id = this.props.policyId;
        this.state = {
            policyId: policy_Id,
            value: {
                name: '',
                description: '',
                path: '/',
                algorithm: 'round-robin',
                hashOn: 'ip',
                header: '',
                targets: [{}]
            },
            algorithmDataSource: [
                {
                    value: 'round-robin',
                    label: this.props.intl.messages["gateway.route.createWeightingPolicy.algorithm.roundRobin"]
                },
                {
                    value: 'consistent-hashing',
                    label: this.props.intl.messages["gateway.route.createWeightingPolicy.algorithm.consistentHashing"]
                },
                {
                    value: 'least-connections',
                    label: this.props.intl.messages["gateway.route.createWeightingPolicy.algorithm.leastConnections"]
                },
            ],
            hashOnDataSource: [
                {
                    value: 'ip',
                    label: this.props.intl.messages["gateway.route.createWeightingPolicy.hashOn.ip"]
                },
                {
                    value: 'header',
                    label: this.props.intl.messages["gateway.route.createWeightingPolicy.hashOn.header"]
                },
            ],
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
                let policyInfo = EditWeightingPolicyForm.doTrans(policyDto);
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
                path: policyDto.path,
                algorithm: policyDto.algorithm,
                hashOn: policyDto.hashOn,
                header: policyDto.header,
                targets: policyDto.configurations
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
                id='gateway.route.editServiceDiscoveryPolicy.waiting'
                defaultMessage={_this.props.intl.messages['gateway.route.editServiceDiscoveryPolicy.waiting']}
            />);
            let url = API.gateway + '/policy/route/weightingPolicy/'+_this.state.policyId;
            Axios.patch(url, this.state.value)
                .then(function (response) {
                    if (response.data.code===0){
                        Toast.success(_this.props.intl.messages['gateway.route.editWeightingPolicy.successInfo']);
                        let route = '/gateway/route';
                        _this.props.history.push(route);
                    } else {
                        Toast.error(_this.props.intl.messages['gateway.route.editWeightingPolicy.errorInfo']);
                    }
                }).catch(function (error) {
                console.log(error);
                Toast.error(_this.props.intl.messages['gateway.route.editWeightingPolicy.errorInfo']);
            });
        }
    };

    removeItem = (index) => {
        this.state.value.targets.splice(index, 1);
        this.setState({
            value: this.state.value
        });
    };

    addItem = () => {
        this.state.value.targets.push({});
        this.setState({value: this.state.value});
    };

    validateAllFormField = () => {
        this.refs.form.validateFields((errors, values) => {
            console.log('errors', errors, 'values', values);
        });
    };

    render() {
        return (
            <div className="create-activity-form">
                <IceContainer title={this.props.intl.messages['gateway.route.editWeightingPolicy.title']} style={styles.container}>
                    <FormBinderWrapper
                        value={this.state.value}
                        onChange={this.formChange}
                        ref="form"
                    >
                        <div>
                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.route.createWeightingPolicy.name']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder
                                        name="name"
                                        required
                                        message={this.props.intl.messages['gateway.route.createWeightingPolicy.nameWarn']}
                                    >
                                        <Input style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="name"/>
                                </Col>
                            </Row>

                            <Row>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.route.createWeightingPolicy.description']}
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
                                    {this.props.intl.messages['gateway.route.createWeightingPolicy.path']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder
                                        name="path"
                                        required
                                        message={this.props.intl.messages['gateway.route.createWeightingPolicy.pathWarn']}
                                    >
                                        <Input style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="path"/>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.route.createWeightingPolicy.algorithm']}
                                </Col>
                                <Col s="12" l="10">
                                    <FormBinder name="algorithm">
                                        <Select placeholder={this.props.intl.messages["gateway.search.pleaseSelect"]}
                                                useDetailValue
                                                defaultValue={{
                                                    value: 'round-robin',
                                                    label: this.props.intl.messages["gateway.route.createWeightingPolicy.algorithm.roundRobin"]
                                                }}
                                                dataSource={this.state.algorithmDataSource}>
                                        </Select>
                                    </FormBinder>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.route.createWeightingPolicy.hashOn']}
                                </Col>
                                <Col s="12" l="10">
                                    <FormBinder name="hashOn">
                                        <Select placeholder={this.props.intl.messages["gateway.search.pleaseSelect"]}
                                                useDetailValue
                                                disabled={this.state.value.algorithm !== 'consistent-hashing'}
                                                defaultValue={{
                                                    value: 'ip',
                                                    label: this.props.intl.messages["gateway.route.createWeightingPolicy.hashOn.ip"]
                                                }}
                                                dataSource={this.state.hashOnDataSource}>
                                        </Select>
                                    </FormBinder>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.route.createWeightingPolicy.header']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder
                                        name="header"
                                        required={this.state.value.hashOn === 'header'}
                                        message={this.props.intl.messages['gateway.route.createWeightingPolicy.headerWarn']}
                                    >
                                        <Input style={{width: '100%'}} disabled={this.state.value.hashOn !== 'header'}/>
                                    </FormBinder>
                                    <FormError name="header"/>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.route.createWeightingPolicy.configs']}
                                </Col>
                            </Row>

                            <Targets
                                targets={this.state.value.targets}
                                addItem={this.addItem}
                                removeItem={this.removeItem}
                                validateAllFormField={this.validateAllFormField}
                            />

                            <Row style={styles.btns}>
                                <Col xxs="6" s="2" l="2" style={styles.formLabel}>
                                    {' '}
                                </Col>
                                <Col s="12" l="10">
                                    <Button style={{marginRight: '20px'}} onClick={this.back}>
                                        {this.props.intl.messages['gateway.route.editWeightingPolicy.cancel']}
                                    </Button>

                                    <Button onClick={this.submit} type='secondary'>
                                        {this.props.intl.messages['gateway.route.editWeightingPolicy.yes']}
                                        {/* <Link to='/test/createInterfaceScripts'>
                    </Link>*/}
                                    </Button>
                                    <Button style={styles.resetBtn} onClick={this.reset}>
                                        {this.props.intl.messages['gateway.route.editWeightingPolicy.reset']}
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

export default injectIntl(EditWeightingPolicyForm);

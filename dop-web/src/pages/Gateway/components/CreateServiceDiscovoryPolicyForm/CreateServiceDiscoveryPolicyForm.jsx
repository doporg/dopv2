import React, {Component} from 'react';
import IceContainer from '@icedesign/container';
import {
    Input,
    Button,
    Checkbox,
    Select,
    DatePicker,
    Switch,
    Radio,
    Grid, Feedback,
} from '@icedesign/base';
import Axios from "axios";
import {Link, withRouter} from "react-router-dom";
import API from "../../../API";
import {FormBinder, FormBinderWrapper, FormError} from '@icedesign/form-binder';
import {FormattedMessage, injectIntl} from "react-intl";

const {Row, Col} = Grid;

// FormBinder 用于获取表单组件的数据，通过标准受控 API value 和 onChange 来双向操作数据
const CheckboxGroup = Checkbox.Group;
const RadioGroup = Radio.Group;
const {RangePicker} = DatePicker;


// Switch 组件的选中等 props 是 checked 不符合表单规范的 value 在此做转换
const SwitchForForm = (props) => {
    const checked = props.checked === undefined ? props.value : props.checked;

    return (
        <Switch
            {...props}
            checked={checked}
            onChange={(currentChecked) => {
                if (props.onChange) props.onChange(currentChecked);
            }}
        />
    );
};

const Toast = Feedback.toast;

class CreateServiceDiscoveryPolicyForm extends Component {
    static displayName = 'CreateServiceDiscoveryPolicyForm';

    static defaultProps = {};

    constructor(props) {
        super(props);
        this.state = {
            value: {
                name: '',
                description: '',
                targetHost: '',
                targetPath: '/',
                targetPort: '80',
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
                targetHost: '',
                targetPath: '/',
                targetPort: '80',
            }
        });
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
            let url = API.gateway + '/policy/route/serviceDiscoveryPolicy';
            Toast.success(<FormattedMessage
                id='gateway.route.createWeightingPolicy.waiting'
                defaultMessage={_this.props.intl.messages['gateway.route.createServiceDiscoveryPolicy.waiting']}
            />);
            Axios.post(url, this.state.value)
                .then(function (response) {
                    Toast.success(_this.props.intl.messages['gateway.route.createServiceDiscoveryPolicy.successInfo']);
                    let route = '/gateway/route';
                    _this.props.history.push(route);
                }).catch(function (error) {
                console.log(error);
                Toast.error(_this.props.intl.messages['gateway.route.createServiceDiscoveryPolicy.errorInfo']);
            });
        }
    };

    render() {
        return (
            <div className="create-serviceDiscoveryPolicy-form">
                <IceContainer title={this.props.intl.messages['gateway.route.createServiceDiscoveryPolicy.title']}
                              style={styles.container}>
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
                                        {this.props.intl.messages['gateway.route.createServiceDiscoveryPolicy.cancel']}
                                    </Button>

                                    <Button onClick={this.submit} type='secondary'>
                                        {this.props.intl.messages['gateway.route.createServiceDiscoveryPolicy.yes']}
                                        {/* <Link to='/test/createInterfaceScripts'>
                    </Link>*/}
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

export default injectIntl(withRouter(CreateServiceDiscoveryPolicyForm));

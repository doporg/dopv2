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
import API from "../../../../API";
import {FormBinder, FormBinderWrapper, FormError} from '@icedesign/form-binder';
import {FormattedMessage, injectIntl} from "react-intl";
import Targets from "../Targets";

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

class CreateWeightingPolicyForm extends Component {
    static displayName = 'CreateWeightingPolicyForm';

    static defaultProps = {};

    constructor(props) {
        super(props);
        this.state = {
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
    }


    formChange = value => {
        // 说明：
        //  1. 表单是双向通行的，所有表单的响应数据都会同步更新 value
        //  2. 这里 setState 只是为了实时展示当前表单数据的演示使用
        this.setState({value});
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

    reset = () => {
        this.setState({
            value: {
                name: '',
                description: '',
                path: '/',
                algorithm: 'round-robin',
                hashOn: 'ip',
                header: '',
                targets: [{}]
            },
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
            let url = API.gateway + '/policy/route/weightingPolicy';
            Toast.success(<FormattedMessage
                id='gateway.route.createWeightingPolicy.waiting'
                defaultMessage={_this.props.intl.messages['gateway.route.createWeightingPolicy.waiting']}
            />);
            Axios.post(url, this.state.value)
                .then(function (response) {
                    Toast.success(_this.props.intl.messages['gateway.route.createWeightingPolicy.successInfo']);
                    let route = '/gateway/route';
                    _this.props.history.push(route);
                }).catch(function (error) {
                console.log(error);
                Toast.error(_this.props.intl.messages['gateway.route.createWeightingPolicy.errorInfo']);
            });
        }
    };

    render() {
        return (
            <div className="create-activity-form">
                <IceContainer title={this.props.intl.messages['gateway.route.createWeightingPolicy.title']}
                              style={styles.container}>
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
                                        {this.props.intl.messages['gateway.route.createWeightingPolicy.cancel']}
                                    </Button>

                                    <Button onClick={this.submit} type='secondary'>
                                        {this.props.intl.messages['gateway.route.createWeightingPolicy.yes']}
                                        {/* <Link to='/test/createInterfaceScripts'>
                    </Link>*/}
                                    </Button>
                                    <Button style={styles.resetBtn} onClick={this.reset}>
                                        {this.props.intl.messages['gateway.route.createWeightingPolicy.reset']}
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

export default injectIntl(withRouter(CreateWeightingPolicyForm));

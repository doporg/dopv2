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

class CreateActivityForm extends Component {
    static displayName = 'CreateActivityForm';

    static defaultProps = {};

    constructor(props) {
        super(props);
        this.state = {
            value: {
                name: 'testApi',
                description: 'this is a api',
                requestMethod: 'GET',
                requestPath: '/test',
                caching: false,
                cachingTime: 0,
                timeout: 1000,
                fusePolicy: {
                    enable: false,
                    criticalFusingFailureRate: 10,
                    fuseDetectionRing: 30,
                    fuseDuration: 1000,
                    replyDetectionRingSize: 10,
                },
                routingPolicyId: '',
                currentLimitPolicyId:'',
            },
            routeDataSource: [],
            currentLimitDataSource:[{label: this.props.intl.messages['gateway.createApi.currentLimitPolicy.null'], value: ''}],
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
                requestMethod: '',
                requestPath: '',
                caching: false,
                cachingTime: 0,
                timeout: 1000,
                fusePolicy: {
                    enable: false,
                    criticalFusingFailureRate: 10,
                    fuseDetectionRing: 30,
                    fuseDuration: 1000,
                    replyDetectionRingSize: 10,
                },
                routingPolicyId: '',
                currentLimitPolicyId:''
            },
        });
    };

    back = () => {
        this.props.history.push('/gateway');
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
            let url = API.gateway + '/lifeCycle';
            Toast.success(<FormattedMessage
                id='gateway.createApi.waiting'
                defaultMessage={_this.props.intl.messages['gateway.createApi.waiting']}
            />);
            Axios.post(url, this.state.value)
                .then(function (response) {
                    Toast.success(_this.props.intl.messages['gateway.createApi.successInfo']);
                    let route = '/gateway';
                    _this.props.history.push(route);
                }).catch(function (error) {
                console.log(error);
                Toast.error(_this.props.intl.messages['gateway.createApi.errorInfo']);
            });
        }
    };

    handleRouteSearch = (value) => {
        let _this = this;
        let url = API.gateway + '/policy/route/search';
        if (this.searchTimeout) {
            clearTimeout(this.searchTimeout);
        }
        this.searchTimeout = setTimeout(() => {
            value ? Axios.get(url, {
                params: {
                    value: value,
                }
            }).then(function (response) {
                const dataSource = response.data.body.map(item => ({
                    label: item.name, value: item.policyId
                }));
                _this.setState({routeDataSource:dataSource});
            }).catch(function (error) {
                console.log(error);
            }) : this.setState({routeDataSource: []});
        }, 100);
    };

    handleCurrentLimitSearch = (value) => {
        let _this = this;
        let url = API.gateway + '/policy/flowControl/currentLimit/search';
        if (this.searchTimeout) {
            clearTimeout(this.searchTimeout);
        }
        this.searchTimeout = setTimeout(() => {
            value ? Axios.get(url, {
                params: {
                    value: value,
                }
            }).then(function (response) {
                const dataSource = response.data.body.map(item => ({
                    label: item.name, value: item.policyId
                }));
                dataSource[dataSource.length] = {label: _this.props.intl.messages['gateway.createApi.currentLimitPolicy.null'], value: ''};
                _this.setState({currentLimitDataSource:dataSource});
            }).catch(function (error) {
                console.log(error);
            }) : this.setState({currentLimitDataSource: []});
        }, 100);
    };

    render() {
        return (
            <div className="create-activity-form">
                <IceContainer title={this.props.intl.messages['gateway.createApi.title']} style={styles.container}>
                    <FormBinderWrapper
                        value={this.state.value}
                        onChange={this.formChange}
                        ref="form"
                    >
                        <div>
                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.createApi.apiName']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder
                                        name="name"
                                        required
                                        message={this.props.intl.messages['gateway.createApi.apiNameWarn']}
                                    >
                                        <Input style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="name"/>
                                </Col>
                            </Row>

                            <Row>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.createApi.apiDesc']}
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
                                    {this.props.intl.messages['gateway.createApi.requestMethod']}
                                </Col>
                                <Col s="12" l="10">
                                    <FormBinder name="requestMethod">
                                        <Select placeholder={this.props.intl.messages["gateway.search.pleaseSelect"]}
                                                style={{width: '100%'}} defaultValue="GET">
                                            <Select.Option
                                                value="GET">{this.props.intl.messages["gateway.search.requestMethod.get"]}</Select.Option>
                                            <Select.Option
                                                value="POST">{this.props.intl.messages["gateway.search.requestMethod.post"]}</Select.Option>
                                            <Select.Option
                                                value="PATCH">{this.props.intl.messages["gateway.search.requestMethod.patch"]}</Select.Option>
                                            <Select.Option
                                                value="PUT">{this.props.intl.messages["gateway.search.requestMethod.put"]}</Select.Option>
                                            <Select.Option
                                                value="DELETE">{this.props.intl.messages["gateway.search.requestMethod.delete"]}</Select.Option>
                                        </Select>
                                    </FormBinder>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.createApi.requestPath']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="requestPath" required
                                                message={this.props.intl.messages['gateway.createApi.requestPathWarn']}>
                                        <Input style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="requestPath"/>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.createApi.timeout']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="timeout">
                                        <Input style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="timeout"/>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.createApi.caching']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="caching" valuePropName="checked">
                                        <Switch checked={this.state.value.caching}/>
                                    </FormBinder>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.createApi.cachingTime']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="cachingTime">
                                        <Input disabled={!this.state.value.caching} style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="cachingTime"/>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.createApi.fuse']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="fusePolicy.enable" valuePropName="checked">
                                        <Switch checked={this.state.value.fusePolicy.enable}/>
                                    </FormBinder>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.createApi.fuse.criticalFusingFailureRate']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="fusePolicy.criticalFusingFailureRate">
                                        <Input disabled={!this.state.value.fusePolicy.enable} style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="fusePolicy.criticalFusingFailureRate"/>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.createApi.fuse.fuseDetectionRing']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="fusePolicy.fuseDetectionRing">
                                        <Input disabled={!this.state.value.fusePolicy.enable} style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="fusePolicy.fuseDetectionRing"/>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.createApi.fuse.fuseDuration']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="fusePolicy.fuseDuration">
                                        <Input disabled={!this.state.value.fusePolicy.enable} style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="fusePolicy.fuseDuration"/>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.createApi.fuse.replyDetectionRingSize']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="fusePolicy.replyDetectionRingSize">
                                        <Input disabled={!this.state.value.fusePolicy.enable} style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="fusePolicy.replyDetectionRingSize"/>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.createApi.routingPolicy']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="routingPolicyId">
                                        <Select showSearch placeholder="select search" filterLocal={false}
                                                dataSource={this.state.routeDataSource} onSearch={this.handleRouteSearch}
                                                style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="routingPolicyId"/>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.createApi.currentLimitPolicy']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="currentLimitPolicyId">
                                        <Select showSearch placeholder="select search" filterLocal={false}
                                                dataSource={this.state.currentLimitDataSource} onSearch={this.handleCurrentLimitSearch}
                                                style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="currentLimitPolicyId"/>
                                </Col>
                            </Row>

                            <Row style={styles.btns}>
                                <Col xxs="6" s="2" l="2" style={styles.formLabel}>
                                    {' '}
                                </Col>
                                <Col s="12" l="10">
                                    <Button style={{marginRight: '20px'}} onClick={this.back}>
                                        {this.props.intl.messages['gateway.createApi.cancel']}
                                    </Button>

                                    <Button onClick={this.submit} type='secondary'>
                                        {this.props.intl.messages['gateway.createApi.yes']}
                                        {/* <Link to='/test/createInterfaceScripts'>
                    </Link>*/}
                                    </Button>
                                    <Button style={styles.resetBtn} onClick={this.reset}>
                                        {this.props.intl.messages['gateway.createApi.reset']}
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

export default injectIntl(withRouter(CreateActivityForm));

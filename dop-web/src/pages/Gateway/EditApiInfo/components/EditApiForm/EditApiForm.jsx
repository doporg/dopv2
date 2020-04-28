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
import API from "../../../../API";
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

class EditApiForm extends Component {

    constructor(props) {
        super(props);
        const api_Id = this.props.apiId;
        this.state = {
            apiId: api_Id,
            value: {
                name: '',
                description: '',
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
                routingPolicy: {
                    value:'',
                    label:''
                },
                currentLimitPolicy: {
                    value:'',
                    label:''
                },
            },
            routeDataSource: [],
            currentLimitDataSource: [{
                label: this.props.intl.messages['gateway.createApi.currentLimitPolicy.null'],
                value: ''
            }],
        };
        this.doGetApi(api_Id);
    }

    formChange = value => {
        // 说明：
        //  1. 表单是双向通行的，所有表单的响应数据都会同步更新 value
        //  2. 这里 setState 只是为了实时展示当前表单数据的演示使用
        //this.setState({value});
    };

    reset = () => {
        this.doGetApi(this.state.apiId)
    };

    doGetApi(ApiId) {
        let url = API.gateway + '/lifeCycle/' + ApiId;
        let _this = this;
        Axios.get(url).then(function (response) {
            let apiDto = response.data.body;
            if (apiDto) {
                let apiInfo = EditApiForm.doTrans(apiDto);
                _this.setState(apiInfo);
            } else {
                Toast.error("No interface case info!");
            }
        }).catch(function (error) {
            Toast.error(error);
        });
    }

    static doTrans(apiDto) {
        let routingPolicy = apiDto.routingPolicy;
        let currentLimitPolicy = apiDto.currentLimitPolicy;
        let currentLimitPolicyId = currentLimitPolicy ? currentLimitPolicy.policyId : null;
        let currentLimitPolicyName = currentLimitPolicy ? currentLimitPolicy.name : null;
        return {
            value: {
                name: apiDto.name,
                description: apiDto.description,
                requestMethod: apiDto.requestMethod,
                requestPath: apiDto.requestPath,
                caching: apiDto.caching,
                cachingTime: apiDto.cachingTime,
                timeout: apiDto.timeout,
                fusePolicy: {
                    enable: apiDto.fusePolicy.enable,
                    criticalFusingFailureRate: apiDto.fusePolicy.criticalFusingFailureRate,
                    fuseDetectionRing: apiDto.fusePolicy.fuseDetectionRing,
                    fuseDuration: apiDto.fusePolicy.fuseDuration,
                    replyDetectionRingSize: apiDto.fusePolicy.replyDetectionRingSize,
                },
                routingPolicy: {
                    value: routingPolicy.policyId,
                    label: routingPolicy.name,
                },
                currentLimitPolicy: {
                    value: currentLimitPolicyId,
                    label: currentLimitPolicyName,
                }
            },
        };
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
            let url = API.gateway + '/lifeCycle/' + _this.state.apiId;
            Toast.success(<FormattedMessage
                id='gateway.editApi.waiting'
                defaultMessage={_this.props.intl.messages['gateway.editApi.waiting']}
            />);
            Axios.patch(url, EditApiForm.submitValueTrans(this.state.value))
                .then(function (response) {
                    if (response.data.code === 0) {
                        Toast.success(_this.props.intl.messages['gateway.editApi.successInfo']);
                        let route = '/gateway';
                        _this.props.history.push(route);
                    } else {
                        Toast.error(_this.props.intl.messages['gateway.editApi.errorInfo']);
                    }
                }).catch(function (error) {
                console.log(error);
                Toast.error(_this.props.intl.messages['gateway.editApi.errorInfo']);
            });
        }
    };

    static submitValueTrans(value){
        return {
            name: value.name,
            description: value.description,
            requestMethod: value.requestMethod,
            requestPath: value.requestPath,
            caching: value.caching,
            cachingTime: value.cachingTime,
            timeout: value.timeout,
            fusePolicy: {
                enable: value.fusePolicy.enable,
                criticalFusingFailureRate: value.fusePolicy.criticalFusingFailureRate,
                fuseDetectionRing: value.fusePolicy.fuseDetectionRing,
                fuseDuration: value.fusePolicy.fuseDuration,
                replyDetectionRingSize: value.fusePolicy.replyDetectionRingSize,
            },
            routingPolicy: value.routingPolicy.value?value.routingPolicy.value:value.routingPolicy,
            currentLimitPolicy: value.currentLimitPolicy.value?value.currentLimitPolicy.value:value.currentLimitPolicy,
        }
    }


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
                _this.setState({routeDataSource: dataSource});
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
                dataSource[dataSource.length] = {
                    label: _this.props.intl.messages['gateway.editApi.currentLimitPolicy.null'],
                    value: ''
                };
                _this.setState({currentLimitDataSource: dataSource});
            }).catch(function (error) {
                console.log(error);
            }) : this.setState({currentLimitDataSource: []});
        }, 100);
    };

    render() {
        return (
            <div className="create-activity-form">
                <IceContainer title={this.props.intl.messages['gateway.editApi.title']} style={styles.container}>
                    <FormBinderWrapper
                        value={this.state.value}
                        onChange={this.formChange}
                        ref="form"
                    >
                        <div>
                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.editApi.apiName']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder
                                        name="name"
                                        required
                                        message={this.props.intl.messages['gateway.editApi.apiNameWarn']}
                                    >
                                        <Input style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="name"/>
                                </Col>
                            </Row>

                            <Row>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.editApi.apiDesc']}
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
                                    {this.props.intl.messages['gateway.editApi.requestMethod']}
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
                                    {this.props.intl.messages['gateway.editApi.requestPath']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="requestPath" required
                                                message={this.props.intl.messages['gateway.editApi.requestPathWarn']}>
                                        <Input style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="requestPath"/>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.editApi.timeout']}
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
                                    {this.props.intl.messages['gateway.editApi.caching']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="caching" valuePropName="checked">
                                        <Switch checked={this.state.value.caching}/>
                                    </FormBinder>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.editApi.cachingTime']}
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
                                    {this.props.intl.messages['gateway.editApi.fuse']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="fusePolicy.enable" valuePropName="checked">
                                        <Switch checked={this.state.value.fusePolicy.enable}/>
                                    </FormBinder>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.editApi.fuse.criticalFusingFailureRate']}
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
                                    {this.props.intl.messages['gateway.editApi.fuse.fuseDetectionRing']}
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
                                    {this.props.intl.messages['gateway.editApi.fuse.fuseDuration']}
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
                                    {this.props.intl.messages['gateway.editApi.fuse.replyDetectionRingSize']}
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
                                    {this.props.intl.messages['gateway.editApi.routingPolicy']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="routingPolicy">
                                        <Select
                                            showSearch
                                            placeholder="select search" filterLocal={false}
                                            dataSource={this.state.routeDataSource}
                                            onSearch={this.handleRouteSearch}
                                            style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="routingPolicy"/>
                                </Col>
                            </Row>

                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="3" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.editApi.currentLimitPolicy']}
                                </Col>

                                <Col s="12" l="10">
                                    <FormBinder name="currentLimitPolicy">
                                        <Select showSearch
                                                placeholder="select search" filterLocal={false}
                                                dataSource={this.state.currentLimitDataSource}
                                                onSearch={this.handleCurrentLimitSearch}
                                                style={{width: '100%'}}/>
                                    </FormBinder>
                                    <FormError name="currentLimitPolicy"/>
                                </Col>
                            </Row>

                            <Row style={styles.btns}>
                                <Col xxs="6" s="2" l="2" style={styles.formLabel}>
                                    {' '}
                                </Col>
                                <Col s="12" l="10">
                                    <Button style={{marginRight: '20px'}} onClick={this.back}>
                                        {this.props.intl.messages['gateway.editApi.cancel']}
                                    </Button>

                                    <Button onClick={this.submit} type='secondary'>
                                        {this.props.intl.messages['gateway.editApi.yes']}
                                        {/* <Link to='/test/createInterfaceScripts'>
                    </Link>*/}
                                    </Button>
                                    <Button style={styles.resetBtn} onClick={this.reset}>
                                        {this.props.intl.messages['gateway.editApi.reset']}
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

export default injectIntl(EditApiForm);

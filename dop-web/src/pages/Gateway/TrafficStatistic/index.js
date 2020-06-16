import React, {Component} from 'react';
import {Table, Switch, Icon, Button, Grid, Pagination, Dialog, Select, Input, Feedback} from '@icedesign/base';
import IceContainer from '@icedesign/container';
import API from "../../API";
import Axios from "axios";
import {withRouter} from "react-router-dom";
import {injectIntl} from "react-intl";
import {FormBinder, FormBinderWrapper} from "@icedesign/form-binder";
import PieChart from './components/PieChart';
import 'echarts/lib/component/tooltip';

const Toast = Feedback.toast;
const {Row, Col} = Grid;


class TrafficStatistic extends Component {
    constructor(props) {
        super(props);
        this.state = {
            statistic: {
                successfulRequests: 0,
                failedRequests: 0,
                responseTime: 0,
                throughput: 0,
                errorRate: 0,
                highFrequencyApi: [],
                timeConsumingApi: [],
                callFailedApi: [],
            },
            searchValue: {
                time: 10080,
            },
        };
        this.doGetStatistic();
    }

    doGetStatistic = () => {
        let url = API.gateway + '/monitor/statistics';
        let _this = this;
        let time = this.state.searchValue.time;
        Axios.get(url, {
            params: {
                time: time,
            }
        }).then(function (response) {
            let statisticDto = response.data.body;
            if (statisticDto) {
                let statisticInfo = TrafficStatistic.doTrans(statisticDto);
                _this.setState(statisticInfo);
            } else {
                Toast.error("No statistic info!");
            }
        }).catch(function (error) {
            Toast.error(error);
        });
    };

    static doTrans(statisticDto) {
        return {
            statistic: {
                successfulRequests: statisticDto.successfulRequests,
                failedRequests: statisticDto.failedRequests,
                responseTime: statisticDto.responseTime,
                throughput: statisticDto.throughput,
                errorRate: statisticDto.errorRate,
                highFrequencyApi: statisticDto.highFrequencyApi,
                timeConsumingApi: statisticDto.timeConsumingApi,
                callFailedApi: statisticDto.callFailedApi,
            },
        };
    };

    formChange = (value) => {
        console.log('changed value', value);
        this.setState({
            formValue: value,
        });
    };

    render() {
        return (
            <div className="api-logs-page">

                <div>
                    <IceContainer title={this.props.intl.messages['gateway.trafficStatistic.tittle']}>
                        <FormBinderWrapper value={this.state.searchValue} onChange={this.formChange}>
                            <Row wrap>

                                <Col xxs="24" l="8" style={styles.formCol}>
                                    <span style={styles.label}>{this.props.intl.messages["gateway.search.time"]}</span>
                                    <FormBinder name="time">
                                        <Select placeholder={this.props.intl.messages["gateway.search.pleaseSelect"]}
                                                style={{width: '200px'}} defaultValue="all"
                                                onClose={this.doGetStatistic}>
                                            <Select.Option
                                                value="30">{this.props.intl.messages["gateway.search.time.thirtyMinute"]}</Select.Option>
                                            <Select.Option
                                                value="1440">{this.props.intl.messages["gateway.search.time.day"]}</Select.Option>
                                            <Select.Option
                                                value="10080">{this.props.intl.messages["gateway.search.time.week"]}</Select.Option>
                                        </Select>
                                    </FormBinder>
                                </Col>
                            </Row>
                        </FormBinderWrapper>
                    </IceContainer>

                    <Row>
                        <Col xxs="24" l="12" style={styles.formCol}>
                            <IceContainer title={this.props.intl.messages['gateway.trafficStatistic.requests']}>
                                <PieChart data={[
                                    {
                                        value: this.state.statistic.successfulRequests,
                                        name: this.props.intl.messages['gateway.trafficStatistic.successfulRequests']
                                    },
                                    {
                                        value: this.state.statistic.failedRequests,
                                        name: this.props.intl.messages['gateway.trafficStatistic.failRequests']
                                    },
                                ]}/>
                            </IceContainer>
                        </Col>

                        <Col xxs="24" l="6" style={styles.formCol}>
                            <IceContainer title={this.props.intl.messages['gateway.trafficStatistic.responseTime']}>
                                <span style={styles.content}>{this.state.statistic.responseTime}cpm</span>
                            </IceContainer>
                        </Col>
                    </Row>
                </div>


                <Row style={styles.table}>
                    <div>
                        <IceContainer title={this.props.intl.messages['gateway.trafficStatistic.highFrequencyApi']}>
                            <Table
                                dataSource={this.state.statistic.highFrequencyApi}
                            >
                                <Table.Column
                                    title={this.props.intl.messages["gateway.trafficStatistic.highFrequencyApi.name"]}
                                    dataIndex="apiName" width={100}/>
                                <Table.Column
                                    title={this.props.intl.messages["gateway.trafficStatistic.highFrequencyApi.path"]}
                                    dataIndex="apiPath" width={100}/>
                                <Table.Column
                                    title={this.props.intl.messages["gateway.trafficStatistic.highFrequencyApi.frequency"]}
                                    dataIndex="frequency" width={100}/>
                            </Table>
                        </IceContainer>
                    </div>


                    <div>
                        <IceContainer title={this.props.intl.messages['gateway.trafficStatistic.timeConsumingApi']}>
                            <Table
                                dataSource={this.state.statistic.timeConsumingApi}
                                size={'small'}
                            >
                                <Table.Column
                                    title={this.props.intl.messages["gateway.trafficStatistic.timeConsumingApi.path"]}
                                    dataIndex="apiPath" width={100}/>
                                <Table.Column
                                    title={this.props.intl.messages["gateway.trafficStatistic.timeConsumingApi.name"]}
                                    dataIndex="apiName" width={100}/>
                                <Table.Column
                                    title={this.props.intl.messages["gateway.trafficStatistic.timeConsumingApi.time"]}
                                    dataIndex="frequency" width={100}/>
                            </Table>
                        </IceContainer>
                    </div>

                    <div>
                        <IceContainer title={this.props.intl.messages['gateway.trafficStatistic.callFailedApi']}>
                            <Table
                                dataSource={this.state.statistic.callFailedApi}
                            >
                                <Table.Column
                                    title={this.props.intl.messages["gateway.trafficStatistic.callFailedApi.path"]}
                                    dataIndex="apiPath" width={100}/>
                                <Table.Column
                                    title={this.props.intl.messages["gateway.trafficStatistic.callFailedApi.name"]}
                                    dataIndex="apiName" width={100}/>
                                <Table.Column
                                    title={this.props.intl.messages["gateway.trafficStatistic.callFailedApi.time"]}
                                    dataIndex="frequency" width={100}/>
                            </Table>
                        </IceContainer>
                    </div>
                </Row>
            </div>
        );
    }
}

const styles = {
    container: {
        paddingBottom: 0,
    },
    table: {
        lineHeight: '28px',
        marginBottom: '1px',
    },
    formLabel: {
        textAlign: 'right',
    },
    formCol: {
        display: 'flex',
        alignItems: 'center',
        marginBottom: '20px',
    },
    label: {
        lineHeight: '28px',
        paddingRight: '10px',
    },
    content:{
        padding: '150px',
        textAlign: 'center',
    },
};

export default injectIntl(withRouter(TrafficStatistic));


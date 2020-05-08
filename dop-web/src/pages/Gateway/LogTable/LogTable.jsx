import React, {Component} from 'react';
import {Table, Switch, Icon, Button, Grid, Pagination, Dialog, DatePicker, Input, Feedback} from '@icedesign/base';
import IceContainer from '@icedesign/container';
import API from "../../API";
import Axios from "axios";
import {withRouter} from "react-router-dom";
import Balloon from "@alifd/next/lib/balloon";
import {injectIntl, FormattedMessage} from 'react-intl';
import {FormBinder, FormBinderWrapper} from "@icedesign/form-binder";


const {Row, Col} = Grid;
const Toast = Feedback.toast;

class LogTable extends Component {
    static displayName = 'CustomTable';

    static propTypes = {};

    static defaultProps = {};

    constructor(props) {
        super(props);
        this.state = {
            total: 1,
            showDetailLog: false,
            currentData: [{"id": "1", "state": false}],
            detailLogData: {},
            searchValue: {
                begin_time: '2020-01-01',
                end_time: '2020-06-01',
                status_code: 200,
            },
        };

        this.handlePaginationChange = this.handlePaginationChange.bind(this);
        this.refreshList(1);
    }

    onChange = (...args) => {

    };

    formChange = (value) => {
        console.log('changed value', value);
        this.setState({
            formValue: value,
        });
        this.refreshList(1);
    };

    handlePaginationChange = (current) => {
        this.refreshList(current);
    };

    refreshList = (current) => {
        if (!current) {
            current = 1;
        }
        let url = API.gateway + '/monitor/log';
        let begin_time = this.state.searchValue.begin_time;
        let end_time = this.state.searchValue.end_time;
        let status_code = this.state.searchValue.status_code;
        let _this = this;
        Axios.get(url, {
            params: {
                pageSize: 10,
                pageNo: current,
                begin_time: begin_time,
                end_time: end_time,
                status_code: status_code,
            }
        }).then(function (response) {
            _this.setState({
                current: current,
                total: response.data.body.totalCount,
                currentData: response.data.body.apiRequestLogs
            });
        }).catch(function (error) {
            console.log(error);
        });
    }

    renderOper = (value, index, record) => {
        let MoveTarget = <Icon
            type="search"
            size="small"
            style={{...styles.icon, ...styles.deleteIcon}}
            onClick={() => {
                this.setState({
                    showDetailLog: true,
                    detailLogData: record
                })
            }}
        />;

        return (
            <div style={styles.oper}>
                <Balloon.Tooltip trigger={MoveTarget} triggerType="hover" align='r'>
                    <FormattedMessage
                        id="gateway.logList.table.detail"
                        defaultMessage="查看日志详情"
                    />
                </Balloon.Tooltip>
            </div>
        );
    };

    onOk = () => {
    };

    onClose = () => {
        this.setState({
            showDetailLog: false
        })
    };

    render() {
        return (
            <div>
                <IceContainer title={this.props.intl.messages['gateway.logList.tittle']}>
                    <FormBinderWrapper value={this.state.searchValue} onChange={this.formChange}>
                        <Row wrap>
                            <Col xxs="24" l="8" style={styles.formCol}>
                                <span
                                    style={styles.label}>{this.props.intl.messages["gateway.search.time.beginTime"]}</span>
                                <FormBinder name="begin_time">
                                    <DatePicker format="YYYY-MM-DD"/>
                                </FormBinder>
                            </Col>

                            <Col xxs="24" l="8" style={styles.formCol}>
                                <span
                                    style={styles.label}>{this.props.intl.messages["gateway.search.time.endTime"]}</span>
                                <FormBinder name="end_time">
                                    <DatePicker format="YYYY-MM-DD"/>
                                </FormBinder>
                            </Col>

                            <Col xxs="24" l="8" style={styles.formCol}>
                                <span
                                    style={styles.label}>{this.props.intl.messages["gateway.search.statusCode"]}</span>
                                <FormBinder name="status_code">
                                    <Input/>
                                </FormBinder>
                            </Col>
                        </Row>
                    </FormBinderWrapper>
                    <Dialog
                        visible={this.state.showDetailLog}
                        footerActions={['cancel']}
                        footerAlign={'center'}
                        onOk={this.onClose}
                        onCancel={this.onClose}
                        onClose={this.onClose}
                        hasMask={false}
                        title={this.props.intl.messages["gateway.logList.table.detail"]}
                        isFullScreen
                    >
                        <div style={styles.formContent}>
                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="9" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.logList.table.detail.logId']}
                                </Col>

                                <Col wrap s="12" l="12">
                                    {this.state.detailLogData.logId}
                                </Col>
                            </Row>
                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="9" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.logList.table.detail.requestMethod']}
                                </Col>

                                <Col s="12" l="10">
                                    {this.state.detailLogData.requestMethod}
                                </Col>
                            </Row>
                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="9" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.createApi.requestPath']}
                                </Col>

                                <Col s="12" l="10">
                                    {this.state.detailLogData.path}
                                </Col>
                            </Row>
                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="9" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.logList.table.detail.requestSize']}
                                </Col>

                                <Col s="12" l="10">
                                    {this.state.detailLogData.requestSize}
                                </Col>
                            </Row>
                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="9" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.logList.table.detail.statusCode']}
                                </Col>

                                <Col s="12" l="10">
                                    {this.state.detailLogData.statusCode}
                                </Col>
                            </Row>
                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="9" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.logList.table.detail.timeConsuming']}
                                </Col>

                                <Col s="12" l="10">
                                    {this.state.detailLogData.timeConsuming}
                                </Col>
                            </Row>
                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="9" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.logList.table.detail.responseTimeout']}
                                </Col>

                                <Col s="12" l="10">
                                    {this.state.detailLogData.responseTimeout}
                                </Col>
                            </Row>
                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="9" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.logList.table.detail.requestSize']}
                                </Col>

                                <Col s="12" l="10">
                                    {this.state.detailLogData.responseSize}
                                </Col>
                            </Row>
                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="9" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.logList.table.detail.time']}
                                </Col>

                                <Col s="12" l="10">
                                    {this.state.detailLogData.time}
                                </Col>
                            </Row>
                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="9" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.logList.table.detail.clientIP']}
                                </Col>

                                <Col s="12" l="10">
                                    {this.state.detailLogData.clientIP}
                                </Col>
                            </Row>
                            <Row style={styles.formItem}>
                                <Col xxs="6" s="2" l="9" style={styles.formLabel}>
                                    {this.props.intl.messages['gateway.logList.table.detail.clientIP']}
                                </Col>

                                <Col s="12" l="10">
                                    {this.state.detailLogData.serviceId}
                                </Col>
                            </Row>
                        </div>
                    </Dialog>

                    <Table
                        dataSource={this.state.currentData}
                    >
                        <Table.Column title={this.props.intl.messages["gateway.logList.table.logId"]}
                                      dataIndex="logId" width={100}/>
                        <Table.Column title={this.props.intl.messages["gateway.logList.table.path"]}
                                      dataIndex="path" width={200}/>
                        <Table.Column title={this.props.intl.messages["gateway.logList.table.statusCode"]}
                                      dataIndex="statusCode" width={100}/>
                        <Table.Column title={this.props.intl.messages["gateway.logList.table.timeConsuming"]}
                                      dataIndex="timeConsuming" width={75}/>
                        <Table.Column title={this.props.intl.messages["gateway.logList.table.time"]}
                                      dataIndex="time" width={75}/>
                        <Table.Column title={this.props.intl.messages["gateway.logList.table.operations"]} width={100}
                                      cell={this.renderOper}/>
                    </Table>
                    <Pagination
                        style={styles.pagination}
                        current={this.state.current}
                        onChange={this.handlePaginationChange}
                        total={this.state.total}
                    />
                </IceContainer>
            </div>
        );
    }
}

const styles = {
    headRow: {
        marginBottom: '10px',
    },
    icon: {
        color: '#2c72ee',
        cursor: 'pointer',
    },
    infoIcon: {
        marginLeft: '20px',
    },
    deleteIcon: {
        marginLeft: '20px',
    },
    editIcon: {
        marginLeft: '20px',
    },
    center: {
        textAlign: 'right',
    },
    button: {
        borderRadius: '4px',
        color: '#5485F7'
    },
    pagination: {
        marginTop: '20px',
        textAlign: 'right',
    },
    formRow: {
        marginBottom: '18px',
    },
    formCol: {
        display: 'flex',
        alignItems: 'center',
        marginBottom: '20px',
    },
    label: {
        lineHeight: '28px',
        paddingRight: '10px',
        textAlign: 'right',
    },
    formContent: {
        width: '100%',
        position: 'relative',
    },
    formItem: {
        alignItems: 'center',
        marginBottom: 25,
    },
    formTitle: {
        margin: '0 0 20px',
        paddingBottom: '10px',
        borderBottom: '1px solid #eee',
    },
};

export default injectIntl(withRouter(LogTable));

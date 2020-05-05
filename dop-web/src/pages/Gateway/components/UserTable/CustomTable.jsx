/* eslint-disable react/no-unused-state, no-plusplus */
import React, {Component} from 'react';
import {Table, Switch, Icon, Button, Grid, Pagination, Dialog, Select, Input, Feedback} from '@icedesign/base';
import IceContainer from '@icedesign/container';
import API from "../../../API";
import Axios from "axios";
import {Link} from "react-router-dom";
import {FormBinder, FormBinderWrapper} from "@icedesign/form-binder";
import {withRouter} from "react-router-dom";
import Balloon from "@alifd/next/lib/balloon";
import {injectIntl, FormattedMessage} from 'react-intl';

const {Row, Col} = Grid;
const Toast = Feedback.toast;

class CustomTable extends Component {
    static displayName = 'CustomTable';

    static propTypes = {};

    static defaultProps = {};

    constructor(props) {
        super(props);
        this.state = {
            formValue: {},
            current: 1,
            createdCaseNeedRefresh: false,
            createManualDialogVisiable: false,
            isSubmit: false,
            total: 1,
            currentData: [{"id": "1", "state": false}],
            searchValue: {
                owner: '',
                type: 'All',
            },
        };

        this.handlePaginationChange = this.handlePaginationChange.bind(this);
        this.refreshList(1);
    }

    formChange = (value) => {
        console.log('changed value', value);
        this.setState({
            formValue: value,
        });
    };

    onChange = (...args) => {

    };

    handlePaginationChange = (current) => {
        this.refreshList(current);
    };

    refreshList(current) {
        if (!current) {
            current = 1;
        }
        let url = API.gateway + '/lifeCycle';
        let type = this.state.searchValue.type;
        let _this = this;
        Axios.get(url, {
            params: {
                pageSize: 10,
                pageNo: current,
                apiType: type,
            }
        }).then(function (response) {
            _this.setState({
                current: current,
                total: response.data.body.totalCount,
                currentData: response.data.body.apiList
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
                this.props.history.push('/gateway/showLogs/' + record.id);
            }}
        />;

        let deleteApi = <Icon
            type="close"
            size="small"
            style={{...styles.icon, ...styles.deleteIcon}}
            onClick={() => {
                this.delete(record.id);
            }}
        />;

        let edit = <Icon
            type="edit"
            size="small"
            style={{...styles.icon, ...styles.editIcon}}
            onClick={() => {
                this.props.history.push('/gateway/editApi/' + record.id);
            }}
        />;
        return (
            <div style={styles.oper}>
                <Balloon.Tooltip trigger={edit} triggerType="hover" align='l'>
                    <FormattedMessage
                        id="gateway.apiLists.table.edit"
                        defaultMessage="编辑api"
                    />
                </Balloon.Tooltip>
                <Balloon.Tooltip trigger={MoveTarget} triggerType="hover" align='r'>
                    <FormattedMessage
                        id="gateway.apiLists.table.logs"
                        defaultMessage="查看日志"
                    />
                </Balloon.Tooltip>
                <Balloon.Tooltip trigger={deleteApi} triggerType="hover" align='r'>
                    <FormattedMessage
                        id="gateway.apiLists.table.delete"
                        defaultMessage="删除"
                    />
                </Balloon.Tooltip>
            </div>
        );
    };

    renderSwitch = (value, index, record) => {
        let apiId = record.id;
        let state = record.state;
        return <Switch defaultChecked={state} onChange={(checked) => {
            if (checked) {
                Toast.success(<FormattedMessage
                    id='gateway.apiLists.table.message.inOnline'
                    defaultMessage="api开始上线"
                />);
                this.online(apiId);
            } else {
                Toast.success(<FormattedMessage
                    id='gateway.apiLists.table.message.inOffline'
                    defaultMessage="api开始下线"
                />);
                this.offline(apiId);
            }
        }
        }/>;
    };

    onOpen = () => {
        this.setState({
            createManualDialogVisiable: true
        })
    };

    onClose = () => {
        this.setState({
            createManualDialogVisiable: false,
            isSubmit: false
        })
    };

    onOk = () => {
        this.setState({
            isSubmit: true
        })
    };

    online = (id) => {
        let url = API.gateway + '/lifeCycle/online/' + id;
        let _this = this;
        Axios.put(url).then(function (response) {
            console.log(response);
            Toast.success(<FormattedMessage
                id='gateway.apiLists.table.message.inOnline'
                defaultMessage="api成功上线"
            />);
        }).catch(function (error) {
            console.log(error);
        });
    };

    offline = (id) => {
        // only interface script is executable
        let url = API.gateway + '/lifeCycle/offline/' + id;
        let _this = this;
        Axios.put(url).then(function (response) {
            console.log(response);
            Toast.success(<FormattedMessage
                id='gateway.apiLists.table.message.inOnline'
                defaultMessage="api成功下线"
            />);
        }).catch(function (error) {
            console.log(error);
        });
    };

    delete = (id) => {
        let url = API.gateway + '/lifeCycle/' + id;
        let _this = this;
        if (id !== '') {
            Toast.loading(<FormattedMessage
                id='gateway.apiLists.table.message.delete.loading'
                defaultMessage={_this.props.intl.messages["gateway.apiLists.table.message.delete.loading"]}
            />);
            Axios.delete(url).then(function (response) {
                console.log(response);
                if (response.data.code === 0) {
                    Toast.success(<FormattedMessage
                        id='gateway.apiLists.table.message.delete.success'
                        defaultMessage={_this.props.intl.messages["gateway.apiLists.table.message.delete.success"]}
                    />);
                    _this.refreshList(_this.state.current);
                } else {
                    Toast.error(<FormattedMessage
                        id='gateway.apiLists.table.message.delete.fail'
                        defaultMessage={_this.props.intl.messages["gateway.apiLists.table.message.delete.fail"]}
                    />);
                }
            }).catch(function (error) {
                console.log(error);
                Toast.error(<FormattedMessage
                    id='gateway.apiLists.table.message.delete.fail'
                    defaultMessage={_this.props.intl.messages["gateway.apiLists.table.message.delete.fail"]}
                />);
            });
        }
    };

    render() {
        const {searchValue} = this.state.searchValue;

        return (
            <div>
                <IceContainer title={this.props.intl.messages["gateway.search.searchTitle"]}>
                    <FormBinderWrapper value={this.state.searchValue} onChange={this.formChange}>
                        <Row wrap>

                            <Col xxs="24" l="8" style={styles.formCol}>
                                <span style={styles.label}>{this.props.intl.messages["gateway.search.apiType"]}</span>
                                <FormBinder name="type">
                                    <Select placeholder={this.props.intl.messages["gateway.search.pleaseSelect"]}
                                            style={{width: '200px'}} defaultValue="all"
                                            onClose={this.refreshList.bind(this, 1)}>
                                        <Select.Option
                                            value="online">{this.props.intl.messages["gateway.search.apiType.online"]}</Select.Option>
                                        <Select.Option
                                            value="offline">{this.props.intl.messages["gateway.search.apiType.offline"]}</Select.Option>
                                        <Select.Option
                                            value="all">{this.props.intl.messages["gateway.search.apiType.all"]}</Select.Option>
                                    </Select>
                                </FormBinder>
                            </Col>

                            <Col xxs="24" l="8" style={styles.formCol}>
                                <span style={styles.label}>{this.props.intl.messages["gateway.search.creator"]}</span>
                                <FormBinder name="owner">
                                    <Input/>
                                </FormBinder>
                            </Col>
                        </Row>
                    </FormBinderWrapper>
                </IceContainer>

                <IceContainer title={this.props.intl.messages['gateway.apiLists.title']}>
                    <Row wrap style={styles.headRow}>
                        <Col l="12">

                            <Button style={{...styles.button, marginLeft: 10}}>
                                <Link to="/gateway/createApi">
                                    <Icon type="add" size="xs" style={{marginRight: '4px'}}/>
                                    {this.props.intl.messages["gateway.apiLists.add.api"]}
                                </Link>
                            </Button>
                        </Col>
                    </Row>

                    <Table
                        dataSource={this.state.currentData}
                    >
                        <Table.Column title={this.props.intl.messages["gateway.apiLists.table.apiId"]}
                                      dataIndex="id" width={100}/>
                        <Table.Column title={this.props.intl.messages["gateway.apiLists.table.name"]}
                                      dataIndex="name" width={100}/>
                        <Table.Column title={this.props.intl.messages["gateway.apiLists.table.path"]}
                                      dataIndex="path" width={200}/>
                        <Table.Column title={this.props.intl.messages["gateway.apiLists.table.creator"]}
                                      dataIndex="createUserName" width={100}/>
                        <Table.Column title={this.props.intl.messages["gateway.apiLists.table.status"]}
                                      dataIndex="health" width={75}/>
                        <Table.Column title={this.props.intl.messages["gateway.apiLists.table.online"]} width={75}
                                      cell={this.renderSwitch}/>
                        <Table.Column title={this.props.intl.messages["gateway.apiLists.table.operations"]} width={100}
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
    },
};

export default injectIntl(withRouter(CustomTable));

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

class CurrentLimitTable extends Component {
    static displayName = 'CurrentLimitTable';

    static propTypes = {};

    static defaultProps = {};

    constructor(props) {
        super(props);
        this.state = {
            formValue: {},
            current: 1,
            isSubmit: false,
            total: 1,
            currentData: [{"policyId": "1"}],
            searchValue: {
                owner: '',
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
        let url = API.gateway + '/policy/flowControl/currentLimit';
        let _this = this;
        Axios.get(url, {
            params: {
                pageSize: 10,
                pageNo: current,
            }
        }).then(function (response) {
            _this.setState({
                current: current,
                total: response.data.body.totalCount,
                currentData: response.data.body.currentLimitPolicyList.map(item => ({
                    name: item.name,
                    policyId: item.policyId,
                    description: item.description,
                }))
            });
        }).catch(function (error) {
            console.log(error);
        });
    };

    renderOper = (value, index, record) => {
        let MoveTarget = <Icon
            type="close"
            size="small"
            style={{...styles.icon, ...styles.deleteIcon}}
            onClick={() => {
                this.delete(record.policyId);
            }}
        />;


        let edit = <Icon
            type="edit"
            size="small"
            style={{...styles.icon, ...styles.editIcon}}
            onClick={() => {
                this.props.history.push('/gateway/currentLimit/editCurrentLimitPolicy/' + record.policyId);
            }}
        />;
        return (
            <div style={styles.oper}>
                <Balloon.Tooltip trigger={edit} triggerType="hover" align='l'>
                    <FormattedMessage
                        id="gateway.currentLimitList.table.edit"
                        defaultMessage="编辑"
                    />
                </Balloon.Tooltip>
                <Balloon.Tooltip trigger={MoveTarget} triggerType="hover" align='r'>
                    <FormattedMessage
                        id="gateway.currentLimitList.table.delete"
                        defaultMessage="删除"
                    />
                </Balloon.Tooltip>
            </div>
        );
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

    delete = (policyId) => {
        let url = API.gateway + '/policy/flowControl/currentLimit/' + policyId;
        let _this = this;
        if (policyId !== '') {
            Toast.loading(<FormattedMessage
                id='gateway.currentLimitList.table.message.delete.loading'
                defaultMessage={_this.props.intl.messages["gateway.currentLimitList.table.message.delete.loading"]}
            />);
            Axios.delete(url).then(function (response) {
                console.log(response);
                if (response.data.code === 0) {
                    Toast.success(<FormattedMessage
                        id='gateway.currentLimitList.table.message.delete.success'
                        defaultMessage={_this.props.intl.messages["gateway.currentLimitList.table.message.delete.success"]}
                    />);
                    _this.refreshList(_this.state.current);
                } else {
                    Toast.error(<FormattedMessage
                        id='gateway.currentLimitList.table.message.delete.success'
                        defaultMessage={_this.props.intl.messages["gateway.currentLimitList.table.message.delete.fail"]}
                    />);
                }
            }).catch(function (error) {
                console.log(error);
                Toast.error(<FormattedMessage
                    id='gateway.currentLimitList.table.message.delete.fail'
                    defaultMessage={_this.props.intl.messages["gateway.currentLimitList.table.message.delete.fail"]}
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
                                <span style={styles.label}>{this.props.intl.messages["gateway.search.creator"]}</span>
                                <FormBinder name="owner">
                                    <Input/>
                                </FormBinder>
                            </Col>
                        </Row>
                    </FormBinderWrapper>
                </IceContainer>

                <IceContainer title={this.props.intl.messages['gateway.currentLimitList.title']}>
                    <Row wrap style={styles.headRow}>
                        <Col l="12">
                            <Button style={{...styles.button, marginLeft: 10}}>
                                <Link to="/gateway/currentLimit/createCurrentLimitForm">
                                    <Icon type="add" size="xs" style={{marginRight: '4px'}}/>
                                    {this.props.intl.messages["gateway.currentLimitList.add"]}
                                </Link>
                            </Button>
                        </Col>
                    </Row>

                    <Table
                        dataSource={this.state.currentData}
                    >
                        <Table.Column title={this.props.intl.messages["gateway.routeList.table.policyId"]}
                                      dataIndex="policyId" width={100}/>
                        <Table.Column title={this.props.intl.messages["gateway.routeList.table.name"]}
                                      dataIndex="name" width={100}/>
                        <Table.Column title={this.props.intl.messages["gateway.routeList.table.description"]}
                                      dataIndex="description" width={200}/>
                        <Table.Column title={this.props.intl.messages["gateway.apiLists.table.creator"]}
                                      dataIndex="creator" width={100}/>
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

export default injectIntl(withRouter(CurrentLimitTable));

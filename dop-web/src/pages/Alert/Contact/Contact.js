import React, {Component} from 'react';
import { Button, Table, Icon, Pagination,Balloon  } from "@icedesign/base";
import IceContainer from '@icedesign/container';
import {injectIntl} from "react-intl";
import ContactInfo from './ContactInfo'
import Axios from "axios/index";
import API from "../../API";

import {Grid} from "@icedesign/base/index";


const { Row, Col } = Grid;
class Contact extends Component {

    constructor(props) {
        super(props);
        this.state = {
            visible: false,
            formValue: {},
            current: 1,
            createdCaseNeedRefresh: false,
            createManualDialogVisiable: false,
            isSubmit: false,
            total: 1,
        };

        this.handlePaginationChange = this.handlePaginationChange.bind(this);
        this.refreshList(1);
    }

    handlePaginationChange = (current) => {
        this.refreshList(current);
    };

    refreshList(current) {
        if (!current) {
            current = 1;
        }
        let url = API.alert + '/alert/contact/page';
        let _this = this;
        Axios.get(url, {
            params: {
                pageSize: 10,
                pageNo: current
            }
        }).then(function (response) {
            _this.setState({
                current: current,
                total: response.data.totalCount,
                currentData: response.data.pageList
            });
        }).catch(function (error) {
            console.log(error);
        });
    }

    onOpen = () =>{

        this.setState({
            visible: true,
        });
    };


    renderOper = (value, index, record) => {
        let edit = <Icon
            type="edit"
            size="small"
            style={{...styles.icon, ...styles.editIcon}}
            onClick={() => {
                this.props.history.push('/alert/editContact/' + record.id);
            }}
        />;
        return (
            <div >
                <Balloon.Tooltip trigger={edit} triggerType="hover" align='r' style={{marginLeft: 30, width: 30}}>
                    {this.props.intl.messages['alert.contact.table.edit']}
                </Balloon.Tooltip>
            </div>
        );
    };

    render() {
        return (
            <div>

                <IceContainer title={this.props.intl.messages['alert.contact.table.title']}>
                    <Row wrap style={styles.headRow}>
                        <Col l="12">
                            <ContactInfo
                                // refresh={this.refreshList()}
                                visible={this.state.visible}
                                title={this.props.intl.messages['alert.newContact.title']}
                            />
                            <Button style={styles.button} onClick={this.onOpen.bind(this)} >
                                <Icon type="add" size="xs" style={{ marginRight: '4px' }} />{this.props.intl.messages['alert.contact.table.createContact']}
                            </Button>

                        </Col>
                        <Col l="12" style={styles.center}>
                            <Button type="normal" style={styles.button}>
                                {this.props.intl.messages['alert.contact.table.delete']}
                            </Button>
                        </Col>
                    </Row>

                    <Table
                        dataSource={this.state.currentData}
                        rowSelection={{ onChange: this.onChange }}
                    >
                        <Table.Column title={this.props.intl.messages['alert.contact.table.name']} dataIndex="name" width={120} />
                        <Table.Column title={this.props.intl.messages['alert.contact.table.mail']} dataIndex="mail" width={120} />
                        <Table.Column title={this.props.intl.messages['alert.contact.table.phone']} dataIndex="phone" width={120} />
                        <Table.Column title={this.props.intl.messages['alert.contact.table.remark']} dataIndex="remark" width={120} />
                        <Table.Column title={this.props.intl.messages['alert.contact.table.mtime']} dataIndex="mtime" width={100} />
                        <Table.Column title={this.props.intl.messages['alert.contact.table.oper']} width={50} cell={this.renderOper} />

                    </Table>
                    <Pagination
                        style={styles.pagination}
                        current={this.state.current}
                        onChange={this.handlePaginationChange}
                        total={this.state.total}
                    />
                </IceContainer>
            </div>
        )
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
    deleteIcon: {
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
export default injectIntl(Contact)
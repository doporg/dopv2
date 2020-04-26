import React, {Component} from 'react';
import { Button, Table, Icon, Pagination,Balloon  } from "@icedesign/base";
import IceContainer from '@icedesign/container';
import {injectIntl} from "react-intl";
import Axios from "axios/index";
import API from "../../API";

import {Grid} from "@icedesign/base/index";

const { Row, Col } = Grid;
class AlertLog extends Component {


    render() {
        return (
            <div>
                <IceContainer title={this.props.intl.messages['alert.contact.table.title']}>
                    <Row wrap style={styles.headRow}>
                        <Col l="12">
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
export default injectIntl(AlertLog)
import React, {Component} from 'react';
import {Button, Icon, Pagination, Table, Tag, Input} from "@icedesign/base";
import {injectIntl} from "react-intl";
import "./User.scss"


class User extends Component {
    constructor(prop) {
        super(prop);
        this.state = {
            dataSource: [
                {
                    id: 100,
                    name: "admin",
                    account: "admin",
                    role: "管理员" //用户
                },{
                    id: 101,
                    name: "user",
                    account: "user",
                    role: "用户"
                }
            ]

        }
    }

    componentWillMount() {

    }

    roleRender(value) {
        return (
            <Tag shape="readonly" size="small">{value}</Tag>
        )
    }


    operationRender(value, index, record) {
        return (
            <Button.Group size="small">
                <Button type="secondary"  ><Icon type="form"/>编辑</Button>
                <Button type="normal" className="button-warning" ><Icon type="ashbin"/>删除</Button>
            </Button.Group>
        )
    }

    onChange(currentPage) {
    }

    render() {
        return (
            <div className="user-wrapper">
                <Input
                    trim
                    value={this.state.searchValue}
                    // onChange={this.searchOnChange.bind(this)}
                    placeholder="查找名称且无法输入空格哦！"
                />
                &nbsp;&nbsp;
                <Button
                    type="primary"
                    // onClick={this.searchSubmit.bind(this)}
                >
                    <Icon type="search"/> 搜索
                </Button>
                &nbsp;&nbsp;&nbsp;
                <Button
                    type="primary"
                    // onClick={this.addSubmit.bind(this)}
                >
                    <Icon type="add"/> 添加
                </Button><br/><br/>
                <Table dataSource={this.state.dataSource}>
                    <Table.Column title="Id"  dataIndex="id"/>
                    <Table.Column title="用户名"  dataIndex="name"/>
                    <Table.Column title="账号"  dataIndex="account"/>
                    <Table.Column title="角色" cell={this.roleRender.bind(this)} dataIndex="role"/>
                    <Table.Column title="操作" cell={this.operationRender.bind(this)}/>
                </Table><br/>
                <Pagination onChange={this.onChange.bind(this)} className="page-demo"/>
            </div>
        )
    }
}

export default injectIntl(User)

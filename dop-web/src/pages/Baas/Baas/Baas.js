import React, {Component} from 'react';
import {Button, Icon, Pagination, Table, Tag, Input} from "@icedesign/base";
import {injectIntl} from "react-intl";
import "./Baas.scss"


class Baas extends Component {
    constructor(prop) {
        super(prop);
        this.state = {
            allDataSource: [],
            currentDataSource: [],
            loading: false,
            searchValue: ""
        }
    }

    componentWillMount() {
        this.getNetwork();
        setTimeout(()=>{
            this.getPageData()
        },0)
    }
    getNetwork(){
        let allDataSource = [ {
            id: 100,
            name: `第一个网络`,
            consensus: `solo`,
            org: [{
                orgName: "org1",
                peers: [{
                    peerName: "peer0-org1",
                    ports: ["8051", "8052", "8053", "9443"]
                }]
            }, {
                orgName: "org2",
                peers: [{
                    peerName: "peer0-org2",
                    ports: ["8051", "8052", "8053", "9443"]
                }]
            }],
            tls: true,
            order: [{
                orderName: "order0",
                orderPort: ["8050"]
            }],
            time: "2020-02-02",
            state: "0" //已停止//错误
            // 0-已停止
            // 1-运行中
            // 2-构建中
            // 3-有错误
        },
            {
                id: 101,
                name: `第二个网络`,
                consensus: `etcdraft`,
                org: [{
                    orgName: "org1",
                    peers: [{
                        peerName: "peer0-org1",
                        ports: ["8051", "8052", "8053", "9443"]
                    }]
                }, {
                    orgName: "org2",
                    peers: [{
                        peerName: "peer0-org2",
                        ports: ["8051", "8052", "8053", "9443"]
                    }]
                }],
                tls: true,
                order: [{
                    orderName: "order0",
                    orderPort: ["8050"]
                }],
                time: "2020-02-02",
                state: "1"//错误
            },
            {
                id: 102,
                name: `第三个网络`,
                consensus: `etcdraft`,
                org: [{
                    orgName: "org1",
                    peers: [{
                        peerName: "peer0-org1",
                        ports: ["8051", "8052", "8053", "9443"]
                    }]
                }, {
                    orgName: "org2",
                    peers: [{
                        peerName: "peer0-org2",
                        ports: ["8051", "8052", "8053", "9443"]
                    }]
                }],
                tls: true,
                order: [{
                    orderName: "order0",
                    orderPort: ["8050"]
                }],
                time: "2020-02-02",
                state: "2"
            },
            {
                id: 103,
                name: `第四个网络`,
                consensus: `etcdraft`,
                org: [{
                    orgName: "org1",
                    peers: [{
                        peerName: "peer0-org1",
                        ports: ["8051", "8052", "8053", "9443"]
                    }]
                }, {
                    orgName: "org2",
                    peers: [{
                        peerName: "peer0-org2",
                        ports: ["8051", "8052", "8053", "9443"]
                    }]
                }],
                tls: true,
                order: [{
                    orderName: "order0",
                    orderPort: ["8050"]
                }],
                time: "2020-02-02",
                state: "3"
            }];
        this.setState({
            allDataSource
        })
    }
    getPageData() {
        let self = this
        this.setState({
            currentDataSource: self.state.allDataSource.slice(0, 5)
        })
    };

    orgRender(value) {
        return (
            value.map((item) => {
                return (
                    <Tag shape="readonly" size="small">{item.orgName}</Tag>
                )
            })
        )
    }
    tlsRender(value) {
        if (value) {
            return "是"
        } else {
            return "否"
        }

    }

    orderRender(value) {
        return (
            value.map((item) => {
                return (
                    <Tag shape="readonly" size="small">{item.orderName}</Tag>
                )
            })
        )
    }

    stateRender(value) {
        if (value === "1") {
            return (
                <div>
                    <Tag shape="readonly" type="secondary" size="small" className="tag-run">运行中</Tag>
                </div>

            )
        } else if (value === "0") {
            return (
                <Tag shape="readonly" type="secondary" size="small" className="tag-stop">已停止</Tag>
            )
        } else if (value === "2") {
            return (
                <Tag shape="readonly" type="secondary" size="small" className="tag-build">构建中</Tag>
            )
        } else {
            return (
                <Tag shape="readonly" type="secondary" size="small" className="tag-error">有错误</Tag>
            )
        }
    }
    networkDelete(index, record){
        let currentDataSource = this.state.currentDataSource;
        currentDataSource.splice(index, 1);
        this.setState({
            currentDataSource
        });
        console.log(record.id)
        //TODO: 发送删除请求
    }
    networkStart(record){
        console.log(record);
        console.log(record.id)
        //TODO: 发送启动网络请求
    }
    networkStop(record){
        console.log(record);
        console.log(record.id)
        //TODO: 发送启动网络请求
    }
    networkCheck(record){
        console.log(record);
        console.log(record.id)
        //TODO: 发送启动网络请求
        this.props.history.push('/baas/monitor/'+record.id)
    }

    operationRender(value, index, record) {
        let self = this;
        if (record.state === "0") {
            return (
                <Button.Group size="small">
                    <Button type="secondary" onClick={this.networkCheck.bind(this, record)} ><Icon type="form"/>查看</Button>
                    <Button type="normal" className="button-start" onClick={this.networkStart.bind(this, record)}><Icon type="play"/>启动</Button>
                    <Button type="normal" className="button-warning" onClick={this.networkDelete.bind(this, index, record)}><Icon type="ashbin"/>删除</Button>
                </Button.Group>
            )
        } else if (record.state === "2") {
            return (
                <Button.Group size="small">
                    <Button type="secondary" onClick={this.networkCheck.bind(this, record)}><Icon type="form"/>查看</Button>
                    <Button type="normal" disabled><Icon type="play"/>启动</Button>
                    <Button type="normal" className="button-warning" onClick={this.networkDelete.bind(this, index, record)}><Icon type="ashbin"/>删除</Button>
                </Button.Group>
            )
        } else if (record.state === "1") {
            return (
                <Button.Group size="small">
                    <Button type="secondary" onClick={this.networkCheck.bind(this, record)}><Icon type="form"/>查看</Button>
                    <Button type="normal" className="button-start" onClick={this.networkStop.bind(this, record)} ><Icon type="stop"/>停止</Button>
                    <Button type="normal" className="button-warning" onClick={this.networkDelete.bind(this, index, record)}><Icon type="ashbin"/>删除</Button>
                </Button.Group>
            )
        } else {
            //有错误
            return (
                <Button.Group size="small">
                    <Button type="secondary" onClick={this.networkCheck.bind(this, record)}><Icon type="form"/>查看</Button>
                    <Button type="normal"  disabled><Icon type="play"/>启动</Button>
                    <Button type="normal" className="button-warning" onClick={this.networkDelete.bind(this, index, record)}><Icon type="ashbin"/>删除</Button>
                </Button.Group>
            )
        }

    }

    onChange(currentPage) {
        let self = this;
        self.setState({
            loading: true
        });
        setTimeout(() => {
            self.setState({
                currentDataSource: self.state.allDataSource.slice((currentPage - 1) * 5, (currentPage - 1) * 5 + 5),
                loading: false
            });
        }, 200);
    }

    searchOnChange(value) {
        this.setState({
            searchValue: value
        });
    }

    searchSubmit() {
        let self = this;
        let allDataSource = self.state.allDataSource;
        let result = [];
        for (let i = 0; i < allDataSource.length; i++) {
            if (allDataSource[i].name.indexOf(self.state.searchValue) !== -1) {
                result.push(allDataSource[i])
            }
        }
        this.setState({
            currentDataSource: result
        })
    }
    addSubmit(){
        this.props.history.push("/baas/add")
    }

    render() {
        return (
            <div className="baas-wrapper">
                <Input
                    trim
                    value={this.state.searchValue}
                    onChange={this.searchOnChange.bind(this)}
                    placeholder="查找名称且无法输入空格哦！"
                />
                &nbsp;&nbsp;
                <Button
                    type="primary"
                    onClick={this.searchSubmit.bind(this)}
                >
                    <Icon type="search"/> 搜索
                </Button>
                &nbsp;&nbsp;&nbsp;
                <Button
                    type="primary"
                    onClick={this.addSubmit.bind(this)}
                >
                    <Icon type="add"/> 添加
                </Button><br/><br/>
                <Table dataSource={this.state.currentDataSource}>
                    <Table.Column title="Id" width="5%" dataIndex="id"/>
                    <Table.Column title="网络名称" width="10%" dataIndex="name"/>
                    <Table.Column title="共识" width="10%" dataIndex="consensus"/>
                    <Table.Column title="排序节点" cell={this.orderRender.bind(this)} dataIndex="order"/>
                    <Table.Column title="组织" cell={this.orgRender.bind(this)} dataIndex="org"/>
                    <Table.Column title="开启TLS" cell={this.tlsRender.bind(this)} width="7%" dataIndex="tls"/>
                    <Table.Column title="创建时间" width="9%" dataIndex="time"/>
                    <Table.Column title="状态" width="9%" cell={this.stateRender.bind(this)} dataIndex="state"/>
                    <Table.Column title="操作" width="20%" cell={this.operationRender.bind(this)}/>
                </Table><br/>
                <Pagination onChange={this.onChange.bind(this)} className="page-demo"/>
            </div>
        )
    }
}

export default injectIntl(Baas)

import React, {Component} from 'react';
import {
    Button,
    Icon,
    Pagination,
    Table,
    Tag,
    Input,
    Dialog,
    Select,
    Form, Grid
} from "@icedesign/base";
import {injectIntl} from "react-intl";
import "./Channel.scss"

const {Combobox, Option, OptionGroup} = Select;

class Channel extends Component {
    constructor(prop) {
        super(prop);
        this.state = {
            allChannels: [],
            currentChannels: [],
            networks: [],
            loading: false,
            searchValue: "",
            visible: false,
            selectedNetworkId: null,
            selectedNetwork: null,
            selectedPeer: null,
            newChannelName: null
        }
    }

    componentWillMount() {
        this.getChannels();
        this.getNetwork();
        setTimeout(() => {
            this.getPageData();
        }, 0);
    }

    getChannels() {
        let allChannels = [
            {
                id: 100,
                name: `第一个通道`,
                peers: ["peer0-org1", "peer1-org1"],
                networkId: 100
            }, {
                id: 101,
                name: `第二个通道`,
                peers: ["peer0-org1", "peer1-org1"],
                networkId: 101
            }, {
                id: 102,
                name: `第三个通道`,
                peers: ["peer0-org1", "peer1-org1"],
                networkId: 101
            },
        ];
        this.setState({
            allChannels
        })
    }

    getNetwork() {
        let networks = [
            {
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
                    },{
                        peerName: "peer1-org1",
                        ports: ["8051", "8052", "8053", "9443"]
                    }]
                }, {
                    orgName: "org2",
                    peers: [{
                        peerName: "peer0-org2",
                        ports: ["8051", "8052", "8053", "9443"]
                    },]
                },{
                    orgName: "org3",
                    peers: [{
                        peerName: "peer0-org3",
                        ports: ["8051", "8052", "8053", "9443"]
                    },]
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
            networks: networks
        })
    }

    getPageData() {
        let self = this;
        this.setState({
            currentChannels: self.state.allChannels.slice(0, 5)
        })
    };


    peerRender(value) {
        return (
            value.map((item) => {
                return (
                    <Tag shape="readonly" size="small">{item}</Tag>
                )
            })
        )
    }

    channelDelete(index, record) {
        let currentChannels = this.state.currentChannels;
        currentChannels.splice(index, 1);
        this.setState({
            currentChannels
        });
        console.log(record.id)
        //TODO: 发送删除请求
    }

    installChainCode() {
        this.props.history.push("/baas/chaincode")
    }

    channelCheck(record) {
        console.log(record);
        console.log(record.id);
        //TODO: 发送启动网络请求
        this.props.history.push('/baas/monitor/' + record.id)
    }

    operationRender(value, index, record) {
        return (
            <Button.Group size="small">
                <Button type="secondary" onClick={this.channelCheck.bind(this, record)}><Icon type="form"/>查看</Button>
                <Button type="normal" className="button-start" onClick={this.installChainCode.bind(this)}><Icon
                    type="repair"/>安装链码</Button>
                <Button type="normal" className="button-warning" onClick={this.channelDelete.bind(this, index, record)}><Icon
                    type="ashbin"/>删除</Button>
            </Button.Group>
        )
    }

    onChange(currentPage) {
        let self = this;
        self.setState({
            loading: true
        });
        setTimeout(() => {
            self.setState({
                currentChannels: self.state.allChannels.slice((currentPage - 1) * 5, (currentPage - 1) * 5 + 5),
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
        let allChannels = self.state.allChannels;
        let result = [];
        for (let i = 0; i < allChannels.length; i++) {
            if (allChannels[i].name.indexOf(self.state.searchValue) !== -1) {
                result.push(allChannels[i])
            }
        }
        this.setState({
            currentChannels: result
        })
    }

    addSubmit() {
        this.setState({
            visible: true
        })
    }

    onClose() {
        this.setState({
            visible: false
        })
    }

    networkOnChange(value, record) {
        let networks = this.state.networks;
        this.setState({
            selectedNetworkId: value
        });
        for (let i = 0; i < networks.length; i++) {
            if (networks[i].id === value) {
                this.setState({
                    selectedNetwork: networks[i]
                })
            }
        }
    }
    nameOnChange(value){
        this.setState({
            newChannelName: value
        })
    }
    peerOnChange(value, record) {
        //["0/0", "1/0"]
        // 第0个组织的第0个节点
        this.setState({
            selectedPeer: value
        });
        console.log(value)
    }
    onOK(){
        // selectedNetwork
        // selectedPeer
        alert("提交")
    }

    render() {
        const {Row, Col} = Grid;
        const FormItem = Form.Item;
        const Option = Select.Option;
        const formItemLayout = {
            labelCol: {span: 6},
            wrapperCol: {span: 18}
        };
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
                <Table dataSource={this.state.currentChannels}>
                    <Table.Column title="Id" width="5%" dataIndex="id"/>
                    <Table.Column title="通道名称" width="10%" dataIndex="name"/>
                    <Table.Column title="包含节点" cell={this.peerRender.bind(this)} dataIndex="peers"/>
                    <Table.Column title="所属网络" width="10%" dataIndex="networkId"/>
                    <Table.Column title="操作" width="25%" cell={this.operationRender.bind(this)}/>
                </Table><br/>
                <Pagination onChange={this.onChange.bind(this)} className="page-demo"/>
                <Dialog
                    visible={this.state.visible}
                    // visible={true}
                    onOk={this.onOK.bind(this)}
                    onCancel={this.onClose.bind(this)}
                    onClose={this.onClose.bind(this)}
                    title="新建通道"
                    style={{width: 400}}
                >
                    <Form>
                        <FormItem label="通道名称: " {...formItemLayout}>
                            <Input htmlType="text" onChange={this.nameOnChange.bind(this)}/>
                        </FormItem>
                        <FormItem label="选择网络: " {...formItemLayout}>
                            <Select
                                size="large"
                                hasClear="true"
                                onChange={this.networkOnChange.bind(this)}
                                style={{width: "100%"}}
                            >
                                {this.state.networks.map((item, index) => {
                                    return (
                                        <Option key={index} value={item.id}>{item.name}</Option>
                                    )
                                })}
                            </Select>
                        </FormItem>
                        <FormItem label="选择节点: " {...formItemLayout}>
                            <Select
                                size="large"
                                hasClear={true}
                                onChange={this.peerOnChange.bind(this)}
                                style={{width: "100%"}}
                                multiple
                                key="peerSelection"
                            >
                                {(() => {
                                    if (this.state.selectedNetwork) {
                                        return (
                                            this.state.selectedNetwork.org.map((orgItem, orgIndex) => {
                                                return (
                                                    <OptionGroup key={orgIndex} label={orgItem.orgName}>
                                                        {
                                                            orgItem.peers.map((peerItem, peerIndex) => {
                                                                return (
                                                                    <option
                                                                        key={peerIndex}
                                                                        value={orgIndex + "/" + peerIndex}
                                                                    >
                                                                        {peerItem.peerName}
                                                                    </option>
                                                                )
                                                            })
                                                        }
                                                    </OptionGroup>
                                                )
                                            })
                                        )
                                    }
                                })()}
                            </Select>
                        </FormItem>
                    </Form>
                </Dialog>
            </div>
        )
    }
}

export default injectIntl(Channel)

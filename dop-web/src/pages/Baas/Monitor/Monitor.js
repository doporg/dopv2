import React, {Component} from 'react';
import {Button, Feedback, Icon, Card, Grid, Select, Table} from "@icedesign/base";
import {injectIntl} from "react-intl";
import "./Monitor.scss"

const {Row, Col} = Grid;
const {Combobox, Option, OptionGroup} = Select;

class Monitor extends Component {
    constructor(prop) {
        super(prop);
        this.state = {
            // name: "第一个网络",
            network: null,
            pods: null,
            allChannels: null,
            selectablePeer: null,
            selectedPeer: "",
            selectedChannelId: null,
            blocks: null,
            transaction: null
        }
    }

    componentWillMount() {
        this.getNetwork();
        this.getPods();
        this.getChannels();
        this.getBlocks();
        this.getTransaction();
    }


    //已停止//错误
    // 0-已停止
    // 1-运行中
    // 2-构建中
    // 3-有错误

    getNetwork() {
        let network = {
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
            state: 0
        };
        this.setState({
            network
        });
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

    getPods() {
        let pods = [{
            id: 1,
            name: "ca-org1-85ccfdc4d5-jxxsl",
            state: "Running",
        }, {
            id: 2,
            name: "ca-org2-59946ff99f-v9rwm",
            state: "Running",
        }, {
            id: 3,
            name: "chaincode-marbles-org1-79cb46c796-r6sd2",
            state: "Creating",
        }, {
            id: 4,
            name: "cli-org1-b8dd77f4b-28n6h",
            state: "Error",
        }, {
            id: 55,
            name: "cli-org2-59b7949d56-t96wx",
            state: "Creating",
        }];
        this.setState({
            pods
        })
    }

    getBlocks(){
        let blocks = [
            {
                id: 0,
                blockNumber: 15,
                time: "2020-05-10",
                blockHash: "111111111111nanananandndndkmsaldkfasfew3ifnewjkfnlsjnfsadnfaskjdfnasd",
                previousHash: "111111111111nanananandndndkmsaldkfasfew3ifnewjkfnlsjnfsadnfaskjdfnasd",
            },{
                id: 1,
                blockNumber: 14,
                time: "2020-05-10",
                blockHash: "111111111111nanananandndndkmsaldkfasfew3ifnewjkfnlsjnfsadnfaskjdfnasd",
                previousHash: "111111111111nanananandndndkmsaldkfasfew3ifnewjkfnlsjnfsadnfaskjdfnasd",
            },{
                id: 2,
                blockNumber: 13,
                time: "2020-05-10",
                blockHash: "111111111111nanananandndndkmsaldkfasfew3ifnewjkfnlsjnfsadnfaskjdfnasd",
                previousHash: "111111111111nanananandndndkmsaldkfasfew3ifnewjkfnlsjnfsadnfaskjdfnasd",
            },{
                id: 3,
                blockNumber: 12,
                time: "2020-05-10",
                blockHash: "111111111111nanananandndndkmsaldkfasfew3ifnewjkfnlsjnfsadnfaskjdfnasd",
                previousHash: "111111111111nanananandndndkmsaldkfasfew3ifnewjkfnlsjnfsadnfaskjdfnasd",
            }
        ];
        this.setState({
            blocks
        })
    }

    getTransaction(){
        let transaction = [
            {
                id: 0,
                blockNumber: 15,
                time: "2020-05-10",
                chainCodeName: "mycc",
                chainCodeVersion: "1.0",
                channelName: "mychannel",
            },{
                id: 1,
                blockNumber: 14,
                time: "2020-05-10",
                chainCodeName: "mycc",
                chainCodeVersion: "1.0",
                channelName: "mychannel",
            },{
                id: 2,
                blockNumber: 13,
                time: "2020-05-10",
                chainCodeName: "mycc",
                chainCodeVersion: "1.0",
                channelName: "mychannel"
            }
        ];
        this.setState({
            transaction
        })
    }

    networkStart() {

    }

    networkStop() {

    }

    peerOnChange(value) {
        console.log(value)
        this.setState({
            selectedPeer: value
        });
    }

    peerOnClick() {
        alert(this.state.selectedPeer)
    }

    channelOnChange(value) {
        this.setState({
            selectedChannelId: value
        });
    }
    channelOnClick(){
        alert(this.state.selectedChannelId)
    }

    render() {
        return (
            <div className="monitor-wrapper">
                <div className="monitor-header">
                    <p className="monitor-title">
                        {this.state.network.name}
                        {(() => {
                            if (this.state.network.state === 0) {
                                return (
                                    <p className="monitor-subtitle monitor-subtitle-stop">
                                        已停止
                                    </p>
                                )
                            } else if (this.state.network.state === 1) {
                                return (
                                    <p className="monitor-subtitle monitor-subtitle-run">
                                        运行中
                                    </p>
                                )
                            } else if (this.state.network.state === 2) {
                                return (
                                    <p className="monitor-subtitle monitor-subtitle-build">
                                        构建中
                                    </p>
                                )
                            } else {
                                return (
                                    <p className="monitor-subtitle monitor-subtitle-error">
                                        有错误
                                    </p>
                                )
                            }
                        })()}
                    </p>
                    <Button type="normal" className="button-start" onClick={this.networkStart.bind(this)}><Icon
                        type="play"/>启动</Button>
                    &nbsp;&nbsp;&nbsp;
                    <Button type="normal" className="button-stop" onClick={this.networkStop.bind(this)}><Icon
                        type="stop"/>停止</Button>
                </div>
                <div className="monitor-container">
                    {this.state.pods.map((item, index) => {
                        if (item.state === "Running") {
                            return (
                                <Feedback className="feedback">{item.name}</Feedback>
                            )
                        } else {
                            return (
                                <Feedback className="feedback" type="error">
                                    {item.name}
                                </Feedback>
                            )
                        }

                    })}
                </div>
                <Row className="monitor-selection" justify="space-between">
                    <Col span="5">
                        <Card
                            className="monitor-card"
                            title="选择节点"
                            language="en-us"
                        >
                            <h5>节点名称</h5>
                            <Select
                                size="large"
                                hasClear="true"
                                onChange={this.peerOnChange.bind(this)}
                                style={{width: "100%"}}
                            >
                                {
                                    this.state.network.org.map((orgItem, orgIndex) => {
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
                                }
                            </Select>
                            <Row>
                                <Col span="24">
                                    <Button type="normal" className="monitor-button"
                                            onClick={this.peerOnClick.bind(this)}>
                                        查询
                                    </Button>
                                </Col>
                            </Row>
                        </Card>
                    </Col>
                    <Col span="5">
                        <Card
                            className="monitor-card"
                            title="选择通道"
                            language="en-us"
                        >
                            <h5>通道名称</h5>
                            <Select
                                size="large"
                                hasClear="true"
                                onChange={this.channelOnChange.bind(this)}
                                style={{width: "100%"}}
                            >
                                {
                                    this.state.allChannels.map((item, index)=>{
                                        return(
                                            <option
                                                key={index}
                                                value={item.id}
                                            >
                                                {item.name}
                                            </option>
                                        )
                                    })
                                }
                            </Select>
                            <Row justify="space-between">
                                <Col span="24">
                                    <Button
                                        type="normal"
                                        className="monitor-button"
                                        onClick={this.channelOnClick.bind(this)}
                                    >
                                        查询
                                    </Button>
                                </Col>
                            </Row>
                        </Card>
                    </Col>
                    <Col span="5">
                        <Card
                            className="monitor-card"
                            title="区块数"
                            language="en-us"
                        >
                            <p className="number">1005</p>
                        </Card>
                    </Col>
                    <Col span="5">
                        <Card
                            className="monitor-card"
                            title="交易数"
                            language="en-us"
                        >
                            <p className="number">15</p>
                        </Card>
                    </Col>
                </Row>
                <Row className="monitor-selection" justify="space-between">
                    <Col span="24">
                        <Card
                            className="monitor-card"
                            title="区块查找"
                            language="en-us"
                        >
                            <Table dataSource={this.state.blocks}>
                                <Table.Column title="Id" dataIndex="id" />
                                <Table.Column title="块号" dataIndex="blockNumber" />
                                <Table.Column title="时间" dataIndex="time" />
                                <Table.Column title="块hash" dataIndex="blockHash" />
                                <Table.Column title="前一块hash" dataIndex="previousHash" />
                            </Table>
                        </Card>
                    </Col>
                </Row>
                <Row className="monitor-selection" justify="space-between">
                    <Col span="24">
                        <Card
                            className="monitor-card"
                            title="交易查找"
                            language="en-us"
                        >
                            <Table dataSource={this.state.transaction}>
                                <Table.Column title="Id" dataIndex="id" />
                                <Table.Column title="块号" dataIndex="blockNumber" />
                                <Table.Column title="时间" dataIndex="time" />
                                <Table.Column title="链码名称" dataIndex="chainCodeName" />
                                <Table.Column title="链码版本" dataIndex="chainCodeVersion" />
                                <Table.Column title="通道名称" dataIndex="channelName" />
                            </Table>
                        </Card>
                    </Col>
                </Row>

            </div>
        )
    }
}

export default injectIntl(Monitor)

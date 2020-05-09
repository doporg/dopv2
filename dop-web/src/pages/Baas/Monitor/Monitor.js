import React, {Component} from 'react';
import {Button, Feedback, Icon, Card, Grid, Select} from "@icedesign/base";
import {injectIntl} from "react-intl";
import "./Monitor.scss"

const {Row, Col} = Grid;
const {Combobox, Option} = Select;

class Monitor extends Component {
    constructor(prop) {
        super(prop);
        this.state = {
            name: "第一个网络",
            pods: [{
                name: "ca-org1-85ccfdc4d5-jxxsl",
                state: "Running",
            }, {
                name: "ca-org2-59946ff99f-v9rwm",
                state: "Running",
            }, {
                name: "chaincode-marbles-org1-79cb46c796-r6sd2",
                state: "Creating",
            }, {
                name: "cli-org1-b8dd77f4b-28n6h",
                state: "Error",
            }, {
                name: "cli-org2-59b7949d56-t96wx",
                state: "Creating",
            }],
            selectedPeer: ""
        }
    }

    networkStart() {

    }

    networkStop() {

    }

    peerOnChange(value) {
        this.setState({
            selectedPeer: value
        });
    }
    peerOnClick(){
        alert(this.state.selectedPeer)
    }

    channelOnChange(value) {
        console.log(value)
    }

    render() {
        return (
            <div className="monitor-wrapper">
                <div className="monitor-header">
                    <p className="monitor-title">
                        {this.state.name}
                        <p className="monitor-subtitle monitor-subtitle-run">
                            运行中
                        </p>
                        <p className="monitor-subtitle monitor-subtitle-stop">
                            已停止
                        </p>
                        <p className="monitor-subtitle monitor-subtitle-build">
                            构建中
                        </p>
                        <p className="monitor-subtitle monitor-subtitle-error">
                            有错误
                        </p>
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
                    <Col span="7">
                        <Card
                            className="monitor-card"
                            title="选择节点"
                            language="en-us"
                        >
                            <h5>节点名称</h5>
                            <Combobox
                                size="large"
                                hasClear="true"
                                onChange={this.peerOnChange.bind(this)}
                                style={{width: "100%"}}
                            >
                                <Option value="">空</Option>
                                <Option value="jack">Jack</Option>
                                <Option value="lucy">Lucy</Option>
                                <Option value="andy">Andy</Option>
                            </Combobox>
                            <Row>
                                <Col span="24">
                                    <Button type="normal" className="monitor-button" onClick={this.peerOnClick.bind(this)}>
                                        查询
                                    </Button>
                                </Col>
                            </Row>
                        </Card>
                    </Col>
                    <Col span="7">
                        <Card
                            className="monitor-card"
                            title="选择通道"
                            language="en-us"
                        >
                            <h5>通道名称</h5>
                            <Combobox
                                size="large"
                                hasClear="true"
                                onChange={this.channelOnChange.bind(this)}
                                style={{width: "100%"}}
                            >
                                <Option value="">空</Option>
                                <Option value="jack">Jack</Option>
                                <Option value="lucy">Lucy</Option>
                                <Option value="andy">Andy</Option>
                            </Combobox>
                            <Row justify="space-between">
                                <Col span="11">
                                    <Button type="normal" className="monitor-button">
                                        查询
                                    </Button>
                                </Col>
                                <Col span="11">
                                    <Button type="normal" className="monitor-button">
                                        新建通道
                                    </Button>
                                </Col>
                            </Row>
                        </Card>
                    </Col>
                    <Col span="7">
                        <Card
                            className="monitor-card"
                            title="选择链码"
                            language="en-us"
                        >
                            <h5>链码名称</h5>
                            <Combobox
                                size="large"
                                hasClear="true"
                                onChange={this.channelOnChange.bind(this)}
                                style={{width: "100%"}}
                            >
                                <Option value="">空</Option>
                                <Option value="jack">Jack</Option>
                                <Option value="lucy">Lucy</Option>
                                <Option value="andy">Andy</Option>
                            </Combobox>
                            <Row justify="space-between">
                                <Col span="7">
                                    <Button type="normal" className="monitor-button">
                                        升级
                                    </Button>
                                </Col>
                                <Col span="7">
                                    <Button type="normal" className="monitor-button">
                                        安装
                                    </Button>
                                </Col>
                                <Col span="7">
                                    <Button type="normal" className="monitor-button">
                                        操作
                                    </Button>
                                </Col>
                            </Row>
                        </Card>
                    </Col>
                </Row>
                <Row className="monitor-block">
                    <Col span="12">
                        <Card
                            className="monitor-card"
                            title="区块数"
                            language="en-us"
                        >
                            <p>15</p>
                        </Card>
                    </Col>
                    <Col span="12">
                        <Card
                            className="monitor-card"
                            title="交易数"
                            language="en-us"
                        >
                            <p>15</p>
                        </Card>
                    </Col>
                </Row>

            </div>
        )
    }
}

export default injectIntl(Monitor)

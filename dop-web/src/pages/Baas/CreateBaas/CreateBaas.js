import React, {Component} from 'react';
import {FormattedMessage, injectIntl} from "react-intl";
import {
    Form,
    Input,
    Button,
    Select,
    Balloon,
    NumberPicker,
    Field,
    Switch,
    Grid,
    Tag,
    Icon,
    Dialog, Feedback
} from "@icedesign/base";
import './CreateBaas.scss'
import Axios from "axios";
import API from "../../API";
const {toast} = Feedback;


class CreateBaas extends Component {
    constructor(prop) {
        super(prop);
        this.state = {
            name: "",
            order: [
                {
                    orderName: "order0",
                    orderPort: ["8050"]
                }
            ],
            org: [
                {
                    orgName: "org1",
                    peers: [{
                        peerName: "peer0-org1",
                        ports: ["8051", "8052", "8053", "9443"]
                    }]
                }
            ],
            consensus: "etcdraft",
            tls: true,
            orderVisible: false,
            peerVisible: false,
            newPort: "8051"
        }
    }

    componentWillMount() {
        // this.setInitValue()
    }

    field = new Field(this);

    handleSubmit(e) {
        e.preventDefault();
        let result = {
            name: this.state.name,
            order: this.state.order,
            org: this.state.org,
            consensus: this.state.consensus,
            tls: this.state.tls,
        };
        alert("提交请求");
        //TODO: 提交请求
        // let url = API.pipeline + "/v1/pipeline/jenkinsfile";
        let url = "";
        Axios.post(url, result).then((response) => {
            if (response.status === 200) {
                toast.show({
                    type: "success",
                    content: "保存成功, 正在创建请稍后",
                    duration: 1000
                });
            }
        }).catch(() => {
        })
    }

    nameOnChange(value) {
        this.setState({
            name: value
        })
    }

    orderOnChange(value) {
        if (value > this.state.order.length) {
            let order = this.state.order;
            order.push({
                orderName: "order" + (value - 1).toString(),
                orderPort: ["8050"]
            });
            this.setState({
                order: order
            });
        } else {
            let order = this.state.order;
            order.pop();
            this.setState({
                "order": order
            })
        }
    }

    orderNameOnChange(index, value) {
        let order = this.state.order;
        order[index].orderName = value;
        this.setState({
            order: order
        })
    }

    portAdd() {
        this.setState({
            orderVisible: true
        });
    }

    portDelete(orderIndex, portIndex) {
        let order = this.state.order;
        order[orderIndex].orderPort.splice(portIndex, 1);
        console.log(order);
        this.setState({
            order: order
        })
    }

    orgOnChange(value) {
        if (value > this.state.org.length) {
            let org = this.state.org;
            org.push({
                orgName: "org" + value.toString(),
                peers: [{
                    peerName: "peer0-" + "org" + value.toString(),
                    ports: ["8051", "8052", "8053", "9443"]
                }]
            });
            this.setState({
                org: org
            });
        } else {
            let org = this.state.org;
            org.pop();
            this.setState({
                org: org
            })
        }
    }

    orgNameOnChange(orgIndex, value) {
        let org = this.state.org;
        org[orgIndex].orgName = value;
        this.setState({
            org: org
        })
    }

    peerNameOnChange(orgIndex, peerIndex, value) {
        let org = this.state.org;
        org[orgIndex].peers[peerIndex].peerName = value;
        this.setState({
            org: org
        })
    }

    peerOnChange(orgIndex) {
        let org = this.state.org;
        org[orgIndex].peers.push({
            peerName: "peer" + (org[orgIndex].peers.length).toString() + "-org" + (orgIndex + 1).toString(),
            ports: ["8051", "8052", "8053", "9443"]
        });
        this.setState({
            org: org
        })
    }

    onPortAdd(value) {
        this.setState({
            newPort: value
        });
    }

    peerPortAdd() {
        this.setState({
            peerVisible: true
        });
    }

    peerPortDelete(orgIndex, peerIndex, peerPortIndex) {
        let org = this.state.org;
        org[orgIndex].peers[peerIndex].ports.splice(peerPortIndex, 1);
        console.log(org);
        this.setState({
            org: org
        })
    }

    onDialogOk(orderIndex) {
        let order = this.state.order;
        order[orderIndex].orderPort.push(this.state.newPort);
        this.setState({
            order: order
        });
        this.setState({
            orderVisible: false
        });
    }

    onPeerDialogOk(orgIndex, peerIndex) {
        let org = this.state.org;
        org[orgIndex].peers[peerIndex].ports.push(this.state.newPort);
        this.setState({
            org: org
        });
        this.setState({
            peerVisible: false
        });
    }

    onClose() {
        this.setState({
            orderVisible: false,
            peerVisible: false,
        });
    };


    onConsensusChange(value){
        this.setState({
            consensus: value
        })
    }
    onTlsChange(value){
        this.setState({
            tls: value
        })
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
            <div className="create-baas-wrapper">
                <Form>
                    <FormItem label="网络名称: " {...formItemLayout}>
                        <Input
                            htmlType="text"
                            value={this.state.name}
                            style={{width: 200}}
                            onChange={this.nameOnChange.bind(this)}
                        />
                    </FormItem>

                    <FormItem label="order个数: " {...formItemLayout}>
                        <NumberPicker
                            min={1}
                            max={10}
                            value={this.state.order.length}
                            onChange={this.orderOnChange.bind(this)}
                        />
                        <span> 台机器</span>
                    </FormItem>
                    <div className="peer-wrapper">
                        {
                            this.state.order.map((orderItem, orderIndex) => {
                                let label = "order名称/端口: ";
                                return (
                                    <div className="peer-child">
                                        <FormItem label={label} {...formItemLayout} >
                                            <Input
                                                htmlType="text"
                                                value={orderItem.orderName}
                                                onChange={this.orderNameOnChange.bind(this, orderIndex)}
                                                style={{width: 200}}
                                            />
                                            {
                                                orderItem.orderPort.map((portItem, portIndex) => {
                                                    return (
                                                        <Tag
                                                            shape="deletable"
                                                            type="secondary"
                                                            animation={false}
                                                            style={{width: 80}}
                                                            onClose={this.portDelete.bind(this, orderIndex, portIndex)}
                                                        >
                                                            {portItem}
                                                        </Tag>
                                                    )
                                                })
                                            }

                                            <Icon type="delete-filling" size="large" className="icon-add"
                                                  onClick={this.portAdd.bind(this, orderIndex)}/>
                                            <Dialog
                                                visible={this.state.orderVisible}
                                                onOk={this.onDialogOk.bind(this, orderIndex)}
                                                closable="esc,mask,close"
                                                onCancel={this.onClose.bind(this)}
                                                onClose={this.onClose.bind(this)}
                                                title="增加开放端口"
                                            >
                                                <Balloon
                                                    trigger={
                                                        <Input
                                                            htmlType="text"
                                                            onChange={this.onPortAdd.bind(this)}
                                                            value={this.state.newPort}
                                                        />
                                                    }
                                                    align="r"
                                                    closable={false}
                                                    triggerType="hover"
                                                >
                                                    1000~65535
                                                </Balloon>
                                            </Dialog>
                                        </FormItem>
                                    </div>
                                )
                            })
                        }
                    </div>

                    <FormItem label="组织个数: " {...formItemLayout}>
                        <NumberPicker
                            min={1}
                            max={10}
                            value={this.state.org.length}
                            onChange={this.orgOnChange.bind(this)}
                        />
                        <span> 个组织</span>
                    </FormItem>
                    <div className="peer-wrapper">
                        {
                            this.state.org.map((orgItem, orgIndex) => {
                                let label = "peer名称/端口: ";
                                return (
                                    <div className="peer-child">
                                        <FormItem label="组织名称: " {...formItemLayout} className>
                                            <Input
                                                htmlType="text"
                                                value={orgItem.orgName}
                                                style={{width: 200}}
                                                onChange={this.orgNameOnChange.bind(this, orgIndex)}
                                            />
                                        </FormItem>
                                        <FormItem label="peer个数: " {...formItemLayout}>
                                            <NumberPicker
                                                min={1}
                                                max={10}
                                                value={this.state.org[orgIndex].peers.length}
                                                onChange={this.peerOnChange.bind(this, orgIndex)}
                                            />
                                            <span> 个peer</span>
                                        </FormItem>
                                        {
                                            orgItem.peers.map((peerItem, peerIndex) => {
                                                return (
                                                    <FormItem label={label} {...formItemLayout}>
                                                        <Input
                                                            htmlType="text"
                                                            value={peerItem.peerName}
                                                            style={{width: 200}}
                                                            onChange={this.peerNameOnChange.bind(this, orgIndex, peerIndex)}
                                                        />
                                                        {
                                                            peerItem.ports.map((peerPortItem, peerPortIndex) => {
                                                                return (
                                                                    <Tag
                                                                        shape="deletable"
                                                                        type="secondary"
                                                                        animation={false}
                                                                        style={{width: 80}}
                                                                        onClose={this.peerPortDelete.bind(this, orgIndex, peerIndex, peerPortIndex)}
                                                                    >
                                                                        {peerPortItem}
                                                                    </Tag>
                                                                )
                                                            })
                                                        }

                                                        <Icon type="delete-filling" size="large" className="icon-add"
                                                              onClick={this.peerPortAdd.bind(this)}
                                                        />
                                                        <Dialog
                                                            visible={this.state.peerVisible}
                                                            onOk={this.onPeerDialogOk.bind(this, orgIndex, peerIndex)}
                                                            closable="esc,mask,close"
                                                            onCancel={this.onClose.bind(this)}
                                                            onClose={this.onClose.bind(this)}
                                                            title="增加开放端口"
                                                        >
                                                            <Balloon
                                                                trigger={
                                                                    <Input
                                                                        htmlType="text"
                                                                        onChange={this.onPortAdd.bind(this)}
                                                                        value={this.state.newPort}
                                                                    />
                                                                }
                                                                align="r"
                                                                closable={false}
                                                                triggerType="hover"
                                                            >
                                                                1000~65535
                                                            </Balloon>
                                                        </Dialog>

                                                    </FormItem>
                                                )
                                            })
                                        }
                                    </div>

                                )
                            })
                        }
                    </div>

                    <FormItem label="共识机制: " {...formItemLayout} required>
                        <Select
                            style={{width: 200}}
                            value={this.state.consensus}
                            onChange={this.onConsensusChange.bind(this)}
                        >
                            <Option value="solo">solo</Option>
                            <Option value="kafka">kafka</Option>
                            <Option value="etcdraft">etcdraft</Option>
                        </Select>
                    </FormItem>
                    <FormItem label="开启TLS: " {...formItemLayout} required>
                        <Switch
                            checked={this.state.tls}
                            onChange={this.onTlsChange.bind(this)}

                        />
                    </FormItem>

                    <Row style={{marginTop: 24}}>
                        <Col offset="6">
                            <Button type="primary" onClick={this.handleSubmit.bind(this)}>
                                确定
                            </Button>
                        </Col>
                    </Row>
                </Form>
            </div>
        )
    }
}

export default injectIntl(CreateBaas)

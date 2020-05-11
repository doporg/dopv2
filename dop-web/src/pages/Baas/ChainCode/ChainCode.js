import React, {Component} from 'react';
import {
    Button,
    Icon,
    Pagination,
    Table,
    Input,
    Dialog,
    Select,
    Form, Grid, Feedback,Tag
} from "@icedesign/base";
import {injectIntl} from "react-intl";
import "./ChainCode.scss"

const {Combobox, Option, OptionGroup} = Select;
const Toast = Feedback.toast;

class ChainCode extends Component {
    constructor(prop) {
        super(prop);
        this.state = {
            allChannels: [],
            allChainCode: [],
            currentChainCode: [],
            networks: [],
            loading: false,
            searchValue: "",
            updateVisible: false,
            selectedNetworkId: null,
            selectableChannel: [],
            selectedChainCode: null,
            updateVersion: null,
            newChainCodeVisible: false,
            newGit: null,
            newVersion: null,
            selectedChannelId: null,
            newChainCodeName: null,


            //操作链码
            operationVisible: false,
            operationName: null,
            selectableOperationType: ["Init","Invoke","Query"],
            operationType: null,
            operationParameter: ["a","100"],
            operationParameterInput: null
        }
    }

    componentWillMount() {
        this.getNetwork();
        this.getChainCode();
        this.getChannels();
        setTimeout(() => {
            this.getPageData();
        }, 0);
    }

    getChainCode() {
        let allChainCode = [
            {
                id: 100,
                name: `mycc`,
                version: "1.0",
                git: "https://github.com/vanitas92/fabric-external-chaincodes",
                channelId: 100,
                networkId: 100,
            }, {
                id: 101,
                name: `fishcc`,
                version: "1.0",
                git: "https://github.com/vanitas92/fabric-external-chaincodes",
                channelId: 101,
                networkId: 101,
            }
        ];
        this.setState({
            allChainCode
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
                    }, {
                        peerName: "peer1-org1",
                        ports: ["8051", "8052", "8053", "9443"]
                    }]
                }, {
                    orgName: "org2",
                    peers: [{
                        peerName: "peer0-org2",
                        ports: ["8051", "8052", "8053", "9443"]
                    },]
                }, {
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

    getChannels() {
        let allChannels = [
            {
                id: 100,
                name: `第一个通道`,
                peers: ["peer0-org1", "peer1-org1"],
                networkId: "100"
            }, {
                id: 101,
                name: `第二个通道`,
                peers: ["peer0-org1", "peer1-org1"],
                networkId: "100"
            }, {
                id: 102,
                name: `第三个通道`,
                peers: ["peer0-org1", "peer1-org1"],
                networkId: "101"
            },
        ];
        this.setState({
            allChannels
        })
    }

    getPageData() {
        let self = this;
        this.setState({
            currentChainCode: self.state.allChainCode.slice(0, 5)
        })
    };

    channelRender(value) {
        let result = null;
        let allChannels = this.state.allChannels;
        for (let i = 0; i < allChannels.length; i++) {
            if (value === allChannels[i].id) {
                result = allChannels[i].name
            }
        }
        return result
    }

    networkRender(value) {
        let result = null;
        let networks = this.state.networks;
        for (let i = 0; i < networks.length; i++) {
            if (value === networks[i].id) {
                result = networks[i].name
            }
        }
        return result
    }

    gitRender(value) {
        return (
            <a href={value} target="view_window">{value}</a>
        )
    }

    chainCodeDelete(index, record) {
        let currentChainCode = this.state.currentChainCode;
        currentChainCode.splice(index, 1);
        this.setState({
            currentChainCode
        });
        console.log(record.id)
        //TODO: 发送删除请求
    }

    updateChainCode(index, record) {
        this.setState({
            updateVisible: true
        });
        this.setState({
            selectedChainCode: record.id
        })
    }

    operateChainCode(index, record) {
        // Toast.prompt("正在开发");
        this.setState({
            selectedChainCode: record.id,
            operationVisible: true
        })
    }

    operationRender(value, index, record) {
        return (
            <Button.Group size="small">
                <Button type="secondary" onClick={this.operateChainCode.bind(this, index, record)}><Icon
                    type="form"/>操作</Button>
                <Button
                    type="normal"
                    className="button-start"
                    onClick={this.updateChainCode.bind(this, index, record)}>
                    <Icon type="training"/>升级
                </Button>
                <Button type="normal" className="button-warning"
                        onClick={this.chainCodeDelete.bind(this, index, record)}><Icon
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
                currentChainCode: self.state.allChainCode.slice((currentPage - 1) * 5, (currentPage - 1) * 5 + 5),
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
        let allChainCode = self.state.allChainCode;
        let result = [];
        for (let i = 0; i < allChainCode.length; i++) {
            if (allChainCode[i].name.indexOf(self.state.searchValue) !== -1) {
                result.push(allChainCode[i])
            }
        }
        this.setState({
            currentChainCode: result
        })
    }

    addSubmit() {
        this.setState({
            newChainCodeVisible: true
        })
    }

    networkOnChange(value) {
        console.log(value)
        let allChannels = this.state.allChannels;
        this.setState({
            selectedNetworkId: value
        });
        let selectableChannel = [];
        for (let i = 0; i < allChannels.length; i++) {
            console.log(allChannels[i]);
            if (allChannels[i].networkId === value.toString()) {
                selectableChannel.push(allChannels[i]);
                console.log(selectableChannel)
            }
        }
        this.setState({
            selectableChannel
        })
    }

    updateVersionOnChange(value) {
        this.setState({
            updateVersion: value
        })
    }

    channelOnChange(value) {
        this.setState({
            selectedChannelId: value
        });
        console.log(value)
    }

    onVersionClose() {
        this.setState({
            updateVisible: false
        })
    }

    onVersionOK() {
        alert("提交: id " + this.state.updateVersion)
    }
    onNewChainCodeOK(){
        console.log(this.state.selectedNetworkId);
        console.log(this.state.selectedChannelId);
        console.log(this.state.newGit);
        console.log(this.state.newVersion);
        Toast.loading("正在提交，若希望更详细的配置请移步流水线管理")
    }
    onNewChainCodeClose(){
        this.setState({
            newChainCodeVisible: false
        })
    }

    newGitOnChange(value) {
        this.setState({
            newGit: value
        })
    }

    newVersionOnChange(value) {
        this.setState({
            newVersion: value
        })
    }
    onOperationOK(){
        // TODO: 执行提交
        console.log(this.state.selectedChainCode);
        console.log(this.state.operationName);
        console.log(this.state.operationType);
        console.log(this.state.operationParameter);
        // alert("执行");
    }
    onOperationClose(){
        this.setState({
            operationVisible: false
        })
    }

    operationNameOnChange(value){
        this.setState({
            operationName: value
        })
    }
    operationTypeOnChange(value){
        this.setState({
            operationType: value
        })
    }
    operationParameterOnChange(value){
        this.setState({
            operationParameterInput: value
        })
    }
    operationParameterOnClick(){
        let operationParameter = this.state.operationParameter;
        operationParameter.push(this.state.operationParameterInput);
        this.setState({
            operationParameter
        })
    }
    operationParameterOnClose(index){
        let operationParameter = this.state.operationParameter;
        operationParameter.splice(index, 1);
        this.setState({
            operationParameter
        })
    };
    nameOnChange(value){
        this.setState({
            newChainCodeName: value
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
            <div className="chaincode-wrapper">
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
                <Table dataSource={this.state.currentChainCode}>
                    <Table.Column title="Id" width="5%" dataIndex="id"/>
                    <Table.Column title="链码名称" width="10%" dataIndex="name"/>
                    <Table.Column title="版本" width="10%" dataIndex="version"/>
                    <Table.Column title="仓库地址" cell={this.gitRender.bind(this)} dataIndex="git"/>
                    <Table.Column title="所属通道" cell={this.channelRender.bind(this)} width="10%" dataIndex="channelId"/>
                    <Table.Column title="所属网络" cell={this.networkRender.bind(this)} width="10%" dataIndex="networkId"/>
                    <Table.Column title="操作" width="25%" cell={this.operationRender.bind(this)}/>
                </Table><br/>
                <Pagination onChange={this.onChange.bind(this)}/>

                <Dialog
                    visible={this.state.newChainCodeVisible}
                    // updateVisible={true}
                    onOk={this.onNewChainCodeOK.bind(this)}
                    onCancel={this.onNewChainCodeClose.bind(this)}
                    onClose={this.onNewChainCodeClose.bind(this)}
                    title="新建链码"
                    style={{width: 400}}
                >
                    <Form>
                        <FormItem label="链码名称: " {...formItemLayout}>
                            <Input htmlType="text" onChange={this.nameOnChange.bind(this)}/>
                        </FormItem>
                        <FormItem label="选择网络: " {...formItemLayout}>
                            <Select
                                size="large"
                                hasClear={true}
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
                        <FormItem label="选择通道: " {...formItemLayout}>
                            <Select
                                size="large"
                                hasClear={true}
                                onChange={this.channelOnChange.bind(this)}
                                style={{width: "100%"}}
                                key="channelSelection"
                            >
                                {
                                    this.state.selectableChannel.map((item, index) => {
                                        return (
                                            <Option key={index} value={item.id}>{item.name}</Option>
                                        )
                                    })
                                }
                            </Select>
                        </FormItem>
                        <Form>
                            <FormItem label="仓库地址: " {...formItemLayout}>
                                <Input htmlType="text" onChange={this.newGitOnChange.bind(this)}/>
                            </FormItem>
                        </Form>
                        <Form>
                            <FormItem label="版本号: " {...formItemLayout}>
                                <Input htmlType="text" onChange={this.newVersionOnChange.bind(this)}/>
                            </FormItem>
                        </Form>
                    </Form>
                </Dialog>

                <Dialog
                    visible={this.state.updateVisible}
                    onOk={this.onVersionOK.bind(this)}
                    onCancel={this.onVersionClose.bind(this)}
                    onClose={this.onVersionClose.bind(this)}
                    title="升级链码"
                    style={{width: 400}}
                >
                    <Form>
                        <FormItem label="版本号: " {...formItemLayout}>
                            <Input htmlType="text" onChange={this.updateVersionOnChange.bind(this)}/>
                            <p style={{color: 'red', fontSize: '13px'}}>*将会根据代码地址目前状态进行升级更新</p>
                        </FormItem>
                    </Form>
                </Dialog>

                <Dialog
                    visible={this.state.operationVisible}
                    onOk={this.onOperationOK.bind(this)}
                    onCancel={this.onOperationClose.bind(this)}
                    onClose={this.onOperationClose.bind(this)}
                    title="操作链码"
                    style={{width: 500}}
                >
                    <Form>
                        <FormItem label="方法: " {...formItemLayout} required>
                            <Input htmlType="text" onChange={this.operationNameOnChange.bind(this)}/>
                        </FormItem>
                        <FormItem label="方法类型: " {...formItemLayout} required>
                            <Select
                                size="large"
                                hasClear={true}
                                onChange={this.operationTypeOnChange.bind(this)}
                                style={{width: "100%"}}
                            >
                                {this.state.selectableOperationType.map((item, index) => {
                                    return (
                                        <Option key={index} value={item}>{item}</Option>
                                    )
                                })}
                            </Select>
                        </FormItem>
                        <FormItem label="参数: " {...formItemLayout}>
                            <Row>
                                <Col span={18}>
                                    <Input
                                        htmlType="text"
                                        onChange={this.operationParameterOnChange.bind(this)}
                                        placeholder="请添加"
                                        style={{width: "100%"}}
                                    />
                                </Col>
                                <Col span={6}>
                                    <Button
                                        type="primary"
                                        style={{width: "100%"}}
                                        onClick={this.operationParameterOnClick.bind(this)}
                                    >
                                        <Icon type="add"/> 添加
                                    </Button>
                                </Col>
                            </Row>
                            {
                                this.state.operationParameter.map((item, index)=>{
                                    return(
                                        <Tag
                                            shape="deletable"
                                            animation={false}
                                            onClose={this.operationParameterOnClose.bind(this, index)}
                                        >
                                            {item}
                                        </Tag>
                                    )
                                })
                            }
                        </FormItem>
                        <FormItem label="返回值: " {...formItemLayout}>
                            <Feedback key="success" type="success" shape="inline" >
                                90
                            </Feedback>
                            <Feedback key="error" type="error" shape="inline">
                                error
                            </Feedback>
                        </FormItem>
                    </Form>
                </Dialog>
            </div>
        )
    }
}

export default injectIntl(ChainCode)

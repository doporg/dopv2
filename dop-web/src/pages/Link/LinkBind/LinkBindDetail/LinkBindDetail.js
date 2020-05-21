import React, {Component} from 'react'
import {injectIntl} from "react-intl";
import BindExist from "../../components/BindExist/BindExist";
import API from "../../../API";
import Axios from "axios";
import Button from "@icedesign/base/lib/button";
import Dialog from "@icedesign/base/lib/dialog";
import Feedback from "@icedesign/base/lib/feedback";
import IceContainer from '@icedesign/container';
import Table from "@icedesign/base/lib/table";
import {formatDate, timestampToDate} from "../../util/TimeUtil";
import Icon from "@icedesign/base/lib/icon";

const {toast} = Feedback;
class LinkBindDetail extends Component{

    constructor(props) {
        super(props);
        console.log("constructor: " + props.match.params.bid);
        this.state = {
            bid: props.match.params.bid,
            bindInfo: {},
            isLoading: true,
            dataSource: [],
            monitorRecord: [],
            noticeList: []
        }
    }

    componentWillMount() {
        this.searchByBid(this.state.bid);
    }

    componentWillReceiveProps(nextProps, nextContext) {
        let bid = nextProps.match.params.bid;
        console.log("will receive: " + bid);
        this.setState({
            bid: bid
        });
        this.searchByBid(bid);

    }

    searchByBid = (bid) => {
        let pattern = /^[0-9]{1,8}$/;
        let reg = new RegExp(pattern);
        if (!reg.test(bid)) {
            return;
        }
        let searchBindById = API.link + "/v2/link/bind/" + bid;
        Axios.get(searchBindById, {}).then((response) => {
            let source = [];
            source.push(response.data);
            this.setState({
                bindInfo: response.data,
                dataSource: source,
                monitorRecord: response.data.records,
                noticeList: response.data.notices
            })
        }).catch((error) => {
            console.log("获取信息失败", error);
        }).finally(() => {
            this.setState({
                isLoading: false
            })
        })
    };

    handleStartBind = () => {
        this.setState({
            isLoading: true
        });
        let operateBindUrl = API.link + '/v2/link/bind/start/' + this.state.bid;
        Axios.post(operateBindUrl).then(response => {
            let resData = response.data;
            switch (resData.result) {
                case 'Success':
                    window.location.reload();
                    break;
                case 'Repeat':
                    toast.show({
                        type: 'error',
                        content: "该服务下由" + resData.repeatCuserName + "创建的监控：" + resData.repeatTitle + " 正在运行中",
                        duration: 10000
                    });
                    break;
                case "Fail":
                    toast.error("启动失败，请重试");
            }
        }).catch((error)=>{
            console.log("停止失败: "+JSON.stringify(error))
        }).finally(() => {
            this.setState({
                isLoading: false
            })
        });
    };

    handleStopBind = () => {
        this.setState({
            isLoading: true
        });
        Dialog.confirm({
            hasMask: false,
            content: "确认停止吗？",
            title: "",
            onOk: () => {
                let operateBindUrl = API.link + '/v2/link/bind/stop/' + this.state.bid;
                Axios.post(operateBindUrl).then(response => {
                    window.location.reload();
                }).catch((error)=>{
                    console.log("停止失败: "+JSON.stringify(error))
                }).finally(() => {
                    this.setState({
                        isLoading: false
                    })
                });
            },
            onCancel: () => {}
        });
    };

    render() {
        const renderTimeDuration = (value) => {
            let toDate = new Date(value);
            console.log("结束时间： " , toDate);
            console.log("数据类型：" , typeof toDate);
            let endTs = toDate.getTime();
            console.log("endTs", endTs);
            let startTs = endTs - 1800000;
            return formatDate(new Date(startTs)) + " - " + formatDate(toDate);
        };
        const renderExceed = (value, index,record) => {
            if (record.errorRate >= record.threshold) {
                return 'T';
            } else {
                return 'F'
            }
        };
        return (
        <BindExist bindInfo={this.state.bindInfo}>
            <div>
                {
                    this.state.bindInfo.state === 'RUNNING' ?
                        <Button loading={this.state.isLoading} style={{...styles.controlBtn}} type='primary' shape='warning' onClick={this.handleStopBind.bind(this)}>停止</Button>
                        :
                        <Button loading={this.state.isLoading} style={{...styles.controlBtn}} type='primary' onClick={this.handleStartBind.bind(this)}>启动</Button>
                }
            </div>
            <Table isLoading={this.state.isLoading} dataSource={this.state.dataSource} style={{marginTop: '10px'}}>
                <Table.Column title={this.props.intl.messages['link.bind.tabletitle.projectName']} dataIndex='projectTitle' align='center' width='20%'/>
                <Table.Column title={this.props.intl.messages['link.bind.tabletitle.service']} dataIndex='service' align='center' width='20%'/>
                <Table.Column title={this.props.intl.messages['link.bind.tabletitle.threshold']} dataIndex='threshold' align='center' width='10%'/>
                <Table.Column title={this.props.intl.messages['link.bind.tabletitle.notifier']} dataIndex='notifiedName' align='center' width='25%'/>
                <Table.Column title={this.props.intl.messages['link.bind.tabletitle.notifierEmail']} dataIndex='notifiedEmail'/>
            </Table>
            <Button style={{marginTop: '10px',marginBottom:'5px', color: 'black', fontSize: '15px'}} type='normal' size='large' shape='text'>
                <Icon type="arrow-right" size='xxs'/>监控记录
            </Button>
            <Table isLoading={this.state.isLoading} dataSource={this.state.monitorRecord}>
                <Table.Column title='时间范围' dataIndex='endTs' cell={renderTimeDuration} align='center' width='40%'/>
                <Table.Column title='不可用率' dataIndex='errorRate' align='center' width='20%'/>
                <Table.Column title='阈值' dataIndex='threshold' align='center' width='20%'/>
                <Table.Column title='是否超过' cell={renderExceed} align='center' width='20%'/>
            </Table>
            <Button style={{marginTop: '10px',marginBottom:'5px', color: 'black', fontSize: '15px'}} type='normal' size='large' shape='text'>
                <Icon type="arrow-right" size='xxs'/>通知列表
            </Button>
            <Table isLoading={this.state.isLoading} dataSource={this.state.noticeList}>
                <Table.Column title='通知发送时间' dataIndex='time' align='center' width='25%'/>
                <Table.Column title='接收邮箱' dataIndex='email' align='center' width='30%'/>
                <Table.Column title='不可用率' dataIndex='errorRate' align='center' width='10%'/>
                <Table.Column title='内容' dataIndex='description'/>
            </Table>
        </BindExist>);
    }

}

const styles = {
    controlBtn : {
        width: '100px'
    }
};

export default injectIntl(LinkBindDetail);
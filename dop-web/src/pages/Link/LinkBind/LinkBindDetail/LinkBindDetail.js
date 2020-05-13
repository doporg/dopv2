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

const {toast} = Feedback;
class LinkBindDetail extends Component{

    constructor(props) {
        super(props);
        console.log("constructor: " + props.match.params.bid);
        this.state = {
            bid: props.match.params.bid,
            bindInfo: {},

            isLoading: true
        }
    }

    componentWillMount() {
        this.searchByBid(this.state.bid);
    }

    componentDidMount() {

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
            this.setState({
                bindInfo: response.data
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
        return (
        <BindExist bindInfo={this.state.bindInfo}>
            <div>
                {
                    (this.state.bindInfo.state === 'RUNNING') ?
                        <Button style={{...styles.controlBtn}} type='primary' shape='warning' onClick={this.handleStopBind.bind(this)}>停止</Button>
                        :
                        <Button style={{...styles.controlBtn}} type='primary' onClick={this.handleStartBind.bind(this)}>启动</Button>
                }
            </div>
            <IceContainer>
                <p>最近一次检测时间</p>
                <p>最近一次检测结果</p>
            </IceContainer>
            <p>通知列表</p>
            <Table/>
        </BindExist>);
    }

}

const styles = {
    controlBtn : {
        width: '100px'
    }
};

export default injectIntl(LinkBindDetail);
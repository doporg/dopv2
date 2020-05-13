import React,{Component} from 'react';
import {injectIntl} from "react-intl";

import '../../linkStyles.css';
import Icon from "@icedesign/base/lib/icon";
import API from "../../../API";
import Axios from "axios";
import Dialog from "@icedesign/base/lib/dialog";
import Input from "@icedesign/base/lib/input";
import Button from "@icedesign/base/lib/button";
import Form from "@icedesign/base/lib/form";
import Field from "@icedesign/base/lib/field";

const styles = {
    starIcon : {
        marginRight: '20px',
        cursor: 'pointer'
    }
};

class StarIcon extends Component{

    constructor(props) {
        super(props);
        this.field = new Field(this);
        this.state = {
            traceId: props.traceId,
            hasStared : false,
            noteVisible: false
        }
    }

    componentWillMount() {
        this.judgeHasStar();
    }

    handleStarTrace = () => {
        const {getValue} = this.field;
        let createStarUrl = API.link + "/v2/link/star";
        let param = {
            userId: window.sessionStorage.getItem("user-id"),
            traceId: this.state.traceId,
            note: getValue("star-note"),
            operation: "ADD"
        };
        console.log("新建收藏参数");
        Axios.post(createStarUrl, param).then(()=>{
            this.setState({
                hasStared: true
            })
        }).catch((err)=>{
            console.log("收藏失败", err)
        }).finally(()=>{
            this.closeNoteDialog();
        });
    };

    handleUnStar = () => {
        let unStarUrl = API.link + "/v2/link/star";
        let param = {
            userId: window.sessionStorage.getItem("user-id"),
            traceId: this.state.traceId,
            operation: "DEL"
        };

        Axios.post(unStarUrl, param).then(()=>{
            this.setState({
                hasStared: false
            })
        }).catch((err)=>{
            console.log("收藏失败", err)
        });
    };

    judgeHasStar = () => {
        let judgeHasStarUrl = API.link + "/v2/link/star/exist";
        let param = {
            userId: window.sessionStorage.getItem("user-id"),
            traceId: this.state.traceId
        };
        // console.log("判断参数");
        Axios.get(judgeHasStarUrl, {params: param}).then(response => {
            // console.log("判断收藏结果： " + JSON.stringify(response));
            this.setState({
                hasStared: response.data
            });
        }).catch((error) => {
            console.log("判断失败 ",error);
        });
    };

    showAddNoteDialog = () => {
        this.setState({
            noteVisible: true
        })
    };

    closeNoteDialog = () => {
        this.setState({
            noteVisible: false
        })
    };

    render() {
        const {init} = this.field;
        const dialogFooter = (<div>
            <Button type="primary" size="medium" onClick={this.handleStarTrace.bind(this)}>
                {this.props.intl.messages['link.confirm']}
            </Button>
            <Button type="normal" size="medium" onClick={this.closeNoteDialog.bind(this)}>
                {this.props.intl.messages['link.cancel']}
            </Button>
        </div>);
        return (
            <div style={{display: 'inline'}}>
                <Dialog visible={this.state.noteVisible}
                        title={this.props.intl.messages['link.star.add.note']}
                        footerAlign='right'
                        footer={dialogFooter}
                        style={{width: '40%'}}
                        hasMask={false}
                        onClose={this.closeNoteDialog.bind(this)}
                >
                    <Form field={this.field}>
                        <Form.Item wrapperCol={{span: 24}}>
                            <Input placeholder={this.props.intl.messages['link.star.add.note']}
                                   defaultValue=''
                                   trim={true}
                                   hasClear={true}
                                   {...init("star-note", {})}/>
                        </Form.Item>
                    </Form>
                </Dialog>
                {
                    this.state.hasStared ?
                        (<Icon type="favorites-filling" style={{...styles.starIcon}} onClick={this.handleUnStar}/>)
                        :
                        (<Icon type="favorite" style={{...styles.starIcon, color: 'black'}} onClick={this.showAddNoteDialog}/>)
                }
            </div>);
    }
}

export default injectIntl(StarIcon);


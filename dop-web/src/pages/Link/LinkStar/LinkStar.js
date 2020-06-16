import React, {Component} from 'react'
import {injectIntl} from "react-intl";
import Table from "@icedesign/base/lib/table";
import API from "../../API";
import Axios from "axios";
import Button from "@icedesign/base/lib/button";
import IceEllipsis from '@icedesign/ellipsis';

import '../linkStyles.css'
import {Link} from "react-router-dom";
import Dialog from "@icedesign/base/lib/dialog";
import Form from "@icedesign/base/lib/form";
import Input from "@icedesign/base/lib/input";
import Field from "@icedesign/base/lib/field";
import Pagination from "@icedesign/base/lib/pagination";

class LinkStar extends Component{
    constructor(props) {
        super(props);
        this.field = new Field(this);
        this.state = {
            starList: [],
            isLoading: true,
            dialogVisible: false,
            tmpStarId: '',

            pageNo: 1,
            pageSize: 10,
            totalCount: 0
        }
    }

    componentWillMount() {
    }

    componentDidMount() {
        this.loadStarList("", 1);
    }

    loadStarList = (keyword, currentPage) => {
        console.log("load data");
        let getStarListUrl = API.link + "/v2/link/star";
        let param = {
            userId: window.sessionStorage.getItem("user-id"),
            pageNo: currentPage,
            pageSize: this.state.pageSize,
            keyword: keyword
        };
        console.log("load param", param);
        Axios.get(getStarListUrl, {params: param}).then((response)=>{
            let data = response.data;
            this.setState({
                starList: data.pageList,
                totalCount: data.totalCount,
                pageNo: data.pageNo
            })
        }).catch((error) => {
            console.log("获取收藏列表失败", error);
        }).finally(()=>{
            this.setState({
                isLoading: false
            })
        })
    };

    handleUnStar = (starRecord) => {
        let {getValue} = this.field;
        let unStarUrl = API.link + "/v2/link/star";
        let param = {
            userId: starRecord.userId,
            traceId: starRecord.traceId,
            operation: "DEL"
        };

        Axios.post(unStarUrl, param).then(()=>{
            let pageNo = this.state.pageNo;
            if (this.state.starList.length <= 1) {
                pageNo = pageNo - 1;
            }
            this.loadStarList(getValue("keywords"), pageNo)
        }).catch((err)=>{
            console.log("删除收藏失败", err)
        });
    };

    handleModify = () => {
        let {getValue} = this.field;
        let modifyNoteUrl = API.link + "/v2/link/star/note";
        let param = {
            sid: this.state.tmpStarId,
            newNote: getValue('star-note')
        };
        Axios.post(modifyNoteUrl, param).then((response) => {
            this.loadStarList(getValue('keywords'), this.state.pageNo);
        }).catch((error) => {
            console.log("编辑备注失败", error)
        }).finally(()=>{
            this.closeDialog();
        })
    };

    openDialog = (starRecord) => {
        let {setValue} = this.field;
        setValue('star-note', starRecord.note);
        this.setState({
            tmpStarId: starRecord.sid,
            dialogVisible: true
        })
    };

    closeDialog = () => {
        this.setState({
            dialogVisible: false
        })
    };

    onSortTable = (dataIndex, order) => {
        let dataSource = this.state.starList.sort(function(a, b) {
            if (dataIndex === 'traceId') { //字符串，不能相减
                let result = a[dataIndex] > b[dataIndex];
                return order === "asc"? (result ? 1 : -1) : (result ? -1 : 1);
            } else {
                let result = a[dataIndex] - b[dataIndex];
                return order === "asc" ? (result > 0 ? 1 : -1) : result > 0 ? -1 : 1;
            }
        });
        this.setState({
            dataSource
        });
    };

    searchByKeywords = () => {
        let {getValue} = this.field;
        let keyword = getValue('keywords');
        console.log("搜索关键字： " + keyword);
        this.loadStarList(keyword, 1);
    };

    changeListPage = (currentPage) => {
        console.log("当前页： " + currentPage);
        this.setState({
            isLoading: true,
        });
        let {getValue} = this.field;
        this.loadStarList(getValue('keywords'), currentPage)
    };

    render() {
        const {init} = this.field;
        const renderOperate = (value, index, record) => {
            // console.log("renderOperate ", record);
            return (
                <div>
                    <Button type="normal" size="small" className="operate-button" onClick={this.openDialog.bind(this, record)}>
                        编辑
                    </Button>
                    <Button type="primary" shape="warning" size="small" onClick={this.handleUnStar.bind(this, record)}>
                        删除
                    </Button>
                </div>)
        };

        const renderTraceId = (value) => {
            return <Link to={"/link/detail/" + value}>{value}</Link>;
        };

        const renderNote = (value) => {
            return <IceEllipsis lineLimit={3} text={value} showTooltip={true} />;
        };

        const dialogFooter = (<div>
            <Button type="primary" size="medium" onClick={this.handleModify.bind(this)}>
                {this.props.intl.messages['link.confirm']}
            </Button>
            <Button type="normal" size="medium" onClick={this.closeDialog.bind(this)}>
                {this.props.intl.messages['link.cancel']}
            </Button>
        </div>);

        return (
            <div>
                <Dialog visible={this.state.dialogVisible}
                        title={this.props.intl.messages['link.star.modify.note']}
                        footerAlign='right'
                        footer={dialogFooter}
                        style={{width: '40%'}}
                        hasMask={false}
                        onClose={this.closeDialog.bind(this)}>
                    <Form field={this.field}>
                        <Form.Item wrapperCol={{span: 24}}>
                            <Input placeholder={this.props.intl.messages['link.star.modify.note']}
                                   defaultValue=''
                                   trim={true}
                                   hasClear={true}
                                   {...init("star-note", {
                                       props: {
                                           onChange: (v) => {
                                           }
                                       }
                                   })}/>
                        </Form.Item>
                    </Form>
                </Dialog>
                <Form field={this.field}>
                    <Form.Item wrapperCol={{span: 10}}>
                        <Input placeholder='输入备注关键字，回车查找'
                               defaultValue=''
                               hasClear={true}
                               onPressEnter={this.searchByKeywords.bind(this)}
                               {...init("keywords", {})}
                        />
                    </Form.Item>
                </Form>
                <Table dataSource={this.state.starList}
                       isLoading={this.state.isLoading}
                       locale={{"empty": this.props.intl.messages['link.no.data']}}
                       onSort={this.onSortTable.bind(this)}
                >
                    <Table.Column title={this.props.intl.messages['link.star.table.id']} dataIndex='sid' align='center' width='10%'/>
                    <Table.Column title={this.props.intl.messages['link.star.table.date']} dataIndex='ctime' align='center' width='25%' sortable={true}/>
                    <Table.Column title={this.props.intl.messages['link.star.table.traceId']} dataIndex='traceId' cell={renderTraceId} sortable={true} align='center' width='20%'/>
                    <Table.Column title={this.props.intl.messages['link.star.table.note']} dataIndex='note' cell={renderNote} align='center'/>
                    <Table.Column title={this.props.intl.messages['link.star.table.operate']} cell={renderOperate} width='15%' align='center'/>
                </Table>
                <Pagination total={this.state.totalCount}
                            current={this.state.pageNo}
                            pageSize={this.state.pageSize}
                            style={{textAlign: 'center', paddingTop:'10px'}}
                            onChange={this.changeListPage.bind(this)}
                />
            </div>)
    }
}

export default injectIntl(LinkStar)
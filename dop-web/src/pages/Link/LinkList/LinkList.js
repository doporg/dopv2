import React, {Component} from 'react'
import {injectIntl} from "react-intl";
import Axios from 'axios';
import Select from "@icedesign/base/lib/select";
import Button from "@icedesign/base/lib/button";
import Dialog from "@icedesign/base/lib/dialog";
import Search from "@icedesign/base/lib/search";
import Table from "@icedesign/base/lib/table";
import Feedback from "@icedesign/base/lib/feedback"

import API from "../../API";
import "../linkStyles.css";
import Input from "@icedesign/base/lib/input";
import Icon from "@icedesign/base/lib/icon";
import IceContainer from '@icedesign/container';
import Pagination from "@icedesign/base/lib/pagination";
import Form from "@icedesign/base/lib/form";
import Grid from "@icedesign/base/lib/grid";
import Field from "@icedesign/base/lib/field";
import Card from "@icedesign/base/lib/card";
import DatePicker from "@icedesign/base/lib/date-picker";

// import {Table,Search,Dialog,Select,Button} from "@icedesign/base";

import {getCurrentTimestamp, toTimestamp, getCurrentTime, toMicroseconds, timestampToDate, formatDuration} from "../util/TimeUtil";
import Tag from "@icedesign/base/lib/tag";

class LinkList extends Component{

    constructor(props) {
        super(props);
        this.field = new Field(this);
        this.state = {
            projectList: [],
            currentPageProList: [],
            isLoading: false,

            clickedProjectID: "",
            clickedProjectName: "",
            chosenProjectID: "",
            chosenProjectName: "",

            chooseProRowSelection: {
                mode: 'single',
                selectedRowKeys: [],
                onChange: this.onChangeChooseProject.bind(this)
            },
            chooseProDialogVisible: false,
            helpDialogVisible: false,
            displayChooseButton: 'block',
            displayChangeButton: 'none',

            pageNoForPro: 1,
            pageSizeForPro: 8,
            totalCountForPro: 0,

            historySearchTraceId: [],
            searchedTraceId: '',

            serviceList: [],
            spanNameList: ["all"],

            searchCondition: {
                serviceName: 'all',
                spanName: 'all',
                lookBack: 3600000,
            },
            timeRangeDisplay: 'none',

            linkList: [],
            pageNo: 1,
            pageSize: 8,
            totalCount: 0
        }
    }

    componentWillMount() {
        // alert("componentWillMount");
        this.state.isLoading = true;
        let projectId = window.sessionStorage.getItem("link_chosenProjectID");
        let projectName = window.sessionStorage.getItem("link_chosenProjectName");

        if (projectId !== '' && projectId !== null && projectName !== '' && projectName !== null) {
            // 页面缓存中已经有了选择的要查看的项目
            this.setState({
                chosenProjectID: projectId,
                chosenProjectName: projectName,
                displayChooseButton: 'none',
                displayChangeButton: 'block'
            });
            this.getServiceList();
        }
        this.state.isLoading = false;
    }
    componentDidMount() {

    }
    getServiceList = () => {
        let getLinkListUrl = API.link + '/api/v2/services';
        Axios.get(getLinkListUrl).then(response => {
            let serviceListTmp = response.data;
            serviceListTmp.unshift("all");
            this.setState({serviceList: serviceListTmp});
        });
    };
    onOpenChooseProDialog = () => {
        // let user_id = window.sessionStorage.getItem('user-id');
        this.setState({
            chooseProDialogVisible: true,
            isLoading: true
        });
        // 获得全部项目列表
        let getProjectListUrl = API.link + "/getProjectList";
        Axios.get(getProjectListUrl).then(response=>
        {
            let projectListTmp = {};
            projectListTmp = response.data;
            this.setState({
                projectList: projectListTmp,
                currentPageProList: projectListTmp.slice(0, 8),
                isLoading: false,
                totalCountForPro: projectListTmp.length,
            });
        });

    };
    onCloseDialog = () => {
        this.setState({chooseProDialogVisible: false});
    };
    onConfirmChooseProject = () => {
        const Toast = Feedback.toast;
        let clickedId = this.state.clickedProjectID;
        let clickedName = this.state.clickedProjectName;
        let clicked = (clickedId !== "" && clickedName !== "");
        if (!clicked) {
            Toast.prompt(this.props.intl.messages['link.warn.noChosenProject']);
            return;
        }
        window.sessionStorage.setItem("link_chosenProjectID", clickedId);
        window.sessionStorage.setItem("link_chosenProjectName", clickedName);

        this.setState({
            chosenProjectID: clickedId,
            chosenProjectName: clickedName,
            chooseProDialogVisible: false,
            displayChooseButton: 'none',
            displayChangeButton: 'block',
        });
        this.getServiceList();
    };
    onChangeChooseProject = (ids, records) => {
        this.state.chooseProRowSelection.selectedRowKeys = ids;
        this.setState({
            clickedProjectID: ids[0],
            clickedProjectName:  records[0].name
        });
    };
    onChangeProjectPage = (currentPage) => {
        this.setState({isLoading:true});
        let size = this.state.pageSizeForPro;
        let begin = size * (currentPage - 1);
        let currentList = this.state.projectList.slice(begin, begin + size);
        this.setState({
            pageNoForPro: currentPage,
            currentPageProList: currentList,
            isLoading: false
        });
    };
    searchTraceList = (e) => {
        const {getValue} = this.field;
        const Toast = Feedback.toast;
        this.field.validate((errors, values) => {
            console.log(values);
            if (errors) {
               Toast.error(this.props.intl.messages['link.error.prompt.contentError']);
               return;
           }
           this.setState({
               isLoading: true
           });
           let getLinkListUrl = API.link + "/getTraceList";
           // 计算lookback和endTs
           var lookBack, endTs;
           if (this.state.timeRangeDisplay === 'none') { //选择列表中的时间选项
               lookBack = this.state.searchCondition.lookBack;
               endTs = getCurrentTimestamp();
           } else { // 如果是自定义时间
               let startTs = toTimestamp(values['start-date'], values['start-time']);
               endTs = toTimestamp(values['end-date'], values['end-time']);
               lookBack = endTs - startTs;
               // console.log("lookback: "+lookBack);
           }
           let params = {
                serviceName: this.state.searchCondition.serviceName,
                spanName: this.state.searchCondition.spanName,
                annotation: values["annotation"],
                minDuration: toMicroseconds(values["minDuration"]),
                maxDuration: toMicroseconds(values["maxDuration"]),
                endTs: endTs,
                lookback: lookBack,
                limit: values["limit"]
            };
            console.log("param: " + JSON.stringify(params));
            Axios.get(getLinkListUrl, {params: params}).then(response => {
                let linkListTmp = response.data;
                console.log(JSON.stringify(linkListTmp));
                this.setState({
                    linkList: linkListTmp,
                    isLoading: false,
                    totalCount: linkListTmp.length
                })
            }).catch((error)=>{
                console.error("查询trace列表出错：", error);
            });
        });
    };
    onChangeLinkPage = (currentPage) => {
        // 具体内容还没写
        this.setState({isLoading:true});
        let size = this.state.pageSize;
        this.setState({
            pageNo: currentPage,
            isLoading: false
        });
    };
    changeServiceName = (value) => {
        this.state.searchCondition.serviceName = value;
        let getSpanNameListUrl = API.link + "/api/v2/spans";
        let params = {
            serviceName: value
        };
        Axios.get(getSpanNameListUrl, {params: params}).then(response => {
            let spanNameListTmp = response.data;
            spanNameListTmp.unshift("all");
            this.setState({
                spanNameList: spanNameListTmp
            });
        });
    };
    chooseLookBackType = (value) =>{
        const {setValues, setErrors} = this.field;
        setValues({
            'start-date': new Date(),
            'start-time': getCurrentTime(),
            'end-date': new Date(),
            'end-time': getCurrentTime(),
        });
        if (value === -1) {
            this.setState({
                timeRangeDisplay: 'flex'
            });
        } else {
            this.setState({
                timeRangeDisplay: 'none'
            });
            this.state.searchCondition.lookBack = value * 3600000;
            setErrors({
                'start-time': "",
                'end-time': "",
            });
        }
    };
    onSortLinkListTable = (dataIndex, order) => {
        console.log("dataIndex: " + dataIndex + ", order: " + order);
        let dataSource = this.state.linkList.sort(function(a, b) {
            if (dataIndex === 'spanName') { //字符串，不能相减
                let result = a[dataIndex] > b[dataIndex];
                // console.log("a[dataIndex]: " + a[dataIndex] + "\n" + "b[dataIndex]: " + b[dataIndex] +"\n" + "result: " + result);
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
    render() {
        const lookBackOptions = [
            {value: 1, label: this.props.intl.messages['link.time.one.hour']},
            {value: 3, label: this.props.intl.messages['link.time.three.hours']},
            {value: 6, label: this.props.intl.messages['link.time.six.hours']},
            {value: 12, label: this.props.intl.messages['link.time.twelve.hours']},
            {value: 24, label: this.props.intl.messages['link.time.one.day']},
            {value: 48, label: this.props.intl.messages['link.time.two.days']},
            {value: 84, label: this.props.intl.messages['link.time.seven.days']},
            {value: -1, label: this.props.intl.messages['link.time.customize']},
        ];
        const sortOptions = [
            this.props.intl.messages['link.sort.by.timestampDesc'],
            this.props.intl.messages['link.sort.by.timestampAsc'],
            this.props.intl.messages['link.sort.by.durationDesc'],
            this.props.intl.messages['link.sort.by.durationAsc'],
            this.props.intl.messages['link.sort.by.servicePercentageDesc'],
            this.props.intl.messages['link.sort.by.servicePercentageAsc']
        ];
        const dialogStyle= {
            width: "50%"
        };
        const selectConditionCardStyle = {
            padding: '15px'
        };
        const datePickerLocale = {
            now: this.props.intl.messages['link.datepicker.now'],
            ok: this.props.intl.messages['link.confirm']
        };
        const chooseProDialogFooter = (<div>
                <Button type="primary" size="medium" onClick={this.onConfirmChooseProject.bind(this)}>
                    {this.props.intl.messages['link.confirm']}
                </Button>
                <Button type="normal" size="medium" onClick={this.onCloseDialog.bind(this)}>
                    {this.props.intl.messages['link.cancel']}
                </Button>
            </div>);
        const {Row, Col} = Grid;
        const { init, getError, getValue, setValue} = this.field;
        const cardTitle = <i>{this.props.intl.messages['link.project.name']}:&nbsp;&nbsp;{this.state.chosenProjectName}</i>;
        const checkTime = (rule, value, callback) => {
            let pattern = /^(20|21|22|23|[0-1]\d):[0-5]\d$/;
            let reg = new RegExp(pattern);
            if (!reg.test(value)) {
                callback(new Error(this.props.intl.messages['link.error.prompt.format']));
            } else {
                callback();
            }
        };
        const checkDuration = (rule, value, callback) => {
            let pattern = /^$|^\d+(\.\d+)?(ms|s)$|^\d+(μs)?$/;
            let reg = new RegExp(pattern);
            if (!reg.test(value)) {
                callback(new Error(this.props.intl.messages['link.error.prompt.format']));
            } else {
                callback();
            }
        };
        const checkLimit =  (rules, value, callback) => {
            value = value.replace(/\b(0+)/gi,"");
            let pattern = /^[1-9]\d*$/; //正整数
            if (!new RegExp(pattern).test(value)) {
                callback(new Error(this.props.intl.messages['link.error.positive.integer']));
            } else {
                return callback();
            }
        };

        const renderTraceId = (value, index, record) => {
            return <a href="/#/link/detail" className='trace-id'>{record['traceId']}</a>;
        };
        const renderHasError = (hasError) => {
            if (hasError) {
                return <Tag shape='readonly' style={{color:'#a94442',margin:'0',width:'80px',backgroundColor: '#f2dede'}}>{this.props.intl.messages['link.fail']}</Tag>
            } else {
                return <Tag shape='readonly' style={{margin:'0',width:'80px'}}>{this.props.intl.messages['link.success']}</Tag>
            }
        };
        const propsConf = {
            className: 'error-link',
            style: {background: '#f2dede', color: '#a94442'}
        };

        const setRowProps = (record, index) => {
            if (record['hasError']) {
                return propsConf;
            }
        };
        return (
        <div>
            {/*未选择过项目，显示选择按钮*/}
            <div style={{display: this.state.displayChooseButton}}>
                <Button className="choose-button" type="primary" size="medium" onClick={this.onOpenChooseProDialog.bind(this)}>
                    {this.props.intl.messages['link.choose.project']}
                </Button>
            </div>
            {/*更换项目按钮 + 根据traceId查询*/}
            <div style={{display: this.state.displayChangeButton, marginBottom: '10px'}}>
                <Row>
                    <Col span='4'>
                        <Button className="change-button" type="primary" size="medium" onClick={this.onOpenChooseProDialog.bind(this)}>
                            {this.props.intl.messages['link.change.project']}
                        </Button>
                    </Col>
                    <Col>
                        <Search className='search-by-traceid'
                                searchText={this.props.intl.messages['link.search.submit.search']}
                                dataSource={this.state.historySearchTraceId}
                                autoWidth
                                placeholder={this.props.intl.messages['link.search.by.traceId']}
                                hasIcon={false}
                                value={this.state.searchedTraceId}
                                size='large'
                        />
                    </Col>
                </Row>
            </div>
            {/*选择项目对话框*/}
            <Dialog
                visible={this.state.chooseProDialogVisible}
                footer={chooseProDialogFooter}
                footerAlign='right'
                shouldUpdatePosition
                minMargin={50}
                onClose={this.onCloseDialog.bind(this)}
                title={this.props.intl.messages['link.choose.project']}
                style={dialogStyle}>
                <Table dataSource={this.state.currentPageProList} isLoading={this.state.isLoading} rowSelection={this.state.chooseProRowSelection}>
                    <Table.Column title={this.props.intl.messages['link.project.id']} dataIndex="id" width="25%" align='center'/>
                    <Table.Column title={this.props.intl.messages['link.project.name']} dataIndex="name" align='center'/>
                </Table>
                <Pagination total={this.state.totalCountForPro}
                            current={this.state.pageNoForPro}
                            onChange={this.onChangeProjectPage.bind(this)}
                            pageSize={this.state.pageSizeForPro}
                            style={{paddingTop: '10px',textAlign: 'center'}}
                />
            </Dialog>
            {/*未选择项目时，主体的选择提示*/}
            <IceContainer style={{display: this.state.displayChooseButton, margin: '0 auto'}}>
                <div className="choose-pro-prompt">
                    <div className='choose-pro-icon-div'>
                        <Icon type="add" size='xxxl' style={{opacity:'0.5',cursor:'pointer'}} onClick={this.onOpenChooseProDialog.bind(this)}/>
                    </div>
                    <div className='choose-pro-h-div'>
                        <p className="prompt-p">{this.props.intl.messages['link.please.choose.project']}</p>
                    </div>
                </div>
            </IceContainer>
            {/*链路主体部分，包括筛选条件和链路列表*/}
            <div style={{display: this.state.displayChangeButton}}>
                <Card className='search-condition' bodyHeight='auto' title={cardTitle} style={selectConditionCardStyle}>
                    <Form labelAlign="top" field={this.field}>
                        {/*服务名，span名，时间选择框*/}
                        <Row>
                            <Col span='6'>
                                <Form.Item label={this.props.intl.messages['link.search.by.serviceName']}>
                                    {/*微服务列表在页面加载will/mounted时同时获取*/}
                                    <Select className='search-select'
                                            dataSource={this.state.serviceList}
                                            showSearch
                                            defaultValue='all'
                                            onChange={this.changeServiceName.bind(this)}/>
                                </Form.Item>
                            </Col>
                            <Col span='6'>
                                <Form.Item label={this.props.intl.messages['link.search.by.spanName']}>
                                    {/*span名称在serviceName发生改变时获取，即通过changeServiceName方法*/}
                                    <Select className='search-select'
                                            dataSource={this.state.spanNameList}
                                            showSearch
                                            defaultValue='all'
                                            onChange={(value) => {
                                                // console.log("choose spanName: " + value);
                                                this.state.searchCondition.spanName = value;}
                                            }
                                    />
                                </Form.Item>
                            </Col>
                            <Col span='12'>
                                <Form.Item label={this.props.intl.messages['link.search.by.time']}>
                                    <Select className='search-select'
                                            autoWidth={true}
                                            dataSource={lookBackOptions}
                                            defaultValue={lookBackOptions[0]}
                                            onChange={this.chooseLookBackType.bind(this)}
                                    />
                                </Form.Item>
                            </Col>
                        </Row>
                        {/*startTime-endTime*/}
                        <Row style={{display: this.state.timeRangeDisplay}}>
                            <Col span='12'/>
                            <Col span='4'>
                                <Form.Item label={this.props.intl.messages['link.search.by.startTime']}>
                                    <DatePicker  id="date-picker1" className='date-picker-style'
                                                readOnly
                                                hasClear={false}
                                                popupAlign={"bl tl"}
                                                formater={["YYYY-MM-DD"]}
                                                defaultValue={"2020-04-01"}
                                                locale={datePickerLocale}
                                                {...init("start-date",{})}
                                    />
                                </Form.Item>
                            </Col>
                            <Col span='2'>
                                <Form.Item label=''>
                                    <Input defaultValue='00:00'
                                           trim={true}
                                           {...init("start-time", {
                                               rules: [
                                                   { validator: checkTime.bind(this)}
                                               ]
                                           })}
                                    />
                                </Form.Item>
                            </Col>
                            <Col span='4'>
                                <Form.Item label={this.props.intl.messages['link.search.by.endTime']}>
                                    <DatePicker className='date-picker-style'
                                                readOnly
                                                hasClear={false}
                                                popupAlign={"bl tl"}
                                                formater={["YYYY-MM-DD"]}
                                                defaultValue='2020-04-01'
                                                locale={datePickerLocale}
                                                {...init("end-date",{})}
                                    />
                                </Form.Item>
                            </Col>
                            <Col span='2'>
                                <Form.Item label=''>
                                    <Input defaultValue="00:00"
                                           trim={true}
                                           {...init("end-time", {
                                               rules: [
                                                   { validator: checkTime.bind(this)}
                                               ]
                                           })}
                                    />
                                </Form.Item>
                            </Col>
                        </Row>
                        {/*annotation,duration,limit,sort*/}
                        <Row>
                            <Col span='12'>
                                <Form.Item label={this.props.intl.messages['link.search.by.annotation']}>
                                    <Input placeholder='For example: http.path=/foo/bar/ and cluster=foo and cache.miss'
                                           defaultValue=''
                                           {...init("annotation", {})}
                                    />
                                </Form.Item>
                            </Col>
                            <Col span='4'>
                                <Form.Item label={this.props.intl.messages['link.search.by.minDuration']}>
                                    <Input placeholder='Ex: 100ms or 5s'
                                           defaultValue=''
                                           {...init("minDuration", {
                                               rules:[
                                                   {validator: checkDuration.bind(this)}
                                               ]
                                           })}
                                    />
                                </Form.Item>
                            </Col>
                            <Col span='4'>
                                <Form.Item label={this.props.intl.messages['link.search.by.maxDuration']}>
                                    <Input placeholder='Ex: 100ms or 5s'
                                           defaultValue=''
                                           {...init("maxDuration", {
                                               rules:[
                                                   {validator: checkDuration.bind(this)}
                                                   ]
                                           })}
                                    />
                                </Form.Item>
                            </Col>
                            <Col span='4'>
                                <Form.Item label={this.props.intl.messages['link.search.by.resultlimit']}>
                                    <Input defaultValue='10'
                                           {...init("limit", {
                                               rules:[{
                                                   validator: checkLimit.bind(this)
                                               }]
                                           })}/>
                                </Form.Item>
                            </Col>
                        </Row>
                        {/*提交查询按钮*/}
                        <Row>
                            <Button type="primary" size='medium' onClick={this.searchTraceList.bind(this)}>
                                {this.props.intl.messages['link.search.submit.search']}
                            </Button>
                            <Icon type='help' size='small'
                                  style={{margin: '4px', color: '#0062cc', cursor:'pointer'}}
                                  onClick={()=>{this.setState({helpDialogVisible: true});}}/>
                        </Row>
                    </Form>
                </Card>
                <Dialog visible={this.state.helpDialogVisible} style={dialogStyle}
                        footer={false}
                        title={this.props.intl.messages['link.search.condition.description']}
                        onClose={()=>{this.setState({helpDialogVisible: false});}}>
                    查询数据说明
                </Dialog>
                <Table dataSource={this.state.linkList} isLoading={this.state.isLoading}
                       onSort={this.onSortLinkListTable.bind(this)}
                       locale={{"empty": this.props.intl.messages['link.no.data']}}
                       // getRowProps={setRowProps}
                >
                    <Table.Column title={this.props.intl.messages['link.list.table.traceId']} dataIndex='traceId' cell={renderTraceId} sortable={true} align='center'/>
                    <Table.Column title={this.props.intl.messages['link.list.table.spanName']} dataIndex='spanName' sortable={true} align='center'/>
                    <Table.Column title={this.props.intl.messages['link.list.table.timestamp']} dataIndex='timestamp' cell={timestampToDate} sortable={true} align='center'/>
                    <Table.Column title={this.props.intl.messages['link.list.table.duration']} dataIndex='duration' cell={formatDuration} sortable={true} align='center'/>
                    {/*<Table.Column title={this.props.intl.messages['link.list.table.spanNum']} dataIndex='spanNum'/>*/}
                    <Table.Column title={this.props.intl.messages['link.list.table.successOrFail']} dataIndex='hasError' cell={renderHasError} sortable={true} align='center'/>
                    <Table.Column title={this.props.intl.messages['link.list.table.detail']} dataIndex='detail' align='center'/>
                </Table>
                <Pagination total={this.state.totalCount}
                            current={this.state.pageNo}
                            onChange={this.onChangeLinkPage.bind(this)}
                            pageSize={this.state.pageSize}
                            style={{paddingTop: '10px',textAlign: 'center'}}
                />
            </div>
        </div>);
    }
}

export default injectIntl(LinkList);
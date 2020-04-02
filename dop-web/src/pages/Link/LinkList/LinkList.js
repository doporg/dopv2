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
import TimePicker from "@icedesign/base/lib/time-picker";
import DatePicker from "@icedesign/base/lib/date-picker";

// import {Table,Search,Dialog,Select,Button} from "@icedesign/base";

class LinkList extends Component{

    constructor(props) {
        super(props);
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
            displayChooseButton: 'block',
            displayChangeButton: 'none',

            pageNo: 1,
            pageSize: 8,
            totalCount: 0,

            historySearchTraceId: [],
            searchedTraceId: '',

            serviceList: [],
            spanNameList: [],

            searchCondition: {
                serviceName: 'all',
                spanName: 'all',
                lookBack: 3600000,
                startTime: null,
                endTime: null,
                annotation: null,
                minDuration: 0,
                limit: 10,
                // sortType: this.props.intl.messages['link.sort.by.timestampDesc']
            },
            timeRangeDisplay: 'none',

            linkList: []
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
        }
        this.state.isLoading = false;
        this.getServiceList();
    }
    componentDidMount() {

    }
    getServiceList = () => {
        let getLinkListUrl = API.link + '/api/v2/services';
        Axios.get(getLinkListUrl).then(response => {
            let serviceListTmp = response.data;
            serviceListTmp.unshift("all");
            console.log("services: " + serviceListTmp);
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
            // console.log("data: " + JSON.stringify(response.data));
            let projectListTmp = {};
            projectListTmp = response.data;
            this.setState({
                projectList: projectListTmp,
                currentPageProList: projectListTmp.slice(0, 8),
                isLoading: false,
                totalCount: projectListTmp.length,
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
            Toast.prompt("未选择项目");
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
        let size = this.state.pageSize;
        let begin = size * (currentPage - 1);
        let currentList = this.state.projectList.slice(begin, begin + size);
        this.setState({
            pageNo: currentPage,
            currentPageProList: currentList,
            isLoading: false
        });
    };
    searchTraceList = () => {
        this.setState({
            isLoading: true
        });
        let getLinkListUrl = API.link + "/getTraceList";
        let params = this.state.searchCondition;
        console.log("param: " + JSON.stringify(this.state.searchCondition));
        Axios.get(getLinkListUrl, {params: params}).then(response => {
            let linkListTmp = response.data;
            this.setState({
                linkList: linkListTmp,
                isLoading: false
            })
        });
    };
    changeServiceName = (value) => {
        this.state.searchCondition.serviceName = value;
        console.log("changeServiceName: " + value);
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
        const cardTitle = <i>{this.props.intl.messages['link.project.name']}:&nbsp;&nbsp;{this.state.chosenProjectName}</i>;
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
                onOk={this.onClose}
                onCancel={this.onClose}
                shouldUpdatePosition
                minMargin={50}
                onClose={this.onClose}
                title={this.props.intl.messages['link.choose.project']}
                style={dialogStyle}>

                <Table dataSource={this.state.currentPageProList} isLoading={this.state.isLoading} rowSelection={this.state.chooseProRowSelection}>
                    <Table.Column title={this.props.intl.messages['link.project.id']} dataIndex="id" width="25%" align='center'/>
                    <Table.Column title={this.props.intl.messages['link.project.name']} dataIndex="name" align='center'/>
                </Table>
                <Pagination total={this.state.totalCount}
                            current={this.state.pageNo}
                            onChange={this.onChangeProjectPage.bind(this)}
                            pageSize={this.state.pageSize}
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
                    <Form labelAlign="top">
                        {/*服务名，span名，时间选择框*/}
                        <Row>
                            <Col span='6'>
                                <Form.Item label={this.props.intl.messages['link.search.by.serviceName']}>
                                    {/*微服务列表在页面加载will/mounted时同时获取*/}
                                    <Select className='search-select'
                                            dataSource={this.state.serviceList}
                                            showSearch
                                            defaultValue='all' onChange={this.changeServiceName.bind(this)}/>
                                </Form.Item>
                            </Col>
                            <Col span='6'>
                                <Form.Item label={this.props.intl.messages['link.search.by.spanName']}>
                                    {/*span名称在serviceName发生改变时获取，即通过changeServiceName方法*/}
                                    <Select className='search-select'
                                            dataSource={this.state.spanNameList}
                                            showSearch
                                            defaultValue='all' onChange={(value) => {
                                                console.log("choose spanName: " + value);
                                                this.state.searchCondition.spanName = value;}
                                            }
                                    />
                                </Form.Item>
                            </Col>
                            <Col span='12'>
                                <Form.Item label={this.props.intl.messages['link.search.by.time']}>
                                    <Select className='search-select'
                                            dataSource={lookBackOptions}
                                            defaultValue={lookBackOptions[0]}
                                            onChange={(value)=>{
                                                alert("选择时间： "+value);
                                                if (value === -1) {
                                                    this.setState({timeRangeDisplay: 'flex'});
                                                } else {
                                                    this.setState({
                                                        timeRangeDisplay: 'none'
                                                    });
                                                    this.state.searchCondition.lookBack = value * 3600000;
                                                    console.log("选择时间之后search-condition： " + JSON.stringify(this.state.searchCondition));
                                                }
                                            }}
                                    />
                                </Form.Item>
                            </Col>
                        </Row>
                        {/*startTime-endTime*/}
                        <Row style={{display: this.state.timeRangeDisplay}}>
                            <Col span='12'/>
                            <Col span='4'>
                                <Form.Item label={this.props.intl.messages['link.search.by.startTime']}>
                                    <DatePicker className='date-picker-style'
                                                readOnly
                                                hasClear={false}
                                                popupAlign={"bl tl"}
                                                format={"YYYY-MM-DD"}
                                                defaultValue={'2018-09-30'}
                                                locale={datePickerLocale}/>
                                </Form.Item>
                            </Col>
                            <Col span='2'>
                                <Form.Item label=''>
                                    <Input value='20:12'/>
                                </Form.Item>
                            </Col>
                            <Col span='4'>
                                <Form.Item label={this.props.intl.messages['link.search.by.endTime']}>
                                    <DatePicker className='date-picker-style'
                                                readOnly
                                                hasClear={false}
                                                popupAlign={"bl tl"}
                                                format={"YYYY-MM-DD"}
                                                locale={datePickerLocale}/>
                                </Form.Item>
                            </Col>
                            <Col span='2'>
                                <Form.Item label=''>
                                    <Input value='22:12'/>
                                </Form.Item>
                            </Col>
                        </Row>
                        {/*annotation,duration,limit,sort*/}
                        <Row>
                            <Col span='12'>
                                <Form.Item label={this.props.intl.messages['link.search.by.annotation']}>
                                    <Input placeholder='For example: http.path=/foo/bar/ and cluster=foo and cache.miss'/>
                                </Form.Item>
                            </Col>
                            <Col span='4'>
                                <Form.Item label={this.props.intl.messages['link.search.by.resultlimit']}>
                                    <Input defaultValue='10'/>
                                </Form.Item>
                            </Col>
                            <Col span='4'>
                                <Form.Item label={this.props.intl.messages['link.search.by.minDuration']}>
                                    <Input placeholder='Ex: 100ms or 5s'/>
                                </Form.Item>
                            </Col>
                            <Col span='4'>
                                <Form.Item label={this.props.intl.messages['link.search.by.maxDuration']}>
                                    <Input placeholder='Ex: 100ms or 5s'/>
                                </Form.Item>
                            </Col>
                            {/*<Col span='4'>*/}
                                {/*<Form.Item label={this.props.intl.messages['link.search.by.sort']}>*/}
                                    {/*<Select className='search-select'*/}
                                            {/*style={{marginTop: '-1px'}}*/}
                                            {/*dataSource={sortOptions} onChange={(value)=>{*/}
                                                {/*alert("sort type: " + value);*/}
                                            {/*}}*/}
                                    {/*/>*/}
                                {/*</Form.Item>*/}
                            {/*</Col>*/}
                        </Row>
                        {/*提交查询按钮*/}
                        <Row>
                            <Button type="primary" size='medium' onClick={this.searchTraceList.bind(this)}>
                                {this.props.intl.messages['link.search.submit.search']}
                            </Button>
                        </Row>
                    </Form>
                </Card>
                <Table dataSource={this.state.linkList} isLoading={this.state.isLoading}>
                    <Table.Column title={this.props.intl.messages['link.list.table.traceId']} dataIndex='traceId'/>
                    <Table.Column title={this.props.intl.messages['link.list.table.spanName']} dataIndex='spanName'/>
                    <Table.Column title={this.props.intl.messages['link.list.table.timestamp']} dataIndex='timestamp'/>
                    <Table.Column title={this.props.intl.messages['link.list.table.duration']} dataIndex='duration'/>
                    <Table.Column title={this.props.intl.messages['link.list.table.spanNum']} dataIndex='spanNum'/>
                    <Table.Column title={this.props.intl.messages['link.list.table.successOrFail']} dataIndex='hasError'/>
                    <Table.Column title={this.props.intl.messages['link.list.table.detail']} dataIndex=''/>
                </Table>
            </div>
        </div>);
    }
}

export default injectIntl(LinkList);
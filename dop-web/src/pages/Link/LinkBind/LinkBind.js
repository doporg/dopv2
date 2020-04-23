import React,{Component} from 'react';
import {injectIntl} from "react-intl";
import Button from "@icedesign/base/lib/button";
import Table from "@icedesign/base/lib/table";
import Dialog from "@icedesign/base/lib/dialog";
import Form from "@icedesign/base/lib/form";
import Field from "@icedesign/base/lib/field";
import Select from "@icedesign/base/lib/select";
import API from "../../API";
import Axios from "axios";
import Input from "@icedesign/base/lib/input";
import Feedback from "@icedesign/base/lib/feedback";
import Tag from "@icedesign/base/lib/tag";

const Toast = Feedback.toast;

class LinkBind extends Component{
    constructor(props) {
        super(props);
        this.field = new Field(this);
        this.state = {
            bindList: [],

            isLoading: false,

            dialogVisible: false,

            projectList: [],
            serviceList: [],
            memberList: [],
            selectDisable: true
        }
    }

    componentDidMount() {
        this.setState({
            isLoading: true
        });
        let getBindListUrl = API.link + '/binds';
        let param = {
            cuser: window.sessionStorage.getItem('user-id')
        };
        Axios.get(getBindListUrl, {params: param}).then(response => {
            let list = response.data;
            this.setState({
                bindList: list,
                isLoading: false
            })
        }).catch((error)=>{
            console.log("获取绑定列表失败", error)
        })
    }

    submitCreateBind = () => {
        this.field.validate((errors, values) => {
            if (errors) {
                Toast.error(this.props.intl.messages['link.error.prompt.contentError']);
                return;
            }
            console.log(values);
            this.setState({
                isLoading: true
            });
            let project = this.state.projectList[values.projectIndex];
            let mList = this.state.memberList;
            let notifierId = [], notifierName = [], notifierEmail = [];
            values.notifierIndex.map((item) => {
                // console.log("arr: " + item);
                notifierId.push(mList[item].id);
                notifierName.push(mList[item].name);
                notifierEmail.push(mList[item].email);
            });
            let param = {
                title: values.bindTitle,
                cuser: window.sessionStorage.getItem('user-id'),
                cuserName: window.sessionStorage.getItem('user-name'),
                projectId: project.id,
                projectTitle: project.title,
                notifiedUid: notifierId.join(","),
                notifiedName: notifierName.join(","),
                notifiedEmail: notifierEmail.join(","),
                service: values.serviceName,
                threshold: values.threshold
            };
            // console.log("submit param: " + JSON.stringify(param));
            let newBindUrl = API.link + "/bind/new";
            Axios.post(newBindUrl, param).then(response => {
                this.setState({
                    isLoading: false
                });
            }).catch((errors) => {
                console.log("创建绑定失败，" + errors)
            });
        })
    };

    operateBind = (bid, opt) => {
        switch (opt) {
            case 'DELETE':
                alert("delete " + bid);
                break;
            case 'STOP':
                alert("stop " + bid);
                break;
            case 'START':
                alert("start " + bid);
                break;
        }
    };

    // 获得项目列表
    getProjectList = () => {
        // console.log("展开选择框");
        let getProjectListUrl = API.link + "/getProjectList";
        let param = {};
        this.setState({
            isLoading: true
        });
        Axios.get(getProjectListUrl, {params: param}).then(response=>
        {
            let projectListTmp = response.data;
            projectListTmp.map((item, index)=>{
                item.value = index;
                item.label = item.title;
            });
            this.setState({
                projectList: projectListTmp,
                isLoading: false,
                selectDisable: false
            });
        });
    };

    getServiceList = (projectId) => {
        // let projectId = getValue("projectIndex");
        // console.log("-----------getServiceList project: " + projectId);
        let getLinkListUrl = API.link + '/api/v2/services';
        Axios.get(getLinkListUrl).then(response => {
            let serviceListTmp = response.data;
            this.setState({serviceList: serviceListTmp});
        });
    };

    getMemberList = (projectId) => {
        let getMemberUrl = API.link + '/project/members';
        let param = {
            "user-id": window.sessionStorage.getItem("user-id"),
            "projectId": projectId,
            "organizationId" : 9
        };
        console.log("param: " + JSON.stringify(param));
        Axios.get(getMemberUrl,{params: param}).then(response => {
            let memberRes = response.data;
            memberRes.map((item, index) => {
                item.value = index;
                item.label = item.name;
            });
            this.setState({
                memberList: memberRes,
            });
        }).catch((error)=>{
            console.error("查询成员列表出错：", error);
        });
    };

    chooseProAndQueryInfo = (projectIndex) => {
        // console.log("value: " + projectIndex);
        // console.log("choose project: " + JSON.stringify(this.state.projectList[projectIndex]));
        let projectId = this.state.projectList[projectIndex].id;
        this.getServiceList(projectId);
        this.getMemberList(projectId);
    };

    createNewBind = () => {
        this.setState({
            dialogVisible: true
        });
    };

    render() {
        const {init} = this.field;
        const createDialogFooter = (<div>
            <Button type="primary" size="medium" onClick={this.submitCreateBind.bind(this)}>
                {this.props.intl.messages['link.confirm']}
            </Button>
            <Button type="normal" size="medium" onClick={()=>{this.setState({dialogVisible: false});}}>
                {this.props.intl.messages['link.cancel']}
            </Button>
        </div>);
        const formItemLayout = {
            labelCol: {
                fixedSpan: 8
            },
            wrapperCol: {
                span: 16
            }
        };
        const checkThreshold = (rule, value, callback) => {
            let pattern = /^0\.[0-9]{1,3}$/;
            let reg = new RegExp(pattern);
            if (!reg.test(value)) {
                callback(new Error(this.props.intl.messages['link.error.prompt.format']));
            } else {
                callback();
            }
        };
        const checkTitle = (rule, value, callback) => {
            if (value.trim() === '') {
                callback(new Error(this.props.intl.messages['link.error.prompt.format']));
            } else {
                callback();
            }
        };

        const renderState = (state) => {
            switch (state) {
                case 'RUNNING':
                    return <Tag shape='readonly' style={{margin:'0',width:'86px',backgroundColor: '#cfefdf', color: '#00a854'}}>
                        {this.props.intl.messages['link.bind.running']}
                    </Tag>;
                case 'FREE':
                    return <Tag shape='readonly' style={{color:'#333333',margin:'0',width:'86px',backgroundColor: '#ebecf0'}}>
                        {this.props.intl.messages['link.bind.free']}
                    </Tag>
            }
        };
        const renderOperate = (value, index, record) => {
            console.log(record);
            switch (record.state) {
                case 'RUNNING':
                    return <Button onClick={this.operateBind.bind(this, record.bid, 'STOP')}>{this.props.intl.messages['link.bind.stop']}</Button>;
                case 'FREE':
                    return <Button onClick={this.operateBind.bind(this, record.bid, 'DELETE')}>{this.props.intl.messages['link.bind.delete']}</Button>;
            }
        };
        return (
            <div>
                <div style={{marginBottom: '10px'}}>
                    <Button type='primary' size='medium' onClick={this.createNewBind.bind(this)}>
                        {this.props.intl.messages['link.bind.create']}
                    </Button>

                    {/*<Button type='primary' size='medium' onClick={this.createNewBind.bind(this)}>*/}
                        {/*{this.props.intl.messages['link.bind.create']}*/}
                    {/*</Button>*/}
                </div>
                <Dialog
                    visible={this.state.dialogVisible}
                    footer={createDialogFooter}
                    footerAlign='right' shouldUpdatePosition
                    minMargin={50}
                    onClose={()=>{this.setState({dialogVisible: false});}}
                    title={this.props.intl.messages['link.bind.create']}
                    style={{width: "50%"}}>
                    <Form labelAlign='left' field={this.field} direction='ver'>
                        <Form.Item label={this.props.intl.messages['link.bind.form.title']} required {...formItemLayout}>
                            <Input placeholder='请填写标题'
                                   defaultValue=''
                                   maxLength={25} hasLimitHint cutString={true}
                                   {...init('bindTitle', {
                                       rules: [
                                           {
                                               required: true,
                                               whiteSpace: true,
                                               message: "请填写标题 "
                                           },
                                           {
                                               validator: checkTitle.bind(this)
                                           }
                                       ]
                                   })}/>
                        </Form.Item>
                        <Form.Item label={this.props.intl.messages['link.choose.project']} required {...formItemLayout}>
                            <Select dataSource={this.state.projectList}
                                    showSearch={true}
                                    placeholder='请选择项目'
                                    autoWidth={true}
                                    onOpen={this.getProjectList.bind(this)}
                                    className='search-select'
                                    {...init("projectIndex", {
                                        rules: [
                                            {
                                                required: true,
                                                message: "请选择项目"
                                            }
                                        ],
                                        props: {
                                            onChange: (v) => {
                                                this.chooseProAndQueryInfo(v);
                                            }
                                        }
                                    })}/>
                        </Form.Item>
                        <Form.Item label={this.props.intl.messages['link.choose.service']} required {...formItemLayout}>
                            <Select dataSource={this.state.serviceList}
                                    disabled={this.state.selectDisable}
                                    placeholder='请选择服务'
                                    showSearch={true}
                                    autoWidth={true}
                                    className='search-select'
                                    // onOpen={this.getServiceList.bind(this)}
                                    {...init('serviceName', {
                                        rules: [
                                            {
                                                required: true,
                                                message: "请选择服务"
                                            }
                                        ]
                                    })}/>
                        </Form.Item>
                        <Form.Item label={this.props.intl.messages['link.bind.tabletitle.threshold']} required {...formItemLayout}>
                            <Input placeholder='请填写阈值'
                                   {...init('threshold',{
                                       rules: [
                                           {
                                               required: true,
                                               message: "请填写阈值"
                                           },
                                           { validator: checkThreshold.bind(this)}
                                       ]
                                   })}/>
                        </Form.Item>
                        <Form.Item label={this.props.intl.messages['link.bind.tabletitle.notifier']} required {...formItemLayout}>
                            <Select dataSource={this.state.memberList}
                                    placeholder='请选择通知人员'
                                    className='search-select'
                                    showSearch={true}
                                    autoWidth={true}
                                    multiple={true}
                                    // onOpen={this.getMemberList.bind(this)}
                                    disabled={this.state.selectDisable}
                                    {...init('notifierIndex',{
                                        rules: [
                                            {
                                                required: true,
                                                message: "请填写成员"
                                            }
                                        ]
                                    })}/>
                        </Form.Item>
                    </Form>
                </Dialog>
                <Table dataSource={this.state.bindList}
                       isLoading={this.state.isLoading}
                       locale={{"empty": this.props.intl.messages['link.no.data']}}
                >
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.id']} dataIndex='bid' align='center' width='10%'/>
                    {/*<Table.Column title={this.props.intl.messages['link.bind.tabletitle.title']} dataIndex='title' align='center' width='20%'/>*/}
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.projectName']} dataIndex='projectTitle' align='center'/>
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.service']} dataIndex='service' align='center'/>
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.threshold']} dataIndex='threshold' align='center'/>
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.state']} cell={renderState} dataIndex='state' align='center'/>
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.notifier']} dataIndex='notifiedName' align='center'/>
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.operate']} cell={renderOperate} align='center'/>
                </Table>
            </div>
        );
    }
}

export default injectIntl(LinkBind);
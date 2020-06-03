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
import Grid from "@icedesign/base/lib/grid";
import '../linkStyles.css';
import Pagination from "@icedesign/base/lib/pagination";
import {Link} from "react-router-dom";

const Toast = Feedback.toast;
const {Row, Col} = Grid;
class LinkBind extends Component{
    constructor(props) {
        super(props);
        this.field = new Field(this);
        this.state = {
            bindList: [],
            pageNo: 1,
            pageSize: 10,
            totalCount: 0,
            isLoading: true,

            dialogVisible: false,

            projectList: [],
            serviceList: [],
            memberList: [],

            projectSelectDisable: false,
            serviceSelectDisable: true,
            memberSelectDisable: true,

            currentOpt: '',
            bidDisplay: 'block'
        }
    }

    componentDidMount() {
        this.loadBindData("", 1);
    }

    loadBindData = (keywords, currentPage) => {
        let getBindListUrl = API.link + '/v2/link/bind';
        let param = {
            cuser: window.sessionStorage.getItem('user-id'),
            pageNo: currentPage,
            pageSize: this.state.pageSize,
            keyword: keywords
        };
        Axios.get(getBindListUrl, {params: param}).then(response => {
            let pageableDataList = response.data;
            this.setState({
                bindList: pageableDataList.pageList,
                totalCount: pageableDataList.totalCount,
                pageNo: pageableDataList.pageNo
            })
        }).catch((error)=>{
            console.log("获取绑定列表失败", error)
        }).finally(()=>{
            this.setState({
                isLoading: false
            })
        })
    };

    handleSubmit = () => {
        let opt = this.state.currentOpt;
        if (opt === 'CREATE') {
            this.createBind();
        } else if (opt === 'MODIFY') {
            this.confirmModifyBind();
        }
    };

    createBind = () => {
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
            let newBindUrl = API.link + "/v2/link/bind";
            Axios.post(newBindUrl, param).then(response => {
                this.setState({
                    dialogVisible: false
                });
                this.loadBindData("",1)
            }).catch((errors) => {
                console.log("创建监控绑定失败，" + errors)
            }).finally(()=>{
                this.setState({
                    isLoading: false
                });
            });
        })
    };

    deleteBind = (bindRecord) => {
        let content;
        if (bindRecord.state === 'RUNNING') {
            content = '确认停止并删除吗？'
        } else {
            content = '确认删除吗？'
        }
        Dialog.confirm({
            hasMask: false,
            content: content,
            title: "删除监控绑定",
            onOk: () => {
                let deleteBindUrl = API.link + '/v2/link/bind/delete/' + bindRecord.bid;
                // let param = {
                //     bid: bindRecord.bid,
                //     operation: 'DEL'
                // };
                Axios.post(deleteBindUrl).then(response => {
                    let {getValue} = this.field;
                    let pageNo = this.state.pageNo;
                    if (this.state.bindList.length <= 1) {
                        pageNo = pageNo - 1;
                    }
                    this.loadBindData(getValue("keywords"), pageNo)
                }).catch((error)=>{
                    console.log("删除失败: "+JSON.stringify(error))
                });
            },
            onCancel: () => {}
        });
    };

    confirmModifyBind = () => {
        this.field.validate((errors, values) => {
            if (errors) {
                Toast.error(this.props.intl.messages['link.error.prompt.contentError']);
                return;
            }
            if (this.state.tmpBind.state === 'RUNNING') {
                Dialog.confirm({
                    hasMask: false,
                    content: '监控将被停止并修改，确认继续吗？',
                    title: '修改监控',
                    onOk: () => {this.modifyBind()},
                    onCancel: () => {}
                });
            } else {
                this.modifyBind();
            }
        });
    };

    modifyBind = () => {
        let beforeBind = this.state.tmpBind;
        let {getValue} = this.field;
        beforeBind.title = getValue('bindTitle');

        let mList = this.state.memberList;
        let notifierId = [], notifierName = [], notifierEmail = [];
        getValue('notifierIndex').map((item) => {
            notifierId.push(mList[item].id);
            notifierName.push(mList[item].name);
            notifierEmail.push(mList[item].email);
        });
        beforeBind.notifiedUid = notifierId.join(",");
        beforeBind.notifiedName = notifierName.join(",");
        beforeBind.notifiedEmail = notifierEmail.join(",");

        let modifyBindUrl = API.link  + "/v2/link/bind/" + beforeBind.bid;
        Axios.post(modifyBindUrl, beforeBind).then((response) => {
            this.setState({
                dialogVisible: false
            });
            this.loadBindData("",1)
        }).catch((error) => {
            console.log("更新失败", error);
        }).finally(()=>{
            this.setState({
                isLoading: false
            })
        })

    };

    // 获得项目列表
    getProjectList = () => {
        // console.log("展开选择框");
        let getProjectListUrl = API.link + "/projects";
        let param = {
            userId: window.sessionStorage.getItem('user-id')
        };
        this.setState({
            isLoading: true
        });
        Axios.get(getProjectListUrl, {params: param}).then(response=> {
            let projectListTmp = response.data;
            projectListTmp.map((item, index)=>{
                item.value = index;
                item.label = item.title;
            });
            this.setState({
                projectList: projectListTmp
            });
        }).catch((error) => {
            console.log("获取项目列表失败", error);
        }).finally( () => {
            this.setState({isLoading: false})
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

            if (this.state.currentOpt === 'MODIFY') {
                let memberSelectValue = [];
                let currMem = this.state.tmpBind.notifiedUid.split(",");
                memberRes.map((item, index) => {
                    if (currMem.indexOf(""+item.id+"") >= 0) {
                        memberSelectValue.push(index);
                    }
                });
                this.field.setValue('notifierIndex', memberSelectValue);
            }
        }).catch((error)=>{
            console.error("查询成员列表出错：", error);
        });
    };

    chooseProAndQueryInfo = (projectIndex) => {
        // console.log("value: " + projectIndex);
        // console.log("choose project: " + JSON.stringify(this.state.projectList[projectIndex]));
        let projectId = this.state.projectList[projectIndex].id;
        let {setValues} = this.field;
        setValues({
            'serviceName': undefined,
            'notifierIndex': undefined
        });
        this.setState({
            serviceSelectDisable: false,
            memberSelectDisable: false,
        });
        this.getServiceList(projectId);
        this.getMemberList(projectId);
    };

    openDialog = (currentOpt, data) => {
        let {setValues, reset} = this.field;
        if (currentOpt === 'CREATE') {
            reset();
            this.setState({
                dialogVisible: true,
                currentOpt: currentOpt,
                bidDisplay: 'none',
                projectSelectDisable: false,
                serviceSelectDisable: true,
                memberSelectDisable: true,
            });
        } else if (currentOpt === 'MODIFY') {
            setValues({
                'bindId': data.bid,
                'bindTitle': data.title,
                'projectIndex': data.projectTitle,
                'serviceName': data.service,
                'threshold': data.threshold,
                // 'notifierIndex': data.notifiedName.split(',')
            });
            this.setState({
                dialogVisible: true,
                currentOpt: currentOpt,
                bidDisplay: 'block',
                projectSelectDisable: true,
                serviceSelectDisable: true,
                memberSelectDisable: false,
                tmpBind: data
            });
            this.getMemberList(data.projectId)
        }
    };

    closeDialog = () => {
        this.setState({
            dialogVisible: false
        });
        this.field.reset();
    };

    changePage = (currentPage) => {
        this.setState({
            isLoading: true
        });
        let {getValue} = this.field;
        this.loadBindData(getValue('keywords'), currentPage);
    };

    searchBindByKeywords = () => {
        let {getValue} = this.field;
        this.loadBindData(getValue('keywords'), 1);
    };

    render() {
        const {init} = this.field;
        const createDialogFooter = (<div>
            <Button type="primary" size="medium" onClick={this.handleSubmit.bind(this)}>
                {this.props.intl.messages['link.confirm']}
            </Button>
            <Button type="normal" size="medium" onClick={this.closeDialog.bind(this)}>
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
            return (
                <div>
                    <Link to={"/link/notify-setting/" + record.bid}>
                        <Button type="primary" size="small" className="operate-button">
                            {this.props.intl.messages['link.view']}
                        </Button>
                    </Link>
                    <Button type="normal" size="small" className="operate-button" onClick={this.openDialog.bind(this, 'MODIFY',record)}>
                        {this.props.intl.messages['link.modify']}
                    </Button>
                    <Button type="primary" shape="warning" size="small" onClick={this.deleteBind.bind(this, record)}>
                        {this.props.intl.messages['link.delete']}
                    </Button>
                </div>)
        };

        return (
            <div>
                <Form field={this.field}>
                    <Row>
                        <Col span='10'>
                            <Form.Item wrapperCol={{span: 24}}>
                                <Input placeholder='根据标题关键字，回车查找'
                                       defaultValue=''
                                       hasClear={true}
                                       onPressEnter={this.searchBindByKeywords.bind(this)}
                                       {...init("keywords", {})}
                                />
                            </Form.Item>
                        </Col>
                        <Col style={{textAlign: 'right'}}>
                            <Button type='primary' size='medium' onClick={this.openDialog.bind(this, 'CREATE',{})}>
                                {this.props.intl.messages['link.bind.create']}
                            </Button>
                        </Col>
                    </Row>
                </Form>
                <Dialog
                    visible={this.state.dialogVisible}
                    footer={createDialogFooter}
                    footerAlign='right' shouldUpdatePosition
                    minMargin={50}
                    hasMask={false}
                    onClose={()=>{this.setState({dialogVisible: false});}}
                    title={
                        this.state.currentOpt === 'CREATE' ? this.props.intl.messages['link.bind.create'] : this.props.intl.messages['link.bind.title.modify']
                    }
                    // title={this.props.intl.messages['link.bind.create']}
                    style={{width: "50%"}}>
                    <Form labelAlign='left' field={this.field} direction='ver'>
                        <Form.Item style={{display: this.state.bidDisplay}} label={this.props.intl.messages['link.bind.tabletitle.id']} {...formItemLayout}>
                            <Input {...init('bindId')} readOnly={true}/>
                        </Form.Item>
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
                                    disabled={this.state.projectSelectDisable}
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
                                    disabled={this.state.serviceSelectDisable}
                                    placeholder='请选择服务'
                                    showSearch={true}
                                    autoWidth={true}
                                    className='search-select'
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
                                    disabled={this.state.memberSelectDisable}
                                    {...init('notifierIndex',{
                                        rules: [
                                            {
                                                required: true,
                                                message: "请填写成员"
                                            }
                                        ],
                                        props: {
                                            onChange: (v) => {
                                                console.log("更新通知人员名单", v)
                                            }
                                        }
                                    })}/>
                        </Form.Item>
                    </Form>
                </Dialog>
                <Table dataSource={this.state.bindList}
                       isLoading={this.state.isLoading}
                       locale={{"empty": this.props.intl.messages['link.no.data']}}
                >
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.id']} dataIndex='bid' align='center' width='7%'/>
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.title']} dataIndex='title' align='center'/>
                    {/*<Table.Column title={this.props.intl.messages['link.bind.tabletitle.projectName']} dataIndex='projectTitle' align='center'/>*/}
                    {/*<Table.Column title={this.props.intl.messages['link.bind.tabletitle.service']} dataIndex='service' align='center'/>*/}
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.ctime']} dataIndex='ctime' align='center' width='24%'/>
                    {/*<Table.Column title={this.props.intl.messages['link.bind.tabletitle.threshold']} dataIndex='threshold' align='center'/>*/}
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.state']} cell={renderState} dataIndex='state' align='center' width='15%'/>
                    {/*<Table.Column title={this.props.intl.messages['link.bind.tabletitle.notifier']} dataIndex='notifiedName' align='center'/>*/}
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.operate']} cell={renderOperate} align='center' width='22%'/>
                </Table>
                <Pagination current={this.state.pageNo}
                            pageSize={this.state.pageSize}
                            total={this.state.totalCount}
                            style={{textAlign: 'center', paddingTop:'10px'}}
                            onChange={this.changePage.bind(this)}
                />
            </div>
        );
    }
}

export default injectIntl(LinkBind);
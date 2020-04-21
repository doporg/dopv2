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

    submitCreateBind = () => {
        this.field.validate((errors, values) => {
            if (errors) {
                Toast.error(this.props.intl.messages['link.error.prompt.contentError']);
                return;
            }
            console.log(values);

        })
    };

    // 获得项目列表
    getProjectList = () => {
        console.log("展开选择框");
        let getProjectListUrl = API.link + "/getProjectList";
        let param = {};
        this.setState({
            isLoading: true
        });
        Axios.get(getProjectListUrl, {params: param}).then(response=>
        {
            let projectListTmp = response.data;
            // console.log("AAAAAAAAAAAAAA:" + JSON.stringify(projectListTmp));
            projectListTmp.map((item, index)=>{
                item.value = item.id;
                item.label = item.title;
            });
            // console.log("BBBBBBBBBBBBBBBBB:" + JSON.stringify(projectListTmp));
            this.setState({
                projectList: projectListTmp,
                isLoading: false,
                selectDisable: false
            });
        });
    };

    getServiceList = () => {
        let getLinkListUrl = API.link + '/api/v2/services';
        Axios.get(getLinkListUrl).then(response => {
            let serviceListTmp = response.data;
            this.setState({serviceList: serviceListTmp});
        });
    };

    getMemberList = () => {
        this.setState({
            memberList: ['worker1', 'worker2', 'worker3'],
            selectDisable: false
        })
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

        return (
            <div>
                <Button type='primary' size='medium' style={{marginBottom: '10px'}} onClick={this.createNewBind.bind(this)}>
                    {this.props.intl.messages['link.bind.create']}
                </Button>
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
                                   {...init('bind-title', {
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
                                    {...init("project-title", {
                                        rules: [
                                            {
                                                required: true,
                                                message: "请选择项目"
                                            }
                                        ]
                                    })}/>
                        </Form.Item>
                        <Form.Item label={this.props.intl.messages['link.choose.service']} required {...formItemLayout}>
                            <Select dataSource={this.state.serviceList}
                                    disabled={this.state.selectDisable}
                                    placeholder='请选择服务'
                                    showSearch={true}
                                    autoWidth={true}
                                    className='search-select'
                                    onOpen={this.getServiceList.bind(this)}
                                    {...init('service-name', {
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
                                    onOpen={this.getMemberList.bind(this)}
                                    disabled={this.state.selectDisable}
                                    {...init('notifier',{
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
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.id']} dataIndex='bid' align='center'/>
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.title']} dataIndex='title' align='center'/>
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.projectName']} dataIndex='projectTitle' align='center'/>
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.microservice']} dataIndex='service' align='center'/>
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.threshold']} dataIndex='threshold' align='center'/>
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.state']} dataIndex='state' align='center'/>
                    <Table.Column title={this.props.intl.messages['link.bind.tabletitle.notifier']} dataIndex='notifiedName' align='center'/>
                </Table>
            </div>
        );
    }
}

export default injectIntl(LinkBind);
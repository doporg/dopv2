import {injectIntl} from "react-intl";
import {Feedback, Form} from "@icedesign/base/index";
import {FormBinder, FormBinderWrapper, FormError} from '@icedesign/form-binder';
import { Dialog, Input, Radio, Field } from "@icedesign/base";
import React from 'react';
import Axios from "axios/index";
import API from "../../API";

const Toast = Feedback.toast;
const { Item: FormItem } = Form;
const { Group: RadioGroup } = Radio;
class ContactInfo extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: {
                name: '',
                mail:'',
                phone:'',
                remark: '',
            },
            align: "cc cc",
            style: {
                width: "35%"
            },
        };
    }

    handleSubmit(event) {
        let url=API.alert+"/alert/contact/info";
        let _this = this;
        this.refs.form.validateAll((error, value) => {
            if (error == null) {

                let param = this.state.value;
                Axios({
                    method:"POST",
                    headers:{
                        'Content-Type':'application/x-www-form-urlencoded',
                        'x-login-user':1,
                    },

                    url:url,
                    params:param,
                    success: function (rs) {
                        if (rs == 1) {
                            alert("添加成功！");
                            Toast.success(_this.props.intl.messages['test.newGroup.success.mes']);
                            window.location.href = document.referrer;
                        } else {

                            Toast.error(_this.props.intl.messages['test.newGroup.success.mes']);
                            alert("添加失败！");
                        }
                    }
                }).then(()=>{
                    this.setState({
                        visible: false,
                    })
                })
            }
        });
        event.preventDefault();
    }

    componentWillReceiveProps(nextProps) {
        this.setState({
            visible: nextProps.visible,
            title: nextProps.title,
        })
    }

    onClose() {
        this.setState({
            visible: false
        });
    }


    render() {
        return (
            <Dialog
            visible={this.state.visible}
            onOk={this.handleSubmit.bind(this)}
            onCancel={this.onClose.bind(this)}
            onClose={this.onClose.bind(this)}
            title={this.state.title}
            style={this.state.style}
            align={this.state.align}>
                <FormBinderWrapper
                    value={this.state.value}   // 传递 values
                    onChange={this.formChange} // 响应 onChange
                    ref="form"
                >
                    <div>
                        <br/>
                        <div>
                            <div style={styles.formItem}>
                                <span style={styles.formLabel}>{this.props.intl.messages[ 'alert.newContact.name']}</span>
                                <FormBinder name="name" required message="请输入正确的名称" >
                                    <Input style={styles.input}/>
                                </FormBinder>
                                <FormError style={styles.formError} name="name" />
                            </div>
                        </div>
                        <div>
                            <div style={styles.formItem}>
                                <span style={styles.formLabel}>{this.props.intl.messages[ 'alert.newContact.mail']}</span>
                                <FormBinder name="mail" type="email" required message="请输入正确的邮箱">
                                    <Input style={styles.input} />
                                </FormBinder>
                                <FormError style={styles.formError} name="mail" />
                            </div>
                        </div>
                        <div>
                            <div style={styles.formItem}>
                                <span style={styles.formLabel}>{this.props.intl.messages[ 'alert.newContact.phone']}</span>
                                <FormBinder name="phone" required message="请输入正确的电话">
                                    <Input style={styles.input}/>
                                </FormBinder>
                                <FormError style={styles.formError} name="phone" />
                            </div>
                        </div>
                        <div>
                            <div style={styles.formItem}>
                                <span style={styles.formLabel}>{this.props.intl.messages[ 'alert.newContact.remark']}</span>
                                <FormBinder name="remark" >
                                    <Input style={styles.input}/>
                                </FormBinder>
                                <FormError style={styles.formError} name="remark" />
                            </div>
                        </div>
                    </div>


                </FormBinderWrapper>

            </Dialog>


        );
    }
}

const styles = {
    input:{
        width: '230px',
    },
    formItem: {
        marginBottom: '20px'
    },
    formLabel: {
        width: '80px',
        marginLeft: '30px',
        marginRight: '20px',
        textAlign: 'right',
        display: 'inline-block'
    },
    formError: {
        marginLeft: '10px',
    },
    preview: {
        border: '1px solid #eee',
        marginTop: 20,
        padding: 10
    }
}

export default injectIntl(ContactInfo)
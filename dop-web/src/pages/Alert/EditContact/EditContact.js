import {injectIntl} from "react-intl";
import {Feedback, Form} from "@icedesign/base/index";
import {FormBinder, FormBinderWrapper, FormError} from '@icedesign/form-binder';
import { Dialog, Input, Radio, Field } from "@icedesign/base";
import React, {Component} from 'react';
import Axios from "axios/index";
import API from "../../API";

import qs from 'qs';
const Toast = Feedback.toast;



class EditContact extends Component{

    constructor(props) {
        super(props);

        const contactId = this.props.match.params.contactId;
        this.state = {
            title: this.props.intl.messages['alert.editContact.title'],
            visible: true,
            data:{
                id: contactId,
                name: "",
                mail: "",
                phone: "",
                remark: "",
                mtime: "",
            },

            align: "cc cc",
            style: {
                width: "35%"
            },
        };
        this.getInfo(contactId);
    }

    getInfo(contactId) {
        let url = API.alert + '/alert/contact/info';
        let _this = this;
        Axios.get(url, {
            params: {
                contactId:contactId
            }
        }).then(function (response) {
            _this.setState({
                data: response.data,

            });
        }).catch(function (error) {
            console.log(error);
        });
    }

    handleSubmit(event) {
        let url=API.alert+"/alert/contact/info";

        this.refs.form.validateAll((error, value) => {
            if (error == null) {
                let params = new URLSearchParams();
                params.append("contact", JSON.stringify(this.state.data));
                let data = this.state.data;
                Axios.put(url,data).then(()=>{
                    this.props.history.push("/alert/contact");
                })
            }
        });
        event.preventDefault();
    }



    onClose() {
        this.props.history.push('/alert/contact');
    }

    handleChange(e){
        this.setState({
            name: e.target.value
        })
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
                <FormBinderWrapper value={this.state.data} onChange={this.formChange} ref="form">
                    <div>
                        <div style={styles.formItem}>
                            <span style={styles.formLabel}>{this.props.intl.messages[ 'alert.newContact.name']}</span>
                            <FormBinder  required message="请输入正确的名称" >
                                <Input style={styles.input} name="name"/>
                            </FormBinder>
                            <FormError style={styles.formError} />
                        </div>
                        <div>
                            <div style={styles.formItem}>
                                <span style={styles.formLabel}>{this.props.intl.messages[ 'alert.newContact.mail']}</span>
                                <FormBinder type="email" required message="请输入正确的邮箱">
                                    <Input style={styles.input} name="mail"  />
                                </FormBinder>
                                <FormError style={styles.formError} />
                            </div>
                        </div>
                        <div>
                            <div style={styles.formItem}>
                                <span style={styles.formLabel}>{this.props.intl.messages[ 'alert.newContact.phone']}</span>
                                <FormBinder required message="请输入正确的电话">
                                    <Input style={styles.input} name="phone"  />
                                </FormBinder>
                                <FormError style={styles.formError} />
                            </div>
                        </div>
                        <div>
                            <div style={styles.formItem}>
                                <span style={styles.formLabel}>{this.props.intl.messages[ 'alert.newContact.remark']}</span>
                                <FormBinder>
                                    <Input style={styles.input} name="remark" />
                                </FormBinder>
                                <FormError style={styles.formError}  />
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
export default injectIntl(EditContact);
import FormBinderWrapper, {
    FormBinder,
    FormError
} from '@icedesign/form-binder';
import { Dialog, Input, Radio, Field } from "@icedesign/base";
import React, {Component} from 'react';
import {injectIntl} from "react-intl";
import Axios from "axios/index";
import API from "../../API";

const value = {
    a: {
        b: 'abc'
    }
};



class Edit extends Component{
    constructor(props) {
        super(props);

        const contactId = this.props.match.params.contactId;
        this.state = {
            title: this.props.intl.messages['alert.editContact.title'],
            visible: true,
            id: contactId,
            data: {
                name: "",
                mail: "",
                phone: "",
                remark: "",
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

    formChange = (value) => {
        console.log(value);
    };

    render(){
        return (
            <FormBinderWrapper value={this.state.data} onChange={this.formChange}>
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
        )
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
export default injectIntl(Edit);
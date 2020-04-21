import { Dialog, Form, Input, Button, Radio, Field } from "@icedesign/base";
import {injectIntl} from "react-intl";
import React from 'react';

const { Item: FormItem } = Form;
const { Group: RadioGroup } = Radio;

class ContactInfo extends React.Component {

    field = new Field(this);
    constructor(props) {
        super(props);
        this.state = {
            // visible: false,
            align: "cc cc",
            style: {
                width: "50%"
            }
        };
    }
    componentWillReceiveProps(nextProps) {
        this.setState({
            visible: nextProps.visible,
            title: nextProps.title,
        })
    }

    render() {
        const formItemLayout = {
            labelCol: {
                span: 6
            },
            wrapperCol: {
                span: 14
            }
        };
        const { init, getError, getState } = this.field;
        return (

            <span>
        <Dialog
            visible={this.state.visible}
            onOk={this.onClose.bind(this)}
            onCancel={this.onClose.bind(this)}
            onClose={this.onClose.bind(this)}
            title={this.state.title}
            style={this.state.style}
            align={this.state.align}>

            <div className="new-strategy-container">
                {/*<div className="div-new-strategy-top">*/}
                    {/*{this.props.intl.messages["alert.newStrategy.top"]}*/}
                {/*</div>*/}
                <Form field={this.field}>
                    <FormItem
                        label="用户名："
                        {...formItemLayout}
                        hasFeedback
                        help={
                            getState("name") === "validating"
                                ? "校验中..."
                                : (getError("name") || []).join(", ")
                        }
                    >
                              <Input
                                  maxLength={20}
                                  hasLimitHint
                                  {...init("name", {
                                      rules: [
                                          { required: true, min: 5, message: "用户名至少为 5 个字符" },
                                          { validator: this.userExists }
                                      ]
                                  })}
                              />
                        </FormItem>
                        <FormItem label="邮箱：" {...formItemLayout} hasFeedback>
                              <Input
                                  type="email"
                                  {...init("email", {
                                      rules: [
                                          { required: true, trigger: "onBlur" },
                                          {
                                              type: "email",
                                              message: <span>请输入正确的邮箱地址</span>,
                                              trigger: ["onBlur", "onChange"]
                                          }
                                      ]
                                  })}
                              />
                        </FormItem>
                        <FormItem label="电话：" {...formItemLayout} hasFeedback>
                              <Input
                                  type="phone"
                                  {...init("phone", {
                                      rules: [
                                          { required: true, trigger: "onBlur" },
                                          {
                                              type: "phone",
                                              message: <span>请输入正确的电话号码</span>,
                                              trigger: ["onBlur", "onChange"]
                                          }

                                      ]
                                  })}
                              />
                        </FormItem>
                        <FormItem label="备注：" {...formItemLayout}>
                            <Input
                                multiple
                                maxLength={20}
                                hasLimitHint
                            />
                        </FormItem>
                </Form>

            </div>
        </Dialog>
      </span>
        );
    }

    onClose() {
        this.setState({
            visible: false
        });
    }

    open() {
        this.setState({
            visible: true
        });
    }

}

export default injectIntl(ContactInfo)
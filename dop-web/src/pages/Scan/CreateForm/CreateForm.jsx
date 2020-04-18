import {
  Form,
  Button,
  Radio,
  Select,
  DatePicker,
  NumberPicker,
  Field,
  Input,
  Feedback,
  Loading,
} from "@icedesign/base";

import Axois from "axios";
import { API_CREATE_SCAN } from "../utility";

import React from "react";
import { injectIntl } from "react-intl";
import Axios from "axios";
import qs from "qs";

import {Link, withRouter} from "react-router-dom";

const { toast } = Feedback;

const FormItem = Form.Item;
const RadioGroup = Radio.Group;

class CreateForm extends React.Component {
  constructor(props) {
    super(props);
    this.field = new Field(this);
    this.state = {
      visible: false,
    };
  }
  handleReset(e) {
    e.preventDefault();
    this.field.reset();
    console.log("reset");
  }
  handleSubmit(e) {
    e.preventDefault();
    this.field.validate((errors, values) => {
      if (errors) {
        console.log("Errors in form!!!");
        return;
      }
      console.log("Submit!!!");
      const data = {
        code_path: values.gitPath,
        project_name: values.taskName,
        code_user: values.gitUser,
        code_pwd: values.gitPwd,
        language_type: values.languageType,
      };
      this.setState({
        visible:true
      })
      Axios.post(API_CREATE_SCAN, data)
        .then((response) => {
          this.setState({
            visible: false,
          });
          console.log(response.status);
          if(response.status === 200){
            toast.success(this.props.intl.messages["scan.quick.start.success"]);
            console.log(response.data)
            let route = '/scan/result/' + response.data;
            this.props.history.push(route);
          }else{
            toast.error(this.props.intl.messages['scan.quick.start.fail']);
          }
        })
        .catch((error) => {
          console.log(error);
          this.setState({
            visible: false,
          });
          toast.error(this.props.intl.messages['scan.quick.start.fail']);
        });
    });
  }

  render() {
    const init = this.field.init;
    const formItemLayout = {
      labelCol: {
        span: 6,
      },
      wrapperCol: {
        span: 14,
      },
    };
    const messages = this.props.intl.messages;
    const languageTip = messages["scan.quick.language.tip"];
    return (
      <Loading
        shape="fusion-reactor"
        visible={this.state.visible}
        className="next-loading my-loading"
        style={{ width: "100%" }}
        tip={messages['scan.quick.tip']}
      >
        <Form
          field={this.field}
          style={{ background: "white", align: "center" }}
        >
          <br />
          <FormItem
            {...formItemLayout}
            required
            label={messages["scan.quick.taskName"]}
          >
            <Input
              placeholder={messages["scan.quick.taskName.tip"]}
              id="taskName"
              name="taskName"
              style={{ width: 400 }}
              {...init("taskName", {
                rules: [{ required: true,message:messages['scan.quick.taskName.tip']}],
              })}
            />
          </FormItem>
          <FormItem
            {...formItemLayout}
            required
            label={messages["scan.quick.git.path"]}
          >
            <Input
              placeholder={messages["scan.quick.git.path.tip"]}
              id="gitPath"
              name="gitPath"
              style={{ width: 400 }}
              {...init("gitPath", {
                rules: [{ required: true ,message:messages['scan.quick.git.path.tip']}],
              })}
            />
          </FormItem>
          <FormItem
            {...formItemLayout}
            required
            label={messages["scan.quick.git.account"]}
          >
            <Input
              placeholder={messages["scan.quick.git.user.tip"]}
              id="gitUser"
              name="gitUser"
              style={{ width: 300 }}
              {...init("gitUser", {
                rules: [{ required: true,message:messages['scan.quick.git.user.tip'] }],
              })}
            />
          </FormItem>
          <FormItem {...formItemLayout} required label="">
            <Input
              htmlType="password"
              placeholder={messages["scan.quick.git.password.tip"]}
              id="gitPwd"
              name="gitPwd"
              style={{ width: 300 }}
              {...init("gitPwd", {
                rules: [{ required: true ,message:messages['scan.quick.git.password.tip']}],
              })}
            />
          </FormItem>
          <FormItem label={messages["scan.quick.language"]} {...formItemLayout}>
            <Select
              placeholder={messages["scan.quick.language.tip"]}
              style={{ width: 200 }}
              {...init("languageType", {
                rules: [{ required: true,message:messages['scan.quick.language.tip'] }],
              })}
            >
              <li value="JAVA">Java</li>
              <li value="PYTHON">Python</li>
              <li value="GO">Go</li>
              <li value="JAVASCRIPT">JavaScript</li>
              <li value="CSHARP">C#</li>
              <li value="PHP">php</li>
            </Select>
          </FormItem>
          <FormItem wrapperCol={{ span: 16, offset: 6 }}>
            <Button type="primary" onClick={this.handleSubmit.bind(this)}>
              {messages["scan.quick.start"]}
            </Button>
            &nbsp;&nbsp;&nbsp;
            <Button onClick={this.handleReset.bind(this)}>
              {messages["scan.quick.reset"]}
            </Button>
          </FormItem>
          <br />
        </Form>
      </Loading>
    );
  }
}

export default injectIntl(withRouter(CreateForm));

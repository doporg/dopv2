import React from 'react';
import {Select, Input, Button, Field, Switch} from "@icedesign/base";
import Axios from 'axios';
import API from "../../API";
import {Feedback} from '@icedesign/base';
import { FormBinderWrapper } from '@icedesign/form-binder';
import {injectIntl } from 'react-intl';

import './NewStrategy.css'

const {toast} = Feedback;

class NewStrategy extends React.Component {

    field = new Field(this);
    constructor(props) {
        super(props);
        let {username} = this.props.match.params;
        this.state = {
            username: username,
            strategyName: "",
            state: true,
            level: "",
            description: "",

            strategy: "",
            ref: "",
            loadingVisible: true,
            creatingVisible:false,
        };
    }

    componentWillMount() {
        // let url = API.code + "/projects/" + this.state.projectid + "/repository/branchandtag";
        // Axios.get(url).then(response => {
        //     this.setState({
        //         refOptions: response.data,
        //         loadingVisible: false
        //     })
        // })
    }

    cancel() {
        // this.props.history.push("/code/" + this.state.projectid + "/branches");
    }

    selectRef(value) {
        this.setState({
            ref: value
        })
    }

    changeStrategyName(e) {
        this.setState({
            strategyName: e.target.value
        })
    }

    changeDescription(e){

    }


    addStrategy() {
        let url=API.alert+"/alert/test";
        Axios({
            method:"POST",
            headers:{
                'Content-Type':'application/x-www-form-urlencoded',
                'x-login-user':2,

            },

            url:url,
            data:{},
            params:{
                // strategyName:this.state.strategyName,
            },
        }).then(()=>{
            // this.props.history.push("/alert/"+this.state.projectid+"/branches");
            alert("send")
        })
    }


    changeState(checked) {
        this.setState({
            state: checked
        })
    }


    render() {
        const { init, getError, getState } = this.field;
        return (
            <div className="new-strategy-container">
                <div className="div-new-strategy-top">
                    {this.props.intl.messages["alert.newStrategy.top"]}
                </div>
                <FormBinderWrapper
                    value={this.state.value}   // 传递 values
                    onChange={this.formChange} // 响应 onChange
                    ref="form"
                >
                    <div>
                        <div className="div-new-strategy-input">
                            <span className="text-new-strategy-name">{this.props.intl.messages["alert.newStrategy.name"]}</span>
                            <input maxLength={20}
                                   hasLimitHint
                                   placeholder=" 策略名称"
                                   {...init("name", {
                                       rules: [
                                           { required: true, min: 5, message: "用户名至少为 5 个字符" },
                                           { validator: this.userExists }
                                       ]
                                   })}
                                   onChange={this.changeStrategyName.bind(this)}
                                   className="input-new-strategy-name"
                            />
                        </div>

                        <div className="div-new-strategy-select">
                            <span className="text-new-strategy-name">{this.props.intl.messages["alert.newStrategy.state"]}</span>
                            <div className="div-new-strategy-state">
                                <Switch checkedChildren="开" onChange={this.changeState.bind(this)} unCheckedChildren="关" />
                            </div>
                        </div>

                        <div className="div-new-strategy-select">
                            <span className="text-new-strategy-name">{this.props.intl.messages["alert.newStrategy.level.name"]}</span>
                            <div className="div-new-strategy-state">
                                <Select>
                                    <Select.Option value="option1">
                                        {this.props.intl.messages["alert.newStrategy.level.normal"]}
                                    </Select.Option>
                                    <Select.Option value="option2">
                                        {this.props.intl.messages["alert.newStrategy.level.serve"]}
                                    </Select.Option>
                                </Select>
                            </div>
                        </div>

                        <div className="div-new-strategy-input">
                            <span className="text-new-strategy-name">{this.props.intl.messages["alert.newStrategy.description"]}</span>
                            <Input multiple placeholder="multiple" className="input-new-strategy-description"/>
                        </div>
                    </div>

                </FormBinderWrapper>


                <div className="div-new-strategy-submit">
                    <Button type="primary" onClick={this.addStrategy.bind(this)} className="btn-new-strategy-add">{this.props.intl.messages["alert.newStrategy.add"]}</Button>
                    <Button type="normal" shape="warning" onClick={this.cancel.bind(this)} className="btn-new-strategy-cancel">{this.props.intl.messages["alert.newStrategy.cancel"]}</Button>
                </div>
            </div>
        )
    }
}

// export default injectIntl((props) => <NewBranch {...props} key={props.location.pathname}/>)
export default injectIntl(NewStrategy)
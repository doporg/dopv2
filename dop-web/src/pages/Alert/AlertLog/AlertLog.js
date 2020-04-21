import React, {Component} from 'react';
import { Button } from "@icedesign/base";
import {injectIntl} from "react-intl";
import Axios from "axios/index";
import API from "../../API";


class AlertLog extends Component {
    addStrategy() {
        let url = API.alert + "/alert/test2";
        Axios({
            method: "POST",
            headers:{
                'Content-Type':'application/x-www-form-urlencoded',
                'X_LOGIN_USER': 111,

            },
            url: url,
            data: {},
            params: {
                // strategyName:this.state.strategyName,
            },
        }).then(() => {
            // this.props.history.push("/alert/"+this.state.projectid+"/branches");
            alert("send")
        })
    }

    render() {
        return (
            <div>
                alertLog
                <Button type="primary" onClick={this.addStrategy.bind(this)}>主要按钮</Button>
            </div>
        )
    }
}
export default injectIntl(AlertLog)
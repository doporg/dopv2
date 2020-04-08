import React, {Component} from 'react'
import {injectIntl} from "react-intl";
import API from "../../API";
import Axios from "axios";

class LinkDetail extends Component{

    constructor(props) {
        super(props);
        this.state = {
            traceId: props.location.search.match("traceId=[0-9a-zA-Z]+")[0].split("=")[1],
        }

    }
    componentWillMount(){};
    componentDidMount(){};

    searchTraceByTraceId = (traceId) => {
        let getTraceByIdUrl = API.link + "/getTraceById";
        Axios.get(getTraceByIdUrl).then(response => {

        }).catch((error) => {
            console.log("根据Id查询trace失败：", error);
        });

    };

    render() {
        return <div>{this.state.traceId}</div>
    }
}

export default injectIntl(LinkDetail);
import React,{Component} from 'react';
import {injectIntl} from "react-intl";

class LinkOverview extends Component{
    constructor(props) {
        super(props);
        console.log(this.props.traceInfo);
        this.state = {
            traceInfo: props.traceInfo
        }
    }

    componentWillMount() {
        console.log("componentWillMount");
    }

    componentDidMount() {
        console.log("componentDidMount");
    }

    componentWillReceiveProps(nextProps, nextContext) {
        console.log("nextProps: " + JSON.stringify(nextProps));
        console.log("nextContext: " + JSON.stringify(nextContext));
    }
}

export default injectIntl(LinkOverview)
import React, {Component} from 'react'
import {injectIntl} from "react-intl";

class LinkDetail extends Component{

    constructor(props) {
        super(props);
        this.state = {};

    }
    componentWillMount(){};
    componentDidMount(){};

    render() {
        return <div>详细信息</div>
    }
}

export default injectIntl(LinkDetail);
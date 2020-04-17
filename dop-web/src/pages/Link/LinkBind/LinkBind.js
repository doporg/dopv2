import React,{Component} from 'react';
import {injectIntl} from "react-intl";

class LinkBind extends Component{
    constructor(props) {
        super(props);
        this.state = {

        }
    }

    render() {
        return <div>绑定设置</div>
    }
}

export default injectIntl(LinkBind);
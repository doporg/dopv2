import React,{Component} from 'react';
import {injectIntl} from "react-intl";

class LinkDependency extends Component{
    constructor(props) {
        super(props);
        this.state = {
            traceInfo: props.traceInfo
        }
    }

    componentWillMount() {
    }

    componentDidMount() {
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({
            traceInfo: nextProps.traceInfo
        })
    }

    render() {
        return (
            <div>
                {
                    this.state.traceInfo.map((item, index) => {
                        return (<p key={index}>
                            依赖：{item.parentId}&nbsp;&nbsp;{item.name}<br/>
                        </p>)
                    })
                }
            </div>
        );
    }
}

export default injectIntl(LinkDependency);
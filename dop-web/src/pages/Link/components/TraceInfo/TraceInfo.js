import React,{Component} from 'react';
import {injectIntl} from "react-intl";
import TraceOverview from "../TraceOverview/TraceOverview";
import LinkDependency from "../LinkDependency/LinkDependency";
import SpanDetail from "../SpanDetail/SpanDetail";

class TraceInfo extends Component{
    constructor(props) {
        super(props);
        this.state = {
            traceInfo : props.traceInfo
        }
    }

    componentWillMount() {
    }

    componentDidMount() {
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({
            traceInfo: nextProps.traceInfo
        });
    }

    render() {
        return <div>
            <TraceOverview/>
            <LinkDependency/>
            <SpanDetail/>
        </div>;
    }
}

export default injectIntl(TraceInfo);
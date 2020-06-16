import React,{Component} from 'react';
import {injectIntl} from "react-intl";
import TraceOverview from "../TraceOverview/TraceOverview";

import '../../linkStyles.css'
import TraceDetailPane from "../TraceDetailPane/TraceDetailPane";

class TraceInfo extends Component{
    constructor(props) {
        super(props);
        this.state = {
            traceInfo : props.traceInfo,
            traceId: props.traceId
        }
    }

    componentWillMount() {
    }

    componentDidMount() {
    }

    componentWillReceiveProps(nextProps, nextContext) {
        // console.log("traceInfo will receive");
        this.setState({
            traceId: nextProps.traceId,
            traceInfo: nextProps.traceInfo
        });
    }

    render() {
        return <div>
            <TraceOverview traceId={this.state.traceId} traceInfo={this.state.traceInfo}/>
            <TraceDetailPane traceId={this.state.traceId} traceInfo={this.state.traceInfo}/>
        </div>;
    }
}

export default injectIntl(TraceInfo);
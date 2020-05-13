import React, {Component} from 'react';
import {injectIntl} from "react-intl";
import {TabPane} from "@icedesign/base/lib/tab";
import Tab from "@icedesign/base/lib/tab";
import SpanDetail from "../SpanDetail/SpanDetail";
import LinkDependency from "../LinkDependency/LinkDependency";
import {buildNodeTree} from "../../LinkSearch/TraceHandle";

class TraceDetailPane extends Component{
    constructor(props) {
        super(props);
        this.state = {
            traceId: props.traceId,
            traceInfo: props.traceInfo,
            nodesTree: null
        }
    }

    componentWillMount() {
        this.setState({
            nodesTree: buildNodeTree(this.state.traceId,this.state.traceInfo)
        })
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({
            traceId: nextProps.traceId,
            traceInfo: nextProps.traceInfo,
            nodesTree: buildNodeTree(nextProps.traceId, nextProps.traceInfo)
        })
    }

    componentDidMount() {
    }

    render() {
        return (
            <Tab className='link-tab'>
                <TabPane key='dependency' tab='依赖图' className='link-tab-pane'>
                    {
                        this.state.nodesTree === null ?
                            <p>Nothing</p>
                            :
                            <LinkDependency traceId={this.state.traceId}
                                            traceInfo={this.state.traceInfo}
                                            nodesTree={this.state.nodesTree}/>
                    }
                </TabPane>
                <TabPane key='timeline' tab='时间轴' className='link-tab-pane'>
                    <SpanDetail traceId={this.state.traceId} traceInfo={this.state.traceInfo}/>
                </TabPane>
            </Tab>
        )
    }
}

export default injectIntl(TraceDetailPane);
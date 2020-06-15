import React, {Component} from 'react';
import {injectIntl} from "react-intl";
import {TabPane} from "@icedesign/base/lib/tab";
import Tab from "@icedesign/base/lib/tab";
import LinkDependency from "../LinkDependency/LinkDependency";
import {buildNodeTree} from "../../LinkSearch/TraceHandle";
import TraceTimeLine from "../TraceTimeLine/TraceTimeLine";

var paneWidth = 0;
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
            nodesTree: buildNodeTree(nextProps.traceId, nextProps.traceInfo),
        })
    }

    componentDidMount() {
        let pane = document.getElementById("pane1");
        paneWidth = pane.offsetWidth;
    }

    render() {
        return (
            <Tab className='link-tab' id='trace-pane'>
                <TabPane key='dependency' tab='依赖图' className='link-tab-pane' id='pane1'>
                    {
                        this.state.nodesTree === null ?
                            <p>Nothing</p>
                            :
                            <LinkDependency traceId={this.state.traceId}
                                            traceInfo={this.state.traceInfo}
                                            nodesTree={this.state.nodesTree}/>
                    }
                </TabPane>
                <TabPane key='timeline' tab='时间轴' className='link-tab-pane-timeline' id='pane2'>
                    {
                        this.state.nodesTree === null ?
                            <p>Nothing</p>
                            :
                            <TraceTimeLine traceId={this.state.traceId}
                                           traceInfo={this.state.traceInfo}
                                           nodesTree={this.state.nodesTree}
                                           paneWidth={paneWidth}/>
                    }
                </TabPane>
            </Tab>
        )
    }
}

export default injectIntl(TraceDetailPane);
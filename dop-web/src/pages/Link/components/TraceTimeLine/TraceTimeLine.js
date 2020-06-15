import React, {Component} from 'react';
import {injectIntl} from "react-intl";
import NodeInfo from "../NodeInfo/NodeInfo";
import "../../linkStyles.css"
import {formatDuration} from "../../util/TimeUtil";

class TraceTimeLine extends Component{

    constructor(props) {
        super(props);
        let virtual = [];
        virtual.push(props.nodesTree);
        console.log("props.paneWidth", props.paneWidth);
        this.state = {
            paneWidth: props.paneWidth,
            traceId: props.traceId,
            traceInfo: props.traceInfo,
            // nodesTree: props.nodesTree
            nodesTree: virtual
        }
    }

    componentWillReceiveProps(nextProps, nextContext) {
        let virtual = [];
        virtual.push(nextProps.nodesTree);
        this.setState({
            paneWidth: nextProps.paneWidth,
            traceId: nextProps.traceId,
            traceInfo: nextProps.traceInfo,
            // nodesTree: nextProps.nodesTree
            nodesTree: virtual
        })
    }

    getMaxDuration = () => {
        return this.state.nodesTree[0].spans[0].duration;
    };

    getBeginTs = () => {
        return this.state.nodesTree[0].spans[0].timestamp;
    };

    render() {
        let result = [], maxLayer = 0, maxDuration = this.getMaxDuration(), beginTs = this.getBeginTs();
        const loopTree = (nodes, layer) => {
            nodes.map(node => {
                console.log("node id: " + node.spanId);
                maxLayer = Math.max(layer, maxLayer);
                result.push(
                    {
                        layer: layer,
                        spanId: node.spanId,
                        parentId: node.parentId,
                        serviceName: node.serviceName,
                        hasError: node.hasError,
                        spans: node.spans,
                        serverSpanIndex: node.serverSpanIndex,
                        clientSpanIndex: node.clientSpanIndex,
                        rootSpanStartTs: node.rootSpanStartTs
                    });
                if (node.nextNodes && node.nextNodes.length) {
                    loopTree(node.nextNodes, layer + 1);
                }
            })
        };

        const renderNode = () => {
            console.log("render Node");
            loopTree(this.state.nodesTree, 0);
            return result.map(node => {
                return <BarNode key={node.spanId}
                                maxLayer={maxLayer}
                                node={node}
                                layer={node.layer}
                                paneWidth={this.state.paneWidth}
                                totalDuration={maxDuration}
                                traceBeginTs={beginTs}/>
            });
        };

        return (
            <div style={{display: 'block'}}>
                {renderNode()}
            </div>
        );
    }
}

class BarNode extends Component{
    constructor(props) {
        super(props);
        console.log("BarNode cons: " + props.paneWidth);
        this.state = {
            maxLayer: props.maxLayer,
            layer: props.layer,
            node: props.node,
            visible: false,
            totalWidth: props.paneWidth,
            totalDuration: props.totalDuration,
            traceBeginTs: props.traceBeginTs
        }
    }

    componentWillReceiveProps(nextProps, nextContext) {
        console.log("接收： " + nextProps.paneWidth);
        this.setState({
            totalDuration: nextProps.totalDuration,
            totalWidth: nextProps.paneWidth,
            maxLayer: nextProps.maxLayer,
            layer: nextProps.layer,
            node: nextProps.node,
            traceBeginTs: nextProps.traceBeginTs
        })
    }

    showNodeInfo = () => {
        this.setState({
            visible: true
        })
    };

    getNodeDivContent = () => {
        let {node, traceBeginTs} = this.state;
        let {clientSpanIndex, serverSpanIndex, spans} = node;
        let duration = 0, spanName = "", timestamp = traceBeginTs;
        if (clientSpanIndex !== -1) {
            duration = spans[clientSpanIndex].duration;
            spanName = spans[clientSpanIndex].name;
            timestamp = spans[clientSpanIndex].timestamp;
        }
        if (serverSpanIndex !== -1) {
            if (duration === 0) {duration = spans[serverSpanIndex].duration;}
            spanName = spans[serverSpanIndex].name;
            timestamp = Math.max(timestamp, spans[serverSpanIndex].timestamp);
        }
        return {
            spanName: spanName,
            duration: duration,
            timestamp: timestamp
        };
    };
    render() {
        let {node, layer, maxLayer, totalWidth, totalDuration, traceBeginTs} = this.state;
        // 显示的节点名
        let nodeName = node.serviceName;
        if (nodeName === null) {
            nodeName = 'Unknown Node';
        }
        let divContent = this.getNodeDivContent(); //时间柱上显示的内容span name和duration
        let {duration, spanName, timestamp} = divContent;
        let left = (layer * 15) + 'px'; // 一行整个div的margin left
        let timeLineDivLeft = (140 + maxLayer * 15);
        let backgroundColor = node.hasError ? 'hsla(1,58%,77%,.3)': 'rgba(66,146,198,.2)';//根据错误状态显示颜色
        let spanBackgroundColor = node.hasError? '#c44442' : '#0084b4';//根据错误状态显示颜色
        let divTotalWidth = totalWidth - timeLineDivLeft;//最长时间柱的长度
        let divWidth = (duration/totalDuration) * divTotalWidth;//根据duration计算时间柱长度
        // let leftOffset = 0;// 根据timestamp和totalBeginTs计算位置
        let leftOffset = divTotalWidth*(timestamp-traceBeginTs)/totalDuration;
        return (
            <div>
                <div className='node-whole-div' style={{marginLeft: left, height: '25px', cursor: 'pointer'}}
                     onClick={this.showNodeInfo.bind(this)}>
                    {/*{nodeName}*/}
                    <span className='node-name-span' style={{backgroundColor: spanBackgroundColor}}>{nodeName}</span>
                    <div className='node-div' style={{left: timeLineDivLeft+leftOffset+'px',width: divWidth+'px',backgroundColor: backgroundColor}}>
                        {spanName}&nbsp;&nbsp;{formatDuration(duration)}
                    </div>
                </div>
                <NodeInfo visible={this.state.visible} node={this.state.node}/>
            </div>);

    }
}

export default injectIntl(TraceTimeLine);
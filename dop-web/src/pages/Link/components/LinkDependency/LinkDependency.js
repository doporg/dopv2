import React,{Component} from 'react';
import {injectIntl} from "react-intl";
import Button from "@icedesign/base/lib/button";

import "../../linkStyles.css"
import NodeInfo from "../NodeInfo/NodeInfo";

class LinkDependency extends Component{
    // 构造函数，初始化组件的state数据
    constructor(props) {
        super(props);
        this.state = {
            traceId: props.traceId,
            traceInfo: props.traceInfo,
            nodesTree: props.nodesTree
        }
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({
            traceId: nextProps.traceId,
            traceInfo: nextProps.traceInfo,
            nodesTree: nextProps.nodesTree
        })
    }
    // 层次遍历
    renderNode = () => {
        let nodesPerLevel = new Map();
        let rootNode = this.state.nodesTree;
        let currentNodes = [];
        currentNodes.push(rootNode);
        let level = 0;
        while (currentNodes !== null && currentNodes.length > 0) {
            let count = 0, total = currentNodes.length;
            let nodes = [];
            while (count < total) {
                let tmpNode = currentNodes.shift();
                let nextNodes = tmpNode.nextNodes, nextNodeCount = 0;
                if (nextNodes !== null) {
                    nextNodeCount = nextNodes.length;
                    nextNodes.map((item) => {currentNodes.push(item)})
                }
                nodes.push({
                    key: level+"_"+count,
                    level: level,
                    row: count,
                    hasError: tmpNode.hasError,
                    serviceName: tmpNode.serviceName,
                    spans: tmpNode.spans,
                    serverSpanIndex: tmpNode.serverSpanIndex,
                    clientSpanIndex: tmpNode.clientSpanIndex,
                    nextNodeCount:nextNodeCount,
                    spanId: tmpNode.spanId,
                    parentId: tmpNode.parentId,
                    rootSpanStartTs: tmpNode.rootSpanStartTs
                });
                count++;
            }
            nodesPerLevel.set(level, nodes);
            level++;
        }
        return nodesPerLevel;
    };
    // 根据节点所在深度和先后顺序计算节点在界面上的位置
    calculatePos = (nodesPerLevel) => {
        let res = new Map();
        let allNodes = [], edges=[];
        nodesPerLevel.forEach((value, key) => { //key是第几层，value是这一层的所有节点
            let nodes = [];
            let base = 0;
            value.map((item) => { //item遍历当前层的节点
                nodes.push(<Node key={key + "_" + item.row} node={item}/>);
                let nextLevelNodes = nodesPerLevel.get(key + 1); //下一层所有节点
                let count = 0;
                while (count < item.nextNodeCount) {
                    edges.push({
                        fromLevel: key,
                        fromNode: item,
                        targetNode: nextLevelNodes[base+count]
                    });
                    count++;
                }
                base = base + item.nextNodeCount;
            });
            allNodes.push(nodes);
        });
        res.set("nodes", allNodes);
        res.set("edges", edges);
        return res;
    };
    render() {
        const nodesPerLevel = this.renderNode();
        const nodeAndEdge = this.calculatePos(nodesPerLevel);
        const nodes = nodeAndEdge.get("nodes");
        const edges = nodeAndEdge.get("edges");
        return (
            <div style={{position: 'relative', width: 0, height: 0}}>
                {nodes}
                <NodeEdge edges={edges}/>
            </div>
        );
    }
}

class Node extends Component{
    constructor(props) {
        super(props);
        this.state = {
            node: props.node,
            visible: false
        }

        //node包含的属性有这些
        // key: level+"_"+count, 没用
        // level: level, 确定位置,用完了
        // row: count, 确定位置,用完了
        // hasError: tmpNode.hasError, 确定红的灰的，用完了
        // serviceName: tmpNode.serviceName, 显示节点名称，用完了
        // spans: tmpNode.spans,
        // serverSpanIndex: tmpNode.serverSpanIndex,
        // clientSpanIndex: tmpNode.clientSpanIndex,
        // nextNodeCount:nextNodeCount 用来生成边的，用完了
    }



    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({
            node: nextProps.node
        })
    }

    showNodeInfo = () => {
        this.setState({
            visible: true
        })
    };
    render() {
        const left = ((this.state.node.level) * (150 + 100))+'px';
        const top = ((this.state.node.row) * 60)+'px';
        let nodeName = this.state.node.serviceName;
        if (nodeName === null) {
            nodeName = 'Unknown Node';
        }
        return (
            <div style={{display: 'inline-block'}}>
                {
                    this.state.node.hasError ?
                        <Button style={{left: left, top: top}} size='large' type='normal' shape='warning' className='node-btn' onClick={this.showNodeInfo.bind(this)}>{nodeName}</Button>
                        :
                        <Button style={{left: left, top: top}} size='large' type='normal' className='node-btn' onClick={this.showNodeInfo.bind(this)}>{nodeName}</Button>
                }
                <NodeInfo visible={this.state.visible} node={this.state.node}/>
            </div>);

    }
}

class NodeEdge extends Component{
    constructor(props) {
        super(props);
        this.state = {
            edges: props.edges
        }
    }
    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({
            edges: nextProps.edges
        })
    }

    componentDidMount() {
        this.renderLine();
    }

    renderLine = () => {
        let lines = [];
        this.state.edges.map((item, index) => {
            let fromNode = item.fromNode, toNode = item.targetNode;
            // if (toNode.name !== null) { // server span缺失的时候要不要显示
                let fromPoint = this.calculateFromPoint(fromNode.level, fromNode.row); //出发节点是第几列，第几行
                let toPoint = this.calculateToPoint(toNode.level, toNode.row);
                lines.push(
                    {
                        x1: fromPoint.X,
                        y1: fromPoint.Y,
                        x2: toPoint.X,
                        y2: toPoint.Y,

                    });
            // }
        });
        return lines;

    };

    calculateFromPoint = (level, row) => {
        let point = {};
        point.X = level * (150 + 100) + 150;
        point.Y = row * (60) + 16;
        return point;
    };

    calculateToPoint = (level, row) => {
        let point = {};
        point.X = level * (150 + 100);
        point.Y = row * (60) + 16;
        return point;
    };

    render() {
        const lines = this.renderLine();
        return <div id='edge-container'>
            {/*<svg className='svg-con'>*/}
                {/*{lines.map((item, index) => {*/}
                    {/*return <line key={index} x1={item.x1} y1={item.y1} x2={item.x2} y2={item.y2}*/}
                                 {/*style={{stroke:'rgba(0,0,0, 0.5)',strokeWidth:'1.5', cursor: 'pointer', zIndex: 97}}*/}
                                 {/*onClick={()=>{console.log("click line" + index)}}/>*/}
                {/*})}*/}
            {/*</svg>*/}
            {
                lines.map((item, index) => {
                    let y1, y2;
                    let svgHeight = item.y1 - item.y2;
                    let svgLeft = item.x1 + 'px';
                    let svgTop;
                    let height;
                    let lineWidth = svgHeight === 0 ? 3 : 2;
                    if (svgHeight === 0) {
                        //横线
                        svgTop = item.y1 + 'px';
                        height = 10;
                        y1 = 0; y2 = 0;
                    } else if (svgHeight > 0) { //上行线
                        svgTop = item.y2 + 'px';
                        height = svgHeight - 10;
                        y1 = height; y2 = 0;
                    } else { // 下行线
                        svgTop = (item.y1 + 10) + 'px';
                        height = svgHeight * (-1) - 10;
                        y1 = 0; y2 = height;
                    }
                    return (
                        <svg key={"svg"+index} className='svg-con' style={{left: svgLeft, top: svgTop, height: height}}>
                            <line key={"line"+index} x1='0' y1={y1} x2='100' y2={y2}
                                     style={{stroke:'rgba(0,0,0, 0.5)',strokeWidth:lineWidth, cursor: 'pointer'}}
                                     onClick={()=>{console.log("click line" + index)}}/>
                        </svg>
                    )
                })
            }
        </div>
    }
}

export default injectIntl(LinkDependency);
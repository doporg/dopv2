import React,{Component} from 'react';
import {injectIntl} from "react-intl";
import Button from "@icedesign/base/lib/button";

import "../../linkStyles.css"

class LinkDependency extends Component{
    constructor(props) {
        super(props);
        this.state = {
            traceId: props.traceId,
            traceInfo: props.traceInfo,
            nodesTree: props.nodesTree,
            levelCount: 0,
            maxHeight: 0,
            // nodeEdges: []
        }
    }

    componentWillMount() {
    }

    componentDidMount() {
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({
            traceId: nextProps.traceId,
            traceInfo: nextProps.traceInfo,
            nodesTree: nextProps.nodesTree
        })
    }

    renderNode = () => {

        let nodesPerLevel = new Map();

        let rootNode = this.state.nodesTree;
        let currentNodes = [];
        currentNodes.push(rootNode);
        let level = 0;
        while (currentNodes !== null && currentNodes.length > 0) {
            let count = 0, total = currentNodes.length;
            // console.log("total: " + total + ", level: " + level);
            let nodes = [];
            while (count < total) {
                // console.log("count: " + count);
                let tmpNode = currentNodes.shift();
                // console.log("去掉第一个节点后的长度：" + currentNodes.length);
                let nextNodes = tmpNode.nextNodes;
                let nextNodeCount = 0;
                if (nextNodes !== null) {
                    nextNodeCount = nextNodes.length;
                    nextNodes.map((item) => {currentNodes.push(item)})
                }
                nodes.push({
                    key: level+"_"+count,
                    level: level,
                    row: count,
                    hasError: tmpNode.hasError,
                    name: tmpNode.serviceName,
                    spans: tmpNode.spans,
                    serverSpanIndex: tmpNode.serverSpan,
                    clientSpanIndex: tmpNode.clientSpan,
                    nextNodeCount:nextNodeCount
                });
                count++;
            }
            nodesPerLevel.set(level, nodes);
            level++;
        }
        return nodesPerLevel;
    };

    calculatePos = (nodesPerLevel) => {
        // 这里allNodes好像没什么用，直接用nodes没有问题
        let res = new Map();
        let allNodes = [], edges=[];
        nodesPerLevel.forEach((value, key) => { //key是第几层，value是这一层的所有节点
            let nodes = [];
            let base = 0;
            value.map((item) => { //item遍历当前层的节点
                // console.log("第" + key + "列，第" + item.row + "行是： " + JSON.stringify(item));
                nodes.push(<Node key={key + "_" + item.row} node={item}/>);
                // let nextNodeNum = item.nextNodeCount;
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
            <div style={{position: 'relative'}}>
                {/*<canvas id='myCanvas' style={{width: '-webkit-fill-available', height: '350px'}}/>*/}
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
            node: props.node
        }
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({
            node: nextProps.node
        })
    }

    render() {
        const left = ((this.state.node.level) * (150 + 100))+'px';
        const top = ((this.state.node.row) * 60)+'px';
        return (
            this.state.node.name === null ? null :
                (this.state.node.hasError ?
                <Button style={{left: left, top: top}} size='large' type='normal' shape='warning' className='node-btn'>{this.state.node.name}</Button>
                :
                <Button style={{left: left, top: top}} size='large' type='normal' className='node-btn'>{this.state.node.name}</Button>
                )
        )


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
            let fromPoint = this.calculateFromPoint(fromNode.level, fromNode.row); //出发节点是第几列，第几行
            let toPoint = this.calculateToPoint(toNode.level, toNode.row);
            lines.push(
            {
                x1: fromPoint.X,
                y1: fromPoint.Y,
                x2: toPoint.X,
                y2: toPoint.Y,

            });
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
        // return <canvas id='myCanvas' ref={(ref)=>this.canvas=ref} style={{width: '-webkit-fill-available', height: '350px'}}/>;
        return <div id='edge-container' style={{position: 'relative'}}>
            <svg className='svg-con'>
                {lines.map((item, index) => {
                    return <line key={index} x1={item.x1} y1={item.y1} x2={item.x2} y2={item.y2} style={{stroke:'rgba(0,0,0, 0.5)',strokeWidth:'1'}}/>
                })}
            </svg>
        </div>
    }
}

export default injectIntl(LinkDependency);
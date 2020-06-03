import React, {Component} from 'react';
import {injectIntl} from "react-intl";
import Dialog from "@icedesign/base/lib/dialog";
import Button from "@icedesign/base/lib/button";

import "../../linkStyles.css"
import Table from "@icedesign/base/lib/table";
import {formatDuration, timestampToDate} from "../../util/TimeUtil";

class NodeInfo extends Component{

    constructor(props) {
        super(props);
        this.state = {
            visible: props.visible,
            node: props.node
        }
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({
            visible: nextProps.visible,
            node: nextProps.node
        })
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
    // spanId
    // parentId
    // 处理node信息
    handleSpans = () => {
        const node = this.state.node;
        // console.log("node: " + JSON.stringify(node));
        let resultMap = new Map();
        let serviceName = node.serviceName,
            clientSpanIndex = node.clientSpanIndex,
            serverSpanIndex = node.serverSpanIndex,
            rootSpanStartTs = node.rootSpanStartTs;
        if (serviceName === null) {
            if (clientSpanIndex !== -1) {
                serviceName = node.spans[clientSpanIndex].localEndpoint.serviceName;
            }
        }
        let tags = [], duration = 0, spanName = "", clientAdd = null;
        let tagsMap = new Map();
        let timeLine = [];
        if (clientSpanIndex !== -1) {
            let clientSpan = node.spans[clientSpanIndex];
            let localEndpoint = clientSpan.localEndpoint;
            let address = localEndpoint.serviceName;
            let addIp = localEndpoint.ipv4 === null ? localEndpoint.ipv6: localEndpoint.ipv4;
            clientAdd = addIp;
            duration = clientSpan.duration;
            spanName = clientSpan.name;
            tagsMap = new Map(Object.entries(clientSpan.tags));
            timeLine.push({
                timestamp: clientSpan.timestamp,
                relative: clientSpan.timestamp - rootSpanStartTs,
                annotation: 'Client Start',
                address: addIp + "("+address+")"
            });
            timeLine.push({
                timestamp: clientSpan.timestamp + duration,
                relative: clientSpan.timestamp + duration - rootSpanStartTs,
                annotation: 'Client Receive',
                address: addIp + "("+address+")"
            })
        }
        if (serverSpanIndex !== -1) {
            let serverSpan = node.spans[serverSpanIndex];
            if (duration === 0) {duration = serverSpan.duration;}

            let localEndpoint = serverSpan.localEndpoint;
            let address = localEndpoint.serviceName;
            let serverAdd = localEndpoint.ipv4 === null ? localEndpoint.ipv6 : localEndpoint.ipv4;

            if (clientAdd === null && serverSpan.remoteEndpoint !== null) {
                clientAdd = serverSpan.remoteEndpoint.ipv4 === null?serverSpan.localEndpoint.ipv6 : serverSpan.localEndpoint.ipv4;
            }
            // clientAdd = localEndpoint.ipv4 === null ? localEndpoint.ipv6 : localEndpoint.ipv4;
            spanName = serverSpan.name;
            const serverTags = serverSpan.tags;
            for (let key in serverTags) {
                if (!serverTags.hasOwnProperty(key)) continue;
                tagsMap.set(key, serverTags[key]);
            }
            timeLine.push({
                timestamp: serverSpan.timestamp,
                relative: serverSpan.timestamp - rootSpanStartTs,
                annotation: 'Server Start',
                address: serverAdd + "("+address+")"
            });
            timeLine.push({
                timestamp: serverSpan.timestamp + serverSpan.duration,
                relative: serverSpan.timestamp + serverSpan.duration - rootSpanStartTs,
                annotation: 'Server Send',
                address: serverAdd + "("+address+")"
            })
        }
        tagsMap.forEach((value, key) => {
            tags.push({key: key, value: value});
        });
        if (clientAdd !== null) tags.push({key: 'Client Address', value: clientAdd});
        let time = timeLine.sort(function (a, b) {
            return a.relative > b.relative ? 1 : -1;
        });
        // console.log("timeLine: " + JSON.stringify(timeLine));
        resultMap.set("serviceName", serviceName);
        resultMap.set("tags", tags);
        resultMap.set("duration", duration);
        resultMap.set("spanName", spanName);
        resultMap.set("spanId", node.spanId);
        resultMap.set("parentId", node.parentId);
        resultMap.set("timeLine", time);
        return resultMap;
    };

    closeDialog = () => {
        this.setState({
            visible: false
        })
    };

    render() {
        const footer = (
            <Button type='primary' onClick={this.closeDialog.bind(this)}>
                {this.props.intl.messages['link.close']}
            </Button>
        );
        const setRowProps = (record, index) => {
            if (record.key === 'error') {
                return {
                    style: {background: '#f2dede', color: '#a94442'}
                };
            }
        };
        const renderTimestamp = (value) => {
            return timestampToDate(value);
        };
        const renderRelativeTime = (value) => {
            return formatDuration(value);
        };
        const info = this.handleSpans();
        return (
        <Dialog visible={this.state.visible}
                onClose={this.closeDialog.bind(this)}
                title={info.get('serviceName') + ": " + formatDuration(info.get('duration'))}
                footerAlign='right' footer={footer}
                style={{...styles.nodeInfoDialog}}
                hasMask={false}>
            {/*{JSON.stringify(this.state.node)}*/}
            <Table dataSource={info.get('timeLine')}>
                <Table.Column dataIndex='timestamp' title='时间' width='30%' cell={renderTimestamp}/>
                <Table.Column dataIndex='relative' title='相对时间' width='15%' cell={renderRelativeTime}/>
                <Table.Column dataIndex='annotation' title='Annotation' width='20%'/>
                <Table.Column dataIndex='address' title='Address'/>
            </Table>
            <Button style={{marginTop: '10px', color: 'black', fontSize: '15px'}} type='normal' size='large' shape='text'>
                访问内容
            </Button>
            <Table dataSource={info.get('tags')} getRowProps={setRowProps}>
                <Table.Column dataIndex='key' title='key' width='30%'/>
                <Table.Column dataIndex='value' title='value'/>
            </Table>
            <Button style={{marginTop: '10px', color: 'black', fontSize: '15px'}} type='normal' size='large' shape='text'>
                ID
            </Button>
            <table border="1" bordercolor="#bfbfbf" width="100%" style={{fontSize: '14px', color: '#333334'}}>
                <tbody>
                <tr className='table-tr'>
                    <td width="30%">spanId</td>
                    <td>{info.get("spanId")}</td>
                </tr>
                <tr className='table-tr'>
                    <td>parentId</td>
                    <td>{info.get("parentId")}</td>
                </tr>
                </tbody>
            </table>
        </Dialog>);
    }

}

const styles = {
    nodeInfoDialog: {
        width: '60%'
    }
};

export default injectIntl(NodeInfo);
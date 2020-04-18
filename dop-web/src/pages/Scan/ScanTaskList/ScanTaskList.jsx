import React, {Component} from 'react';
import {Button, Loading, Feedback} from '@icedesign/base';
import {Link} from 'react-router-dom';
import Axios from "axios/index";
import {injectIntl, FormattedMessage} from "react-intl";
import {Table} from 'antd';
import {API_GET_TASKS_INFO} from '../utility'

const {toast} = Feedback;


class ScanTaskList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            dataSource: [],
            current: 1,
            visible: false
        }
    }

    componentWillMount() {
        this.getTaskInfo();
        this.setState({
            visible: true
        })
    }

    getTaskInfo = () => {
        let url = API_GET_TASKS_INFO
        let self = this;
        Axios.get(url).then((response) => {
            let dataSource = [];
            let data = response.data
            for (let i = 0; i < data.length; i++) {
                dataSource.push({
                    key: i+1,
                    projectName: data[i].componentKey,
                    status: data[i].status,
                    createTime: data[i].submittedAt,
                    startType: data[i].startType
                })
            }
            self.setState({
                dataSource: dataSource,
                visible: false
            });
        }).catch(() => {
            toast.show({
                type: "error",
                content: self.props.intl.messages["scan.table.requestFailure"],
                duration: 1000
            });
            self.setState({
                visible: false
            });
        })
    }

    renderOperation(index, record) {
        let router = "/scan/result/" + record.projectName;
        return (
            <div>
                <Link to={router}>
                    <Button type="primary" size="small">
                        <FormattedMessage
                            id="scan.table.operation.check"
                            defaultMessage="查看"
                        />
                    </Button>
                </Link>
            </div>
        );
    };

    render() {
        const columns = [{
            title: this.props.intl.messages["scan.table.index"],
            width: 180,
            dataIndex: 'key',
            align: 'center'
        }, {
            title: this.props.intl.messages["scan.table.projectName"],
            width: 600,
            dataIndex: 'projectName',
            align: 'center'
        }, {
            title: this.props.intl.messages["scan.table.startType"],
            width: 400,
            dataIndex: 'startType',
            align: 'center'
        }, {
            title: this.props.intl.messages["scan.table.status"],
            width: 250,
            dataIndex: 'status',
            align: 'center'
        }, {
            title: this.props.intl.messages["scan.table.createTime"],
            width: 300,
            dataIndex: 'createTime',
            align: 'center'
        }, {
            title: this.props.intl.messages["scan.table.operation"],
            width: 300,
            dataIndex: 'operation',
            render: this.renderOperation.bind(this),
            align: 'center'
        }];
        return (
            <div>
                <Loading shape="fusion-reactor" visible={this.state.visible} className="next-loading my-loading">
                    <Table
                        dataSource={this.state.dataSource}
                        columns={columns}
                        pagination={{pageSize: 10}}
                        style={{"background": "white",align:"center"}}
                    />
                </Loading>
            </div>
        )
    }
}

export default injectIntl(ScanTaskList)
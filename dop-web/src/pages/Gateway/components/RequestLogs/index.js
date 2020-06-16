import React, {Component} from 'react';
import RequestLogsTable from "./RequestLogsTable";

export default class RequestLogs extends Component {
    constructor(props) {
        super(props);
        const api_Id = this.props.match.params.apiId;
        this.state = {
            apiId:api_Id,
        };
    }

    render() {
        return (
            <div className="api-logs-page">
                <RequestLogsTable apiId = {this.state.apiId} history={this.props.history}/>
            </div>
        );
    }
}

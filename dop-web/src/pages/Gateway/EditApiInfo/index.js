import React, {Component} from 'react';
import EditApiForm from "./components/EditApiForm";

export default class EditApiInfo extends Component {
    constructor(props) {
        super(props);
        const api_Id = this.props.match.params.apiId;
        this.state = {
            apiId:api_Id,
        };
    }

    render() {
        return (
            <div className="edit-api-page">
                <EditApiForm apiId = {this.state.apiId} history={this.props.history}/>
            </div>
        );
    }
}

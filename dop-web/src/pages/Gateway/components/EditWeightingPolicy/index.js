import React, {Component} from 'react';
import EditWeightingPolicyForm from "./components/EditWeightingPolicyForm";

export default class EditApiInfo extends Component {
    constructor(props) {
        super(props);
        const policy_Id = this.props.match.params.policyId;
        this.state = {
            policyId:policy_Id,
        };
    }

    render() {
        return (
            <div className="edit-api-page">
                <EditWeightingPolicyForm policyId = {this.state.policyId} history={this.props.history}/>
            </div>
        );
    }
}

import {Feedback} from "@icedesign/base";
import React, { Component } from 'react';
import GroupInfo from "./GroupInfo";

const Toast = Feedback.toast;

export default class NewContact extends Component{

    constructor(props) {
        super(props);
        this.state = {
            groupDto: {
                appId: '',
                groupName: '',
                comment: '',
                executeWay: 'PARALLEL',
                caseUnits: []
            }
        }
    }

    render() {
        return (
            <ContactInfo />
        );
    }
}

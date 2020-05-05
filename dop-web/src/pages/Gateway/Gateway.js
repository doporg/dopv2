/**
 *  用于API管理首页，网关概况
 *  @author zhangfuli
 *
 * */
import React, { Component } from 'react';
import CustomTable from "./components/UserTable/CustomTable";

export default class Pipeline extends Component {

    constructor(props) {
        super(props);
        this.state = {};
    }


    render(){
        return (
            <div className="test-cases-page">
                <CustomTable />
            </div>
        );
    };
}

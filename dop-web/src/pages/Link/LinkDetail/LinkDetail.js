import React, {Component} from 'react';
import {injectIntl} from "react-intl";

import API from "../../API";
import Axios from "axios";
import Search from "@icedesign/base/lib/search";
import Feedback from "@icedesign/base/lib/feedback";
import Grid from "@icedesign/base/lib/grid";

import '../linkStyles.css'
import IcePanel from '@icedesign/panel';
import ShowTraceOrNothing from "../components/ShowTraceOrNothing/ShowTraceOrNothing";

const Toast = Feedback.toast;
const {Row, Col} = Grid;
class LinkDetail extends Component{

    constructor(props) {
        super(props);
        this.state = {
            traceId: this.props.match.params.traceId,
            trace: [],
            isLoading: false
        }

    }
    componentWillMount(){
        let param = this.props.match.params.traceId;
        console.log("params: " + JSON.stringify(param));
        if (param !== undefined) {
            this.searchTraceById(param);
        }
    };

    componentDidMount(){
    };

    componentWillReceiveProps(nextProps, nextContext) {
        console.log("componentWillReceiveProps props: " + JSON.stringify(nextProps));
        let newTraceId = nextProps.match.params.traceId;
        if (this.validTraceId(newTraceId)) {
            this.setState({
                traceId: newTraceId,
                trace: this.searchTraceById(newTraceId)
            });
        } else {
            this.showTraceIdFormatError();
        }
    }

    searchTraceById = (traceId) => {
        console.log("search by trace id");
        this.setState({
            isLoading: true
        });
        let getTraceByIdUrl = API.link + "/getTraceById";
        // let d = await
        Axios.get(getTraceByIdUrl,{params:{traceId: traceId}}).then(response => {
            let data = response.data;
            console.log("data:" + JSON.stringify(data));
            this.setState({
                trace: data
            });
        }).catch((error) => {
            console.log("根据Id查询trace失败：", error);
        });
        this.setState({
            isLoading: false
        });

    };

    onSearchTrace = (value) => {
        console.log("search button: " + JSON.stringify(value));
        let traceId = value.key;
        let pattern = /^[a-f0-9]{16,32}$/;
        if (this.validTraceId(traceId)) {
            this.props.history.push("/link/detail/" + traceId);
        } else {
            this.showTraceIdFormatError();
        }
    };

    showTraceIdFormatError = () => {
        Toast.show({
            type: "error",
            content: this.props.intl.messages['link.error.traceId.regex'],
            duration: 5000
        });
    };

    validTraceId = (traceId) => {
        let pattern = /^[a-f0-9]{16,32}$/;
        return new RegExp(pattern).test(traceId);
    };

    render() {
        return (
            <div>
                {/*页面顶部链路查询部分*/}
                <div style={{marginBottom: '15px', marginTop: '-5px'}}>
                    <Row>
                        {/*<Col span='8'>*/}
                            {/*<Link to='/link/list'>*/}
                                {/*<Button className="change-button" type="primary" size="medium" >*/}
                                    {/*返回列表*/}
                                {/*</Button>*/}
                            {/*</Link>*/}
                        {/*</Col>*/}
                        {/*<Col span='16'>*/}
                            <Search className='search-by-traceid'
                                    searchText={this.props.intl.messages['link.search.submit.search']}
                                    autoWidth={true}
                                    placeholder={this.props.intl.messages['link.search.by.traceId']}
                                    hasIcon={false}
                                    size='large'
                                    defaultValue={this.state.traceId}
                                    onSearch={this.onSearchTrace.bind(this)}/>
                        {/*</Col>*/}

                    </Row>
                </div>

                <ShowTraceOrNothing traceId={this.state.traceId} trace={this.state.trace}>
                    <IcePanel>
                        <IcePanel.Header>概览</IcePanel.Header>
                        <IcePanel.Body>{this.state.traceId}</IcePanel.Body>
                    </IcePanel>
                    <IcePanel>
                        <IcePanel.Header>详情</IcePanel.Header>
                        <IcePanel.Body>
                            这里暂时什么也没有
                        </IcePanel.Body>
                    </IcePanel>
                </ShowTraceOrNothing>

            </div>);
    }
}

export default injectIntl(LinkDetail);
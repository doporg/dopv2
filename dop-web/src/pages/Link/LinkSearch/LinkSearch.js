import React, {Component} from 'react';
import {injectIntl} from "react-intl";

import API from "../../API";
import Axios from "axios";
import Search from "@icedesign/base/lib/search";
import Feedback from "@icedesign/base/lib/feedback";
import Grid from "@icedesign/base/lib/grid";

import '../linkStyles.css'
import ShowTraceOrNothing from "../components/ShowTraceOrNothing/ShowTraceOrNothing";
import TraceInfo from "../components/TraceInfo/TraceInfo";

const Toast = Feedback.toast;
const {Row, Col} = Grid;
class LinkSearch extends Component{

    constructor(props) {
        super(props);

        this.state = {
            traceId: this.props.match.params.traceId,
            trace: [],
            isLoading: false
        };
    }
    componentWillMount(){
        let param = this.props.match.params.traceId;
        // console.log("params: " + JSON.stringify(param));
        if (param !== undefined) {
            this.searchTraceById(param);
        }
    };

    componentDidMount(){
    };

    componentWillReceiveProps(nextProps, nextContext) {
        let newTraceId = nextProps.match.params.traceId;
        if (this.validTraceId(newTraceId)) {
            this.setState({
                traceId: newTraceId,
                // trace:
            });
            this.searchTraceById(newTraceId);
        } else {
            this.showTraceIdFormatError();
        }
    }

    searchTraceById = (traceId) => {

        this.setState({
            isLoading: true
        });
        let getTraceByIdUrl = API.link + "/v2/link/trace/" + traceId;
        Axios.get(getTraceByIdUrl).then(response => {
            let data = response.data;
            // console.log("data:" + JSON.stringify(data));
            this.setState({
                trace: data
            });
            // window.location.reload();
        }).catch((error) => {
            console.log("根据Id查询trace失败：", error);
        }).finally(() => {
            this.setState({
                isLoading: false
            });
        });
    };

    onSearchTrace = (value) => {
        console.log("search button: " + JSON.stringify(value));
        let traceId = value.key;
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
                        <Search className='search-by-traceid'
                                searchText={this.props.intl.messages['link.search.submit.search']}
                                autoWidth={true}
                                placeholder={this.props.intl.messages['link.search.by.traceId']}
                                hasIcon={false}
                                size='large'
                                defaultValue={this.state.traceId}
                                onSearch={this.onSearchTrace.bind(this)}/>
                    </Row>
                </div>

                <ShowTraceOrNothing traceId={this.state.traceId} trace={this.state.trace}>
                    <TraceInfo traceId={this.state.traceId} traceInfo={this.state.trace}/>
                </ShowTraceOrNothing>

            </div>);
    }
}

export default injectIntl(LinkSearch);
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
        // Axios.get(getTraceByIdUrl,{params:{traceId: traceId}}).then(response => {
        //     let data = response.data;
        //     console.log("data:" + JSON.stringify(data));
        //     this.setState({
        //         trace: data
        //     });
        // }).catch((error) => {
        //     console.log("根据Id查询trace失败：", error);
        // });
        let data = [
            {
                "traceId": "da1536541887c58c",
                "name": "get /service2",
                "parentId": null,
                "id": "da1536541887c58c",
                "kind": "SERVER",
                "timestamp": 1585723339974491,
                "duration": 56486,
                "debug": false,
                "shared": false,
                "localEndpoint": {
                    "serviceName": "ribbon-consumer",
                    "ipv4": "192.168.191.2",
                    "ipv6": null,
                    "port": 0
                },
                "remoteEndpoint": null,
                "annotations": null,
                "tags": {
                    "http.method": "GET",
                    "http.path": "/service2",
                    "mvc.controller.class": "HelloController",
                    "mvc.controller.method": "getHello"
                }
            },
            {
                "traceId": "da1536541887c58c",
                "name": "get /service1",
                "parentId": "da1536541887c58c",
                "id": "218ac2fc80901d80",
                "kind": "CLIENT",
                "timestamp": 1585723339992948,
                "duration": 30686,
                "debug": false,
                "shared": false,
                "localEndpoint": {
                    "serviceName": "ribbon-consumer",
                    "ipv4": "192.168.191.2",
                    "ipv6": null,
                    "port": 0
                },
                "remoteEndpoint": null,
                "annotations": null,
                "tags": {
                    "http.method": "GET",
                    "http.path": "/service1"
                }
            },
            {
                "traceId": "da1536541887c58c",
                "name": "get /service1",
                "parentId": "da1536541887c58c",
                "id": "218ac2fc80901d80",
                "kind": "SERVER",
                "timestamp": 1585723340005384,
                "duration": 17788,
                "debug": false,
                "shared": true,
                "localEndpoint": {
                    "serviceName": "ribbon-provider",
                    "ipv4": "192.168.191.2",
                    "ipv6": null,
                    "port": 0
                },
                "remoteEndpoint": {
                    "serviceName": null,
                    "ipv4": "192.168.191.2",
                    "ipv6": null,
                    "port": 0
                },
                "annotations": null,
                "tags": {
                    "http.method": "GET",
                    "http.path": "/service1",
                    "mvc.controller.class": "HelloController",
                    "mvc.controller.method": "getHello"
                }
            }
        ];
        this.setState({
            trace: data
        });
        console.log("获取data");
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
                    <TraceInfo traceInfo={this.state.trace} traceId={this.state.traceId}/>
                </ShowTraceOrNothing>

            </div>);
    }
}

export default injectIntl(LinkSearch);
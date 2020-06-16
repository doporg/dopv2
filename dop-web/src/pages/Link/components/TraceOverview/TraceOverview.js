import React,{Component} from 'react';
import {injectIntl} from "react-intl";
// import {FilePicker} from 'react-file-picker';

import IceLabel from '@icedesign/label';

import '../../linkStyles.css';
import Card from "@icedesign/base/lib/card";
import Grid from "@icedesign/base/lib/grid";
import Icon from "@icedesign/base/lib/icon";
import StarIcon from "../StarIcon/StarIcon";
import {formatDuration} from "../../util/TimeUtil";
import {getTraceSummary} from "../../LinkSearch/TraceHandle";

const {Row, Col} = Grid;
class TraceOverview extends Component{
    constructor(props) {
        super(props);
        this.state = {
            traceId: props.traceId,
            traceInfo: props.traceInfo,
            traceSummary: {}
        }
    }

    componentWillMount() {
        let traceSummary = getTraceSummary(this.state.traceId, this.state.traceInfo);
        this.setState({traceSummary});
    }


    componentDidMount() {
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({
            traceId: nextProps.traceId,
            traceInfo: nextProps.traceInfo
        });
        let traceSummary = getTraceSummary(this.state.traceId, this.state.traceInfo);
        this.setState({traceSummary});
    }

    handleDownloadTrace = () => {

    };

    render() {
        const services = this.renderService(this.state.traceSummary.serviceList);
        return (
            <Card className='search-condition'
                  bodyHeight='auto'
                  style={{padding: '15px'}}
                  title={<b style={{fontSize : '17px'}}>{this.props.intl.messages['link.detail.overview']}</b>}
                  titlePrefixLine={false}
                  // extra={<Icon type="favorite" style={{marginRight: '20px'}}/>}
                  extra={
                      <div>
                          <StarIcon traceId={this.state.traceId}/>
                          <Icon type="download" style={{cursor: 'pointer', color: 'black'}} onClick={this.handleDownloadTrace.bind(this)}/>
                      </div>}

            >
                <Row style={{marginTop: '10px',marginBottom: '10px',paddingLeft: '5px', textAlign: 'left', fontSize: '15px'}}>
                    <div style={{...styles.rowMargin}}>
                        <label>{this.props.intl.messages['link.detail.duration']}</label>
                        <IceLabel status='primary' inverse={true}>{formatDuration(this.state.traceSummary.duration)}</IceLabel>
                    </div>
                    <div style={{...styles.rowMargin}}>
                        <label>{this.props.intl.messages['link.detail.spanNum']}</label>
                        <IceLabel status='primary' inverse={true}>{this.state.traceSummary.spanNum}</IceLabel>
                    </div>
                    <div style={{...styles.rowMargin}}>
                        <label>{this.props.intl.messages['link.detail.serviceNum']}</label>
                        <IceLabel status='primary' inverse={true}>{this.state.traceSummary.serviceNum}</IceLabel>
                    </div>
                    {/*<div style={{...styles.rowMargin}}>*/}
                        {/*<label>{this.props.intl.messages['link.detail.depth']}</label>*/}
                        {/*<IceLabel status='primary' inverse={true}>{this.state.traceInfo.length}</IceLabel>*/}
                    {/*</div>*/}
                </Row>
                <Row style={{marginTop: '20px',marginBottom: '10px',paddingLeft: '5px', textAlign: 'left', fontSize: '15px'}}>
                    {/*<label>span数/每个服务：</label>*/}
                    {
                        services
                        // this.state.traceSummary.serviceList.map((value, index) => {
                        //     let serviceInfo = this.state.traceSummary.serviceList[serviceName];
                        //     if (serviceInfo.hasError) {
                        //         return <IceLabel key={index} status='danger' style={{...styles.spanNumPerService}}>{serviceName} x{serviceInfo.count}</IceLabel>
                        //     } else {
                        //         return <IceLabel key={index} status='success' style={{...styles.spanNumPerService}}>{serviceName} x{serviceInfo.count}</IceLabel>
                        //     }
                        // })
                    }
                   </Row>
            </Card>
        );
    }

    renderService = (serviceList) => {
        let services = [];
        serviceList.forEach(((value, key) => {
            services.push(<IceLabel key={key} status='success' style={{...styles.spanNumPerService}}>{key} x{value}</IceLabel>);
        }));
        return services;
    }
}

const styles = {
    spanNumPerService : {
        marginRight : '5px'
    },

    rowMargin : {
        marginRight: '70px'
    }
};

export default injectIntl(TraceOverview)
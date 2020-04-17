import React,{Component} from 'react';
import {injectIntl} from "react-intl";

import IceLabel from '@icedesign/label';

import '../../linkStyles.css';
import Card from "@icedesign/base/lib/card";
import Grid from "@icedesign/base/lib/grid";
import Icon from "@icedesign/base/lib/icon";

const {Row, Col} = Grid;
class LinkOverview extends Component{
    constructor(props) {
        super(props);
        this.state = {
            traceId: props.traceId,
            traceInfo: props.traceInfo
        }
    }

    componentWillMount() {
    }

    componentDidMount() {
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState = {
            traceId: nextProps.traceId,
            traceInfo: nextProps.traceInfo
        }
    }

    render() {
        console.log("link overview render");
        return (
            <Card className='search-condition'
                  bodyHeight='auto'
                  style={{padding: '15px'}}
                  title={<b style={{fontSize : '17px'}}>{this.props.intl.messages['link.detail.overview']}</b>}
                  titlePrefixLine={false}
                  // extra={<Icon type="favorite" style={{marginRight: '20px'}}/>}
                  extra={<Icon type="favorites-filling" style={{marginRight: '20px'}}/>}

            >
                <Row style={{marginTop: '10px',marginBottom: '10px',paddingLeft: '5px', textAlign: 'left', fontSize: '15px'}}>
                    <div style={{...styles.rowMargin}}>
                        <label>{this.props.intl.messages['link.detail.duration']}</label>
                        <IceLabel status='primary' inverse={true}>{this.state.traceInfo[0].duration}ms</IceLabel>
                    </div>
                    <div style={{...styles.rowMargin}}>
                        <label>{this.props.intl.messages['link.detail.serviceNum']}</label>
                        <IceLabel status='primary' inverse={true}>3</IceLabel>
                    </div>
                    <div style={{...styles.rowMargin}}>
                        <label>{this.props.intl.messages['link.detail.spanNum']}</label>
                        <IceLabel status='primary' inverse={true}>{this.state.traceInfo.length}</IceLabel>
                    </div>
                    <div style={{...styles.rowMargin}}>
                        <label>{this.props.intl.messages['link.detail.depth']}</label>
                        <IceLabel status='primary' inverse={true}>{this.state.traceInfo.length}</IceLabel>
                    </div>
                </Row>
                <Row style={{marginTop: '20px',marginBottom: '10px',paddingLeft: '5px', textAlign: 'left', fontSize: '15px'}}>
                    {/*<label>span数/每个服务：</label>*/}
                    <IceLabel status='success' style={{...styles.spanNumPerService}}>service1 x3</IceLabel>
                    <IceLabel status='success' style={{...styles.spanNumPerService}}>service2 x2</IceLabel>
                    <IceLabel status='danger' style={{...styles.spanNumPerService}}>service3 x2</IceLabel>
                </Row>
            </Card>
        );
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




export default injectIntl(LinkOverview)
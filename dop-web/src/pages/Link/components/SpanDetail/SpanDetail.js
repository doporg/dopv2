import React,{Component} from 'react';
import {injectIntl} from "react-intl";

class SpanDetail extends Component{
    constructor(props) {
        super(props);
        this.state = {
            traceInfo : props.traceInfo
        }
    }

    componentWillMount() {
    }

    componentDidMount() {
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({
            traceInfo: nextProps.traceInfo
        });
    }

    render() {
        console.log("SpanDetail render");
        return (
            <div>
                {
                    this.state.traceInfo.map((item, index) => {
                        return (
                            <p key={index}>
                                content:{item.id}&nbsp;&nbsp;{item.duration}
                            </p>
                        );
                    })
                }
            </div>
        );
    }
}

export default injectIntl(SpanDetail);
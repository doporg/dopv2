import React,{Component} from 'react';
import {injectIntl} from 'react-intl';

import NothingPrompt from '../NothingPrompt/NothingPrompt'

class ShowTraceOrNothing extends Component{
    constructor(props) {
        super(props);
        this.state = {
            traceId: props.traceId,
            trace: props.trace
        };
    }

    componentWillMount() {
    }

    componentDidMount() {
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({
            traceId: nextProps.traceId,
            trace: nextProps.trace
        });
    }

    judgeIfShowTrace = (traceId) => {
        // console.log("判断有没有traceId： " + traceId);
        return !(traceId === undefined || traceId === '' || traceId === null);
    };

    judgeTraceExist = (trace) => {
        return trace.length !== 0;
    };

    render() {
        const {traceId, trace, children} = this.props;
        if (!this.judgeIfShowTrace(this.state.traceId)) { // url中不包含traceId
            return <NothingPrompt type='atm' prompt={this.props.intl.messages['link.enter.traceId.prompt']} />;
        } else if (!this.judgeTraceExist(this.state.trace)) { // url中包含了要查找的trace，但是该trace不存在
            return <NothingPrompt type='error' prompt={this.props.intl.messages['link.trace.not.exist']}/>
        } else {
            return children;
        }

    }
}

export default injectIntl(ShowTraceOrNothing);
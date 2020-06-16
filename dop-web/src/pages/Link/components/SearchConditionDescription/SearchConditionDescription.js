import React,{Component} from 'react';
import {injectIntl} from 'react-intl';
import Dialog from "@icedesign/base/lib/dialog";

class SearchConditionDescription extends Component{

    constructor(props) {
        super(props);
        this.state = {
            visible: props.helpDialogVisible
        }
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({
            visible: nextProps.helpDialogVisible
        })
    }

    render() {
        return (
        <Dialog visible={this.state.visible}
                style={{width: "50%"}}
                footer={false}
                title={this.props.intl.messages['link.search.condition.description']}
                onClose={()=>{this.setState({visible: false});}}>
            查询数据说明
        </Dialog>);
    }
}

export default injectIntl(SearchConditionDescription);
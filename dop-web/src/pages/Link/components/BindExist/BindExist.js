import React, {Component} from 'react';
import {injectIntl} from "react-intl";
import {Link} from "react-router-dom";

import Icon from "@icedesign/base/lib/icon";
import IceContainer from '@icedesign/container';

import "../../linkStyles.css"

class BindExist extends Component{

    constructor(props) {
        super(props);
        this.state = {

        }
    }

    render() {
        const {bindInfo, children} = this.props;
        if (bindInfo === null || bindInfo === "" || bindInfo === {}) {
            return (
            <IceContainer style={{margin: '0 auto'}}>
                <div className="empty-prompt">
                    <div className='prompt-icon-div'>
                        <Icon type='cry' size='xxxl' style={{opacity:'0.5',cursor:'pointer'}}/>
                    </div>
                    <div className='prompt-h-div'>
                        <p className="prompt-p">
                            很抱歉，什么都没找到。<Link to={"/link/notify-setting"}>返回列表</Link>
                        </p>
                    </div>
                </div>
            </IceContainer>)
        } else {
            return children;
        }
    }
}

export default injectIntl(BindExist);
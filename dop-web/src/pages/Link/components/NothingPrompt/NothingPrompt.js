import React,{Component} from 'react';
import {injectIntl} from "react-intl";

import Icon from "@icedesign/base/lib/icon";
import IceContainer from '@icedesign/container';

import '../../linkStyles.css'

class NothingPrompt extends Component{
    constructor(props) {
        super(props);
        // console.log("NothingPrompt: " + JSON.stringify(props));
    }

    render() {
        const {type, prompt} = this.props;
        return (
            <IceContainer style={{margin: '0 auto'}}>
                <div className="empty-prompt">
                    <div className='prompt-icon-div'>
                        <Icon type={type} size='xxxl' style={{opacity:'0.5',cursor:'pointer'}}/>
                    </div>
                    <div className='prompt-h-div'>
                        <p className="prompt-p">{prompt}</p>
                    </div>
                </div>
            </IceContainer>
        );
    }
}

export default injectIntl(NothingPrompt);
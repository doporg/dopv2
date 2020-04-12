import React, {Component} from 'react';
import { Icon, Button } from "@icedesign/base";
import {injectIntl} from "react-intl";
import './Strategy.css'


class Strategy extends Component {

    onOpen = () =>{
        this.props.history.push('/alert/newStrategy');
    };
    render() {
        return (
            <div>
                <Button type="primary" style={styles.button} text-align="center" onClick={this.onOpen.bind(this)} className="btn-new-branch-add">
                    <Icon type="add"  size="xs"  />
                    { this.props.intl.messages['alert.strategy,newStrategy']}
                </Button>
            </div>

        )

    }

}


const styles = {
    icon: {
        color: '#2c72ee',
        cursor: 'pointer',
    },
    center: {
        textAlign: 'right',
    },
};

export default injectIntl(Strategy)
import React, {Component} from 'react';
import { Button } from "@icedesign/base";
import {injectIntl} from "react-intl";


class AlertLog extends Component {
    render() {
        return (
            <div>
                alertLog
                <Button type="primary">主要按钮</Button>
            </div>
        )
    }
}
export default injectIntl(AlertLog)
import React, {Component} from 'react';
import { Button } from "@icedesign/base";
import {injectIntl} from "react-intl";


class Alert extends Component {
    render() {
        return (
            <div>
                11111
                <Button type="primary">主要按钮</Button>
            </div>
        )
    }
}
export default injectIntl(Alert)
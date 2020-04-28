import React, {Component} from 'react';
import { Button } from "@icedesign/base";
import {injectIntl} from "react-intl";


class Demo extends Component {
    render() {
        return (
            <div>
                11111
                <Button type="primary">主要222按钮</Button>
            </div>
        )
    }
}
export default injectIntl(Demo)

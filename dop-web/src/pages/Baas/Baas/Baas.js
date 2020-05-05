import React, {Component} from 'react';
import { Button } from "@icedesign/base";
import {FormattedMessage, injectIntl} from "react-intl";


class Baas extends Component {
    render() {
        return (
            <div>
                <Button type="primary">主要222按钮</Button>
                <FormattedMessage
                    id="baas.hpy"
                    // defaultMessage="运行流水线"
                />
            </div>
        )
    }
}
export default injectIntl(Baas)

import React, {Component} from 'react';
import { FormattedMessage} from 'react-intl';
const alertMenuConfig=[
    {
        name: <FormattedMessage id="permission.home" defaultMessage="首页"/>,
        path: '/',
        icon: 'home2'
    },
    {
        name: <FormattedMessage id="alert.menu.strategy" defaultMessage="策略管理"/>,
        path: '/alert/strategy',
        icon: 'form' ,
    },
    {
        name: <FormattedMessage id="alert.menu.alertLog" defaultMessage="告警日志"/>,
        path: '/alert/alertLog',
        icon: 'history',
    }
];
export {alertMenuConfig};

import React, {Component} from 'react';
import { FormattedMessage} from 'react-intl';
const gatewayMenuConfig=[
    {
        name: <FormattedMessage id="permission.home" defaultMessage="首页"/>,
        path: '/',
        icon: 'home2'
    },
    {
        name: <FormattedMessage id="gateway.menu.apiManage" defaultMessage="API管理"/>,
        path: '/gateway',
        icon: 'repair' ,
    },
    {
        name: <FormattedMessage id="gateway.menu.routePolicyManage" defaultMessage="路由策略管理"/>,
        path: '/gateway/route',
        icon: 'box',
    },
    {
        name: <FormattedMessage id="gateway.menu.currentLimitPolicyManage" defaultMessage="流控策略管理"/>,
        path: '/gateway/currentLimit',
        icon: 'box',
    },
];
export {gatewayMenuConfig};

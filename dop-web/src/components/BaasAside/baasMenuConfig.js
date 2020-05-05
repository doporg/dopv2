import React, {Component} from 'react';
import { FormattedMessage} from 'react-intl';
const baasMenuConfig=[
    {
        name: <FormattedMessage id="baas.home" defaultMessage="首页"/>,
        path: '/',
        icon: 'home2'
    },
    {
        name: <FormattedMessage id="baas.menu.monitor" defaultMessage="网络监控"/>,
        path: '/baas/monitor',
        icon: 'chart' ,
    },
    {
        name: <FormattedMessage id="baas.menu.chaincode" defaultMessage="链码管理"/>,
        path: '/baas/chaincode',
        icon: 'repair' ,
    },
    {
        name: <FormattedMessage id="baas.menu.user" defaultMessage="用户管理"/>,
        path: '/baas/user',
        icon: 'fans' ,
    },
    {
        name: <FormattedMessage id="baas.menu.create" defaultMessage="新建fabric网络"/>,
        path: '/baas/create',
        icon: 'add',
    },
];
export {baasMenuConfig};

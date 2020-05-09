import React, {Component} from 'react';
import { FormattedMessage} from 'react-intl';
const baasMenuConfig=[
    {
        name: '返回',
        path: '/',
        icon: 'arrow-left'
    },
    {
        name: <FormattedMessage id="baas.home" defaultMessage="区块链首页"/>,
        path: '/baas',
        icon: 'home2'
    },
    {
        name: <FormattedMessage id="baas.menu.channel" defaultMessage="通道管理"/>,
        path: '/baas/channel',
        icon: 'process' ,
    },

    {
        name: <FormattedMessage id="baas.menu.chaincode" defaultMessage="链码管理"/>,
        path: '/baas/chaincode',
        icon: 'repair' ,
    },
    {
        name: <FormattedMessage id="baas.menu.monitor" defaultMessage="网络监控"/>,
        path: '/baas/monitor',
        icon: 'chart' ,
    },
    {
        name: <FormattedMessage id="baas.menu.user" defaultMessage="用户管理"/>,
        path: '/baas/user',
        icon: 'fans' ,
    },
    {
        name: <FormattedMessage id="baas.menu.create" defaultMessage="新建fabric网络"/>,
        path: '/baas/add',
        icon: 'add',
    },
];
export {baasMenuConfig};

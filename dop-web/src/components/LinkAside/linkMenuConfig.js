import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
const linkAsideMenuConfig=[
    {
        name: <FormattedMessage id="link.home" defaultMessage="首页"/>,
        path: '/project',
        icon: 'home2'
    },
    {
        name: <FormattedMessage id="link.list" defaultMessage="链路列表"/>,
        path: '/link/list',
        icon: 'nav-list',
    },
    {
        name: <FormattedMessage id="link.detail" defaultMessage="链路详情"/>,
        path: '/link/detail',
        icon: 'form'
    },
    {
        name: <FormattedMessage id="link.star" defaultMessage="收藏列表"/>,
        path: '/link/star',
        icon: 'favorite',
    },
    {
        name: <FormattedMessage id='link.bind' defaultMessage="监控绑定"/>,
        path: '/link/notify-setting',
        icon: 'set'
    }

];
export {linkAsideMenuConfig};
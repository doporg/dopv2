import Baas from './Baas/Baas'
import User from './User/User'
import Monitor from './Monitor/Monitor'
import CreateBaas from './CreateBaas/CreateBaas'
import Channel from './Channel/Channel'
import ChainCode from './ChainCode/ChainCode'
import BaasLayout from "../../layouts/BaasLayout";
import {FormattedMessage} from "react-intl";
import React from "react";


const baasConfig = [
    {
        path: '/baas/monitor/:id',
        component: Monitor,
        layout: BaasLayout,
        isLogin: true
    },
    {
        path: '/baas/monitor',
        component: Monitor,
        layout: BaasLayout,
        isLogin: true
    },
    {
        path: '/baas/chaincode',
        component: ChainCode,
        layout: BaasLayout,
        isLogin: true
    },
    {
        path: '/baas/user',
        component: User,
        layout: BaasLayout,
        isLogin: true
    },

    {
        path: '/baas/add',
        component: CreateBaas,
        layout: BaasLayout,
        isLogin: true
    },
    {
        path: '/baas',
        component: Baas,
        layout: BaasLayout,
        isLogin: true
    },
    {
        path: '/baas/channel',
        component: Channel,
        layout: BaasLayout,
        isLogin: true
    }
];


export {baasConfig};

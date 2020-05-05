import Baas from './Baas/Baas'
import BaasLayout from "../../layouts/BaasLayout";
import {FormattedMessage} from "react-intl";
import React from "react";


const baasConfig = [
    {
        path: '/baas/monitor',
        component: Baas,
        layout: BaasLayout,
        isLogin: true
    },
    {
        path: '/baas/chaincode',
        component: Baas,
        layout: BaasLayout,
        isLogin: true
    },
    {
        path: '/baas/user',
        component: Baas,
        layout: BaasLayout,
        isLogin: true
    },

    {
        path: '/baas/create',
        component: Baas,
        layout: BaasLayout,
        isLogin: true
    },
    {
        path: '/baas',
        component: Baas,
        layout: BaasLayout,
        isLogin: true
    }
];


export {baasConfig};

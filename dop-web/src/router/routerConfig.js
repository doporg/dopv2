// 以下文件格式为描述路由的协议格式
// 你可以调整 routerConfig 里的内容


import BasicLayout from '../layouts/BasicLayout';
import {projectConfig} from '../pages/Projects'
import {pipelineConfig} from '../pages/Pipeline';
import {NotFound, NotPermission} from '../pages/NotFound';
import {loginConfig} from '../pages/Login'
import {permissionConfig} from "../pages/Permissions";
import codeConfig from "../pages/Code";
import {testConfig} from "../pages/TestCases";
import {imageConfig} from "../pages/Image";
import {alertConfig} from "../pages/Alert";
import {scanConfig} from '../pages/Scan'
import {gatewayConfig} from "../pages/Gateway";

import {linkConfig} from "../pages/Link";
import {demoConfig} from "../pages/Demo";
import {baasConfig} from "../pages/Baas";


const baseConfig = [
    {
        path: '/notPermission',
        layout: BasicLayout,
        component: NotPermission,
    },
    {
        path: '*',
        layout: BasicLayout,
        component: NotFound,
    },

];


const routerConfig = [
    ...alertConfig,
    ...scanConfig,
    ...linkConfig,
    ...demoConfig,
    ...imageConfig,
    ...testConfig,
    ...codeConfig,
    ...projectConfig,
    ...permissionConfig,
    ...pipelineConfig,
    ...loginConfig,
    ...baasConfig,
    ...gatewayConfig,
    ...baseConfig
];

export default routerConfig;

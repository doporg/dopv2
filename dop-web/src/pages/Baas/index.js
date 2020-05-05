import Baas from './Baas/Baas'
import BasicLayout from "../../layouts/BasicLayout";


const baasConfig = [
    {
        path: '/baas',
        component: Baas,
        layout: BasicLayout,
        isLogin: true
    }
];


export {baasConfig};

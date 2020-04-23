import Gateway from './Gateway';
import RouteTable from './components/RouteTable/RouteTable';
import BasicLayout from "../../layouts/BasicLayout";
import CreateApi from "./CreateApi";
import EditApiInfo from "./EditApiInfo";


const gatewayConfig = [
    {
        path: '/gateway',
        layout: BasicLayout,
        component: Gateway,
        isLogin: true
    },
    {
        path: "/gateway/createApi",
        layout: BasicLayout,
        component: CreateApi,
        isLogin: true
    },
    {
        path: "/gateway/editApi/:apiId",
        layout: BasicLayout,
        component: EditApiInfo,
        isLogin: true
    },
    {
        path: "/gateway/route",
        layout: BasicLayout,
        component: RouteTable,
        isLogin: true
    },
];

export {Gateway,gatewayConfig};

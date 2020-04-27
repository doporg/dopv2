import Gateway from './Gateway';
import GatewayLayout from "../../layouts/GatewayLayout";
import RouteTable from './components/RouteTable/RouteTable';
import CurrentLimitTable from "./components/CurrentLimitTable/CurrentLimitTable";
import CreateApi from "./CreateApi";
import EditApiInfo from "./EditApiInfo";
import CreateWeightingPolicyForm from "./components/CreateWeightingPolicy/CreateWeightingPolicyForm/index";
import CreateServiceDiscoveryPolicyForm from "./components/CreateServiceDiscovoryPolicyForm";
import CreateCurrentLimitPolicyForm from "./components/CreateCurrentLimitPolicyForm";
import  EditWeightingPolicy from "./components/EditWeightingPolicy"
import  EditServiceDiscoveryPolicy from "./components/EditServiceDiscoveryPolicy"
import  EditCurrentLimitPolicy from "./components/EditCurrentLimitPolicy";


const gatewayConfig = [
    {
        path: '/gateway',
        layout: GatewayLayout,
        component: Gateway,
        isLogin: true
    },
    {
        path: "/gateway/createApi",
        layout: GatewayLayout,
        component: CreateApi,
        isLogin: true
    },
    {
        path: "/gateway/editApi/:apiId",
        layout: GatewayLayout,
        component: EditApiInfo,
        isLogin: true
    },
    {
        path: "/gateway/route",
        layout: GatewayLayout,
        component: RouteTable,
        isLogin: true
    },
    {
        path: "/gateway/route/createWeightingPolicy",
        layout: GatewayLayout,
        component: CreateWeightingPolicyForm,
        isLogin: true
    },
    {
        path: "/gateway/route/createServiceDiscoveryPolicyForm",
        layout: GatewayLayout,
        component: CreateServiceDiscoveryPolicyForm,
        isLogin: true
    },
    {
        path: "/gateway/route/editWeightingPolicy/:policyId",
        layout: GatewayLayout,
        component: EditWeightingPolicy,
        isLogin: true
    },
    {
        path: "/gateway/route/editServiceDiscoveryPolicy/:policyId",
        layout: GatewayLayout,
        component: EditServiceDiscoveryPolicy,
        isLogin: true
    },
    {
        path: "/gateway/currentLimit",
        layout: GatewayLayout,
        component: CurrentLimitTable,
        isLogin: true
    },
    {
        path: "/gateway/currentLimit/createCurrentLimitForm",
        layout: GatewayLayout,
        component: CreateCurrentLimitPolicyForm,
        isLogin: true
    },
    {
        path: "/gateway/currentLimit/editCurrentLimitPolicy/:policyId",
        layout: GatewayLayout,
        component: EditCurrentLimitPolicy,
        isLogin: true
    },
];

export {Gateway,gatewayConfig};

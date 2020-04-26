import Gateway from './Gateway';
import RouteTable from './components/RouteTable/RouteTable';
import BasicLayout from "../../layouts/BasicLayout";
import GatewayLayout from "../../layouts/GatewayLayout";
import CreateApi from "./CreateApi";
import EditApiInfo from "./EditApiInfo";
import CreateWeightingPolicyForm from "./components/CreateWeightingPolicy/CreateWeightingPolicyForm/index";
import CreateServiceDiscoveryPolicyForm from "./components/CreateServiceDiscovoryPolicyForm/CreateServiceDiscoveryPolicyForm";
import  EditWeightingPolicy from "./components/EditWeightingPolicy"
import  EditServiceDiscoveryPolicy from "./components/EditServiceDiscoveryPolicy"


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
];

export {Gateway,gatewayConfig};

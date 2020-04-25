import Gateway from './Gateway';
import RouteTable from './components/RouteTable/RouteTable';
import BasicLayout from "../../layouts/BasicLayout";
import CreateApi from "./CreateApi";
import EditApiInfo from "./EditApiInfo";
import CreateWeightingPolicyForm from "./components/CreateWeightingPolicy/CreateWeightingPolicyForm/index";
import CreateServiceDiscoveryPolicyForm from "./components/CreateServiceDiscovoryPolicyForm/CreateServiceDiscoveryPolicyForm";
import  EditWeightingPolicy from "./components/EditWeightingPolicy"
import  EditServiceDiscoveryPolicy from "./components/EditServiceDiscoveryPolicy"


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
    {
        path: "/gateway/route/createWeightingPolicy",
        layout: BasicLayout,
        component: CreateWeightingPolicyForm,
        isLogin: true
    },
    {
        path: "/gateway/route/createServiceDiscoveryPolicyForm",
        layout: BasicLayout,
        component: CreateServiceDiscoveryPolicyForm,
        isLogin: true
    },
    {
        path: "/gateway/route/editWeightingPolicy/:policyId",
        layout: BasicLayout,
        component: EditWeightingPolicy,
        isLogin: true
    },
    {
        path: "/gateway/route/editServiceDiscoveryPolicy/:policyId",
        layout: BasicLayout,
        component: EditServiceDiscoveryPolicy,
        isLogin: true
    },
];

export {Gateway,gatewayConfig};

import Alert from './Alert'
import Strategy from "./Strategy/Strategy";
import AlertLog from "./AlertLog/AlertLog";
import AlertLayout from "../../layouts/AlertLayout/AlertLayout";
import NewStrategy from "./NewStrategy/NewStrategy";
import Contact from "./Contact/Contact";

const alertConfig = [
    {
        path: '/alert',
        component: Alert,
        layout: AlertLayout,
        isLogin: true
    },
    {
        path: '/alert/Strategy',
        component: Strategy,
        layout: AlertLayout,
        isLogin: true
    },
    {
        path: '/alert/AlertLog',
        component: AlertLog,
        layout: AlertLayout,
        isLogin: true
    },
    {
        path: '/alert/NewStrategy',
        component: NewStrategy,
        layout: AlertLayout,
        isLogin: true
    },
    {
        path: '/alert/Contact',
        component: Contact,
        layout: AlertLayout,
        isLogin: true
    }
];


export {alertConfig};
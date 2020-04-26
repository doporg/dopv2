import Alert from './Alert'
import Strategy from "./Strategy/Strategy";
import AlertLog from "./AlertLog/AlertLog";
import AlertLayout from "../../layouts/AlertLayout/AlertLayout";
import NewStrategy from "./NewStrategy/NewStrategy";
import Contact from "./Contact/Contact";
import EditContact from "./EditContact/EditContact"

const alertConfig = [
    {
        path: '/alert',
        component: Alert,
        layout: AlertLayout,
        isLogin: true
    },
    {
        path: '/alert/strategy',
        component: Strategy,
        layout: AlertLayout,
        isLogin: true
    },
    {
        path: '/alert/alertLog',
        component: AlertLog,
        layout: AlertLayout,
        isLogin: true
    },
    {
        path: '/alert/newStrategy',
        component: NewStrategy,
        layout: AlertLayout,
        isLogin: true
    },
    {
        path: '/alert/contact',
        component: Contact,
        layout: AlertLayout,
        isLogin: true
    },
    {
        path: '/alert/editContact/:contactId',
        component: EditContact,
        layout: AlertLayout,
        isLogin: true
    }
];


export {alertConfig};
import Requirement from './Requirement/Requirement';
import RequirementLayout from "../../layouts/RequirementLayout";

const requirementConfig = [

    {
        path: '/requirement/newRequirement',
        layout: RequirementLayout,
        component: Requirement,
        isLogin: true
    },

];

export {requirementConfig}

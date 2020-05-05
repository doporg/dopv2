import ScanTaskList from './ScanTaskList/ScanTaskList'
import BasicLayout from '../../layouts/BasicLayout'
import TestLayout from '../../layouts/TestLayout'
import ShowScanResult from './ShowScanResult/ShowScanResult'
import Scan from './Scan'

const scanConfig = [
    {
        path: '/scan',
        component: Scan,
        layout: BasicLayout,
        isLogin: true
    },
    {
        path: '/scan/result/:projectKey',
        component:ShowScanResult,
        layout:BasicLayout,
        isLogin:true
    }
]


export {scanConfig}

import LinkList from './LinkList/LinkList';
import LinkDetail from './LinkDetail/LinkDetail';
import LinkStar from './LinkStar/LinkStar';
import LinkBind from './LinkBind/LinkBind';
import LinkLayout from '../../layouts/LinkLayout';

const linkConfig = [

    {
        path: '/link/list',
        layout: LinkLayout,
        component: LinkList,
        isLogin: true
    },
    {
        path: '/link/detail/:traceId',
        layout: LinkLayout,
        component: LinkDetail,
        isLogin: true
    },
    {
        path: '/link/detail',
        layout: LinkLayout,
        component: LinkDetail,
        isLogin: true
    },
    {
        path: '/link/star',
        layout: LinkLayout,
        component: LinkStar,
        isLogin: true
    },
    {
        path: '/link/notify-setting',
        layout: LinkLayout,
        component: LinkBind,
        isLogin: true
    }
];

export {LinkList, LinkDetail, LinkStar, linkConfig}

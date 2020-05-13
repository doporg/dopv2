import LinkList from './LinkList/LinkList';
import LinkSearch from './LinkSearch/LinkSearch';
import LinkStar from './LinkStar/LinkStar';
import LinkBind from './LinkBind/LinkBind';
import LinkBindDetail from "./LinkBind/LinkBindDetail/LinkBindDetail";
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
        component: LinkSearch,
        isLogin: true
    },
    {
        path: '/link/detail',
        layout: LinkLayout,
        component: LinkSearch,
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
    },
    {
        path: '/link/notify-setting/:bid',
        layout: LinkLayout,
        component: LinkBindDetail,
        isLogin: true
    }
];

export {LinkList, LinkSearch, LinkStar, LinkBind, LinkBindDetail, linkConfig}

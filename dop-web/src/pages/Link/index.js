import LinkList from './LinkList/LinkList';
import LinkDetail from './LinkDetail/LinkDetail';
import LinkStar from './LinkStar/LinkStar';
import LinkLayout from "../../layouts/LinkLayout";

const linkConfig = [

    {
        path: '/link/list',
        layout: LinkLayout,
        component: LinkList,
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
    }
];

export {LinkList, LinkDetail, LinkStar, linkConfig}

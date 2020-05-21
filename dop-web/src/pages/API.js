// 配置所有接口的API文件

//const host = "http://open.dop.clsaa.com";
const host = "http://localhost:14700";
const pipeline = "/pipeline-server";
const application = "/application-server";
const permission = "/permission-server";
const code = "/code-server";
const user = "/user-server";
const image = "/image-server";
const test = "/test-server";
const scan = "/testing-server";
const gateway = "/v2/api";
const link = "/link-server";

const API = {
    address: "http://www.dop.clsaa.com/#/",
    // address: "http://localhost:3000/#/",
    gateway: host,

    pipeline: host + pipeline,
    application: host + application,
    permission: host + permission,
    code: host + code,
    user: host + user,
    image: host + image,
    test: host + test,
    scan:host+scan,
    link: host + link

};

export default API;

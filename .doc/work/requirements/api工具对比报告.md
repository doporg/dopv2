## api工具对比报告



### 1. zuul

​		Zuul是Netflix出品的一个基于JVM路由和服务端的负载均衡器，能够提供动态路由，监控，弹性，安全等的边缘服务。

​		**Zuul组成**

​		Filter是Zuul的核心，Filter的生命周期有4个，分别是  “PRE”、“ROUTING”、“POST”、“ERROR”，整个生命周期可以用下图来表示。

![img](http://favorites.ren/assets/images/2018/springcloud/zuul-core.png)

​		Zuul大部分功能都是通过过滤器来实现的，这些过滤器类型对应于请求的典型生命周期。

- **PRE：** 这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
- **ROUTING：**这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用Apache HttpClient或Netfilx Ribbon请求微服务。
- **POST：**这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
- **ERROR：**在其他阶段发生错误时执行该过滤器。 除了默认的过滤器类型，Zuul还允许我们创建自定义的过滤器类型。例如，我们可以定制一种STATIC类型的过滤器，直接在Zuul中生成响应，而不将请求转发到后端的微服务。



**Zuul 特点**

* 构建于 Servlet 2.5，兼容 3.x
* 使用阻塞式的 API，不支持长连接，比如 websockets

### 2. spring cloud gateway

​		Spring Cloud Gateway 是 Spring Cloud 的一个全新项目，该项目是基于 Spring 5.0，Spring Boot 2.0 和 Project Reactor 等技术开发的网关，它旨在为微服务架构提供一种简单有效的统一的 API 路由管理方式。

**spring cloud Gateway组成：**

![img](http://favorites.ren/assets/images/2018/springcloud/spring-cloud-gateway.png)

​		客户端向 Spring Cloud Gateway 发出请求。如果 Gateway Handler Mapping 中找到与请求相匹配的路由，将其发送到 Gateway Web Handler。Handler 再通过指定的过滤器链来将请求发送到我们实际的服务执行业务逻辑，然后返回。 过滤器可能会在发送代理请求之前（“pre”）或之后（“post”）执行业务逻辑。

**spring cloud gateway特点：**

- 基于 Spring Framework 5，Project Reactor 和 Spring Boot 2.0
- 动态路由
- 使用响应式的、非阻塞式的 API，支持 websockets，和 Spring 框架紧密集成
- Predicates 和 Filters 作用于特定路由
- 集成 Hystrix 断路器
- 集成 Spring Cloud DiscoveryClient
- 易于编写的 Predicates 和 Filters

### 3. Kong

​		Kong，是基于Nginx_Lua模块写的高可用，易扩展的，由Mashape公司开源的API Gateway项目。由于Kong是基于Nginx的，所以可以水平扩展多个Kong服务器，通过前置的负载均衡配置把请求均匀地分发到各个Server，来应对大批量的网络请求。

**Kong 组成**

Kong主要有三个组件：

- Kong Server ：基于nginx的服务器，用来接收API请求。
- Apache Cassandra/PostgreSQL ：用来存储操作数据。
- Kong dashboard：官方推荐UI管理工具，当然，也可以使用 restfull 方式 管理admin api。

Kong采用插件机制进行功能定制，插件集（可以是0或n个）在API请求响应循环的生命周期中被执行。插件使用Lua编写，目前已有几个基础功能：HTTP基本认证、密钥认证、CORS（ Cross-origin Resource Sharing，跨域资源共享）、TCP、UDP、文件日志、API请求限流、请求转发以及nginx监控。

**Kong 特点**

* 云原生
* 基于nginx实现
* 支持动态路由
* 可扩展，支持分布式
* 模块化功能：授权、日志、ip限制、限流、api 统计分析
* 二次开发难度较大







### 4. 参考资料

1. 服务网关 Spring Cloud Gateway 入门.http://www.ityouknow.com/springcloud/2018/12/12/spring-cloud-gateway-start.html
2. 服务网关zuul初级篇.http://www.ityouknow.com/springcloud/2017/06/01/gateway-service-zuul.html

3. Comparing API Gateway Performances: NGINX vs. ZUUL vs. Spring Cloud Gateway vs. Linkerd.[Turgay Çelik](https://engineering.opsgenie.com/@tcelik?source=post_page-----b2cc59c65369----------------------).https://engineering.opsgenie.com/comparing-api-gateway-performances-nginx-vs-zuul-vs-spring-cloud-gateway-vs-linkerd-b2cc59c65369
4. KONG API Gateway-用户指南.https://github.com/cloudframeworks-apigateway/user-guide-apigateway#%E6%A1%86%E6%9E%B6%E8%AF%B4%E6%98%8E-%E4%B8%9A%E5%8A%A1
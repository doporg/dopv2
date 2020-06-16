# 链路跟踪模块

### 1. 接入

应用需要接入链路跟踪模块，以Spring Boot搭建的web应用为例，只需在pom文件中添加spring-cloud-starter-zipkin依赖，并在application.yml（或application.properties）中设置zipkin服务器的url（http://localhost:14300）即可。其他架构应用如何接入zipkin，请自行学习。

![](https://ftp.bmp.ovh/imgs/2020/06/ee7f177562607e64.png)

### 2. 使用

#### 2.1 进入

登录DevOps平台后，选择“链路跟踪”，进入本模块。

![](https://ftp.bmp.ovh/imgs/2020/06/50eaef4fa07ded96.png)

#### 2.2 链路筛选

对于接入链路跟踪服务的项目，可以在这个页面筛选符合要求的链路，首先选择要查看的项目。

![](https://i.bmp.ovh/imgs/2020/06/4a034bbfcf14bf6f.png)![](https://ftp.bmp.ovh/imgs/2020/06/7891e49abea00819.png)

填写筛选条件筛选条件后，点击提交可以查看符合条件的链路

![](https://ftp.bmp.ovh/imgs/2020/06/8684e5f189cb6a1a.png)

#### 2.3 查看链路详情

在“链路搜索”界面输入traceId

![](https://ftp.bmp.ovh/imgs/2020/06/78aeb2311bbc9091.png)

或者点击链路列表中的traceId，可以查看该链路的详细信息，还可以收藏或缓存该链路

![](https://ftp.bmp.ovh/imgs/2020/06/8b33666876573b68.png)

链路详情有两种展示视图，可以点击“时间轴”切换视图

![](https://ftp.bmp.ovh/imgs/2020/06/48387f9d339a2313.png)

点击依赖图和时间轴的服务节点都可以查看具体信息

![](https://ftp.bmp.ovh/imgs/2020/06/43aa51e9a4c5f49b.png)

#### 2.4 链路收藏

点击“收藏列表”可以查看所有收藏的链路，列表提供分页和排序功能，还可以根据备注关键字搜索。对于每个链路可以进行编辑和删除操作。

![](https://ftp.bmp.ovh/imgs/2020/06/13ffdbb390903cc8.png)

![](https://ftp.bmp.ovh/imgs/2020/06/16a2c8c1c1b01e2d.png)

#### 2.5 链路监控

点击“监控绑定”可以查看所有的监控列表。

![](https://ftp.bmp.ovh/imgs/2020/06/5c3ef00b14cb6968.png)

点击“创建链路监控”，填写创建表单，创建新的监控服务。对于每个监控，还可以执行编辑和删除操作。

![](https://ftp.bmp.ovh/imgs/2020/06/d6a83904fca32bc6.png)

点击“查看”可以查询到该监控的全部信息，包括配置内容、监控数据和通知列表，在这个界面还可以进行停止或启动操作。

![](https://ftp.bmp.ovh/imgs/2020/06/50da4549f19f46fa.png)


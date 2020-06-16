#告警模块

## 历史版本
| 版本 | 修改内容 | 修改人 | 修改日期   |
| ---- | -------- | ------ | ---------- |
| v1.0 | 初始版本 | 洪铨健 | 2020-02-12 |
| v1.5 | 详细需求版本 | 洪铨健 | 2020-02-15 |



## 一、	概述

​	微服务的架构成为如今软件开发的主流，应用会被拆分成多个微服务，导致监控数据的爆炸增长，运维人员无法快速处理和展示这些监控数据。如果我们不能进行有效的实时监控，及时的处理，经常会带来巨大损失。为了保证异常的即使处理，我们设计一套系统，对产生的日志进行实时的监控报警，当出现异常或者指定的信息的时候，给我们的对应工作人员发送邮件和短信提醒，同时记录异常的信息。做到线上异常的实时监控。




## 二、	告警模块功能点介绍
### 2.1	告警策略管理

​	告警策略管理是告警模块的核心部分，通过对告警规则的设置，模块能够对告警对象按照规则进行实时监控告警。

​	点击菜单栏的告警图标，可以进入告警管理界面。在告警管理界面，点击第二菜单栏的“告警设置”选项，进入告警设置页面。在这个页面，用户可以创建新的告警策略，查看，修改和删除已有的告警策略。

​	当触发规则时，系统会自动对告警联系人发送提醒。电话会通过语音告知警告，短信和邮箱会告知告警所在的产品和告警规则并附上链接，跳转至告警日志显示告警的信息。

#### 2.1.1	创建新告警策略
当点击”创建告警策略”按钮，会进入添加告警规则页面，需要根据以下步骤配置告警规则：

>（1）在''基本信息“一栏，填写新增告警规则名和描述，选择状态和告警级别
>（2）在''告警规则“一栏，添加若干条规则，每个规则需选择一个指标参数和条件，设定阈值，统计周期和重复次数
>（3）在''告警对象“一栏，在实例列表中选着相应的产品与实例应用于该告警规则
>（4）在''告警通知“一栏，加入相应的接受告警的用户账号，选择通知方式和通知频率。通知方式可以选择电话或短信加邮箱或邮箱通知。
>（5）在''确认信息“一栏，对新建的告警规则进行确认，点击确认创建，创建新的告警策略

#### 2.1.2	查看告警策略

​	在告警设置页面，可以通过搜索框选择对规则名称，描述，或产品名称进行文字匹配搜索。告警级别一栏，可以下拉勾选级别来筛选告警策略。状态栏，可以下拉勾选状态来筛选告警策略。

#### 2.1.3	修改和删除告警策略
​	在告警设置页面，可以对已配置的告警规则进行操作，点击告警规则操作一列的“…”键，可以选择修改和删除该告警规则。点击修该规则会进入与创建告警规则时一样的界面，可以对已设置的规则，状态，对象等属性进行修改



### 2.2	告警日志管理

​	在告警管理界面，点击第二菜单栏的“告警历史”选项，进入告警日志页面。在这个页面，会显示告警的历史，每一次告警的数据，告警规则，超额指标，发生时间，持续时间，产品类型，故障资源，告警对象，状态等。

​	点击每一条告警日志会进入日志信息的界面，显示更多的详细信息（超过阈值的参数数值，时间折线图），处理方式（不确定），每一条告警日志处理之后状态会自动发生更改，从未处理转到已处理状态。

​	在告警日志页面，默认按照时间顺序排序。用户能够根据时间，产品名或规则名称来对告警日志进行筛选，搜索。状态栏，可以下拉勾选状态来筛选告警策略。每一条告警日志可以进行删除操作。



### 2.3	告警联系人管理

​	在告警管理界面，点击第二菜单栏的“告警联系人”选项，进入告警联系人管理页面。在这个页面，可以创建新的告警联系人，修改和删除已有的告警联系人。



#### 2.3.1	新建告警联系人

​	点击新建联系人按钮，会进入新建联系人窗口，需要填写联系人姓名，电话，邮箱

#### 2.3.1	查看告警联系人

​	可以通过搜索框对联系人姓名，电话，邮箱进行搜索

####2.3.1	修改和删除告警联系人

​	在告警联系人页面，可以对告警联系人行操作，点击告警联系人操作一列的“…”键，可以选择修改和删除该告警联系人。点击修改会进入与创建告警联系人一样的界面，可以对已设置的联系人姓名，电话，邮箱进行修改。点击批量删除按钮可以快速删除选择的告警联系人



## 三、与其他模块的关系

### 3.1	用户管理、组织层级、权限管理
​	告警模块需要调用用户及其组织信息，项目信息。组织、项目的管理人员有权对相应项目的告警规则进行设置。



### 3.3	监控模块

​	告警模块需要调用监控模块的所有数据，对数据进行分析，告警。



​	**告警规则如下：**

​	CPU 用量；

​	内存用量 (包含缓存)；

​	内存用量；

​	网络：网络发送数据速率、网络接收数据速率；



​	参考腾讯云：

​	工作负载指标：部署副本不可用率、有状态副本集副本不可用率、守护进程集不可用率 (工作负载的副本不可用率：比如对 Nginx 的 部署设置 5 个副本后正常运行的副本状态是 5/5， 如果部署不可用率设置了大于等于 20%，那么只要当副本运行状态为 4/5 的时刻就会发送告警)


|  |  |  |
| ------------------- | -------------------------------------------------- | ------------------------------------------------------------ |
| 集群健康状态        | 统计周期1分钟，>=1，持续5个周期，每30分钟告警一次  | 集群健康状态取值为：0：绿色，表示集群所有主分片和副本分片都可用，集群处于最健康的状态。1：黄色，表示所有的主分片均可用，但存在不可用副本分片。此时，搜索结果仍然是完整的，但集群的高可用性在一定程度上受到影响，数据面临较高的丢失风险。2：红色，表示至少一个主分片以及它的全部副本分片均不可用。集群处于红色状态意味着已有部分数据不可用，搜索只能返回部分数据，而分配到丢失分片上的请求会返回异常。集群健康状态是集群当前运行情况的最直接体现，当集群处于黄色或红色状态时，应立即排查产生原因，并及时修复，防止数据丢失和服务不可用。 |
| 平均磁盘使用率      | 统计周期1分钟，>80%，持续5个周期，每30分钟告警一次 | 平均磁盘使用率表示集群各节点磁盘使用率的平均值。磁盘使用率过高会导致节点没有足够的磁盘空间容纳分配到该节点上的分片，从而导致创建索引，添加文档等基本操作执行失败。建议在平均磁盘使用率超过75%时及时清理数据或扩容集群。 |
| 平均 JVM 内存使用率 | 统计周期1分钟，>85%，持续5个周期，每30分钟告警一次 | 平均 JVM 内存使用率表示集群各节点 JVM 内存使用率的平均值。JVM 内存使用率过高会导致读写操作被拒绝，集群 GC 频繁，甚至出现 OOM 等问题。当发现 JVM 内存使用率超过阈值时，建议通过纵向扩容的方式提高集群节点的规格。 |
| 平均 CPU 使用率     | 统计周期1分钟，>90%，持续5个周期，每30分钟告警一次 | 平均 CPU 使用率表示集群各节点 CPU 使用率的平均值。该值过高会导致集群节点处理能力下降，甚至宕机。发现 CPU 过高时，应根据集群当前节点配置情况和业务情况，提高节点规格或降低业务请求量。 |
| bulk 拒绝率         | 统计周期1分钟，>0%，持续1个周期， 每30分钟告警一次 | bulk 拒绝率表示单周期内集群执行 bulk 操作被拒绝次数占 bulk 总操作次数的百分比。当 bulk 拒绝率大于0%，即出现 bulk 拒绝时，说明集群已经达到了 bulk 操作处理能力的上限，或集群出现异常，应及时排除出现 bulk 拒绝的原因并及时解决，否则会影响业务的 bulk 操作，甚至出现数据丢失情况。 |
| 查询拒绝率          | 统计周期1分钟，>0%，持续1个周期，每30分钟告警一次  | 查询拒绝率表示单周期内集群执行查询操作被拒绝次数占查询总操作数的百分比。当查询拒绝率大于0%，即出现查询拒绝时，说明集群已经达到了查询操作处理能力的上限，或集群出现异常，因及时排查出现查询拒绝的原因并及时解决，否则会影响业务的查询操作。 |



## 四、	功能点分析
| 用户故事                               | 功能点         | M    | S    | C    | W    | 约束               | 估时/人日 |
| -------------------------------------- | -------------- | ---- | ---- | ---- | ---- | ------------------ | --------- |
| 作为用户，我希望能够创建查看告警策略   | 创建告警策略   | Y    |      |      |      | 用户管理、权限管理 | 3         |
|                                        | 查看告警策略   | Y    |      |      |      |                    | 3         |
|                                        | 删除告警策略   |      |      | Y    |      |                    | 1         |
| 作为用户，我希望能够编辑告警策略       | 修改告警策略   | Y    |      |      |      | 用户管理、权限管理 | 2         |
| 作为用户，我希望能够查看告警日志       | 查看告警日志   | Y    |      |      |      |                    | 2         |
|                                        | 搜索告警日志   |      |      | Y    |      |                    | 2         |
| 作为用户，我希望能够删除告警日志       | 删除告警日志   |      |      | Y    |      | 用户管理           | 1         |
| 作为用户，我希望能够创建查看告警联系人 | 创建告警联系人 | Y    |      |      |      | 用户管理、权限管理 | 3         |
|                                        | 查看告警联系人 | Y    |      |      |      |                    | 1         |
|                                        | 删除告警联系人 |      |      | Y    |      | 用户管理           | 1         |
| 作为用户，我希望能够编辑告警联系人     | 修改告警联系人 | Y    |      |      |      | 用户管理、权限管理 | 2         |

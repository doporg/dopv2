const LINK_ZH_CN = {

    'link.name': '链路跟踪',
    'link.home': '首页',
    'link.list': '链路筛选',
    'link.detail': '链路搜索',
    'link.star': '收藏列表',
    'link.bind': '监控绑定',

    'link.cancel': '取消',
    'link.confirm': '确定',
    'link.close': '关闭',

    'link.view': '查看',
    'link.delete': '删除',
    'link.modify': '编辑',

    'link.success' : '成功',
    'link.fail': '失败',

    'link.choose.project': '选择项目',
    'link.please.choose.project': '请选择要查看的项目',

    'link.change.project': '更换项目',
    'link.project.id': '项目Id',
    'link.project.name': '项目名称',

    //    条件选择部分
    'link.search.by.serviceName': '服务名',
    'link.search.by.spanName': 'Span名称',
    'link.search.by.time': '时间',
    'link.search.by.startTime': '开始时间',
    'link.search.by.endTime': '结束时间',
    'link.search.by.annotation': '根据Annotation查询',
    'link.search.by.minDuration': '持续时间(μs)>=',
    'link.search.by.maxDuration': '持续时间(μs)<=',
    'link.search.by.resultlimit': '数量',
    'link.search.by.sort': '排序',

    'link.search.condition.description': '查询条件说明',

    'link.search.by.traceId': '请输入traceId',

    'link.datepicker.now': '今天',

    'link.search.submit.search': '查询',

    //时间选项
    'link.time.one.hour': '1小时',
    'link.time.three.hours': '3小时',
    'link.time.six.hours': '6小时',
    'link.time.twelve.hours': '12小时',
    'link.time.one.day': '1天',
    'link.time.two.days': '2天',
    'link.time.seven.days': '7天',
    'link.time.customize': '自定义时间区间',

    // 排序选项
    'link.sort.by.timestampDesc': '时间降序',
    'link.sort.by.timestampAsc': '时间升序',
    'link.sort.by.durationDesc':'耗时降序',
    'link.sort.by.durationAsc':'耗时升序',
    'link.sort.by.servicePercentageDesc':'服务占比降序',
    'link.sort.by.servicePercentageAsc':'服务占比升序',

    // 链路列表表头
    'link.list.table.traceId': 'traceId',
    'link.list.table.spanName': '入口span名称',
    'link.list.table.spanNum': 'span总数',
    'link.list.table.timestamp': '时间',
    'link.list.table.duration': '持续时间(ms/s)',
    'link.list.table.successOrFail': '成功/失败',
    'link.list.table.detail':'详情',

    // 提示信息
    'link.warn.noChosenProject' : '未选择项目',
    'link.error.prompt.format': '格式错误',
    'link.error.positive.integer': '请填写正整数',
    'link.error.prompt.contentError': '请正确填写表单',
    'link.error.traceId.regex': 'traceId编码为16-32位小写十六进制字符，请重新输入',

    'link.no.data': '没有数据',

    // link detail页面
    'link.enter.traceId.prompt': '请输入您要查找的traceId',
    'link.trace.not.exist': '您要查找的trace不存在',
    'link.detail.overview': '概览',
    'link.detail.duration': '持续时间：',
    'link.detail.serviceNum': '服务数：',
    'link.detail.depth': '深度：',
    'link.detail.spanNum': 'span总数：',

    // 监控绑定模块
    'link.bind.create': '创建链路监控',
    'link.bind.title.modify': '编辑链路监控',
    'link.bind.tabletitle.id': 'Id',
    'link.bind.tabletitle.title': '名称',
    'link.bind.tabletitle.projectName': '项目名',
    'link.bind.tabletitle.service': '服务',
    'link.bind.tabletitle.ctime': '创建时间',
    'link.bind.tabletitle.threshold': '阈值',
    'link.bind.tabletitle.state': '当前状态',
    'link.bind.tabletitle.notifier': '通知人员',
    'link.bind.tabletitle.notifierEmail': '接收邮箱',
    'link.bind.tabletitle.operate': '操作',
    'link.bind.free': '空闲中',
    'link.bind.running': '运行中',

    'link.bind.stop': '停止',
    'link.bind.delete': '删除',
    'link.bind.modify': '修改',
    'link.bind.start': '开启',

    'link.bind.form.title': '标题',
    'link.choose.service': '选择微服务',

    // 收藏列表
    'link.star.table.id': 'Id',
    'link.star.table.note': '备注',
    'link.star.table.date': '收藏时间',
    'link.star.table.traceId': 'traceId',
    'link.star.table.operate': '操作',

    'link.star.add.note': '添加备注',
    'link.star.modify.note': '编辑备注'
};

export default LINK_ZH_CN;
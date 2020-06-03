export function getTraceSummary(traceId, traceInfo) {

    let summary = {}, rootSpan = {}, rootSpanIndex;
    let spansPerService = new Map();
    traceInfo.sort(function (a, b) {
        return a['timestamp'] - b['timestamp'] > 0 ? 1 : -1
    });
    traceInfo.map((span, index) => {
        if (span.id === traceId) {
            rootSpan = span;
            rootSpanIndex = index;
        }
        if (span.localEndpoint != null) {
            let serviceNameKey = span.localEndpoint.serviceName;
            if (spansPerService.has(serviceNameKey)) {
                spansPerService.set(serviceNameKey, spansPerService.get(serviceNameKey) + 1);
            } else {
                spansPerService.set(serviceNameKey, 1)
            }
        }
    });
    summary.serviceNum = spansPerService.size;
    summary.duration = rootSpan.duration;
    summary.serviceList = spansPerService;
    summary.spanNum = traceInfo.length;
    return summary;
}

let rootStartTs = 0;
export function buildNodeTree(traceId, traceInfo) {
    let rootSpan;
    for (let span of traceInfo) { // 寻找root span
        if (span.id === traceId && span.parentId == null) {
            rootSpan = span;
            rootStartTs = span.timestamp;
            break;
        }
    }
    if (rootSpan === undefined) return null;
    let rootNode = buildRootNode(rootSpan); // 构造根节点
    rootNode.nextNodes = getNextNodes(traceId, traceInfo); // 计算子节点
    return rootNode;
}

function getNextNodes(parentId, traceInfo) {
    let nodes = [];
    // 根据parentId找到所有子span
    let thisLevelSpans = traceInfo.filter(item => item.parentId === parentId);
    if (thisLevelSpans.length === 0) return null;
    let map = new Map();
    for (let span of thisLevelSpans) { // 根据span id区分不同节点
        let spanId = span.id;
        if (!map.has(spanId)) {
            let spanArr = [];
            spanArr.push(span);
            map.set(spanId, spanArr);
        } else {
            let tmpArr = map.get(spanId);
            tmpArr.push(span);
            map.set(spanId, tmpArr);
        }
    }
    map.forEach((value, key) => { // 根据span构造节点
        nodes.push(buildNode(value, key, parentId))
    });
    nodes.map((node) => { // 递归寻找子节点
        node.nextNodes = getNextNodes(node.spanId, traceInfo);
    });
    return nodes;
}
function buildNode(spans, spanId, parentId) {
    let node;
    let hasError = false;
    let serverSpanIndex = -1, clientSpanIndex = -1;
    for (let i = 0;i < spans.length;i++) {
        if (spans[i].kind === 'SERVER') {
            serverSpanIndex = i;
        } else if (spans[i].kind === 'CLIENT') {
            clientSpanIndex = i;
        }
        hasError = hasError || (spans[i].tags.error !== undefined)
    }
    let serviceName;
    if (serverSpanIndex !== -1 ) {
        serviceName = spans[serverSpanIndex].localEndpoint.serviceName
    } else {
        serviceName = null;
    }
    node = {
        spanId: spanId,
        parentId: parentId,
        serviceName: serviceName,
        hasError: hasError,
        spans: spans,
        serverSpanIndex: serverSpanIndex,
        clientSpanIndex: clientSpanIndex,
        rootSpanStartTs: rootStartTs
    };
    return node;
}

function buildRootNode(rootSpan) {
    let spans = [];
    spans.push(rootSpan);
    let rootNode;
    rootNode = {
        spanId: rootSpan.id,
        parentId: null,
        serviceName: rootSpan.localEndpoint.serviceName,
        hasError: rootSpan.tags.error !== undefined,
        spans: spans,
        serverSpanIndex: 0,
        clientSpanIndex: -1,
        rootSpanStartTs: rootStartTs
    };
    return rootNode;
}
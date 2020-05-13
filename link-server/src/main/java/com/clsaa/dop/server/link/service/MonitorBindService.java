package com.clsaa.dop.server.link.service;

import com.clsaa.dop.server.link.dao.BindDao;
import com.clsaa.dop.server.link.enums.MonitorState;
import com.clsaa.dop.server.link.model.po.Bind;
import com.clsaa.dop.server.link.model.vo.monitor.BindVO;
import com.clsaa.dop.server.link.model.vo.monitor.CreateMonitorBind;
import com.clsaa.dop.server.link.model.vo.monitor.ModifyMonitorBind;
import com.clsaa.dop.server.link.model.vo.monitor.StartBindRes;
import com.clsaa.dop.server.link.util.BeanUtils;
import com.clsaa.rest.result.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MonitorBindService {

    private static final String fail = "Fail";
    private static final String success = "Success";
    private static final String repeat = "Repeat";

    @Autowired
    private BindDao bindDao;

    public Pagination<BindVO> getPageableList(long cuser, int pageNo, int pageSize, String keyword) {
        Pagination<BindVO> pagination = new Pagination<>();

        pagination.setPageNo(pageNo);
        pagination.setPageSize(pageSize);

        int count = bindDao.countBindByCuserAndTitleLikeAndIsDeleteFalse(cuser, "%"+keyword+"%");
        List<Bind> res = bindDao.findByCuserAndTitleLike(cuser, pagination.getRowOffset(), pageSize, keyword);
        pagination.setTotalCount(count);
        pagination.setPageList(convertToVo(res));
        return pagination;
    }

    public BindVO findById(long bid) {
        Bind bind = bindDao.findById(bid);
        if (null == bind) {
            return null;
        }
        return BeanUtils.convertType(bind, BindVO.class);
    }

    public void add(CreateMonitorBind createMonitorBind) {
        Bind create = BeanUtils.convertType(createMonitorBind, Bind.class);
        create.setState(MonitorState.FREE);
        create.setCtime(new Date());
        create.setDelete(false);
//        create.setDelete(false);
        System.out.println("create: " + create.toString());
        bindDao.save(create);
    }

    public StartBindRes start(long bid) {
        Bind bind = bindDao.findById(bid);
        if (null == bind) {
            return new StartBindRes(fail);
        }
        // 先判断同项目同服务下是否已经存在正在运行的监控
        Bind repeatBind = bindDao.checkRepeat(bind.getProjectId(), bind.getService(), MonitorState.RUNNING);
        if (null != repeatBind) {
            return new StartBindRes(repeat, repeatBind.getTitle(), repeatBind.getCuserName());
        }
        bindDao.startBind(bid);
        return new StartBindRes(success);
    }

    public void stop(long bid) {
        bindDao.stopBind(bid);
    }

    public void delete(long bid) {
        bindDao.delete(bid);
    }

    public void modify(ModifyMonitorBind modifyMonitorBind) {
        Bind bind = BeanUtils.convertType(modifyMonitorBind, Bind.class);
        bind.setState(MonitorState.FREE);
        bind.setDelete(false);
        bindDao.save(bind);
    }

    private List<BindVO> convertToVo(List<Bind> binds) {
        return binds.stream()
                .map(bind -> BeanUtils.convertType(bind, BindVO.class))
                .collect(Collectors.toList());
    }

}

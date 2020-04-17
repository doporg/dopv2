package com.clsaa.dop.server.link.service;

import com.clsaa.dop.server.link.dao.BindDao;
import com.clsaa.dop.server.link.model.po.Bind;
import com.clsaa.dop.server.link.model.vo.BindVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BindService {

    @Autowired
    private BindDao bindDao;

    public List<BindVO> getList(long cuser) {
        List<Bind> res = bindDao.findByCuser(cuser);
        return convertToVo(res);
    }

    public void add(BindVO vo) {

    }

    public void delById(long bid) {
        bindDao.deleteById(bid);
    }

    public void modify(BindVO bindVO) {

    }

    private List<BindVO> convertToVo(List<Bind> binds) {
        return new ArrayList<BindVO>();
    }
}

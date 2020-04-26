package com.clsaa.dop.server.link.service;

import com.clsaa.dop.server.link.dao.StarTraceDao;
import com.clsaa.dop.server.link.model.po.StarTrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StarTraceService {

    @Autowired
    private StarTraceDao dao;

    public void star(long userId, String traceId, String note) {
        dao.save(new StarTrace(0, userId, traceId, new Date(), note));
    }

    public void unStar(long itemId) {
        dao.deleteById(itemId);
    }

    public List<StarTrace> getStarsByUserId(long userId) {
        return dao.findByUserId(userId);
    }
}

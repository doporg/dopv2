package com.clsaa.dop.server.link.service;

import com.clsaa.dop.server.link.dao.StarTraceDao;
import com.clsaa.dop.server.link.model.po.StarTrace;
import com.clsaa.dop.server.link.model.vo.star.AddOrDelStar;
import com.clsaa.dop.server.link.model.vo.star.ModStar;
import com.clsaa.rest.result.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class StarTraceService {

    @Autowired
    private StarTraceDao dao;

    public void star(AddOrDelStar addOrDelStar) {
        switch (addOrDelStar.getOperation()) {
            case ADD:
                this.addStar(addOrDelStar);
                break;
            case DEL:
                this.removeStar(addOrDelStar);
                break;

        }
    }

    public void addStar(AddOrDelStar addOrDelStar) {
        if (hasStar(addOrDelStar.getUserId(), addOrDelStar.getTraceId())) {
            return;
        }
        dao.save(new StarTrace(
                0,
                addOrDelStar.getUserId(),
                addOrDelStar.getTraceId(),
                new Date(),
                addOrDelStar.getNote()));
    }

    public void removeStar(AddOrDelStar addOrDelStar) {
        dao.deleteStarTraceByUserIdEqualsAndTraceIdEquals(addOrDelStar.getUserId(), addOrDelStar.getTraceId());
    }

    public void updateStar(ModStar modStar) {
        dao.updateNoteBySid(modStar.getSid(), modStar.getNewNote());
    }

    public Pagination<StarTrace> getStars(long userId, int pageNo, int pageSize, String keyword) {
        System.out.println("userId: " + userId + ", pageNo: " + pageNo + ", pageSize: " + pageSize + ", key: " + keyword);
        Pagination<StarTrace> pagination = new Pagination<>();
        pagination.setPageNo(pageNo);
        pagination.setPageSize(pageSize);

        int count = dao.countAllByUserIdAndNoteLike(userId, "%"+keyword+"%");
        List<StarTrace> list = dao.findByUserIdAndKeyword(userId, pagination.getRowOffset(), pageSize, keyword);
        pagination.setTotalCount(count);
        pagination.setPageList(list);
        System.out.println("count: " + count);
        return pagination;
    }

    public boolean hasStar(long userId, String traceId) {
        boolean res = dao.existsStarTraceByUserIdEqualsAndTraceIdEquals(userId, traceId);
        System.out.println(res);
        return res;
//        return dao.existByUserAndTrace(userId, traceId) != 0;
    }
}

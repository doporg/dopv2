package com.clsaa.dop.server.link.service;

import com.clsaa.dop.server.link.dao.NoticeDao;
import com.clsaa.dop.server.link.model.po.Notice;
import com.clsaa.dop.server.link.model.vo.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeDao noticeDao;

    public List<NoticeVO> getListByCuser(long cuser) {
        List<Notice> notices = noticeDao.findByCuserOrderByNid(cuser);
        return convertToVo(notices);
    }

    public List<Notice> getNoticeListByBid(long bid) {
        return noticeDao.findByBidOrderByTimeDesc(bid);
    }

    private List<NoticeVO> convertToVo(List<Notice> notices) {
        return new ArrayList<>();
    }
}

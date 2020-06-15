package com.clsaa.dop.server.link.dao;

import com.clsaa.dop.server.link.model.po.StarTrace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StarTraceDao extends JpaRepository<StarTrace, Long> {

    List<StarTrace> findByUserId(long userId);

    @Modifying
    void deleteStarTraceByUserIdEqualsAndTraceIdEquals(long userId, String traceId);

    boolean existsStarTraceByUserIdEqualsAndTraceIdEquals(long userId, String traceId);

    @Modifying
    @Query(value = "update StarTrace s set s.note=?2 where s.sid=?1")
    void updateNoteBySid(long sid, String newNote);

    int countAllByUserIdAndNoteLike(long userId, String keyword);

    @Query(value = "select * from star_trace where user_id=:userId and note like concat('%',:keyword,'%') " +
            "order by ctime asc limit :offset, :pageSize ", nativeQuery = true)
    List<StarTrace> findByUserIdAndKeyword(@Param("userId") long userId,
                                           @Param("offset") int offset,
                                           @Param("pageSize") int pageSize,
                                           @Param("keyword") String key);
}

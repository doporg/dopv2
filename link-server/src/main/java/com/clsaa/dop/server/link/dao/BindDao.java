package com.clsaa.dop.server.link.dao;

import com.clsaa.dop.server.link.enums.MonitorState;
import com.clsaa.dop.server.link.model.po.Bind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BindDao extends JpaRepository<Bind, Long> {

    @Query(value = "select b from Bind b where b.bid=?1 and b.isDelete=false ")
    Bind findById(long bid);

    int countBindByCuserAndTitleLikeAndIsDeleteFalse(long cuser, String title);

    @Query(value = "select * from bind where cuser=:cuser and title like concat('%',:keyword,'%') and is_delete=0 " +
            "order by ctime asc limit :offset, :pageSize", nativeQuery = true)
    List<Bind> findByCuserAndTitleLike(@Param("cuser")long cuser,
                                       @Param("offset")int offset,
                                       @Param("pageSize")int pageSize,
                                       @Param("keyword")String keyword);

    @Modifying
    @Query(value = "update Bind b set b.isDelete=true where b.bid=?1")
    int delete(long bid);

    @Modifying
    @Query(value = "update Bind b set b.state='RUNNING' where b.bid=?1")
    int startBind(long bid);

    @Modifying
    @Query(value = "update Bind b set b.state='FREE' where b.bid=?1")
    int stopBind(long bid);

    @Query(value = "select b from Bind b where b.projectId=?1 and b.service=?2 " +
            "and b.state=?3 and b.isDelete=false ")
    Bind checkRepeat(long projectId, String service, MonitorState state);

    List<Bind> findAllByStateEquals(MonitorState state);
}

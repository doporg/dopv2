package com.clsaa.dop.server.baas.Mapper;

import com.clsaa.dop.server.baas.model.dbMo.NetInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Mapper
@Repository
public interface NetMapper {
    //insert
    @Insert("insert into net (NetName,Description,CreateTime,Status) values(#{NetName},#{Description},#{CreateTime},#{Status})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    public void insertNet(NetInfo netInfo);

    //QueryByid
    @Select("select * from net where id = #{id}")
    public NetInfo findNetById (@Param("id") int id);
    @Select("select id from net where NetName = #{NetName}")
    public int queryId(@Param("NetName")String NetName);
    //QueryAllNet
    @Select("select * from net")
    public List<NetInfo> getAllNet();

    @Update("update net set Status = #{Status} where id = #{id}")
    public void updateNetStatu(@Param("Status")int Status,@Param("id") int id);
}
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
    @Insert("insert into net (NetName,Description) values(#{NetName},#{Description})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    public void insertNet(NetInfo netInfo);

    //QueryByid
    @Select("select * from net where id = #{id}")
    public NetInfo findNetById (@Param("id") int id);

    //QueryAllNet
    @Select("select * from net")
    public List<NetInfo> getAllNet();

}
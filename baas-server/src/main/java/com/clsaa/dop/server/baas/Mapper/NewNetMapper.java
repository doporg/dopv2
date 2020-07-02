package com.clsaa.dop.server.baas.Mapper;

import com.clsaa.dop.server.baas.model.dbMo.NewNetInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Mapper
@Repository
public interface NewNetMapper {
    //insert
    @Insert("insert into newnet (namesapce,ordererList,orgList,consensus,tls,createtime,status) values(#{namespace},#{orderList},#{orgList},#{consensus},#{tls},#{createtime},#{status})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    public void insertNet(NewNetInfo newNetInfo);

    //QueryByid
    @Select("select * from newnet where id = #{id}")
    public NewNetInfo findNetById (@Param("id") int id);
    @Select("select id from newnet where namesapce = #{namesapce}")
    public int queryId(@Param("namesapce")String namesapce);
    //QueryAllNet
    @Select("select * from newnet")
    public List<NewNetInfo> getAllNet();

    @Update("update newnet set status = #{status} where id = #{id}")
    public void updateNetStatu(@Param("status")int status,@Param("id") int id);
}
package com.clsaa.dop.server.baas.Mapper;

import com.clsaa.dop.server.baas.model.dbMo.ChaincodeInfo;
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
public interface ChaincodeMapper {

    @Select("select * from chaincode")
    public List<ChaincodeInfo> getAllChaincode();

    @Insert("insert into chaincode (ChaincodeName,ChaincodeVersion,git,ChannelId,NetId) values (#{ChaincodeName},#{ChaincodeVersion},#{git},#{ChannelId},#{NetId})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    public void insertChaincode(ChaincodeInfo chaincodeInfo);

    @Select("select * from chaincode where id = #{id}")
    public ChaincodeInfo findChaincodeById(@Param("id") int id);
}
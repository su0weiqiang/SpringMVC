package com.swq.Dao.Mapper.Common;

import com.swq.Dao.Entity.Common.SysParam;

public interface SysParamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysParam record);

    int insertSelective(SysParam record);

    SysParam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysParam record);

    int updateByPrimaryKey(SysParam record);
}
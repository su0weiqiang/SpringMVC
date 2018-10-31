package com.swq.Service.Common;

import com.swq.Dao.Entity.Common.SysParam;
import com.swq.Dao.Entity.Common.SysParam;


public interface SysParamService {

    int insert(SysParam record);

    SysParam selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(SysParam record);

    int deleteByPrimaryKey(Integer id);

}

package com.Service.Common;

import com.Dao.Entity.Common.SysParam;
import org.springframework.stereotype.Service;


public interface SysParamService {

    int insert(SysParam record);

    SysParam selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(SysParam record);

    int deleteByPrimaryKey(Integer id);

}

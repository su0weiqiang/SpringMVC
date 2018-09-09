package com.Service.Common;

import com.Dao.Entity.Common.SysParam;
import com.Dao.Mapper.Common.SysParamMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class SysParamServiceImp implements SysParamService {

    @Resource
    private SysParamMapper sysParamMapper;


    @Override
    public int insert(SysParam record) {
        return sysParamMapper.insert(record);
    }

    @Override
    public SysParam selectByPrimaryKey(Integer id) {
        return sysParamMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(SysParam record) {

        return sysParamMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysParamMapper.deleteByPrimaryKey(id);
    }
}

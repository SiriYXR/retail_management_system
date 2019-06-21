package com.siri.retail_management_system.service;

import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.domain.SystemLog;

import java.util.List;

/**
 * @author SiriYang
 * @title: SystemLogService
 * @projectName retail_management_system
 * @description: TODO
 * @date 2019/6/21 15:34
 */
public interface SystemLogService {
    public Result<SystemLog> findOne(Integer id);

    public Result<List<SystemLog>> findAll();

    public Result<List<SystemLog>> findNew(int n);

    public Result<SystemLog> add(String info);

    public Result<SystemLog> update(int id,String info);

    public Result<SystemLog> save(SystemLog systemLog);

    public Result<Boolean> delete(Integer id);

    public Result<SystemLog> login(Integer adminid,String ip);
}

package com.siri.retail_management_system.service;


import com.siri.retail_management_system.domain.InCome;
import com.siri.retail_management_system.domain.Result;

import java.util.List;

/**
 * @author SiriYang
 * @title: InComeService
 * @projectName retail_management_system
 * @description: 进货记录服务接口
 * @date 2019/4/18 9:30
 */
public interface InComeService {

    public Result<InCome> findOne(Integer id);

    public Result<List<InCome>> findByMerchandiseName(String merchandise_name);

    public Result<List<InCome>> findAll();

    public Result<InCome> addOrUpdate(InCome inCome);

    public void delete(Integer id);
}

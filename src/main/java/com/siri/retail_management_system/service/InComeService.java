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

    public Result<List<InCome>> findAll();

    public Result<List<InCome>> findNew(int n);

    public Result<InCome> add(int merchandiseid,String merchandisename, int number,double incomePrice,String provider);

    public Result<InCome> update(int id,String merchandisename, int number,double incomePrice,String provider);

    public Result<InCome> save(InCome inCome);

    public Result<InCome> delete(Integer id);
}

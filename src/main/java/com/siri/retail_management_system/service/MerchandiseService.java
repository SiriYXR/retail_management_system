package com.siri.retail_management_system.service;

import com.siri.retail_management_system.domain.Merchandise;
import com.siri.retail_management_system.domain.Result;

import java.util.List;

/**
 * @author SiriYang
 * @title: MerchandiseService
 * @projectName retail_management_system
 * @description: 商品服务类接口
 * @date 2019/4/17 21:32
 */
public interface MerchandiseService {

    public Result<Merchandise> findOne(Integer id);

    public Result<List<Merchandise>> findAll();

    public Result<Merchandise> addOrUpdate(Merchandise merchandise);

    public Result<List<Integer>> countNumber();

    public void delete(Integer id);
}

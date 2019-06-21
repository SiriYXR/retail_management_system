package com.siri.retail_management_system.service;

import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.domain.Sale;

import java.util.List;

/**
 * @author SiriYang
 * @title: SaleService
 * @projectName retail_management_system
 * @description: 出货服务类接口
 * @date 2019/4/21 22:58
 */
public interface SaleService {
    public Result<Sale> findOne(Integer id);

    public Result<List<Sale>> findAll();

    public Result<List<Sale>> findNew(int n);

    public Result<Sale> add(int merchandiseid,String merchandisename, int number, String telephone);

    public Result<Sale> update(int id,String merchandisename, int number, String membername);

    public Result<Sale> save(Sale sale);

    public Result<Boolean> delete(Integer id);

//    public Result<Boolean> withdraw(Integer id);
}

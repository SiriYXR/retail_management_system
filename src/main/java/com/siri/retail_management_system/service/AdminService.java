package com.siri.retail_management_system.service;

import com.siri.retail_management_system.domain.Admin;
import com.siri.retail_management_system.domain.Result;

import java.util.List;

/**
 * @author SiriYang
 * @title: AdminService
 * @projectName retail_management_system
 * @description: 管理员服务类接口
 * @date 2019/4/15 14:01
 */
public interface AdminService  {

    public Result<Admin> findOne(Integer id);

    public Result<Admin> findByUsername(String username);

    public Result<List<Admin>> findAll();

    public Result<Admin> addOrUpdate(Admin admin);

    public void delete(Integer id);

    public Result<Integer> login(String username,String password);
}
package com.siri.retail_management_system.service;

import com.siri.retail_management_system.domain.Admin;
import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.enums.ResultEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author SiriYang
 * @title: AdminServiceImplTest
 * @projectName retail_management_system
 * @description: TODO
 * @date 2019/6/17 23:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceImplTest {

    @Autowired
    AdminService adminService;

    @Test
    public void findOne(){

        Result<Admin> adminResult =adminService.findOne(1);
        if(adminResult.getErrCode()== ResultEnum.SUCCESS.getCode()){
            Admin admin=adminResult.getData();
            Assert.assertEquals("admin",admin.getUsername());
            Assert.assertEquals("123",admin.getPassword());
        }
    }

    @Test
    public void findByUsername() {
        Result<Admin> adminResult =adminService.findByUsername("admin");
        if(adminResult.getErrCode()== ResultEnum.SUCCESS.getCode()){
            Admin admin=adminResult.getData();
            Assert.assertEquals("admin",admin.getUsername());
            Assert.assertEquals("123",admin.getPassword());
        }
    }

    @Test
    public void findAll() {
    }

    @Test
    public void addOrUpdate() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void login() {
    }
}
package com.siri.retail_management_system.service;

import com.siri.retail_management_system.domain.InCome;
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
 * @title: InComeServiceImplTest
 * @projectName retail_management_system
 * @description: TODO
 * @date 2019/6/18 0:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InComeServiceImplTest {

    @Autowired
    InComeService inComeService;

    @Test
    public void findOne() {
        Result<InCome> result = inComeService.findOne(8);

        if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            InCome inCome=result.getData();
            Assert.assertEquals("苹果",inCome.getMerchandisename());
        }
    }

    @Test
    public void findByMerchandiseName() {
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
}
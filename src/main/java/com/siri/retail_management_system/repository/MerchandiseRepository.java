package com.siri.retail_management_system.repository;

import com.siri.retail_management_system.domain.Merchandise;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author SiriYang
 * @title: MerchandiseRepository
 * @projectName retail_management_system
 * @description: 商品持久层
 * @date 2019/4/17 15:13
 */
public interface MerchandiseRepository extends JpaRepository<Merchandise,Integer> {

    /**
     * 通过用户名查询会员
     * @param name
     * @return
     */
    Merchandise findByName(String name);
}

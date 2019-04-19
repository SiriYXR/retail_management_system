package com.siri.retail_management_system.repository;

import com.siri.retail_management_system.domain.InCome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author SiriYang
 * @title: InComeRepository
 * @projectName retail_management_system
 * @description: 进货记录持久层
 * @date 2019/4/18 9:28
 */
public interface InComeRepository extends JpaRepository<InCome,Integer> {

    /**
     * 通过商品名查询记录
     * @return
     */
    List<InCome> findAllByMerchandisename(String merchandise_name);
}

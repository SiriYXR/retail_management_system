package com.siri.retail_management_system.repository;

import com.siri.retail_management_system.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author SiriYang
 * @title: SaleRepository
 * @projectName retail_management_system
 * @description: 出货实体持久层
 * @date 2019/4/21 22:56
 */
public interface SaleRepository extends JpaRepository<Sale,Integer> {
}

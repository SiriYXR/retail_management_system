package com.siri.retail_management_system.repository;

import com.siri.retail_management_system.domain.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author SiriYang
 * @title: SystemLogRepository
 * @projectName retail_management_system
 * @description: TODO
 * @date 2019/6/21 15:33
 */
public interface SystemLogRepository extends JpaRepository<SystemLog,Integer> {
}

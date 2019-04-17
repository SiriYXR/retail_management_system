package com.siri.retail_management_system.repository;

import com.siri.retail_management_system.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author SiriYang
 * @title: AdminRepository
 * @projectName retail_management_system
 * @description: TODO
 * @date 2019/4/15 13:59
 */
public interface AdminRepository extends JpaRepository<Admin,Integer> {

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    Admin findByUsername(String username);
}


package com.siri.retail_management_system.repository;

import com.siri.retail_management_system.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author SiriYang
 * @title: MemberRepository
 * @projectName retail_management_system
 * @description: 会员持久层
 * @date 2019/4/17 13:21
 */
public interface MemberRepository extends JpaRepository<Member,Integer> {

    /**
     * 通过用户名查询会员
     * @param membername
     * @return
     */
    List<Member> findByMembername(String membername);

    /**
     * 通过手机号查询会员
     * @param telephone
     * @return
     */
    List<Member> findByTelephon(String telephone);
}

package com.siri.retail_management_system.service;

import com.siri.retail_management_system.domain.Member;
import com.siri.retail_management_system.domain.Result;

import java.util.List;

/**
 * @author SiriYang
 * @title: MemberService
 * @projectName retail_management_system
 * @description: 会员服务类接口
 * @date 2019/4/17 13:23
 */
public interface MemberService {

    public Result<Member> findOne(Integer id);

    public Result<Member> findByMembername(String membername);

    public Result<List<Member>> findAll();

    public Result<Member> addOrUpdate(Member member);

    public void delete(Integer id);
}

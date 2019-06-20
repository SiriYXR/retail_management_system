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

    public Result<Member> findByTelephone(String telephone);

    public Result<List<Member>> findAll();

    public Result<Member> add(String membername,String telephone);

    public Result<Member> update(Integer id,String membername,String telephone,Integer points);

    public Result<Member> save(Member member);

    public void delete(Integer id);

    public Result<Integer> countMember();

    public boolean isExistTelephone(Integer id,String telephone);
}

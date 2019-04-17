package com.siri.retail_management_system.service;

import com.siri.retail_management_system.domain.Member;
import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.enums.ResultEnum;
import com.siri.retail_management_system.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author SiriYang
 * @title: MemberServiceImpl
 * @projectName retail_management_system
 * @description: 会员服务类实例
 * @date 2019/4/17 13:25
 */
@Service
public class MemberServiceImpl implements MemberService {

    private final static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Autowired
    MemberRepository memberRepository;

    /**
     * 通过id查询会员
     *
     * @param id
     * @return
     */
    @Override
    public Result<Member> findOne(Integer id) {
        Result<Member> result = new Result<>();
        Member member = memberRepository.findById(id).get();
        if (member == null) {
            result.setResultEnum(ResultEnum.MEMBER_ID_WRONG);
        } else {
            logger.info(member.toString());
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(member);
        }
        return result;
    }

    /**
     * 通过姓名查询会员
     *
     * @param membername
     * @return
     */
    @Override
    public Result<Member> findByMembername(String membername) {
        Result<Member> result = new Result<>();
        Member member = memberRepository.findByMembername(membername);
        if (member == null) {
            result.setResultEnum(ResultEnum.MEMBER_NAME_WRONG);
        } else {
            logger.info(member.toString());
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(member);
        }
        return result;
    }


    /**
     * 查询所有会员
     *
     * @return
     */
    @Override
    public Result<List<Member>> findAll() {
        Result<List<Member>> result = new Result<>();

        List<Member> memberList = memberRepository.findAll();

        if (memberList == null) {
            result.setResultEnum(ResultEnum.MEMBER_FIND_ERROR);
        } else {
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(memberList);
        }

        return result;
    }

    /**
     * 添加或更新会员
     *
     * @param member
     * @return
     */
    @Override
    @Transactional
    public Result<Member> addOrUpdate(Member member) {
        Result<Member> result = new Result<>();
        Member m = memberRepository.save(member);
        if (m != null) {
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(m);
        } else {
            result.setResultEnum(ResultEnum.MEMBER_ADD_ERROR);
        }
        return result;
    }

    /**
     * 通过id删除会员
     *
     * @param id
     */
    @Override
    @Transactional
    public void delete(Integer id) {
        memberRepository.deleteById(id);
    }
}

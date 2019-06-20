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
import java.util.LinkedList;
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
            if (member.isDelet()) {
                result.setResultEnum(ResultEnum.MEMBER_DELET);
            } else {
                logger.info(member.toString());
                result.setResultEnum(ResultEnum.SUCCESS);
                result.setData(member);
            }
        }
        return result;
    }


    /**
     * 通过手机号查询会员
     *
     * @param telephone
     * @return
     */
    @Override
    public Result<Member> findByTelephone(String telephone) {
        Result<Member> result = new Result<>();
        List<Member> memberListAll = memberRepository.findByTelephon(telephone);

        Member member = null;
        for (Member i : memberListAll) {
            if (!i.isDelet()) {
                member = i;
                break;
            }
        }

        if (member == null) {
            result.setResultEnum(ResultEnum.MEMBER_TELEPHONE_WRONG);
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

        List<Member> memberListAll = memberRepository.findAll();

        if (memberListAll == null) {
            result.setResultEnum(ResultEnum.MEMBER_FIND_ERROR);
        } else {
            result.setResultEnum(ResultEnum.SUCCESS);
            List<Member> memberList = new LinkedList<>();
            for (Member i : memberListAll) {
                if (!i.isDelet()) {
                    memberList.add(i);
                }
            }
            result.setData(memberList);
        }

        return result;
    }


    /**
     * 添加会员
     *
     * @param membername
     * @param telephone
     * @return
     */
    @Override
    @Transactional
    public Result<Member> add(String membername, String telephone) {
        Result<Member> result = new Result<>();

        Member member = new Member(membername, telephone);

        if (member == null) {
            result.setResultEnum(ResultEnum.UNKONW_ERROR);
        } else {

            logger.info(member.toString());
            result = save(member);

        }
        return result;
    }

    /**
     * 修改会员
     *
     * @param id
     * @param membername
     * @param telephone
     * @param points
     * @return
     */
    @Override
    @Transactional
    public Result<Member> update(Integer id, String membername, String telephone, Integer points) {
        Result<Member> result = new Result<>();

        if(isExistTelephone(id,telephone)){
            result.setResultEnum(ResultEnum.MEMBER_EXIST_TELEPHONE);
            return result;
        }

        Member member = memberRepository.findById(id).get();

        if (member == null) {
            result.setResultEnum(ResultEnum.USERNAME_WRONG);
        } else {
            if (member.isDelet()) {
                result.setResultEnum(ResultEnum.ADMIN_DELET);
            } else {
                //修改数据
                member.setMembername(membername);
                member.setTelephon(telephone);
                member.setPoints(points);

                logger.info(member.toString());
                result = save(member);
            }
        }
        return result;
    }

    /**
     * 保存会员
     *
     * @param member
     * @return
     */
    @Override
    @Transactional
    public Result<Member> save(Member member) {
        Result<Member> result = new Result<>();

        if (isExistTelephone(member.getId(), member.getTelephon())) {
            result.setResultEnum(ResultEnum.MEMBER_EXIST_TELEPHONE);
            return result;
        }

        Member m = memberRepository.save(member);
        if (m != null) {
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(m);
        } else {
            result.setResultEnum(ResultEnum.SALE_UPDATE_ERROR);
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

        Member member = memberRepository.findById(id).get();
        if (member == null) {
            logger.info(ResultEnum.MEMBER_ID_WRONG.getMsg());
        } else {
            logger.info(member.toString());
            member.setDelet(true);
            save(member);
        }
    }

    /**
     * 计算会员数量
     *
     * @return
     */
    @Override
    public Result<Integer> countMember() {
        Result<Integer> result = new Result<>();

        List<Member> memberList = memberRepository.findAll();

        if (memberList == null) {
            result.setResultEnum(ResultEnum.MEMBER_FIND_ERROR);
        } else {
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(memberList.size());
        }

        return result;
    }


    /**
     * 检查手机号是否已经存在
     *
     * @param id
     * @param telephone
     * @return
     */
    @Override
    public boolean isExistTelephone(Integer id, String telephone) {
        List<Member> memberList = memberRepository.findByTelephon(telephone);

        Member member = null;
        for (Member i : memberList) {
            if (!i.isDelet()) {
                member = i;
                break;
            }
        }

        if (member != null && (id != member.getId()))
            return true;
        return false;
    }
}

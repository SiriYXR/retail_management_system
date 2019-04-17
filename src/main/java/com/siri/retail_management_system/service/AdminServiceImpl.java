package com.siri.retail_management_system.service;

import com.siri.retail_management_system.domain.Admin;
import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.enums.ResultEnum;
import com.siri.retail_management_system.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author SiriYang
 * @title: AdminServiceImpl
 * @projectName retail_management_system
 * @description: 管理员服务类实列
 * @date 2019/4/15 14:03
 */
@Service
public class AdminServiceImpl implements AdminService {

    private final static Logger logger= LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private AdminRepository adminRepository;

    /**
     * 通过id查询管理员
     * @param id
     * @return
     */
    public Result<Admin> findOne(Integer id){
        Result<Admin> result=new Result<>();
        Admin admin=adminRepository.findById(id).get();
        if(admin==null){
            result.setResultEnum(ResultEnum.ADMIN_ID_WRONG);
        }
        else{
            logger.info(admin.toString());
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(admin);
        }
        return result;
    }

    /**
     * 通过名字查询管理员
     * @param username
     * @return
     */
    public Result<Admin> findByUsername(String username){
        Result<Admin> result=new Result<>();
        Admin admin=adminRepository.findByUsername(username);
        if(admin==null){
            result.setResultEnum(ResultEnum.USERNAME_WRONG);
        }
        else{
            logger.info(admin.toString());
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(admin);
        }
        return result;
    }

    /**
     * 查询所有管理员
     * @return
     */
    public Result<List<Admin>> findAll(){
        Result<List<Admin>> result=new Result<>();

        List<Admin> adminList=adminRepository.findAll();

        if(adminList==null){
            result.setResultEnum(ResultEnum.ADMIN_FIND_ERROR);
        }
        else {
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(adminList);
        }

        return result;
    }

    /**
     * 添加或修改管理员
     * @param admin
     * @return
     */
    @Transactional
    public Result<Admin> addOrUpdate(Admin admin){
        Result<Admin> result=new Result<>();
        Admin a=adminRepository.save(admin);
        if(a!=null){
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(a);
        }
        else {
            result.setResultEnum(ResultEnum.ADMIN_ADD_ERROR);
        }
        return result;
    }

    /**
     * 通过id删除管理员
     * @param id
     */
    @Override
    @Transactional
    public void delete(Integer id) {
        adminRepository.deleteById(id);
    }

    /**
     * 基础登陆逻辑
     * @param username
     * @param password
     * @return
     */
    public Result<Integer> login(String username,String password){
        Result<Integer> result=new Result<>();
        Admin admin=adminRepository.findByUsername(username);
        if(admin==null){
            logger.info(ResultEnum.USERNAME_WRONG.getMsg());
            result.setResultEnum(ResultEnum.USERNAME_WRONG);
        }
        else if (admin.getPassword().equals(password)){
            logger.info(admin.toString());
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(admin.getId());
        }
        else {
            logger.info(ResultEnum.PASSWORD_WRONG.getMsg());
            result.setResultEnum(ResultEnum.PASSWORD_WRONG);
        }

        return result;
    }
}

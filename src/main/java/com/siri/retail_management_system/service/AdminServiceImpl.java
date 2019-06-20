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
import java.util.LinkedList;
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

    private final static Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private AdminRepository adminRepository;

    /**
     * 通过id查询管理员
     *
     * @param id
     * @return
     */
    public Result<Admin> findOne(Integer id) {
        Result<Admin> result = new Result<>();
        Admin admin = adminRepository.findById(id).get();
        if (admin == null) {
            result.setResultEnum(ResultEnum.ADMIN_ID_WRONG);
        } else {
            if (admin.isDelet()) {
                result.setResultEnum(ResultEnum.ADMIN_DELET);
            } else {
                logger.info(admin.toString());
                result.setResultEnum(ResultEnum.SUCCESS);
                result.setData(admin);
            }
        }
        return result;
    }

    /**
     * 通过名字查询管理员
     *
     * @param username
     * @return
     */
    public Result<Admin> findByUsername(String username) {
        Result<Admin> result = new Result<>();
        List<Admin> adminListALL = adminRepository.findByUsername(username);

        Admin admin = null;
        for (Admin i : adminListALL) {
            if (!i.isDelet()) {
                admin = i;
                break;
            }
        }

        if (admin == null) {
            result.setResultEnum(ResultEnum.USERNAME_WRONG);
        } else {
            if (admin.isDelet()) {
                result.setResultEnum(ResultEnum.ADMIN_DELET);
            } else {
                logger.info(admin.toString());
                result.setResultEnum(ResultEnum.SUCCESS);
                result.setData(admin);
            }
        }
        return result;
    }

    /**
     * 查询所有管理员
     *
     * @return
     */
    public Result<List<Admin>> findAll() {
        Result<List<Admin>> result = new Result<>();

        List<Admin> adminListALL = adminRepository.findAll();

        if (adminListALL == null) {
            result.setResultEnum(ResultEnum.ADMIN_FIND_ERROR);
        } else {
            List<Admin> adminList = new LinkedList<>();
            for (Admin i : adminListALL) {
                if (!i.isDelet())
                    adminList.add(i);
            }
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(adminList);
        }

        return result;
    }

    /**
     * 添加管理员
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    @Transactional
    public Result<Admin> add(String username, String password) {
        Result<Admin> result = new Result<>();
        Admin admin = new Admin(username, password);

        if (admin == null) {
            result.setResultEnum(ResultEnum.UNKONW_ERROR);
        } else {

            logger.info(admin.toString());
            result = save(admin);

        }
        return result;
    }

    /**
     * 更新管理员
     *
     * @param id
     * @param username
     * @param password
     * @return
     */
    @Override
    @Transactional
    public Result<Admin> update(Integer id, String username, String password) {
        Result<Admin> result = new Result<>();

        if (isExistUsername(id, username)) {
            result.setResultEnum(ResultEnum.ADMIN_EXIST_USERNAME);
            return result;
        }

        Admin admin = adminRepository.findById(id).get();

        if (admin == null) {
            result.setResultEnum(ResultEnum.USERNAME_WRONG);
        } else {
            if (admin.isDelet()) {
                result.setResultEnum(ResultEnum.ADMIN_DELET);
            } else {

                //修改数据
                admin.setUsername(username);
                admin.setPassword(password);

                logger.info(admin.toString());
                result = save(admin);
            }
        }
        return result;
    }

    /**
     * 保存管理员
     *
     * @param admin
     * @return
     */
    @Override
    @Transactional
    public Result<Admin> save(Admin admin) {
        Result<Admin> result = new Result<>();

        if (isExistUsername(admin.getId(), admin.getUsername())) {
            result.setResultEnum(ResultEnum.ADMIN_EXIST_USERNAME);
            return result;
        }

        Admin a = adminRepository.save(admin);
        if (a != null) {
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(a);
        } else {
            result.setResultEnum(ResultEnum.ADMIN_SAVE_ERROR);
        }
        return result;
    }

    /**
     * 通过id删除管理员
     *
     * @param id
     */
    @Override
    @Transactional
    public void delete(Integer id) {

        Admin admin = adminRepository.findById(id).get();
        if (admin == null) {
            logger.info(ResultEnum.ADMIN_ID_WRONG.getMsg());
        } else {
            logger.info(admin.toString());
            admin.setDelet(true);
            save(admin);
        }
    }

    /**
     * 基础登陆逻辑
     *
     * @param username
     * @param password
     * @return
     */
    public Result<Integer> login(String username, String password) {
        Result<Integer> result = new Result<>();
        Result<Admin> r = findByUsername(username);
        Admin admin = r.getData();

        if (admin == null) {
            logger.info(ResultEnum.USERNAME_WRONG.getMsg());
            result.setResultEnum(ResultEnum.USERNAME_WRONG);
        } else if (admin.getPassword().equals(password)) {
            logger.info(admin.toString());
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(admin.getId());
        } else {
            logger.info(ResultEnum.PASSWORD_WRONG.getMsg());
            result.setResultEnum(ResultEnum.PASSWORD_WRONG);
        }

        return result;
    }

    /**
     * 检查管理员名是否已经存在
     *
     * @param id
     * @param username
     * @return
     */
    @Override
    public boolean isExistUsername(Integer id, String username) {
        Result<Admin> r = findByUsername(username);
        Admin admin = r.getData();
        if (admin != null && (id != admin.getId()))
            return true;
        return false;
    }
}

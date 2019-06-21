package com.siri.retail_management_system.service;

import com.siri.retail_management_system.domain.Admin;
import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.domain.SystemLog;
import com.siri.retail_management_system.enums.ResultEnum;
import com.siri.retail_management_system.repository.AdminRepository;
import com.siri.retail_management_system.repository.SystemLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

/**
 * @author SiriYang
 * @title: SystemLogServiceImpl
 * @projectName retail_management_system
 * @description: TODO
 * @date 2019/6/21 15:37
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {
    private final static Logger logger = LoggerFactory.getLogger(SystemLogServiceImpl.class);

    @Autowired
    SystemLogRepository systemLogRepository;

    @Autowired
    AdminRepository adminRepository;

    @Override
    public Result<SystemLog> findOne(Integer id) {
        Result<SystemLog> result = new Result<>();
        SystemLog systemLog = systemLogRepository.findById(id).get();
        if (systemLog == null) {
            result.setResultEnum(ResultEnum.SYSTEMLOG_ID_WRONG);
        } else if (systemLog.isDelet()) {
            result.setResultEnum(ResultEnum.SYSTEMLOG_DELET);
        } else {
            logger.info(systemLog.toString());
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(systemLog);
        }
        
        return result;
    }

    @Override
    public Result<List<SystemLog>> findAll() {
        Result<List<SystemLog>> result = new Result<>();

        List<SystemLog> systemLogListAll = systemLogRepository.findAll();

        if (systemLogListAll == null) {
            result.setResultEnum(ResultEnum.SYSTEMLOG_FIND_ERROR);
        } else {
            List<SystemLog> systemLogList = new LinkedList<>();
            for (SystemLog i : systemLogListAll) {
                if (!i.isDelet())
                    systemLogList.add(i);
            }
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(systemLogList);
        }

        return result;
    }

    @Override
    public Result<List<SystemLog>> findNew(int n) {
        Result<List<SystemLog>> result = new Result<>();
        Result<List<SystemLog>> resultAll = findAll();
        if (resultAll.getErrCode() != ResultEnum.SUCCESS.getCode()) {
            result.setResultEnum(ResultEnum.SYSTEMLOG_FIND_ERROR);
            return result;
        }

        List<SystemLog> systemLogs = resultAll.getData();
        List<SystemLog> systemLogList = new LinkedList<>();
        int count = 0;
        for (int i = systemLogs.size(); i > 0; i--) {
            if (n>0 && count >= n)
                break;
            systemLogList.add(systemLogs.get(i - 1));
            count++;
        }
        result.setResultEnum(ResultEnum.SUCCESS);
        result.setData(systemLogList);

        return result;
    }

    @Override
    @Transactional
    public Result<SystemLog> add(String info) {
        
        SystemLog systemLog=new SystemLog(info);
        
        return save(systemLog);
    }

    @Override
    @Transactional
    public Result<SystemLog> update(int id, String info) {

        Result<SystemLog> result=findOne(id);
        
        if (result.getErrCode()!=ResultEnum.SUCCESS.getCode()){
            result.setResultEnum(ResultEnum.SYSTEMLOG_ID_WRONG);
            return result;
        }
        
        SystemLog systemLog=result.getData();
        systemLog.setInfo(info);
        
        return save(systemLog);
    }

    @Override
    @Transactional
    public Result<SystemLog> save(SystemLog systemLog) {
        Result<SystemLog> result = new Result<>();

        SystemLog s = systemLogRepository.save(systemLog);
        if (s != null) {
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(s);
        } else {
            result.setResultEnum(ResultEnum.SYSTEMLOG_UPDATE_ERROR);
        }
        return result;
    }

    @Override
    @Transactional
    public Result<Boolean> delete(Integer id) {
        Result<Boolean> result = new Result<>();
        result.setData(true);
        SystemLog systemLog = systemLogRepository.findById(id).get();
        if (systemLog == null) {
            result.setResultEnum(ResultEnum.SYSTEMLOG_ID_WRONG);
            result.setData(false);
        } else if (systemLog.isDelet()) {
            result.setResultEnum(ResultEnum.SYSTEMLOG_DELET);
            return result;
        } else {
            systemLog.setDelet(true);
            if (save(systemLog).getErrCode() != ResultEnum.SUCCESS.getCode()) {
                result.setResultEnum(ResultEnum.DELET_ERROR);
                result.setData(false);
            } else {
                result.setResultEnum(ResultEnum.SUCCESS);
            }
        }
        return result;
    }

    @Override
    public Result<Integer> countNumber() {
        Result<Integer> result=new Result<>();

        Result<List<SystemLog>> logAll=findAll();

        if (logAll.getErrCode()!=ResultEnum.SUCCESS.getCode()){
            result.setResultEnum(ResultEnum.SYSTEMLOG_FIND_ERROR);
            return result;
        }

        result.setData(logAll.getData().size());
        result.setResultEnum(ResultEnum.SUCCESS);

        return result;
    }

    @Override
    public Result<SystemLog> login(Integer adminid, String ip) {

        Result<SystemLog> result=new Result<>();

        Admin admin=adminRepository.findById(adminid).get();

        if (admin==null){
            result.setResultEnum(ResultEnum.ADMIN_ID_WRONG);
            return result;
        }

        String info=admin.getUsername()+" 登陆，ip:"+ip;

        result=add(info);

        return result;
    }

    @Override
    public Result<SystemLog> newlog(Integer adminid, String info) {
        Result<SystemLog> result=new Result<>();

        Admin admin=adminRepository.findById(adminid).get();

        if (admin==null){
            result.setResultEnum(ResultEnum.ADMIN_ID_WRONG);
            return result;
        }

        String string=admin.getUsername()+info;

        result=add(string);

        return result;
    }

    @Override
    public Result<SystemLog> errorlog(Integer adminid, String info) {
        Result<SystemLog> result=new Result<>();

        Admin admin=adminRepository.findById(adminid).get();

        if (admin==null){
            result.setResultEnum(ResultEnum.ADMIN_ID_WRONG);
            return result;
        }

        String string=admin.getUsername()+" 错误："+info;

        result=add(string);

        return result;
    }
}

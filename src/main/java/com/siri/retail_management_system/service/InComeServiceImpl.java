package com.siri.retail_management_system.service;

import com.siri.retail_management_system.domain.InCome;
import com.siri.retail_management_system.domain.Merchandise;
import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.enums.ResultEnum;
import com.siri.retail_management_system.repository.InComeRepository;
import com.siri.retail_management_system.repository.MerchandiseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author SiriYang
 * @title: InComeServiceImpl
 * @projectName retail_management_system
 * @description: 进货记录服务实例
 * @date 2019/4/18 9:35
 */
@Service
public class InComeServiceImpl implements InComeService {

    private final static Logger logger = LoggerFactory.getLogger(InComeServiceImpl.class);

    @Autowired
    InComeRepository inComeRepository;

    @Autowired
    MerchandiseRepository merchandiseRepository;

    @Override
    public Result<InCome> findOne(Integer id) {
        Result<InCome> result = new Result<>();
        InCome inCome = inComeRepository.findById(id).get();
        if (inCome == null) {
            result.setResultEnum(ResultEnum.INCOME_ID_WRONG);
        } else {
            logger.info(inCome.toString());
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(inCome);
        }
        return result;
    }

    @Override
    public Result<List<InCome>> findByMerchandiseName(String merchandise_name) {
        Result<List<InCome>> result = new Result<>();
        List<InCome> inComeList = inComeRepository.findAllByMerchandisename(merchandise_name);
        if (inComeList == null) {
            result.setResultEnum(ResultEnum.MERCHANDISE_NAME_WRONG);
        } else {
            logger.info(inComeList.toString());
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(inComeList);
        }
        return result;
    }

    @Override
    public Result<List<InCome>> findAll() {
        Result<List<InCome>> result = new Result<>();
        List<InCome> inComeList = inComeRepository.findAll();
        if (inComeList == null) {
            result.setResultEnum(ResultEnum.INCOME_FIND_ERROR);
        } else {
            logger.info(inComeList.toString());
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(inComeList);
        }
        return result;
    }

    @Override
    @Transactional
    public Result<InCome> addOrUpdate(InCome inCome) {
        Result<InCome> result = new Result<>();
        InCome i = inComeRepository.save(inCome);
        Merchandise m = merchandiseRepository.findByName(i.getMerchandisename());
        m.setNumber(m.getNumber() + i.getNumber());
        m.setIncome_price(i.getIncome_price());
        m = merchandiseRepository.save(m);

        if (i != null && m != null) {
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(i);
        } else {
            result.setResultEnum(ResultEnum.INCOME_ADD_ERROR);
        }
        return result;
    }

    @Override
    public void delete(Integer id) {
        inComeRepository.deleteById(id);
    }
}

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
import java.util.LinkedList;
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
            if (inCome.isDelet()) {
                result.setResultEnum(ResultEnum.INCOME_DELET);
            } else {
                logger.info(inCome.toString());
                result.setResultEnum(ResultEnum.SUCCESS);
                result.setData(inCome);
            }
        }
        return result;
    }


    @Override
    public Result<List<InCome>> findAll() {
        Result<List<InCome>> result = new Result<>();
        List<InCome> inComeListAll = inComeRepository.findAll();
        if (inComeListAll == null) {
            result.setResultEnum(ResultEnum.INCOME_FIND_ERROR);
        } else {
            List<InCome> inComeList = new LinkedList<>();
            for (InCome i : inComeListAll) {
                if (!i.isDelet())
                    inComeList.add(i);
            }
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(inComeList);
        }
        return result;
    }

    @Override
    public Result<List<InCome>> findNew(int n) {
        Result<List<InCome>> result = new Result<>();
        Result<List<InCome>> resultAll = findAll();
        if (resultAll.getErrCode() != ResultEnum.SUCCESS.getCode()) {
            result.setResultEnum(ResultEnum.SALE_FIND_ERROR);
            return result;
        }

        List<InCome> incomes = resultAll.getData();
        List<InCome> incomeList = new LinkedList<>();
        int count = 0;
        for (int i = incomes.size(); i > 0; i--) {
            if (count >= n)
                break;

            incomeList.add(incomes.get(i - 1));
            count++;

        }
        result.setResultEnum(ResultEnum.SUCCESS);
        result.setData(incomeList);

        return result;
    }

    @Override
    @Transactional
    public Result<InCome> add(int merchandiseid, String merchandisename, int number, double incomePrice, String provider) {
        Result<InCome> result = new Result<>();

        InCome inCome = new InCome(merchandisename + "(id:" + merchandiseid + ")", provider, number, incomePrice);

        Merchandise m = merchandiseRepository.findById(merchandiseid).get();
        m.setNumber(m.getNumber() + inCome.getNumber());
        m.setIncome_price(inCome.getIncome_price());
        m = merchandiseRepository.save(m);

        if (m == null) {
            result.setResultEnum(ResultEnum.MERCHANDISE_SAVE_ERROR);
            return result;
        }

        result = save(inCome);
        return result;
    }

    @Override
    @Transactional
    public Result<InCome> update(int id, String merchandisename, int number, double incomePrice, String provider) {
        Result<InCome> result = new Result<>();

        InCome income = inComeRepository.findById(id).get();
        if (income == null) {
            result.setResultEnum(ResultEnum.INCOME_ID_WRONG);
            return result;
        }

        income.setMerchandisename(merchandisename);
        income.setIncome_price(incomePrice);
        income.setNumber(number);
        income.setSupplier(provider);


        income = inComeRepository.save(income);
        if (income != null) {
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(income);
        } else {
            result.setResultEnum(ResultEnum.INCOME_UPDATE_ERROR);
        }
        return result;
    }

    @Override
    public Result<InCome> save(InCome inCome) {
        Result<InCome> result = new Result<>();

        inCome = inComeRepository.save(inCome);

        if (inCome != null) {
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(inCome);
        } else {
            result.setResultEnum(ResultEnum.INCOME_SAVE_ERROR);
        }

        return result;
    }

    @Override
    public Result<InCome> delete(Integer id) {
        Result<InCome> result = new Result<>();
        InCome inCome = inComeRepository.findById(id).get();
        if (inCome == null) {
            logger.info(ResultEnum.INCOME_ID_WRONG.getMsg());
        } else {
            logger.info(inCome.toString());
            inCome.setDelet(true);
            result = save(inCome);
        }

        return result;
    }
}

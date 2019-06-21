package com.siri.retail_management_system.service;

import com.siri.retail_management_system.domain.Merchandise;
import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.enums.ResultEnum;
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
 * @title: MerchandiseServiceImpl
 * @projectName retail_management_system
 * @description: 商品服务类实列
 * @date 2019/4/17 21:50
 */
@Service
public class MerchandiseServiceImpl implements MerchandiseService {

    private final static Logger logger = LoggerFactory.getLogger(MerchandiseServiceImpl.class);

    @Autowired
    MerchandiseRepository merchandiseRepository;

    /**
     * 根据id查询商品
     *
     * @param id
     * @return
     */
    @Override
    public Result<Merchandise> findOne(Integer id) {
        Result<Merchandise> result = new Result<>();
        Merchandise merchandise = merchandiseRepository.findById(id).get();
        if (merchandise == null) {
            result.setResultEnum(ResultEnum.MERCHANDISE_ID_WRONG);
        } else {
            if (merchandise.isDelet()) {
                result.setResultEnum(ResultEnum.MERCHANDISE_DELET);
            } else {
                logger.info(merchandise.toString());
                result.setResultEnum(ResultEnum.SUCCESS);
                result.setData(merchandise);
            }

        }
        return result;
    }


    /**
     * 查询所有商品
     *
     * @return
     */
    @Override
    public Result<List<Merchandise>> findAll() {
        Result<List<Merchandise>> result = new Result<>();

        List<Merchandise> merchandiseListAll = merchandiseRepository.findAll();

        if (merchandiseListAll == null) {
            result.setResultEnum(ResultEnum.MERCHANDISE_FIND_ERROR);
        } else {
            List<Merchandise> merchandiseList = new LinkedList<>();
            for (Merchandise i : merchandiseListAll) {
                if (!i.isDelet())
                    merchandiseList.add(i);
            }
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(merchandiseList);
        }

        return result;
    }

    /**
     * 增加或修改商品
     *
     * @param merchandise
     * @return
     */
    @Override
    @Transactional
    public Result<Merchandise> addOrUpdate(Merchandise merchandise) {
        Result<Merchandise> result = new Result<>();
        Merchandise m = merchandiseRepository.save(merchandise);
        if (m != null) {
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(m);
        } else {
            result.setResultEnum(ResultEnum.MERCHANDISE_ADD_ERROR);
        }
        return result;
    }

    @Override
    @Transactional
    public Result<Merchandise> add(String name, double income_price, double sale_price, double member_price) {

        Merchandise merchandise = new Merchandise(name, income_price, sale_price, member_price);

        Result<Merchandise> result=save(merchandise);

        return result;
    }

    @Override
    @Transactional
    public Result<Merchandise> update(Integer id, String name, Integer number, double income_price, double sale_price, double member_price) {
        Result<Merchandise> result=findOne(id);
        if (result.getErrCode()!=ResultEnum.SUCCESS.getCode()){
            result.setResultEnum(ResultEnum.MERCHANDISE_ID_WRONG);
            return result;
        }

        Merchandise merchandise=result.getData();

        merchandise.setName(name);
        merchandise.setNumber(number);
        merchandise.setIncome_price(income_price);
        merchandise.setSale_price(sale_price);
        merchandise.setMember_price(member_price);

        result=save(merchandise);

        return result;
    }

    @Override
    @Transactional
    public Result<Merchandise> save(Merchandise merchandise) {

        Result<Merchandise> result = new Result<>();
        Merchandise m = merchandiseRepository.save(merchandise);
        if (m != null) {
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(m);
        } else {
            result.setResultEnum(ResultEnum.MERCHANDISE_SAVE_ERROR);
        }
        return result;
    }

    /**
     * 删除商品
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {

        Merchandise merchandise = merchandiseRepository.findById(id).get();
        if (merchandise == null) {
            logger.info(ResultEnum.MERCHANDISE_ID_WRONG.getMsg());
        } else {
            logger.info(merchandise.toString());
            merchandise.setDelet(true);
            addOrUpdate(merchandise);
        }
    }

    @Override
    public Result<List<Integer>> countNumber() {
        Result<List<Integer>> result = new Result<>();
        Result<List<Merchandise>> resultMerchandise = findAll();
        int merchandiseNum, merchandiseClass;

        if (resultMerchandise.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            merchandiseClass = resultMerchandise.getData().size();
            merchandiseNum = 0;
            for (Merchandise i : resultMerchandise.getData()) {
                merchandiseNum += i.getNumber();
            }

            List<Integer> list = new LinkedList<>();
            list.add(merchandiseNum);
            list.add(merchandiseClass);
            result.setData(list);
            result.setResultEnum(ResultEnum.SUCCESS);
        } else {
            result.setResultEnum(ResultEnum.MERCHANDISE_FIND_ERROR);
            return result;
        }

        return result;
    }
}

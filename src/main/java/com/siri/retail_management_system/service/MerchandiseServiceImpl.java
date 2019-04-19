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

    private final static Logger logger= LoggerFactory.getLogger(MerchandiseServiceImpl.class);

    @Autowired
    MerchandiseRepository merchandiseRepository;

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    @Override
    public Result<Merchandise> findOne(Integer id) {
        Result<Merchandise> result=new Result<>();
        Merchandise merchandise=merchandiseRepository.findById(id).get();
        if(merchandise==null){
            result.setResultEnum(ResultEnum.MERCHANDISE_ID_WRONG);
        }
        else{
            logger.info(merchandise.toString());
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(merchandise);
        }
        return result;
    }

    /**
     * 通过名字查询商品
     * @param name
     * @return
     */
    @Override
    public Result<Merchandise> findByName(String name) {
        Result<Merchandise> result = new Result<>();
        Merchandise merchandise = merchandiseRepository.findByName(name);
        if (merchandise == null) {
            result.setResultEnum(ResultEnum.MERCHANDISE_NAME_WRONG);
        } else {
            logger.info(merchandise.toString());
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(merchandise);
        }
        return result;
    }

    /**
     * 查询所有商品
     * @return
     */
    @Override
    public Result<List<Merchandise>> findAll() {
        Result<List<Merchandise>> result = new Result<>();

        List<Merchandise> merchandiseList = merchandiseRepository.findAll();

        if (merchandiseList == null) {
            result.setResultEnum(ResultEnum.MERCHANDISE_FIND_ERROR);
        } else {
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(merchandiseList);
        }

        return result;
    }

    /**
     * 增加或修改商品
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

    /**
     * 删除商品
     * @param id
     */
    @Override
    public void delete(Integer id) {
        merchandiseRepository.deleteById(id);
    }
}

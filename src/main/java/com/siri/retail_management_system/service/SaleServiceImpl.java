package com.siri.retail_management_system.service;

import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.domain.Sale;
import com.siri.retail_management_system.enums.ResultEnum;
import com.siri.retail_management_system.repository.SaleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SiriYang
 * @title: SaleServiceImpl
 * @projectName retail_management_system
 * @description: 出货服务类接口实列
 * @date 2019/4/21 23:00
 */
@Service
public class SaleServiceImpl implements SaleService {

    private final static Logger logger= LoggerFactory.getLogger(SaleServiceImpl.class);

    @Autowired
    SaleRepository saleRepository;

    /**
     * 通过id查询出货记录
     * @param id
     * @return
     */
    @Override
    public Result<Sale> findOne(Integer id) {
        Result<Sale> result=new Result<>();
        Sale sale=saleRepository.findById(id).get();
        if(sale==null){
            result.setResultEnum(ResultEnum.SALE_ID_WRONG);
        }
        else{
            logger.info(sale.toString());
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(sale);
        }
        return result;
    }

    /**
     * 查询所有出货记录
     * @return
     */
    @Override
    public Result<List<Sale>> findAll() {
        Result<List<Sale>> result = new Result<>();

        List<Sale> saleList = saleRepository.findAll();

        if (saleList == null) {
            result.setResultEnum(ResultEnum.SALE_FIND_ERROR);
        } else {
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(saleList);
        }

        return result;
    }

    /**
     * 添加或更改出货记录
     * @param sale
     * @return
     */
    @Override
    public Result<Sale> addOrUpdate(Sale sale) {
        Result<Sale> result = new Result<>();
        Sale s = saleRepository.save(sale);
        if (s != null) {
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(s);
        } else {
            result.setResultEnum(ResultEnum.SALE_ADD_ERROR);
        }
        return result;
    }

    /**
     * 删除出货记录
     * @param id
     */
    @Override
    public void delete(Integer id) {
        saleRepository.deleteById(id);
    }
}

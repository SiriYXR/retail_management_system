package com.siri.retail_management_system.service;

import com.siri.retail_management_system.domain.Member;
import com.siri.retail_management_system.domain.Merchandise;
import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.domain.Sale;
import com.siri.retail_management_system.enums.ResultEnum;
import com.siri.retail_management_system.repository.MemberRepository;
import com.siri.retail_management_system.repository.MerchandiseRepository;
import com.siri.retail_management_system.repository.SaleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
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

    private final static Logger logger = LoggerFactory.getLogger(SaleServiceImpl.class);

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MerchandiseRepository merchandiseRepository;

    /**
     * 通过id查询出货记录
     *
     * @param id
     * @return
     */
    @Override
    public Result<Sale> findOne(Integer id) {
        Result<Sale> result = new Result<>();
        Sale sale = saleRepository.findById(id).get();
        if (sale == null) {
            result.setResultEnum(ResultEnum.SALE_ID_WRONG);
        } else if (sale.isDelet()) {
            result.setResultEnum(ResultEnum.SALE_DELET);
        } else {
            logger.info(sale.toString());
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(sale);
        }


        return result;
    }

    /**
     * 查询所有出货记录
     *
     * @return
     */
    @Override
    public Result<List<Sale>> findAll() {
        Result<List<Sale>> result = new Result<>();

        List<Sale> saleListAll = saleRepository.findAll();

        if (saleListAll == null) {
            result.setResultEnum(ResultEnum.SALE_FIND_ERROR);
        } else {
            List<Sale> saleList = new LinkedList<>();
            for (Sale i : saleListAll) {
                if (!i.isDelet())
                    saleList.add(i);
            }
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(saleList);
        }

        return result;
    }

    /**
     * 添加出货记录
     *
     * @param merchandisename
     * @param number
     * @param telephone
     * @return
     */
    @Override
    @Transactional
    public Result<Sale> add(String merchandisename, int number, String telephone) {
        Result<Sale> result = new Result<>();
        Sale sale = new Sale(merchandisename, number, 0.0);

        //查询商品
        Merchandise merchandise = merchandiseRepository.findByName(merchandisename);
        if (merchandise == null) {
            result.setResultEnum(ResultEnum.MERCHANDISE_NAME_WRONG);
            return result;
        } else if (merchandise.isDelet()) {
            result.setResultEnum(ResultEnum.MERCHANDISE_DELET);
            return result;
        } else if (merchandise.getNumber() < number) {
            result.setResultEnum(ResultEnum.SALE_LACK_OF_GOODS);
            return result;
        }

        //查询会员
        Member member = null;
        if (telephone.length() > 0) {
            member = memberRepository.findByTelephon(telephone);
            if (member == null) {
                result.setResultEnum(ResultEnum.MEMBER_TELEPHONE_WRONG);
                return result;
            } else if (member.isDelet()) {
                result.setResultEnum(ResultEnum.MEMBER_DELET);
                return result;
            }
        }

        //修改数据
        sale.setMerchandisename(merchandisename);
        sale.setNumber(number);
        merchandise.setNumber(merchandise.getNumber() - number);
        if (member != null) {
            sale.setSale_price(merchandise.getMember_price());
            sale.setMembername(member.getMembername());

            member.setPoints(member.getPoints() + (int) (number * merchandise.getMember_price()));


        } else {
            sale.setSale_price(merchandise.getSale_price());
            sale.setMembername(null);
        }


        //保存
        merchandise = merchandiseRepository.save(merchandise);
        if (merchandise == null) {
            result.setResultEnum(ResultEnum.MERCHANDISE_ADD_ERROR);
            return result;
        }

        if (member != null) {
            member = memberRepository.save(member);
            if (member == null) {
                result.setResultEnum(ResultEnum.MEMBER_ADD_ERROR);
                return result;
            }
        }

        result = save(sale);

        return result;
    }

    /**
     * 更改出货记录
     *
     * @param id
     * @param merchandisename
     * @param number
     * @param telephone
     * @return
     */
    @Override
    @Transactional
    public Result<Sale> update(int id, String merchandisename, int number, String telephone) {
        Result<Sale> result = new Result<>();

        //查询
        Sale sale = saleRepository.findById(id).get();
        if (sale != null) {
            //修改
            sale.setMembername(merchandisename);
            sale.setNumber(number);

            //查询会员
            Member member = null;
            if (telephone.length() > 0) {
                member = memberRepository.findByTelephon(telephone);
                if (member == null) {
                    result.setResultEnum(ResultEnum.MEMBER_TELEPHONE_WRONG);
                    return result;
                } else if (member.isDelet()) {
                    result.setResultEnum(ResultEnum.MEMBER_DELET);
                    return result;
                } else {
                    sale.setMembername(member.getMembername());
                }
            }

        } else if (sale.isDelet()) {
            result.setResultEnum(ResultEnum.SALE_DELET);
            return result;
        } else {
            result.setResultEnum(ResultEnum.SALE_ID_WRONG);
            return result;
        }

        //保存
        result = save(sale);

        return result;
    }

    @Override
    @Transactional
    public Result<Sale> save(Sale sale) {
        Result<Sale> result = new Result<>();

        Sale s = saleRepository.save(sale);
        if (s != null) {
            result.setResultEnum(ResultEnum.SUCCESS);
            result.setData(s);
        } else {
            result.setResultEnum(ResultEnum.SALE_UPDATE_ERROR);
        }
        return result;
    }

    /**
     * 删除出货记录
     *
     * @param id
     */
    @Override
    @Transactional
    public Result<Boolean> delete(Integer id) {
        Result<Boolean> result = new Result<>();
        result.setData(true);
        Sale sale = saleRepository.findById(id).get();
        if (sale == null) {
            result.setResultEnum(ResultEnum.SALE_ID_WRONG);
            result.setData(false);
        } else if (sale.isDelet()) {
            result.setResultEnum(ResultEnum.SALE_DELET);
            return result;
        } else {
            sale.setDelet(true);
            if (save(sale).getErrCode() != ResultEnum.SUCCESS.getCode()) {
                result.setResultEnum(ResultEnum.DELET_ERROR);
                result.setData(false);
            } else {
                result.setResultEnum(ResultEnum.SUCCESS);
            }
        }
        return result;
    }

//    /**
//     * 退货
//     *
//     * @param id
//     * @return
//     */
//    @Override
//    @Transactional
//    public Result<Boolean> withdraw(Integer id) {
//        Result<Boolean> result = new Result<>();
//        result.setData(true);
//
//        //查询销售记录
//        Sale sale = saleRepository.findById(id).get();
//        if (sale == null) {
//            result.setResultEnum(ResultEnum.SALE_ID_WRONG);
//            result.setData(false);
//            return result;
//        }else if (sale.isDelet()){
//            result.setResultEnum(ResultEnum.SALE_DELET);
//            return result;
//        }
//
//        //查询商品
//        Merchandise merchandise = merchandiseRepository.findByName(sale.getMerchandisename());
//        if (merchandise == null) {
//            result.setResultEnum(ResultEnum.MERCHANDISE_NAME_WRONG);
//            result.setData(false);
//            return result;
//        }else if(merchandise.isDelet()){
//            result.setResultEnum(ResultEnum.MERCHANDISE_DELET);
//            result.setData(false);
//            return result;
//        }
//
//        //修改商品数量
//        merchandise.setNumber(merchandise.getNumber() + sale.getNumber());
//
//        //保存商品
//        merchandise = merchandiseRepository.save(merchandise);
//        if (merchandise == null) {
//            result.setResultEnum(ResultEnum.MERCHANDISE_SAVE_ERROR);
//            result.setData(false);
//            return result;
//        }
//
//        //修改会员积分
//        Member member = memberRepository.findByMembername(sale.getMembername());
//        if (member != null && !member.isDelet()) {
//            int points = member.getPoints() - (int) (sale.getNumber() * sale.getSale_price());
//            member.setPoints(points > 0 ? points : 0);
//            member = memberRepository.save(member);
//            if (member == null) {
//                result.setResultEnum(ResultEnum.MEMBER_SAVE_ERROR);
//                result.setData(false);
//                return result;
//            }
//        }
//
//        //删除销售记录
//        result = delete(id);
//        return result;
//    }
}

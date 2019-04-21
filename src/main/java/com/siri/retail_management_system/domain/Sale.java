package com.siri.retail_management_system.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * @author SiriYang
 * @title: Sale
 * @projectName retail_management_system
 * @description: 出货记录实体
 * @date 2019/4/21 22:50
 */
@Entity
public class Sale {

    /**
     * ID主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品名称
     */
    @NotEmpty(message = "商品名称不能为空")
    @Size(min = 1,max = 20)
    @Column(nullable = false,length = 20)
    private String merchandisename;

    /**
     * 会员名称
     */
    @Size(min = 1,max = 20)
    @Column(nullable = true,length = 20)
    private String membername;

    /**
     * 数量
     */
    @Column(nullable =false, columnDefinition = "int default 0")
    private Integer number=0;


    /**
     * 售价
     */
    @Column(nullable =false,precision = 12,scale = 2)
    private Double sale_price=0.0;

    /**
     * 创建时间
     */
    @Column(nullable = false) // 映射为字段，值不能为空
    @org.hibernate.annotations.CreationTimestamp  // 由数据库自动创建时间
    private Timestamp createTime;

    /**
     * 更新时间
     */
    @Column(nullable = false)
    @org.hibernate.annotations.UpdateTimestamp  // 由数据库自动创建时间
    private Timestamp updateTime;

    private Sale() {
    }

    public Sale(@NotEmpty(message = "商品名称不能为空") @Size(min = 1, max = 20) String merchandisename, Integer number, Double sale_price) {
        this.merchandisename = merchandisename;
        this.number = number;
        this.sale_price = sale_price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchandisename() {
        return merchandisename;
    }

    public void setMerchandisename(String merchandisename) {
        this.merchandisename = merchandisename;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getSale_price() {
        return sale_price;
    }

    public void setSale_price(Double sale_price) {
        this.sale_price = sale_price;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", merchandisename='" + merchandisename + '\'' +
                ", membername='" + membername + '\'' +
                ", number=" + number +
                ", sale_price=" + sale_price +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}

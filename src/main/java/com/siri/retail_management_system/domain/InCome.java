package com.siri.retail_management_system.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * @author SiriYang
 * @title: InCome
 * @projectName retail_management_system
 * @description: 进货记录实体
 * @date 2019/4/18 8:57
 */
@Entity
public class InCome {

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
     * 供应商名称
     */
    @NotEmpty(message = "供应商名称不能为空")
    @Size(min = 1,max = 20)
    @Column(nullable = true,length = 20)
    private String supplier;

    /**
     * 数量
     */
    @Column(nullable =false, columnDefinition = "int default 0")
    private Integer number=0;


    /**
     * 进价
     */
    @Column(nullable =false,precision = 12,scale = 2)
    private Double income_price=0.0;

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

    public InCome() {
    }

    public InCome(@NotEmpty(message = "商品名称不能为空") @Size(min = 1, max = 20) String merchandisename, @NotEmpty(message = "供应商名称不能为空") @Size(min = 1, max = 20) String supplier, Integer number, Double income_price) {
        this.merchandisename = merchandisename;
        this.supplier = supplier;
        this.number = number;
        this.income_price = income_price;
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

    public void setMerchandisename(String merchandise_name) {
        this.merchandisename = merchandise_name;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getIncome_price() {
        return income_price;
    }

    public void setIncome_price(Double income_price) {
        this.income_price = income_price;
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
        return "InCome{" +
                "id=" + id +
                ", merchandise_name='" + merchandisename + '\'' +
                ", supplier='" + supplier + '\'' +
                ", number=" + number +
                ", income_price=" + income_price +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}

package com.siri.retail_management_system.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * @author SiriYang
 * @title: Merchandise
 * @projectName retail_management_system
 * @description: 商品实体
 * @date 2019/4/17 15:02
 */
@Entity
public class Merchandise {

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
    @Column(nullable = false,length = 20,unique = true)
    private String name;

    /**
     * 供应商名称
     */
    @NotEmpty(message = "供应商名称不能为空")
    @Size(min = 1,max = 20)
    @Column(nullable = true,length = 20)
    private String supplier;

    /**
     * 库存数量
     */
    @Column(nullable =false, columnDefinition = "int default 0")
    private Integer number=0;


    /**
     * 进价
     */
    @Column(nullable =false)
    private Double income_price=0.0;

    /**
     * 标准售价
     */
    @Column(nullable =false)
    private Double sale_price=0.0;

    /**
     * 会员价
     */
    @Column(nullable =true)
    private Double member_price=0.0;

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

    protected Merchandise() {
    }

    public Merchandise(@NotEmpty(message = "商品名称不能为空") @Size(min = 1, max = 20) String name, @NotEmpty(message = "供应商名称不能为空") @Size(min = 1, max = 20) String supplier, Double income_price, Double sale_price, Double member_price) {
        this.name = name;
        this.supplier = supplier;
        this.income_price = income_price;
        this.sale_price = sale_price;
        this.member_price = member_price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Double getSale_price() {
        return sale_price;
    }

    public void setSale_price(Double sale_price) {
        this.sale_price = sale_price;
    }

    public Double getMember_price() {
        return member_price;
    }

    public void setMember_price(Double member_price) {
        this.member_price = member_price;
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
        return "Merchandise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", supplier='" + supplier + '\'' +
                ", number=" + number +
                ", income_price=" + income_price +
                ", sale_price=" + sale_price +
                ", member_price=" + member_price +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}

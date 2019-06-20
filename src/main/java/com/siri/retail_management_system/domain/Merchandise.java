package com.siri.retail_management_system.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
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
     * 库存数量
     */
    @Column(nullable =false, columnDefinition = "int default 0")
    private Integer number=0;

    /**
     * 进价
     */
    @Column(nullable =false,precision = 12,scale = 2)
    private Double income_price=0.0;

    /**
     * 标准售价
     */
    @Column(nullable =false,precision = 12,scale = 2)
    private Double sale_price=0.0;

    /**
     * 会员价
     */
    @Column(nullable =true,precision = 12,scale = 2)
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

    /**
     * 是否逻辑删除
     */
    @Column(nullable = false)
    @Type(type="yes_no")
    private boolean isDelet;

    protected Merchandise() {
    }

    public Merchandise(@NotEmpty(message = "商品名称不能为空") @Size(min = 1, max = 20) String name, Double income_price, Double sale_price, Double member_price) {
        this.name = name;
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

    public boolean isDelet() {
        return isDelet;
    }

    public void setDelet(boolean delet) {
        isDelet = delet;
    }

    @Override
    public String toString() {
        return "Merchandise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", income_price=" + income_price +
                ", sale_price=" + sale_price +
                ", member_price=" + member_price +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDelet=" + isDelet +
                '}';
    }
}

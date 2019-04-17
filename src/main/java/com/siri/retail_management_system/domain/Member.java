package com.siri.retail_management_system.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * @author SiriYang
 * @title: Member
 * @projectName retail_management_system
 * @description: 会员实体
 * @date 2019/4/17 13:13
 */
@Entity
public class Member {

    /**
     * ID主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会员姓名
     */
    @NotEmpty(message = "姓名不能为空")
    @Size(min = 1,max = 20)
    @Column(nullable = false,length = 20,unique = true) // 映射为字段，值不能为空
    private String membername;

    /**
     * 手机号
     */
    @NotEmpty(message = "手机号不能为空")
    @Size(max = 100)
    @Column(nullable = false,length = 100,unique = true)
    private String telephon;

    /**
     * 会员积分
     */
    @Column(nullable =false, columnDefinition = "int default 0")
    private Integer points=0;

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

    protected Member() {
    }

    public Member(@NotEmpty(message = "姓名不能为空") @Size(min = 1, max = 20) String membername, @NotEmpty(message = "手机号不能为空") @Size(max = 100) String telephon) {
        this.membername = membername;
        this.telephon = telephon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getTelephon() {
        return telephon;
    }

    public void setTelephon(String telephon) {
        this.telephon = telephon;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
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
        return "Member{" +
                "id=" + id +
                ", membername='" + membername + '\'' +
                ", telephon='" + telephon + '\'' +
                ", points=" + points +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}

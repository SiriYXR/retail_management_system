package com.siri.retail_management_system.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * @author SiriYang
 * @title: SystemLog
 * @projectName retail_management_system
 * @description: TODO
 * @date 2019/6/21 15:29
 */
@Entity
public class SystemLog {

    /**
     * ID主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 日志信息
     */
    @NotEmpty(message = "日志信息不能为空")
    @Column(nullable = false, length = 255)
    private String info;

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
    @Type(type = "yes_no")
    private boolean isDelet;

    protected SystemLog() {
    }

    public SystemLog(@NotEmpty(message = "日志信息不能为空") String info) {
        this.info = info;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
        return "SystemLog{" +
                "id=" + id +
                ", info='" + info + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDelet=" + isDelet +
                '}';
    }
}

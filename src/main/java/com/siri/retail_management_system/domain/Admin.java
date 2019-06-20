package com.siri.retail_management_system.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * @author SiriYang
 * @title: Admin
 * @projectName retail_management_system
 * @description: 管理员实体
 * @date 2019/4/15 13:34
 */
@Entity
@Table(name = "admin")
public class Admin {
    /**
     * ID主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 管理员账号，管理员登录时的唯一标志
     */
    @NotEmpty(message = "账号不能为空")
    @Size(min = 1, max = 20)
    @Column(nullable = false, length = 20, unique = true) // 映射为字段，值不能为空, 值不重复
    private String username;

    /**
     * 登陆密码
     */
    @NotEmpty(message = "密码不能为空")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String password;

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

    protected Admin() {  // JPA 的规范要求无参构造函数；设为 protected 防止直接使用
    }

    public Admin(@NotEmpty(message = "账号不能为空") @Size(min = 1, max = 20) String username, @NotEmpty(message = "密码不能为空") @Size(max = 100) String password) {
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDelet=" + isDelet +
                '}';
    }
}

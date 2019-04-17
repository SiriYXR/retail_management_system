package com.siri.retail_management_system.enums;

/**
 * @author SiriYang
 * @title: ResultEnum
 * @projectName retail_management_system
 * @description: TODO
 * @date 2019/4/15 14:02
 */
public enum  ResultEnum {
    UNKONW_ERROR(-1,"未知错误"),
    SUCCESS(0,"成功"),
    USERNAME_WRONG(101,"用户名不存在"),
    PASSWORD_WRONG(102,"密码错误"),
    ADMIN_ID_WRONG(103,"ID不存在"),
    ADMIN_ADD_ERROR(104,"管理员添加/保存失败"),
    ADMIN_FIND_ERROR(105,"管理员查询失败"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

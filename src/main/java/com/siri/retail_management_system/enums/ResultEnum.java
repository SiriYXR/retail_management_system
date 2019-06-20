package com.siri.retail_management_system.enums;

/**
 * @author SiriYang
 * @title: ResultEnum
 * @projectName retail_management_system
 * @description: 结果枚举类型
 * @date 2019/4/15 14:02
 */
public enum ResultEnum {
    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
    DELET_ERROR(1,"删除错误"),

    USERNAME_WRONG(101, "管理员名不存在"),
    PASSWORD_WRONG(102, "密码错误"),
    ADMIN_ID_WRONG(103, "管理员ID不存在"),
    ADMIN_ADD_ERROR(104, "管理员添加失败"),
    ADMIN_SAVE_ERROR(105, "管理员保存失败"),
    ADMIN_FIND_ERROR(106, "管理员查询失败"),
    ADMIN_DELET(107,"管理员已删除"),
    ADMIN_EXIST_USERNAME(108,"管理员名已存在"),

    MEMBER_NAME_WRONG(201, "会员名不存在"),
    MEMBER_ID_WRONG(202, "会员ID不存在"),
    MEMBER_ADD_ERROR(203, "会员添加失败"),
    MEMBER_SAVE_ERROR(204, "会员保存失败"),
    MEMBER_FIND_ERROR(205, "会员查询失败"),
    MEMBER_TELEPHONE_WRONG(206, "会员手机号查询失败"),
    MEMBER_DELET(207,"会员已删除"),
    MEMBER_EXIST_MEMBERNAME(208,"会员名已存在"),
    MEMBER_EXIST_TELEPHONE(209,"会员手机号已存在"),

    MERCHANDISE_NAME_WRONG(301, "商品名不存在"),
    MERCHANDISE_ID_WRONG(302, "商品ID不存在"),
    MERCHANDISE_ADD_ERROR(303, "商品添加失败"),
    MERCHANDISE_SAVE_ERROR(304, "商品保存失败"),
    MERCHANDISE_FIND_ERROR(305, "商品查询失败"),
    MERCHANDISE_DELET(306,"商品已删除"),

    INCOME_ID_WRONG(401, "进货记录ID不存在"),
    INCOME_ADD_ERROR(402, "进货记录添加失败"),
    INCOME_UPDATE_ERROR(402, "进货记录更新失败"),
    INCOME_FIND_ERROR(404, "进货记录查询失败"),
    INCOME_DELET(406,"进货记录已删除"),

    SALE_ID_WRONG(501, "出货记录ID不存在"),
    SALE_ADD_ERROR(502, "出货记录添加失败"),
    SALE_UPDATE_ERROR(503, "出货记录更新失败"),
    SALE_FIND_ERROR(504, "出货记录查询失败"),
    SALE_DELET(506,"出货记录已删除"),
    SALE_LACK_OF_GOODS(507,"商品数量不足"),
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

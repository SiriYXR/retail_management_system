package com.siri.retail_management_system.domain;

import com.siri.retail_management_system.enums.ResultEnum;

/**
 * @author SiriYang
 * @title: Result
 * @projectName retail_management_system
 * @description: 结果实体
 * @date 2019/4/15 14:02
 */
public class Result<T> {
    private Integer errCode;
    private String errMessage;
    private T data;

    public void setResultEnum(ResultEnum resultEnum){
        this.errCode=resultEnum.getCode();
        this.errMessage=resultEnum.getMsg();
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "errCode=" + errCode +
                ", errMessage='" + errMessage + '\'' +
                ", data=" + data +
                '}';
    }
}


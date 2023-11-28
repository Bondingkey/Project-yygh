package com.gzc.yygh.common.result;

public enum REnum {

    SUCCESS(20000,"成功",true),
    ERROR(20001,"失败",false)
    ;

    private Integer code;
    private String message;
    private Boolean success;

    REnum(Integer code, String message, Boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}

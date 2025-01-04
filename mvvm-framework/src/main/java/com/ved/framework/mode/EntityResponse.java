package com.ved.framework.mode;

public class EntityResponse<T> {
    private int resultCode;
    private String resultMsg;
    private T data;

    public int getCode() {
        return resultCode;
    }

    public void setCode(int code) {
        this.resultCode = code;
    }

    public String getMsg() {
        return resultMsg;
    }

    public void setMsg(String msg) {
        this.resultMsg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

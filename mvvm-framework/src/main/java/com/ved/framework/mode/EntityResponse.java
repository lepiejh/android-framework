package com.ved.framework.mode;

public class EntityResponse<T> {
    private int result;
    private String datas;
    private T message;

    public int getCode() {
        return result;
    }

    public void setCode(int code) {
        this.result = code;
    }

    public String getMsg() {
        return datas;
    }

    public void setMsg(String msg) {
        this.datas = msg;
    }

    public T getData() {
        return message;
    }

    public void setData(T data) {
        this.message = data;
    }
}

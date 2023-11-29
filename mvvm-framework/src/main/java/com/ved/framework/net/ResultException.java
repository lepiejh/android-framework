package com.ved.framework.net;

import com.ved.framework.utils.SPUtils;

import java.io.IOException;

class ResultException extends IOException {

    private String errMsg;
    private int errCode;

    public ResultException(String errMsg, int errCode){
        SPUtils.getInstance("net_sp").put("net_key",errMsg);
        this.errMsg = errMsg;
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}

package com.ably.demo.dto;

import com.ably.demo.errors.ErrorCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Error {
    private String code;
    private String msg;
    private String detailMsg;
    private String path;

    public Error() {
        code = "";
        msg = "";
        detailMsg = "";
        path = "";
    }

    public Error(
            String code,
            String msg,
            String path
    ) {
        this.code = code;
        this.msg = msg;
        this.path = path;
        this.detailMsg = "";
    }

    public Error(
            String code,
            String msg,
            String path,
            String detailMsg
    ) {
        this(code,msg,path);
        this.detailMsg = detailMsg;
    }

    public Error(
            ErrorCode errorCode,
            String path
    ) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.path = path;
        this.detailMsg = "";
    }

    public Error(
            ErrorCode errorCode,
            String path,
            String detailMsg
    ) {
        this(errorCode, path);
        this.detailMsg = detailMsg;
    }

    public void setErrorCode(ErrorCode errorCode){
        code = errorCode.getCode();
        msg = errorCode.getMsg();
    }

}
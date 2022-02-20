package com.xkj.poetryserver.controller.utils;

import lombok.Data;

@Data
public class R {
    public enum STATUS {
        success,warning,error
    }
    private STATUS status;
    private Object data;
    private String message;

    public R(STATUS status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public R(STATUS status, Object data) {
        this.status = status;
        this.data = data;
    }

    public R(Object data) {
        this.status = STATUS.success;
        this.data = data;
    }

    public R(STATUS status, String message) {
        this.status = status;
        this.message = message;
    }
}

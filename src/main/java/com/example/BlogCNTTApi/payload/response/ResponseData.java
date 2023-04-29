package com.example.BlogCNTTApi.payload.response;

import java.util.Objects;

public class ResponseData {
    private boolean success;
    private int status;
    private Object data;

    public ResponseData() {
    }

    public ResponseData(boolean success, int status, Object data) {
        this.success = success;
        this.status = status;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

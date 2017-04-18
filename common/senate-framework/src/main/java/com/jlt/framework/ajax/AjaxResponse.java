package com.jlt.framework.ajax;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AjaxResponse<T> implements Serializable {

    public final static int RESPONSE_STATUS_SUCCESS = 100;

    protected int status = RESPONSE_STATUS_SUCCESS;
    protected String message = null;
    protected T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static AjaxResponse success() {
        AjaxResponse response = new AjaxResponse();
        response.setStatus(RESPONSE_STATUS_SUCCESS);
        return response;
    }

    public static AjaxResponse failed(int statusCode) {
        AjaxResponse response = new AjaxResponse();
        response.setStatus(statusCode);
        return response;
    }
}

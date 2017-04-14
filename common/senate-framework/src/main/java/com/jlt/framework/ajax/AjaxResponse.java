package com.jlt.framework.ajax;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AjaxResponse implements Serializable {

    public final static int RESPONSE_STATUS_SUCCESS = 100;
    public final static int RESPONSE_STATUS_FAIURE = -1;
    public final static int RESPONSE_STATUS_VALIDATION_FAILED = -2;

    protected int status;
    protected String statusMessage = null;
    protected List<Object> rows = new ArrayList<Object>();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Object> getRows() {
        return rows;
    }

    public void addDataEntry(Map<String, Object> dataEntry) {
        this.rows.add(dataEntry);
    }

    public void setErrorMessage(Throwable t) {
        this.setStatusMessage(t.getMessage());
    }

    public void setErrorString(String t) {
        this.setStatusMessage(t);
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
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

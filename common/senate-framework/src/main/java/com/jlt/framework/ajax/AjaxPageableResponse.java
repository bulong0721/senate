package com.jlt.framework.ajax;

import com.jlt.framework.data.Page;

import java.util.List;

public class AjaxPageableResponse<T> extends AjaxResponse<T> {
    private long total;
    private int page;
    private int size;
    private List<T> list;

    public AjaxPageableResponse() {
    }

    public AjaxPageableResponse(Page<T> page) {
        this.total = page.getTotalElements();
        this.list = page.getContent();
        this.size = page.getSize();
        this.page = page.getNumber() / size;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}

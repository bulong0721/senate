package com.jlt.framework.dto;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by Administrator on 2016/6/6.
 */
public class PageResult<T> {

    private long total;

    private Pagination pagination;

    private List<T> list = Lists.newArrayList();

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}

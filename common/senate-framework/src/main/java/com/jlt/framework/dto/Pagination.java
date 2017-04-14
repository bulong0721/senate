package com.jlt.framework.dto;

/**
 * Created by Administrator on 2016/6/6.
 */
public class Pagination {

    private int count;

    private int page;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getOffset() {
        if (page > 0) {
            return (page - 1) * count;
        }
        return 0;
    }
}

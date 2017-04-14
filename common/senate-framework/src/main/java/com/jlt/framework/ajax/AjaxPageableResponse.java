package com.jlt.framework.ajax;

import java.util.Map;

public class AjaxPageableResponse extends AjaxResponse {
    private long total;
    private long count;

    @Override
    public void addDataEntry(Map<String, Object> dataEntry) {
        super.addDataEntry(dataEntry);
        count = rows.size();
        total = rows.size();
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}

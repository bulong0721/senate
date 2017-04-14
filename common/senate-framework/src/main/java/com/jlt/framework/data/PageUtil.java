package com.jlt.framework.data;

import org.springframework.util.Assert;

import java.util.List;
import java.util.function.LongSupplier;

/**
 * Created by Martin.Xu on 2017/4/13.
 */
public class PageUtil {

    public static <T> Page<T> getPage(List<T> content, Pageable pageable, LongSupplier totalSupplier) {

        Assert.notNull(content, "Content must not be null!");
        Assert.notNull(pageable, "Pageable must not be null!");
        Assert.notNull(totalSupplier, "TotalSupplier must not be null!");

        if (pageable.isUnpaged() || pageable.getOffset() == 0) {
            if (pageable.isUnpaged() || pageable.getPageSize() > content.size()) {
                return new PageImpl<>(content, pageable, content.size());
            }

            return new PageImpl<>(content, pageable, totalSupplier.getAsLong());
        }

        if (content.size() != 0 && pageable.getPageSize() > content.size()) {
            return new PageImpl<>(content, pageable, pageable.getOffset() + content.size());
        }

        return new PageImpl<>(content, pageable, totalSupplier.getAsLong());
    }
}

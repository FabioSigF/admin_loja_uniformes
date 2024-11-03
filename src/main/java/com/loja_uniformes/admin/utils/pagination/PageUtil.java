package com.loja_uniformes.admin.utils.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtil {

    private PageUtil() {}

    public static Pageable generatedPage(Integer page, Integer limit, Sort.Direction direction, String... sortList) {
        if (page == 0) {
            page = 1;
        }
        return PageRequest.of(page - 1, limit, Sort.by(direction, sortList));
    }

    public static Pageable generatedPage(Integer page, Integer limit, String... sortList) {
        return generatedPage(page, limit, Sort.Direction.ASC, sortList);
    }
}
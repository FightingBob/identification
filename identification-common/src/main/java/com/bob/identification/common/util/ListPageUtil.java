package com.bob.identification.common.util;

import java.util.List;
import java.util.stream.Collectors;

/**
 * list分页获取
 * Created by LittleBob on 2020/12/30/030.
 */
public class ListPageUtil {

    public static <T> List<T> getPageResult(List<T> list, int page, int pageNums) {
        return list.stream()
                .skip((page-1) * pageNums)
                .limit(pageNums)
                .collect(Collectors.toList());
    }
}

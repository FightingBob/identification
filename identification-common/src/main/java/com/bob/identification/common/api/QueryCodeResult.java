package com.bob.identification.common.api;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LittleBob on 2021/1/6/006.
 */

@Getter
@Setter
public class QueryCodeResult {

    /**
     * 防伪码结果
     */
    private String result;

    /**
     * 防伪码
     */
    private QueryCode queryCode;
}

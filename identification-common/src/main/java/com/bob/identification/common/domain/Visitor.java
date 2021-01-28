package com.bob.identification.common.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 访问参数
 * Created by LittleBob on 2021/1/4/004.
 */
@Getter
@Setter
public class Visitor {

    /**
     * 访问地址
     */
    private String ipAddress;

    /**
     * 访问次数
     */
    private int visitTimes;
}

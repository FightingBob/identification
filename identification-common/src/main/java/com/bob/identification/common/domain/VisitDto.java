package com.bob.identification.common.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LittleBob on 2021/1/8/008.
 */
@Getter
@Setter
public class VisitDto {

    /**
     * 访问总人数
     */
    private Integer visitNumber;

    /**
     * 访问总次数
     */
    private Integer visitTimes;
}

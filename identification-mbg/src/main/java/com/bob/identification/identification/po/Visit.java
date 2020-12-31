package com.bob.identification.identification.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 防伪访问记录表
 * </p>
 *
 * @author LittleBob
 * @since 2020-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 访问总人数
     */
    private Integer visitNumber;

    /**
     * 访问总次数
     */
    private Integer visitTimes;

    /**
     * 频繁访问的ip地址
     */
    private String oftenVisitIp;

    /**
     * 某ip访问次数
     */
    private Integer oftenVisitTimes;


}

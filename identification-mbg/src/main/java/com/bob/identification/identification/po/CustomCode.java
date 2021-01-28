package com.bob.identification.identification.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 自定义防伪码
 * </p>
 *
 * @author LittleBob
 * @since 2021-01-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 防伪编号
     */
    private String serialNumber;

    /**
     * 查询次数
     */
    private Integer queryTime;

    /**
     * 防伪码状态，1有效，0无效
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 品牌id
     */
    private Integer brandId;


}

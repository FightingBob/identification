package com.bob.identification.identification.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 防伪参数配置表
 * </p>
 *
 * @author LittleBob
 * @since 2020-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关键词
     */
    private String code;

    /**
     * 关键词定义
     */
    private String codeName;

    /**
     * 关键词内容
     */
    private String codeValue;


}

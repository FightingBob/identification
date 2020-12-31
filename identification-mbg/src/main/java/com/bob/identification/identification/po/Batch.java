package com.bob.identification.identification.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 防伪批次表
 * </p>
 *
 * @author LittleBob
 * @since 2020-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Batch implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 防伪码第一个数字
     */
    private Integer firstNumber;

    /**
     * 防伪码第二个数字
     */
    private Integer secondNumber;

    /**
     * 防伪码第三个数字
     */
    private Integer thirdNumber;

    /**
     * 防伪码前三个数字
     */
    private String preThreeNumber;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 数量
     */
    private Integer demand;

    /**
     * 默认启用，1启用，0停用
     */
    private Integer status;

    /**
     * 防伪码文件存放后缀
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String codeSufUrl;

    /**
     * 防伪码文件存放后缀加密字符串
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String encryptCodeSufUrl;


}

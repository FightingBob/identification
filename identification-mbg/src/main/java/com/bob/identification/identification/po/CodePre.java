package com.bob.identification.identification.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 防伪码第二数字0
 * </p>
 *
 * @author LittleBob
 * @since 2020-12-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CodePre implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 防伪码批次
     */
    private Integer batchId;

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


}

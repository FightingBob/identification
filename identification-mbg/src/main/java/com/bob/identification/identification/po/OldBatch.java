package com.bob.identification.identification.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 旧批次表
 * </p>
 *
 * @author LittleBob
 * @since 2021-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OldBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 品牌id
     */
    private Integer brandId;

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
     * 防伪码文件存放后缀
     */
    private String codeSufUrl;

    /**
     * 防伪码文件存放后缀加密字符串
     */
    private String encryptCodeSufUrl;


}

package com.bob.identification.dto;

import com.bob.identification.authority.po.UmsMenu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 后台菜单节点封装
 * Created by LittleBob on 2020/12/26/026.
 */
@Getter
@Setter
public class UmsMenuNode extends UmsMenu {

    /**
     * 子节点
     */
    private List<UmsMenuNode> children;
}

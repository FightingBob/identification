package com.bob.identification.common.util;

import cn.hutool.core.util.RandomUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 随机数生成工具
 * Created by LittleBob on 2020/12/30/030.
 */
public class NumberGeneratorUtil {

    /**
     * 批量生成前缀+随机数
     * @param preNumber 前缀
     * @param length 随机数长度
     * @param demand 需求量
     * @return 指定量的且不重复的随机数
     */
    public static List<String> generateRandomNumberList(String preNumber, int length, Integer demand) {
        Set<String> set = new HashSet<>();
        generateRandomNumberList(preNumber, length, demand, set);
        return new ArrayList<>(set);
    }

    /**
     * 随机数生成
     * @param preNumber 前缀
     * @param length 随机数长度
     * @param times 生成次数
     * @param set 容器
     * @return 数据
     */
    private static Set<String> generateRandomNumberList(String preNumber, int length, Integer times, Set<String> set) {
        for(int i=0; i <  times; i++) {
            set.add(preNumber + RandomUtil.randomNumbers(length));
        }
        int generatedNumber = set.size();
        System.out.println("生成不重复数量为：" + generatedNumber);
        Integer lastNumber = times -  set.size();
        return lastNumber > 0 ? generateRandomNumberList(preNumber, length, lastNumber, set) : set;
    }
}

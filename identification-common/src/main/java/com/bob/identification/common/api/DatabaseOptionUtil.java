package com.bob.identification.common.api;

/**
 * 数据库选项工具
 * Created by LittleBob on 2021/1/3/003.
 */
public class DatabaseOptionUtil {

    /**
     * 根据数据库选项获取数据库代号
     * @param databaseOption 数据库选项
     * @return 数据库代号
     */
    public static String getCodeNameByOption(Integer databaseOption) {
        if (DatabaseOption.LAIKOU.getDatabaseOption().equals(databaseOption)) {
            return DatabaseOption.LAIKOU.getDatabaseCodeName();
        }
        if (DatabaseOption.FENYI.getDatabaseOption().equals(databaseOption)) {
            return DatabaseOption.FENYI.getDatabaseCodeName();
        }
        if (DatabaseOption.KAIBIN.getDatabaseOption().equals(databaseOption)) {
            return DatabaseOption.KAIBIN.getDatabaseCodeName();
        }
        return DatabaseOption.YALADYS.getDatabaseCodeName();
    }

    /**
     * 获取数据库选项
     * @param codeName 数据库代号
     * @return 数据库代号
     */
    public static Integer getOptionByCodeName(String codeName) {
        if (DatabaseOption.LAIKOU.getDatabaseCodeName().equals(codeName)) {
            return DatabaseOption.LAIKOU.getDatabaseOption();
        }
        if (DatabaseOption.FENYI.getDatabaseCodeName().equals(codeName)) {
            return DatabaseOption.FENYI.getDatabaseOption();
        }
        if (DatabaseOption.KAIBIN.getDatabaseCodeName().equals(codeName)) {
            return DatabaseOption.KAIBIN.getDatabaseOption();
        }
        return DatabaseOption.YALADYS.getDatabaseOption();
    }

}

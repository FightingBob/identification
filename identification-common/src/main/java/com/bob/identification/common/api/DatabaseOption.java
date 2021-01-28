package com.bob.identification.common.api;

/**
 * 数据库选项
 * Created by LittleBob on 2021/1/3/003.
 */
public enum DatabaseOption implements IDatabaseOption{

    LAIKOU("LAIKOU", 1),
    FENYI("FENYI", 2),
    KAIBIN("KAIBIN", 3),
    YALADYS("YALADYS", 4);

    private final String databaseCodeName;

    private final Integer databaseOption;

    DatabaseOption(String databaseCodeName, Integer databaseOption) {
        this.databaseCodeName = databaseCodeName;
        this.databaseOption = databaseOption;
    }

    @Override
    public String getDatabaseCodeName() {
        return databaseCodeName;
    }

    @Override
    public Integer getDatabaseOption() {
        return databaseOption;
    }
}


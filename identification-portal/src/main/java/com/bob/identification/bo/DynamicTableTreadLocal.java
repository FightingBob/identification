package com.bob.identification.bo;

/**
 * 动态数据表存储
 * Created by LittleBob on 2020/12/30/030.
 */
public enum DynamicTableTreadLocal {
    INSTANCE;
    private ThreadLocal<String> tableNameSuffix = new ThreadLocal<>();

    public String getTableName() {
        return tableNameSuffix.get();
    }

    public void setTableName(String tableName) {
        this.tableNameSuffix.set(tableName);
    }

    public void remove() {
        tableNameSuffix.remove();
    }
}

package com.bob.identification.common.api;

/**
 * 常用状态类
 * Created by LittleBob on 2020/12/22/022.
 */
public enum CommonStatus implements ICommonStatus {
    SUCCESS(1),
    FAILED(0);
    private int status;

    CommonStatus(int status) {
        this.setStatus(status);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int getStatus() {
        return status;
    }
}

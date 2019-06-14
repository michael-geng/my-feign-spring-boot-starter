package com.sq.common.feignclient;

public class DriverRequestBean {
    private String driverId;

    private String createTime;

    public DriverRequestBean() {
    }

    public DriverRequestBean(String driverId, String createTime) {
        this.driverId = driverId;
        this.createTime = createTime;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

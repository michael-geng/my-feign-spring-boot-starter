package com.sq.common.feignclient;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @description 城市等级配置
 * @create 2019-02-19 18:53
 **/
public class DriverLevelCityConfig {

    private Integer id;
    private Integer cityId;
    private Date firstCalculateDate;
    private Integer calcuateType;
    private Integer calcuateCycle;

    private List<DriverLevelCityConfigItem> rankList;

    public List<DriverLevelCityConfigItem> getRankList() {
        return rankList;
    }

    public void setRankList(List<DriverLevelCityConfigItem> rankList) {
        this.rankList = rankList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Date getFirstCalculateDate() {
        return firstCalculateDate;
    }

    public void setFirstCalculateDate(Date firstCalculateDate) {
        this.firstCalculateDate = firstCalculateDate;
    }

    public Integer getCalcuateType() {
        return calcuateType;
    }

    public void setCalcuateType(Integer calcuateType) {
        this.calcuateType = calcuateType;
    }

    public Integer getCalcuateCycle() {
        return calcuateCycle;
    }

    public void setCalcuateCycle(Integer calcuateCycle) {
        this.calcuateCycle = calcuateCycle;
    }
}

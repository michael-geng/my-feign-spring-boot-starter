package com.sq.common.feignclient;

import java.math.BigDecimal;

/**
 * @author
 * @description
 **/
public class DriverLevelCityConfigItem {

    private Integer membershipRank;
    private String membershipName;
    private Integer membershipMinRange;
    private Integer membershipMaxRange;
    private BigDecimal appraiseScore;

    public BigDecimal getAppraiseScore() {
        return appraiseScore;
    }

    public void setAppraiseScore(BigDecimal appraiseScore) {
        this.appraiseScore = appraiseScore;
    }

    public Integer getMembershipRank() {
        return membershipRank;
    }

    public void setMembershipRank(Integer membershipRank) {
        this.membershipRank = membershipRank;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public Integer getMembershipMinRange() {
        return membershipMinRange;
    }

    public void setMembershipMinRange(Integer membershipMinRange) {
        this.membershipMinRange = membershipMinRange;
    }

    public Integer getMembershipMaxRange() {
        return membershipMaxRange;
    }

    public void setMembershipMaxRange(Integer membershipMaxRange) {
        this.membershipMaxRange = membershipMaxRange;
    }
}

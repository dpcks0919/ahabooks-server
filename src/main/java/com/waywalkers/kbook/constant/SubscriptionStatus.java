package com.waywalkers.kbook.constant;

public enum SubscriptionStatus {
    MONTHLY(4900L, 9800L), YEARLY(54000L, 108000L), UNSUBSCRIBE(0L,0L);

    private final Long vulnerablePrice;
    private final Long generalPrice;

    private SubscriptionStatus(Long vulnerablePrice, Long generalPrice) {
        this.vulnerablePrice = vulnerablePrice;
        this.generalPrice = generalPrice;
    }

    public Long getVulnerablePrice() {
        return vulnerablePrice;
    }

    public Long getGeneralPrice() {
        return generalPrice;
    }

}

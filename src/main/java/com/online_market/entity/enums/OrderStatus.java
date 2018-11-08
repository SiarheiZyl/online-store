package com.online_market.entity.enums;

public enum OrderStatus {
    AWAITING_PAYMENT,
    AWAITING_SHIPMENT,
    SHIPPED,
    DELIVERED;

    @Override
    public String toString() {
        String s = super.toString();

        return s.toCharArray()[0] + s.substring(1).toLowerCase().replaceAll("_", " ");
    }
}

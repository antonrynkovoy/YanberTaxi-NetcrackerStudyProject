package com.netcracker.project.study.vaadin.client.events;

import com.netcracker.project.study.vaadin.driver.components.tabs.NewOrdersTab;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;


public class RefreshClientViewEvent {
    private final BigInteger orderId;
    private final NewOrdersTab page;

    public RefreshClientViewEvent (NewOrdersTab page, BigInteger orderId) {
        this.page = page;
        this.orderId = orderId;
    }

    public BigInteger getOrderId() {
        return orderId;
    }

    public NewOrdersTab getPage() {
        return page;
    }

}

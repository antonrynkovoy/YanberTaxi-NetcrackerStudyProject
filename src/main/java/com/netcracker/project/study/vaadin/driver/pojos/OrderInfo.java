package com.netcracker.project.study.vaadin.driver.pojos;


import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;

import java.math.BigInteger;

public class OrderInfo {

    private BigInteger objectId;

    private int queueN;
    private String driverName;
    private String clientName;
    private String status;
    private String cost;
    private String distance;

    public void setQueueN(int queueN) {
        this.queueN = queueN;
    }

    public int getQueueN() {
        return queueN;
    }

    public BigInteger getObjectId() {
        return objectId;
    }

    public void setObjectId(BigInteger objectId) {
        this.objectId = objectId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
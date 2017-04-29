package com.netcracker.project.study.model.order.orderStatus;

import java.sql.Time;

/**
 * Created by Mark on 27.04.2017.
 */
public class OrderStatus {
    public final int OBJECT_TYPE_ID=5;

    private String name;
    private String description;

    private int orderId;
    private String status;
    private Time timeStamp;

    public int getObjectId() {
        return OBJECT_TYPE_ID;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name=name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public int getOrderId(){
        return orderId;
    }

    public void setOrderId(int orderId){
        this.orderId=orderId;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status=status;
    }

    public Time getTimeStamp(){
        return timeStamp;
    }

    public void setTimeStamp(Time timeStamp){
        this.timeStamp=timeStamp;
    }
}
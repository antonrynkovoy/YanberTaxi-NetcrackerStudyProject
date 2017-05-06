package com.netcracker.project.study.model.driver.status;

import com.netcracker.project.study.model.Model;
import com.netcracker.project.study.model.annotations.Attribute;
import com.netcracker.project.study.model.annotations.ObjectType;
import com.netcracker.project.study.model.annotations.Reference;
import com.netcracker.project.study.model.driver.DriverAttr;

import java.sql.Time;

@ObjectType(objectTypeId = DriverStatusAttr.OBJECT_TYPE_ID)
public class DriverStatus extends Model{

    @Reference(objectTypeId = DriverAttr.OBJECT_TYPE_ID)
    private int driverId;

    @Attribute(attrId = DriverStatusAttr.STATUS_ATTR)
    private String status;

    @Attribute(attrId = DriverStatusAttr.TIME_STAMP_ATTR)
    private Time timeStamp;

    public DriverStatus(long objectId){
        this.objectId = objectId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Time getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Time timeStamp) {
        this.timeStamp = timeStamp;
    }

}

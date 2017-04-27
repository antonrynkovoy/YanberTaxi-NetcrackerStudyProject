package com.netcracker.project.study.model.client;

import java.util.Date;

/**
 * Created by Mark on 25.04.2017.
 */
public class Client {
    public final int OBJECT_TYPE_ID=2;

    private String last_name;
    private String first_name;
    private String middle_name;

    private int phoneNumber;
    private int points;

    public String getLast_name(){
        return last_name;
    }

    public void setLast_name(String last_name){
        this.last_name=last_name;
    }

    public String getFirst_name(){
        return first_name;
    }

    public void setFirst_name(String first_name){
        this.first_name=first_name;
    }

    public String getMiddle_name(){
        return middle_name;
    }

    public void setMiddle_name(String middle_name){
        this.middle_name=middle_name;
    }

    public int getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPoints(){
        return points;
    }

    public void setPoints(int points){
        this.points=points;
    }


}

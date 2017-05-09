package com.netcracker.project.study;

import com.netcracker.project.study.model.client.Client;
import com.netcracker.project.study.model.driver.Driver;
import com.netcracker.project.study.persistence.PersistenceEntity;
import com.netcracker.project.study.persistence.converter.Converter;
import com.netcracker.project.study.persistence.converter.impl.ConverterFactory;
import com.netcracker.project.study.persistence.facade.impl.PersistenceFacade;
import com.netcracker.project.study.persistence.manager.impl.PersistenceManager;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        PersistenceFacade facade = (PersistenceFacade) ctx.getBean("persistenceFacade");

        /* Client client = new Client();
        client.setName("Client Anton");
        client.setFirstName("Anton");
        client.setLastName("Rynkovoy");
        client.setDescription("It's client");

        Driver driver = new Driver();
        driver.setName("Driver Ed");
        driver.setFirstName("Ed");
        driver.setEmail("ed@mail.ru");
        driver.setDescription("It's driver");

        facade.create(client);
        facade.create(driver);*/

        /*Client client = new Client(12);
        client.setName("New Client Anton");
        client.setPoints(49);
        client.setLastName("Targarian");

        Driver driver = new Driver(13);
        driver.setName("New Driver Ed");
        driver.setPhoneNumber("56565");
        driver.setEmail("new-mail@mail.ru");

        facade.update(client);
        facade.update(driver);*/

        Driver driver = (Driver) facade.getOne(11, Driver.class);
        System.out.println(driver);

        driver.setFirstName("Antonio");
        driver.setFirstName("Rynkovyy");
        driver.setName("new Anton Driver");

        facade.update(driver);

        System.out.println(driver);


    }
}




package com.netcracker.project.study.services;

import com.netcracker.project.study.model.order.Order;
import com.netcracker.project.study.model.order.route.Route;
import com.netcracker.project.study.vaadin.driver.components.tabs.NewOrdersTab;
import com.netcracker.project.study.vaadin.driver.components.views.OrdersViewForDrivers;
import com.netcracker.project.study.vaadin.driver.pojos.OrderInfo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface OrderService {

    BigDecimal calcPrice(BigDecimal distance, BigInteger orderId);

    void changeStatus(BigInteger status, BigInteger orderId);

    void setDistance(BigInteger orderId, double distance);

    String getStatusValue(BigInteger statusId);

    List<OrderInfo>getOrdersInfo(List<Order> orders);

    List<Order> getOrders(BigInteger statusId);

    List<Order> getAllOrders();

    List<Route> getRoutes(BigInteger orderId);

    List<Order> allModelsAsList();

    List<Order> getOrdersByDriverId(BigInteger driverId, BigInteger orderStatusId);

    List<Order> getOrdersByClientId(BigInteger clientId);

    void orderStatusLog(Order order);

    List<Order> getActiveOrdersByClientId(BigInteger clientId);

    List<Order> getPerformingOrdersByClientId(BigInteger clientId);

    List<Order>getCurrentOrderByDriverId(BigInteger driverId);

    List<Order>getCurrentOrderByClientId(BigInteger clientId);

    Order getOrder(BigInteger orderId);

    List<OrderInfo> getOrdersInfoByDriverId(BigInteger driverId, BigInteger orderStatusId);

    void setClientPoints(BigInteger orderId);

    Timestamp getLastDateFromOrdersLog(Order order);

}

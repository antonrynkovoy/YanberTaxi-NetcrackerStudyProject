package com.netcracker.project.study.vaadin.client.components.grids;

import com.netcracker.project.study.model.client.Client;
import com.netcracker.project.study.model.order.Order;
import com.netcracker.project.study.model.order.OrderStatusEnum;
import com.netcracker.project.study.services.OrderService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.List;

@SpringComponent
public class ClientOrdersGrid  extends CustomComponent {

    @Autowired
    OrderService orderService;

    private Grid<Order> clientOrderGrid;

    Client client;

    @PostConstruct
    public void init() {
        clientOrderGrid = generateClientOrderGrid();

        setCompositionRoot(clientOrderGrid);
    }

    private Grid<Order> generateClientOrderGrid() {
        Grid<Order> ordersGrid = new Grid<>();
        ordersGrid.setSizeFull();
        List<Order> clientOrderList = orderService.getOrdersByClientId(BigInteger.valueOf(242));
        orderService.getOrdersInfo(clientOrderList);
        ordersGrid.setItems(clientOrderList);

        ordersGrid.addColumn(Order::getObjectId).setCaption("№");
        ordersGrid.addColumn(Order -> Order.getName().substring(0, Order.getName().indexOf(" "))).setCaption("Start point");
        ordersGrid.addColumn(Order -> Order.getName().substring((Order.getName().indexOf("- "))+1)).setCaption("Destination");
        ordersGrid.addColumn(Order -> Order.getDriverOnOrder() != null ? Order.getDriverOnOrder().getFirstName() + " " +
                Order.getDriverOnOrder().getLastName() : "").setCaption("Driver");
        ordersGrid.addColumn(order -> OrderStatusEnum.getStatusValue(order.getStatus())).setCaption("Status");
        ordersGrid.addColumn(Order::getCost).setCaption("Cost");
        ordersGrid.addColumn(Order::getDistance).setCaption("Distance");
        ordersGrid.addColumn(Order::getDriverRating).setCaption("Driver rating");
        ordersGrid.addColumn(Order::getDriverMemo).setCaption("Driver comment");

        return ordersGrid;
    }

    public void setClient(Client client) {this.client = client;}

}
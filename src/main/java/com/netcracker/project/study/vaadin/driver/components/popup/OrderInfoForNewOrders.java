package com.netcracker.project.study.vaadin.driver.components.popup;

import com.netcracker.project.study.model.order.route.Route;
import com.netcracker.project.study.services.OrderService;
import com.netcracker.project.study.vaadin.driver.pojos.OrderInfo;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
public class OrderInfoForNewOrders extends HorizontalLayout {

    final String NO_ORDER_MESSAGE = "There is no order selected yet";

    HorizontalLayout rootLayout;

    OrderInfo orderInfo;

    @Autowired
    OrderService orderService;

    public void init(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
        removeAllComponents();
        initRootLayout();
        initOrderInfoLayout();
        addComponent(rootLayout);
    }

    private void initRootLayout(){
        rootLayout = new HorizontalLayout();
        rootLayout.setSizeFull();
        rootLayout.setSpacing(true);
        rootLayout.setMargin(true);
    }

    private void initOrderInfoLayout(){

        VerticalLayout orderLayout = new VerticalLayout();
        if(orderInfo != null){
            Label driverId = new Label("<b>Driver: </b>"+ orderInfo.getDriverName(), ContentMode.HTML);
            driverId.setIcon(FontAwesome.MALE);
            Label clientId = new Label("<b>Client: </b>" + orderInfo.getClientName(), ContentMode.HTML);
            clientId.setIcon(FontAwesome.MALE);
            Label status = new Label("<b>Status: </b>" + orderInfo.getStatus(), ContentMode.HTML);
            status.setIcon(FontAwesome.ROUBLE);
            Label cost = new Label("<b>Cost: </b>" + orderInfo.getCost(), ContentMode.HTML);
            cost.setIcon(FontAwesome.DOLLAR);
            Label distance = new Label("<b>Distance: </b>" + orderInfo.getDistance(), ContentMode.HTML);
            distance.setIcon(FontAwesome.ARROWS_H);
            orderLayout.addComponents(driverId,clientId,status,cost,distance);
            rootLayout.addComponent(orderLayout);
            rootLayout.addComponent(getRoutesLayout());
        }else{
            Label noOrderSelectedLabel = new Label(NO_ORDER_MESSAGE);
            rootLayout.addComponent(noOrderSelectedLabel);
        }

    }

    private VerticalLayout getRoutesLayout(){
        VerticalLayout routeLayout= new VerticalLayout();
        List<Route> routes = orderService.getRoutes(orderInfo.getObjectId());

        int i = 0;
        for(Route route:routes){
            Label label = new Label("<b>Address " + i + ": </b>"+ route.getCheckPoint(), ContentMode.HTML);
            label.setIcon(FontAwesome.MAP_MARKER);
            routeLayout.addComponent(label);
            i++;
        }

        return routeLayout;
    }
}
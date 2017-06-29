package com.netcracker.project.study.vaadin.admin.components.grids;

import com.netcracker.project.study.model.order.Order;
import com.netcracker.project.study.model.order.OrderStatusEnum;
import com.netcracker.project.study.services.AdminService;
import com.netcracker.project.study.vaadin.admin.components.popup.AdminOrderInfoPopUp;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.*;

@SpringComponent
@Scope(value = "prototype")
public class OrdersGrid extends CustomComponent {

    @Autowired AdminService adminService;

    private Grid<Order> ordersGrid;

    private VerticalLayout componentLayout;

    private List<Order> ordersList;

    private Window orderInfoWindow;

    @Autowired
    AdminOrderInfoPopUp orderInfoPopUp;

    public void init() {
        ordersGrid = generateOrdersGrid();
        initOrderInfoWindow();
        componentLayout = getFilledComponentLayout();
        componentLayout.addComponent(getButtons());
        setGridSettings(ordersGrid);
        setCompositionRoot(componentLayout);
    }

    private void initOrderInfoWindow() {
        orderInfoWindow = new Window("Information about the order");
        orderInfoWindow.setIcon(VaadinIcons.INFO);
        orderInfoWindow.center();
        orderInfoWindow.setModal(true);
        orderInfoWindow.setResizable(false);
        orderInfoWindow.setContent(orderInfoPopUp);
    }

    private VerticalLayout getFilledComponentLayout() {
        VerticalLayout componentLayout = new VerticalLayout();
        componentLayout.setSizeFull();
        componentLayout.setMargin(false);
        componentLayout.setSpacing(false);

        componentLayout.addComponents(ordersGrid);

        return componentLayout;
    }

    private Grid<Order> generateOrdersGrid() {
        Grid<Order> ordersGrid = new Grid<>();
        ordersGrid.addColumn(Order::getObjectId).setCaption("№");
        ordersGrid.addColumn(Order -> Order.getDriverOnOrder() != null ? Order.getDriverOnOrder().getFirstName() + " " +
                Order.getDriverOnOrder().getLastName() : "").setCaption("Driver");
        ordersGrid.addColumn(Order -> Order.getClientOnOrder() != null ? Order.getClientOnOrder().getFirstName() + " " +
                Order.getClientOnOrder().getLastName() : "").setCaption("Client");
        ordersGrid.addColumn(order -> OrderStatusEnum.getStatusValue(order.getStatus())).setCaption("Status");
        ordersGrid.addColumn(order -> order.getCost() != null ? order.getCost() + " hrn" : "").setCaption("Cost");
        ordersGrid.addColumn(order -> order.getDistance() != null ? order.getDistance() + " km" : "").setCaption("Distance");
        ordersGrid.addColumn(order -> order.getDriverRating()).setCaption("Driver rating");
        return ordersGrid;
    }

    private HorizontalLayout getButtons(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        componentLayout.setMargin(false);
        componentLayout.setSpacing(false);
        Button viewOrderButton = new Button("View order", FontAwesome.INFO);
        viewOrderButton.addClickListener(event->{
            if(!ordersGrid.asSingleSelect().isEmpty()){
                Order order = ordersGrid.asSingleSelect().getValue();
                orderInfoPopUp.init(order);
                UI.getCurrent().addWindow(orderInfoWindow);
            }
        });
        horizontalLayout.addComponent(viewOrderButton);
        horizontalLayout.setComponentAlignment(viewOrderButton,Alignment.BOTTOM_LEFT);
        return horizontalLayout;
    }

    private void setGridSettings(Grid<Order> ordersGrid) {
        ordersGrid.setSizeFull();
    }

    public Grid<Order> getOrdersGrid() {
        return ordersGrid;
    }

    public List getOrdersList() {
        return ordersList;
    }

    public void refreshGrid() {
        ordersList = adminService.allModelsAsList(Order.class);
        ordersGrid.setItems(ordersList);
        //init();
    }

}


package com.netcracker.project.study.vaadin.client.popups;

import com.google.common.collect.ImmutableList;
import com.netcracker.project.study.model.Role;
import com.netcracker.project.study.model.client.Client;
import com.netcracker.project.study.model.order.Order;
import com.netcracker.project.study.model.user.User;
import com.netcracker.project.study.services.ClientService;
import com.netcracker.project.study.services.OrderService;
import com.netcracker.project.study.vaadin.common.components.PhoneField;
import com.vaadin.data.HasValue;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.vaadin.addons.Toastr;
import org.vaadin.addons.builder.ToastBuilder;

import java.math.BigInteger;

@SpringComponent
@Scope(value = "prototype")
public class DriverEvaluation extends Window {
    @Autowired
    OrderService orderService;

    @Autowired
    ClientService clientService;

    private BigInteger orderId;
    private TextField driverMemo;
    private TextField orderCost;
    private Toastr toastr;

    public void init(BigInteger orderId) {
        this.orderId = orderId;
        VerticalLayout root = genRootLayout();
        root.addComponent(setRadioButton());
        setModal(true);
        setResizable(false);
        setCaption(" Driver evaluation");
        setContent(root);

    }

    private VerticalLayout genRootLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidth(400, Unit.PIXELS);
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);
        return verticalLayout;
    }

    private VerticalLayout setRadioButton(){
        Order order = orderService.getOrder(orderId);
        VerticalLayout verticalLayout = new VerticalLayout();
        RadioButtonGroup<Integer> single = new RadioButtonGroup<>("Appreciate the driver");
        single.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
        //RadioButtonGroup.ValueChangeListener singleListener = new RadioButtonListener();
       single.addValueChangeListener(new RadioButtonListener());
        single.setItems(1,2,3,4,5);
        single.setIcon(VaadinIcons.STAR);
        driverMemo = new TextField("Leave your review about the driver");
        orderCost = new TextField("The cost of you trip is");
        orderCost.setValue(order.getCost().toString());
        orderCost.setReadOnly(true);
        Button ok = new Button("Ok", VaadinIcons.USER_CHECK);
        ok.setWidth(100, Unit.PIXELS);
        verticalLayout.addComponents(orderCost,single,driverMemo,ok);
        ok.addClickListener(event -> {
            clientService.sendDriverMemo(order,driverMemo.getValue());
            orderService.setDriverRating(order, single.getValue() != null ? single.getValue() : 4);
            //UI.getCurrent().setContent(toastr);
            driverMemo.clear();
            close();
           /* toastr.toast(ToastBuilder.success(
                    "Thank you!\nYour opinion is very important")
                    .build());*/
        });
        return verticalLayout;
    }

    class RadioButtonListener implements RadioButtonGroup.ValueChangeListener {
        @Override
        public void valueChange(HasValue.ValueChangeEvent valueChangeEvent) {
            Order order = orderService.getOrder(orderId);
            clientService.sendDriverRating(order,new BigInteger(valueChangeEvent.getValue().toString()));
        }
    }
}
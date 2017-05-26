package com.netcracker.project.study.vaadin.admin.views;


import com.netcracker.project.study.vaadin.admin.components.grids.DriversGrid;
import com.netcracker.project.study.vaadin.admin.components.grids.DriversRequestsGrid;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = DriversView.VIEW_NAME)
public class DriversView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "drivers";

    @Autowired DriversGrid driversGrid;

    @Autowired DriversRequestsGrid driversRequestsGrid;

    TabSheet tabSheet;

    @PostConstruct
    void init() {
        tabSheet = new TabSheet();
        setTabs();
        addComponent(tabSheet);
    }


    private VerticalLayout getAllDriversTab() {
        VerticalLayout controlLayout = new VerticalLayout();
        controlLayout.setMargin(true);
        controlLayout.setSpacing(false);
        controlLayout.addComponent(driversGrid);
        return controlLayout;
    }

    private VerticalLayout getDriversRequestsTab() {
        VerticalLayout controlLayout = new VerticalLayout();
        controlLayout.addComponent(driversRequestsGrid);
        return controlLayout;
    }

    public void setTabs() {
        tabSheet.addTab(getAllDriversTab(), "All drivers");
        tabSheet.addTab(getDriversRequestsTab(),
                "Drivers Requests(" + driversRequestsGrid.getDriversRequestsList().size() + ")");
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}

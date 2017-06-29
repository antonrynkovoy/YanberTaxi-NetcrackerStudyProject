package com.netcracker.project.study.vaadin.client.page;

import com.github.appreciated.material.MaterialTheme;
import com.netcracker.project.study.model.client.Client;
import com.netcracker.project.study.services.impl.UserDetailsServiceImpl;
import com.netcracker.project.study.vaadin.admin.components.logo.Copyright;
import com.netcracker.project.study.vaadin.client.events.RefreshClientViewEvent;
import com.netcracker.project.study.vaadin.client.popups.ClientUpdate;
import com.netcracker.project.study.vaadin.client.popups.DriverEvaluation;
import com.netcracker.project.study.vaadin.client.views.ClientView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.vaadin.addons.Toast;
import org.vaadin.addons.ToastPosition;
import org.vaadin.addons.ToastType;
import org.vaadin.addons.Toastr;
import org.vaadin.addons.builder.ToastBuilder;
import org.vaadin.addons.builder.ToastOptionsBuilder;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.ArrayList;

@Theme("valo")
@SpringUI(path = "/client")
@Title("YanberTaxi-Client")
public class ClientPage extends UI {

    @Autowired
    private SpringViewProvider provider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    ClientUpdate clientWindow;

    @Autowired
    Copyright bottomTeamLogo;


    private Client client;

    private Panel viewDisplay;


    public static Navigator navigator;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        client = userDetailsService.getCurrentUser();

        VerticalLayout rootLayout = getVerticalLayout();
        setContent(rootLayout);

        HorizontalLayout panelCaption = getPanelCaption();

        Label logo = new Label("<h3>YanberTaxi<h3>", ContentMode.HTML);
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setMargin(new MarginInfo(false, false, false, false));
        logoLayout.setSpacing(false);
        logoLayout.addComponent(logo);
        panelCaption.addComponent(logoLayout);
        panelCaption.setComponentAlignment(logoLayout, Alignment.MIDDLE_LEFT);
        panelCaption.setExpandRatio(logoLayout, 0.3f);

        HorizontalLayout clientPoints = getClientPoints();
        clientPoints.setMargin(new MarginInfo(false, false, false, false));
        panelCaption.addComponent(clientPoints);
        panelCaption.setComponentAlignment(clientPoints, Alignment.MIDDLE_CENTER);
        panelCaption.setExpandRatio(clientPoints, 1f);

        Label hello = getHelloLable();
        panelCaption.addComponent(hello);

        Button infoChanger = getInfoChangerButton();
        panelCaption.addComponent(infoChanger);

        Button logOutButton = getLogOutButton();
        panelCaption.addComponent(logOutButton);

        rootLayout.addComponent(panelCaption);

        viewDisplay = getViewDisplay();
        rootLayout.addComponent(viewDisplay);
        rootLayout.addComponent(bottomTeamLogo);
        rootLayout.setExpandRatio(viewDisplay, 0.8f);

        navigator = new Navigator(this, viewDisplay);
        navigator.addProvider(provider);
        navigator.navigateTo(ClientView.VIEW_NAME);


    }


    private VerticalLayout getVerticalLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.setMargin(false);
        verticalLayout.setSpacing(false);
        verticalLayout.setHeight(100, Unit.PERCENTAGE);
        return verticalLayout;
    }

    private HorizontalLayout getPanelCaption(){
        HorizontalLayout panelCaption = new HorizontalLayout();
        panelCaption.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        panelCaption.setStyleName(MaterialTheme.LAYOUT_CARD);
        panelCaption.setMargin(new MarginInfo(false, true, false, true));
        panelCaption.setWidth("100%");

        return panelCaption;
    }

    private Label getHelloLable(){
        String clientName = client.getFirstName() + " " + client.getLastName();
        Label hello = new Label( "Hello, " + clientName);

        return hello;
    }

    private Button getInfoChangerButton(){
        Button action = new Button();
        action.setIcon(VaadinIcons.PENCIL);
        action.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        action.addStyleName(ValoTheme.BUTTON_SMALL);
        action.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        action.addClickListener(clock -> {
            clientWindow.init(userDetailsService.getUser(), client);
            getUI().addWindow(clientWindow);
        });

        return action;
    }

    private Button getLogOutButton(){
        Button logOutButton = new Button("Logout");
        logOutButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        logOutButton.addStyleName(ValoTheme.BUTTON_SMALL);
        logOutButton.addClickListener(clickEvent -> {
            SecurityContextHolder.clearContext();
            getUI().getSession().close();
            getUI().getPage().setLocation("/authorization");
        });
        logOutButton.setIcon(VaadinIcons.EXIT);

        return logOutButton;
    }

    private Panel getViewDisplay() {
        Panel springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();
        return  springViewDisplay;
    }

    private Panel getStandartPanel() {
        Panel panel = new Panel("Astronomer Panel");
        panel.addStyleName("mypanelexample");
        panel.setSizeUndefined(); // Shrink to fit content
        return panel;
    }

    private HorizontalLayout getClientPoints(){
        HorizontalLayout clientPointslayout = new HorizontalLayout();
        BigInteger clientPoints = client.getPoints() != null ? client.getPoints() : BigInteger.ZERO;
        Label icon = new Label();
        icon.setIcon(VaadinIcons.COIN_PILES);
        Label pointsInfo = new Label("Your points: " + clientPoints);
        clientPointslayout.addComponents(icon, pointsInfo);
        clientPointslayout.setComponentAlignment(icon, Alignment.MIDDLE_CENTER);
        return clientPointslayout;
    }

}

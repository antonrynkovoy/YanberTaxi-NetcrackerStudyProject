package com.netcracker.project.study.vaadin.test.components;

import com.netcracker.project.study.vaadin.test.ui.DriversCreatePopUp;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@SpringComponent
public class HeaderMenu extends CustomComponent {



    public static final String DRIVERS_TAB = "Drivers";
    public static final String CLIENTS_TAB = "Clients";
    public static final String ORDERS_TAB = "Orders";


    private VerticalLayout layout;

    private VerticalLayout gridLayout;

    private MenuBar menuBar;

    @Autowired
    private DriversCreatePopUp driversCreatePopUp;

    @Autowired
    private ModelGrid modelGrid;

    public HeaderMenu() {
        layout = new VerticalLayout();
        gridLayout = new VerticalLayout();
        menuBar = new MenuBar();
        setSettings();
        initAdministration();
    }

    private void setSettings() {
        menuBar.setAutoOpen(true);
        menuBar.setStyleName(ValoTheme.MENUBAR_SMALL);
        menuBar.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
        layout.setSpacing(false);
        layout.setMargin(false);
        gridLayout.setSpacing(false);
        gridLayout.setMargin(false);
        layout.addComponents(menuBar, gridLayout);
        setCompositionRoot(layout);
    }

    private void initAdministration() {
        MenuBar.MenuItem miAdministration = menuBar.addItem("Control", null, null);
        miAdministration.addItem(DRIVERS_TAB, null, showModels);
        miAdministration.addItem(CLIENTS_TAB, null, showModels);
        miAdministration.addItem(ORDERS_TAB, null, showModels);
    }


    public MenuBar.Command showModels = new MenuBar.Command() {
        @Override
        public void menuSelected(MenuBar.MenuItem menuItem) {
            switch (menuItem.getText()) {
                case HeaderMenu.DRIVERS_TAB:
                    gridLayout.removeAllComponents();
                    PopupView popupView = new PopupView(null, driversCreatePopUp);
                    Button button = new Button("Add driver", click ->
                            popupView.setPopupVisible(true));
                    gridLayout.addComponents(modelGrid.getDriversGrid(), button, popupView);
                    Notification.show(HeaderMenu.DRIVERS_TAB);
                    break;
                case HeaderMenu.CLIENTS_TAB:
                    gridLayout.removeAllComponents();
                    gridLayout.addComponent(modelGrid.getClientsGrid());
                    Notification.show(HeaderMenu.CLIENTS_TAB);
                    break;
                case HeaderMenu.ORDERS_TAB:
                    gridLayout.removeAllComponents();
                    gridLayout.addComponent(modelGrid.getOrdersGrid());
                    Notification.show(HeaderMenu.ORDERS_TAB);
                    break;
            }
        }
    };
}
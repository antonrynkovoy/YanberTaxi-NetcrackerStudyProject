package com.netcracker.project.study.vaadin.admin.components.grids;


import com.netcracker.project.study.model.driver.Driver;
import com.netcracker.project.study.model.driver.DriverStatusEnum;
import com.netcracker.project.study.model.driver.DriverStatusList;
import com.netcracker.project.study.services.AdminService;
import com.netcracker.project.study.services.DriverService;
import com.netcracker.project.study.vaadin.admin.components.popup.DriverCreatePopUp;
import com.netcracker.project.study.vaadin.admin.components.popup.DriverInfoPopUP;
import com.netcracker.project.study.vaadin.admin.components.popup.DriverUpdatePopUp;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.Arrays;
import java.util.List;


@SpringComponent
@Scope(value = "prototype")
public class DriversGrid extends CustomComponent{

    @Autowired
    AdminService adminService;

    @Autowired
    DriverService driverService;

    @Autowired
    DriverCreatePopUp driverCreatePopUp;
    @Autowired
    DriverInfoPopUP driverInfoPopUp;
    @Autowired
    DriverUpdatePopUp driverUpdatePopUp;

    @Autowired
    DriversBanGrid driversBanGrid;
    @Autowired
    DriversRequestsGrid driversRequestsGrid;

    private Grid<Driver> driversGrid;

    private VerticalLayout componentLayout;

    private List<Driver> driversList;

    private Window createDriverWindow;

    private Window driverInfoWindow;

    private Window updateDriverWindow;

    private NativeSelect statusSelect;

    private HorizontalLayout filtersLayout;

    private TextField fieldFilter;

    public void init() {
        initFilters();
        driversGrid = generateDriversGrid();
        componentLayout = getFilledComponentLayout();
        //initCreateDriverWindow();
        initDriverInfoWindow();
        //initUpdateInfoWindow();
        setCompositionRoot(componentLayout);
    }

    private void initFilters(){
        filtersLayout = new HorizontalLayout();
        filtersLayout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
        initStatusChooser();
        filtersLayout.addComponent(statusSelect);
        initTextFieldFilter();
        filtersLayout.addComponent(fieldFilter);
    }

    private VerticalLayout getFilledComponentLayout() {
        VerticalLayout componentLayout = new VerticalLayout();
        componentLayout.setSizeFull();

        componentLayout.setMargin(false);
        componentLayout.setSpacing(false);
        HorizontalLayout controlButtons = getControlButtonsLayout();

        componentLayout.addComponent(filtersLayout);
        componentLayout.addComponent(driversGrid);
        componentLayout.addComponent(controlButtons);

        return componentLayout;
    }

    private void initTextFieldFilter() {
        fieldFilter = new TextField();
        fieldFilter.addValueChangeListener(this::onNameFilterTextChange);
        fieldFilter.setPlaceholder("Search by last name");
    }

    private void onNameFilterTextChange(HasValue.ValueChangeEvent<String> event) {
        ListDataProvider<Driver> dataProvider = (ListDataProvider<Driver>) driversGrid.getDataProvider();
        dataProvider.setFilter(Driver::getLastName, s -> caseInsensitiveContains(s, event.getValue()));
    }


    private Boolean caseInsensitiveContains(String where, String what) {
        if (where != null) {
            return where.toLowerCase().contains(what.toLowerCase());
        }
        return false;
    }


    private void initStatusChooser() {
        statusSelect = new NativeSelect("Status filter");
        List<String> statusList = Arrays.asList("All",
                DriverStatusEnum.getStatusValue(DriverStatusList.OFF_DUTY),
                DriverStatusEnum.getStatusValue(DriverStatusList.FREE),
                DriverStatusEnum.getStatusValue(DriverStatusList.ON_CALL),
                DriverStatusEnum.getStatusValue(DriverStatusList.PERFORMING_ORDER),
                DriverStatusEnum.getStatusValue(DriverStatusList.DISMISSED));
        statusSelect.setItems(statusList);

        statusSelect.setEmptySelectionAllowed(false);
        statusSelect.setSelectedItem(statusList.get(0));

        statusSelect.addValueChangeListener(new HasValue.ValueChangeListener() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent valueChangeEvent) {
                switch (valueChangeEvent.getValue().toString()) {
                    case "All":
                        driversGrid.setItems(driverService.getActiveDrivers());
                        break;
                    case "Off duty":
                        driversGrid.setItems(driverService.getDriversByStatusId(DriverStatusList.OFF_DUTY));
                        break;
                    case "Free":
                        driversGrid.setItems(driverService.getDriversByStatusId(DriverStatusList.FREE));
                        break;
                    case "On call":
                        driversGrid.setItems(driverService.getDriversByStatusId(DriverStatusList.ON_CALL));
                        break;
                    case "Performing order":
                        driversGrid.setItems(driverService.getDriversByStatusId(DriverStatusList.PERFORMING_ORDER));
                        break;
                    case "Dismissed":
                        driversGrid.setItems(driverService.getDriversByStatusId(DriverStatusList.DISMISSED));
                        break;
                }
            }
        });
    }

    private Grid<Driver> generateDriversGrid() {
        driversGrid = new Grid<>();
        driversGrid.setSizeFull();
        driversGrid.setHeightByRows(8.8);

        driversGrid.setStyleName(ValoTheme.TABLE_SMALL);
        driversGrid.setItems(driverService.getActiveDrivers());
        driversGrid.addColumn(Driver::getObjectId).setCaption("№");
        driversGrid.addColumn(Driver::getLastName).setCaption("Last name");
        driversGrid.addColumn(Driver::getFirstName).setCaption("First name");
        driversGrid.addColumn(Driver::getMiddleName).setCaption("Middle name");
        driversGrid.addColumn(Driver::getPhoneNumber).setCaption("Phone number");
        driversGrid.addColumn(Driver::getEmail).setCaption("Email");
        driversGrid.addColumn(driver -> DriverStatusEnum.getStatusValue(driver.getStatus())).setCaption("Status");
        driversGrid.addColumn(Driver::getRating).setCaption("Rating");
        driversGrid.addColumn(Driver::getHireDate).setCaption("Hire date");
        driversGrid.addColumn(Driver::getExperience).setCaption("Exp");
        return driversGrid;
    }


    /*private void initCreateDriverWindow() {
        createDriverWindow = new Window("Add new driver");
        createDriverWindow.setIcon(VaadinIcons.INSERT);
        createDriverWindow.center();
        createDriverWindow.setModal(true);
        createDriverWindow.setResizable(false);
        createDriverWindow.setContent(driverCreatePopUp);
    }*/

    private void initDriverInfoWindow() {
        driverInfoWindow = new Window("Driver information");
        driverInfoWindow.setIcon(VaadinIcons.INFO);
        driverInfoWindow.center();
        driverInfoWindow.setModal(true);
        driverInfoWindow.setResizable(false);
        driverInfoWindow.setContent(driverInfoPopUp);
    }

    /*private void initUpdateInfoWindow() {
        updateDriverWindow = new Window("Update driver");
        updateDriverWindow.setIcon(VaadinIcons.UPLOAD);
        updateDriverWindow.center();
        updateDriverWindow.setModal(true);
        updateDriverWindow.setResizable(false);
        updateDriverWindow.setContent(driverUpdatePopUp);
    }*/

    public Window getDriversCreateSubWindow() {
        return createDriverWindow;
    }

    public Window getUpdateDriverWindow() {
        return updateDriverWindow;
    }

    private HorizontalLayout getControlButtonsLayout() {
        HorizontalLayout controlButtonsLayout = new HorizontalLayout();
        controlButtonsLayout.setMargin(false);
        controlButtonsLayout.setSpacing(false);

        /*Button btnAddDriver = new Button("Add driver", VaadinIcons.PLUS);
        controlButtonsLayout.addComponent(btnAddDriver);
        controlButtonsLayout.setComponentAlignment(btnAddDriver, Alignment.BOTTOM_LEFT);
        btnAddDriver.addClickListener(event -> {
            driverCreatePopUp.setDriversGrid(this);
            UI.getCurrent().addWindow(createDriverWindow);
        });*/


       /* Button btnDeleteDriver = new Button("Delete driver", VaadinIcons.FILE_REMOVE);
        controlButtonsLayout.addComponent(btnDeleteDriver);
        controlButtonsLayout.setComponentAlignment(btnDeleteDriver, Alignment.BOTTOM_LEFT);
        btnDeleteDriver.addClickListener(event -> {
            Driver driver = driversGrid.asSingleSelect().getValue();
            String firstName = driver.getFirstName();
            String lastName = driver.getLastName();
            MessageBox
                    .createQuestion()
                    .withCaption("Delete")
                    .withMessage("Are you want to delete " + firstName + " " + lastName + "?")
                    .withYesButton(() -> {
                        adminService.deleteModel(driver);
                    refreshGrid();
                    })
                    .withNoButton(() -> {})
                    .open();
        });*/


       /* Button btnUpdateDriver = new Button("Update driver", VaadinIcons.UPLOAD);
        controlButtonsLayout.addComponent(btnUpdateDriver);
        controlButtonsLayout.setComponentAlignment(btnUpdateDriver, Alignment.BOTTOM_LEFT);
        btnUpdateDriver.addClickListener(event -> {
            if(!driversGrid.asSingleSelect().isEmpty() ) {
                Driver driver = driversGrid.asSingleSelect().getValue();
                driverUpdatePopUp.setDriversGrid(this);
                driverUpdatePopUp.init(driver);
                UI.getCurrent().addWindow(updateDriverWindow);
                updateDriverWindow.center();
            }
        });*/


        Button btnDriverInfo = new Button("Driver info", VaadinIcons.INFO);
        controlButtonsLayout.addComponent(btnDriverInfo);
        controlButtonsLayout.setComponentAlignment(btnDriverInfo, Alignment.BOTTOM_RIGHT);
        btnDriverInfo.addClickListener(event ->{
            if(!driversGrid.asSingleSelect().isEmpty() ) {
                Driver driver = adminService.getModelById(driversGrid.asSingleSelect().getValue().getObjectId(), Driver.class);
                driverInfoPopUp.setDriversGrid(this);
                driverInfoPopUp.setDriversBanGrid(driversBanGrid);
                driverInfoPopUp.init(driver);
                UI.getCurrent().addWindow(driverInfoWindow);
            }
        });

        return controlButtonsLayout;
    }

    public void refreshGrid(){
        if (statusSelect.getValue().toString().equals("All")) {
            driversGrid.setItems(driverService.getActiveDrivers());
        }else if (statusSelect.getValue().toString().equals("Off duty")) {
            driversGrid.setItems(driverService.getDriversByStatusId(DriverStatusList.OFF_DUTY));
        }else if (statusSelect.getValue().toString().equals("Free")) {
            driversGrid.setItems(driverService.getDriversByStatusId(DriverStatusList.FREE));
        }else if (statusSelect.getValue().toString().equals("On call")) {
            driversGrid.setItems(driverService.getDriversByStatusId(DriverStatusList.ON_CALL));
        }else if (statusSelect.getValue().toString().equals("Performing order")) {
            driversGrid.setItems(driverService.getDriversByStatusId(DriverStatusList.PERFORMING_ORDER));
        }
        //driversGrid.setItems(driversList);
    }

    public Grid<Driver> getDriversGrid() {
        return driversGrid;
    }

    public List<Driver> getApprovedDriversList() {
        return driversList;
    }

    public void setDriversList(List<Driver> driversList) {
        this.driversList = driversList;
    }

    public Window getDriverInfoWindow() {
        return driverInfoWindow;
    }

    public DriversBanGrid getDriversBanGrid() {
        return driversBanGrid;
    }

    public DriversGrid getDriverssGrid() {
        return this;
    }

    public NativeSelect getStatusSelect() {
        return statusSelect;
    }
}

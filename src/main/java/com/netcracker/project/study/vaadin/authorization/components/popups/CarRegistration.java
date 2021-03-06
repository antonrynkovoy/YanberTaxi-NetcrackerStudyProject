package com.netcracker.project.study.vaadin.authorization.components.popups;

import com.google.common.collect.ImmutableList;
import com.netcracker.project.study.model.Role;
import com.netcracker.project.study.model.driver.Driver;
import com.netcracker.project.study.model.driver.car.Car;
import com.netcracker.project.study.model.user.User;
import com.netcracker.project.study.persistence.facade.UserFacade;
import com.netcracker.project.study.persistence.facade.impl.PersistenceFacade;
import com.netcracker.project.study.services.DriverService;
import com.netcracker.project.study.services.impl.UserDetailsServiceImpl;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vaadin.addons.Toastr;
import org.vaadin.addons.builder.ToastBuilder;
import org.vaadin.spring.events.EventBus;

import java.math.BigInteger;
import java.util.ArrayList;


@SpringComponent
@Scope(value = "prototype")
public class CarRegistration extends Window {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    PersistenceFacade persistenceFacade;

    @Autowired
    UserFacade userFacade;

    @Autowired
    DriverService driverService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private Window driverRegWindow;
    private DriverRegistration driverBackWindow;

    private Driver driver;
    private String password;

    private VerticalLayout root;
    private HorizontalLayout carsLayout;
    private Toastr toastr;
    private ArrayList<TextField> names;
    private ArrayList<TextField> models;
    private ArrayList<TextField> stateNumbers;
    private ArrayList<ComboBox<Integer>> prodDates;
    private ArrayList<ComboBox<Integer>> seatsCounts;
    private ArrayList<CheckBox> childSeats;
    private ArrayList<Component> carsComponent;
    private ArrayList<Car> cars;
    private ArrayList<Integer> yearsDate;
    private ArrayList<Integer> seats;

    public void init() {
        genFieldsArray();
        setWindowSettings();
        carsComponent.add(genFields());
        root = genRootLayout();
        carsLayout = genCarsLayout();
        carsLayout.addComponent(carsComponent.get(0));
        root.addComponent(carsLayout);
        //root.addComponent(genButtons());
        toastr = new Toastr();
        root.addComponent(toastr);
        setContent(root);
    }

    public void initDriverRegWindow(Window window, DriverRegistration driverRegWindow) {
        this.driverRegWindow = window;
        this.driverBackWindow = driverRegWindow;
    }


    private VerticalLayout genRootLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidth(590, Unit.PIXELS);
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);
        return verticalLayout;
    }

    private HorizontalLayout genCarsLayout() {
        carsLayout = new HorizontalLayout();
        carsLayout.setSizeFull();
        carsLayout.setMargin(false);
        carsLayout.setSpacing(false);
        return carsLayout;
    }

    private void setWindowSettings() {
        center();
        setIcon(VaadinIcons.TAXI);
        setModal(true);
        setResizable(false);
        setCaption(" Car registration");
    }

    private void genFieldsArray() {
        names = new ArrayList<>();
        models = new ArrayList<>();
        stateNumbers = new ArrayList<>();
        prodDates = new ArrayList<>();
        seatsCounts = new ArrayList<>();
        childSeats = new ArrayList<>();
        carsComponent = new ArrayList<>();
        cars = new ArrayList<>();
        yearsDate = new ArrayList<>();
        seats = new ArrayList<>();
        for (int i = 2000; i < 2018; i++) {
            yearsDate.add(i);
        }
        for (int i = 2; i < 17; i++) {
            seats.add(i);
        }
    }

    private Component genFields() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout left = new VerticalLayout();
        VerticalLayout right = new VerticalLayout();
        horizontalLayout.addComponents(left, right);
        names.add(new TextField("Name of car"));
        models.add(new TextField("Model"));
        stateNumbers.add(new TextField("State number"));
        ComboBox<Integer> prodYear = new ComboBox<>("Production year");
        prodYear.setItems(yearsDate);
        prodDates.add(prodYear);
        ComboBox<Integer> seatsCountsCb = new ComboBox<>("Seats count");
        seatsCountsCb.setItems(seats);
        seatsCounts.add(seatsCountsCb);
        childSeats.add(new CheckBox("Child seat"));
        int lastCar = models.size() - 1;
       /* verticalLayout.addComponents(
                names.get(lastCar), models.get(lastCar),
                stateNumbers.get(lastCar), prodDates.get(lastCar),
                seatsCounts.get(lastCar), childSeats.get(lastCar));*/
        Button ok = new Button("Ok", VaadinIcons.USER_CHECK);
        ok.setWidth(114, Unit.PIXELS);
        Button prev = new Button("Cancel", VaadinIcons.EXIT);
        prev.setWidth(114, Unit.PIXELS);

        left.addComponents(names.get(lastCar), models.get(lastCar), stateNumbers.get(lastCar), prev);
        right.addComponents(prodDates.get(lastCar), seatsCounts.get(lastCar),  new Label(), childSeats.get(lastCar), ok);
        right.setComponentAlignment(ok, Alignment.MIDDLE_RIGHT);

        ok.addClickListener(event -> {

            for (int i = 0; i < carsLayout.getComponentCount(); i++) {
                if (names.get(i).isEmpty()
                        || models.get(i).isEmpty()
                        || stateNumbers.get(i).isEmpty()
                        || prodDates.get(i).isEmpty()
                        || seatsCounts.get(i).isEmpty()) {
                    toastr.toast(ToastBuilder.error("All fields must be filled!").build());
                    return;
                } else {
                    persistenceFacade.create(driver);
                    Driver tempDriver = driverService.getDriverByPhoneNumber(driver.getPhoneNumber());
                    User user = new User();
                    user.setObjectId(tempDriver.getObjectId());
                    user.setUsername(tempDriver.getPhoneNumber());
                    user.setPassword(passwordEncoder.encode(password));
                    user.setEnabled(true);
                    user.setAuthorities(ImmutableList.of(Role.ROLE_DRIVER));
                    userFacade.createUser(user);
                    Car car = new Car();
                    car.setDriverId(tempDriver.getObjectId());
                    car.setMakeOfCar(names.get(i).getValue());
                    car.setModelType(models.get(i).getValue());
                    car.setStateNumber(stateNumbers.get(i).getValue());
                    car.setReleaseDate(java.sql.Date.valueOf(prodDates.get(i).getValue() + "-01-01"));
                    car.setSeatsCount(new BigInteger(String.valueOf(seatsCounts.get(i).getValue())));
                    car.setChildSeat(childSeats.get(i).getValue() ? true : false);
                    cars.add(car);
                }
            }
            for (int i = 0; i < cars.size(); i++) {
                persistenceFacade.create(cars.get(i));
            }

            UI.getCurrent().setContent(toastr);
            driverRegWindow.close();
            driverBackWindow.init();
            toastr.toast(ToastBuilder.success(
                    "You are successfully registered!\nAdministrator will contact you by email.")
                    .build());
        });

        prev.addClickListener(event -> {
            names.clear();
            models.clear();
            stateNumbers.clear();
            prodDates.clear();
            stateNumbers.clear();
            driverBackWindow.init();
            driverRegWindow.close();
            //close();
            //UI.getCurrent().getPage().setLocation("/authorization");
        });

        addCloseListener(e -> {
           prev.click();
        });

        return horizontalLayout;
    }

   /* private HorizontalLayout genButtons() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        Button ok = new Button("Ok", VaadinIcons.USER_CHECK);
        ok.setWidth(114, Unit.PIXELS);
        Button prev = new Button("Cancel", VaadinIcons.EXIT);
        prev.setWidth(114, Unit.PIXELS);
        Button add = new Button("Add car", VaadinIcons.PLUS);
        prev.setWidth(114, Unit.PIXELS);
        Button del = new Button("Delete car", VaadinIcons.MINUS);
        prev.setWidth(114, Unit.PIXELS);
        horizontalLayout.addComponents(prev, del, add, ok);
        horizontalLayout.setExpandRatio(prev, 0.8f);
        ok.addClickListener(event -> {
            for (int i = 0; i < carsLayout.getComponentCount(); i++) {
                if (names.get(i).isEmpty()
                        || models.get(i).isEmpty()
                        || stateNumbers.get(i).isEmpty()
                        || prodDates.get(i).isEmpty()
                        || seatsCounts.get(i).isEmpty()) {
                    toastr.toast(ToastBuilder.error("All fields must be filled!").build());
                    return;
                } else {
                    Car car = new Car();
                    car.setDriverId(driver.getObjectId());
                    car.setMakeOfCar(names.get(i).getValue());
                    car.setModelType(models.get(i).getValue());
                    car.setStateNumber(stateNumbers.get(i).getValue());
                    car.setReleaseDate(java.sql.Date.valueOf(prodDates.get(i).getValue() + "-01-01"));
                    car.setSeatsCount(new BigInteger(String.valueOf(seatsCounts.get(i).getValue())));
                    car.setChildSeat(childSeats.get(i).getValue() ? true : false);
                    cars.add(car);
                }
            }
            for (int i = 0; i < cars.size(); i++) {
                persistenceFacade.create(cars.get(i));
            }
            User user = new User();
            user.setObjectId(driver.getObjectId());
            user.setUsername(driver.getPhoneNumber());
            user.setPassword(passwordEncoder.encode(password));
            user.setEnabled(true);
            user.setAuthorities(ImmutableList.of(Role.ROLE_DRIVER));
            userFacade.createUser(user);

            UI.getCurrent().setContent(toastr);
            driverRegWindow.close();
            toastr.toast(ToastBuilder.success(
                    "You are successfully registered!\nAdministrator will contact you by email.")
                    .build());
        });

        add.addClickListener(event -> {
            if (carsComponent.size() == 2) {
                toastr.toast(ToastBuilder.warning("You cannot add more than two cars!").build());
                return;
            }
            carsComponent.add(genFields());
            carsLayout.addComponent(carsComponent.get(carsComponent.size() - 1));
        });

        del.addClickListener(event -> {
            if (carsComponent.size() == 1) {
                toastr.toast(ToastBuilder.warning("You must have one car at least!").build());
                return;
            }
            if (cars.size() > 1) {
                cars.remove(cars.size() - 1);
            }
            carsLayout.removeComponent(carsComponent.get(carsComponent.size() - 1));
            //cars.remove(cars.size()-1);
            carsComponent.remove(carsComponent.get(carsComponent.size() - 1));
            names.remove(names.size() - 1);
            models.remove(models.size() - 1);
            stateNumbers.remove(stateNumbers.size() - 1);
            prodDates.remove(prodDates.size() - 1);
            seatsCounts.remove(seatsCounts.size() - 1);
        });

        prev.addClickListener(event -> {
            names.clear();
            models.clear();
            stateNumbers.clear();
            prodDates.clear();
            stateNumbers.clear();
            driverRegWindow.close();
            UI.getCurrent().getPage().setLocation("/authorization");
        });

        addCloseListener(e -> {
            for (int i = 0; i < carsLayout.getComponentCount(); i++) {
                names.get(i).clear();
                models.get(i).clear();
                stateNumbers.get(i).clear();
                prodDates.get(i).clear();
                seatsCounts.get(i).clear();
            }
            driverRegWindow.close();
            //setContent(null);
        });
        return horizontalLayout;
    }
*/
    public void setDriverAndPassword(Driver driver, String password) {
        this.driver = driver;
        this.password = password;
    }
}

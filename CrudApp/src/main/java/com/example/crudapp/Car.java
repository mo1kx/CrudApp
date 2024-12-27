package com.example.crudapp;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Car {
    private final IntegerProperty id;
    private final StringProperty brand;
    private final StringProperty model;
    private final IntegerProperty year;
    private final DoubleProperty price;
    private final StringProperty created;
    private final StringProperty updated;

    // Конструктор
    public Car(int id, String brand, String model, int year, double price) {
        this.id = new SimpleIntegerProperty(id);
        this.brand = new SimpleStringProperty(brand);
        this.model = new SimpleStringProperty(model);
        this.year = new SimpleIntegerProperty(year);
        this.price = new SimpleDoubleProperty(price);
        this.created = new SimpleStringProperty(getCurrentDateTime());
        this.updated = new SimpleStringProperty(getCurrentDateTime());
    }

    // Геттеры и сеттеры
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getBrand() {
        return brand.get();
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
        updateTimestamp();
    }

    public StringProperty brandProperty() {
        return brand;
    }

    public String getModel() {
        return model.get();
    }

    public void setModel(String model) {
        this.model.set(model);
        updateTimestamp();
    }

    public StringProperty modelProperty() {
        return model;
    }

    public int getYear() {
        return year.get();
    }

    public void setYear(int year) {
        this.year.set(year);
        updateTimestamp();
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
        updateTimestamp();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public String getCreated() {
        return created.get();
    }

    public StringProperty createdProperty() {
        return created;  // Возвращаем строковое свойство created
    }

    public String getUpdated() {
        return updated.get();
    }

    public StringProperty updatedProperty() {
        return updated;  // Возвращаем строковое свойство updated
    }

    // Метод для получения текущего времени в формате строки
    private String getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    // Метод для обновления времени последнего обновления
    private void updateTimestamp() {
        this.updated.set(getCurrentDateTime());
    }
}

package com.example.crudapp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private TableView<Car> table;
    private ObservableList<Car> carList;

    public static void main(String[] args) {
        CarDatabase.initializeDatabase();  // Инициализация базы данных
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        table = new TableView<>();
        carList = FXCollections.observableArrayList(CarDatabase.getAllCars());

        TableColumn<Car, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(cellData -> cellData.getValue().brandProperty());

        TableColumn<Car, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());

        TableColumn<Car, Integer> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());

        TableColumn<Car, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        table.getColumns().addAll(brandColumn, modelColumn, yearColumn, priceColumn);
        table.setItems(carList);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> showAddDialog());

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> showEditDialog());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteCar());

        VBox vbox = new VBox(table, addButton, editButton, deleteButton);
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Car Management");
        primaryStage.show();
    }

    // Диалог для добавления нового автомобиля
    private void showAddDialog() {
        Dialog<Car> dialog = new Dialog<>();
        dialog.setTitle("Add New Car");

        // Кнопка подтверждения
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Создаем поля ввода
        TextField brandField = new TextField();
        brandField.setPromptText("Brand");

        TextField modelField = new TextField();
        modelField.setPromptText("Model");

        TextField yearField = new TextField();
        yearField.setPromptText("Year");

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        // Добавляем поля в форму
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(new Label("Brand:"), brandField, new Label("Model:"), modelField,
                new Label("Year:"), yearField, new Label("Price:"), priceField);
        dialog.getDialogPane().setContent(vbox);

        // Обрабатываем нажатие кнопки "Add"
        dialog.setResultConverter(button -> {
            if (button == addButtonType) {
                String brand = brandField.getText();
                String model = modelField.getText();
                int year = Integer.parseInt(yearField.getText());
                double price = Double.parseDouble(priceField.getText());
                Car newCar = new Car(0, brand, model, year, price);
                CarDatabase.addCar(newCar);  // Добавление в базу данных
                carList.add(newCar);  // Обновление списка
                return newCar;
            }
            return null;
        });

        dialog.showAndWait();
    }

    // Диалог для редактирования автомобиля
    private void showEditDialog() {
        Car selectedCar = table.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            Dialog<Car> dialog = new Dialog<>();
            dialog.setTitle("Edit Car");

            // Кнопка подтверждения
            ButtonType editButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

            // Создаем поля ввода с текущими значениями
            TextField brandField = new TextField(selectedCar.getBrand());
            brandField.setPromptText("Brand");

            TextField modelField = new TextField(selectedCar.getModel());
            modelField.setPromptText("Model");

            TextField yearField = new TextField(String.valueOf(selectedCar.getYear()));
            yearField.setPromptText("Year");

            TextField priceField = new TextField(String.valueOf(selectedCar.getPrice()));
            priceField.setPromptText("Price");

            // Добавляем поля в форму
            VBox vbox = new VBox(10);
            vbox.getChildren().addAll(new Label("Brand:"), brandField, new Label("Model:"), modelField,
                    new Label("Year:"), yearField, new Label("Price:"), priceField);
            dialog.getDialogPane().setContent(vbox);

            // Обрабатываем нажатие кнопки "Edit"
            dialog.setResultConverter(button -> {
                if (button == editButtonType) {
                    selectedCar.setBrand(brandField.getText());
                    selectedCar.setModel(modelField.getText());
                    selectedCar.setYear(Integer.parseInt(yearField.getText()));
                    selectedCar.setPrice(Double.parseDouble(priceField.getText()));

                    CarDatabase.updateCar(selectedCar);  // Обновление в базе данных
                    table.refresh();  // Обновление таблицы
                    return selectedCar;
                }
                return null;
            });

            dialog.showAndWait();
        }
    }

    // Удаление автомобиля
    private void deleteCar() {
        Car selectedCar = table.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            CarDatabase.deleteCar(selectedCar.getId());
            carList.remove(selectedCar);
        }
    }
}

package com.example.crudapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CarController {

    // Связь с полем поиска
    @FXML
    private TextField searchField;

    // Связь с таблицей
    @FXML
    private TableView<Car> tableView;

    // Связь с кнопкой "Add"
    @FXML
    private Button addButton;

    // Список автомобилей
    private ObservableList<Car> carList;

    // Метод, вызываемый при старте приложения
    @FXML
    public void initialize() {
        // Получаем все автомобили из базы данных
        carList = FXCollections.observableArrayList(CarDatabase.getAllCars());

        // Настроим таблицу
        TableColumn<Car, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<Car, String> colBrand = new TableColumn<>("Brand");
        colBrand.setCellValueFactory(cellData -> cellData.getValue().brandProperty());

        TableColumn<Car, String> colModel = new TableColumn<>("Model");
        colModel.setCellValueFactory(cellData -> cellData.getValue().modelProperty());

        TableColumn<Car, Integer> colYear = new TableColumn<>("Year");
        colYear.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());

        TableColumn<Car, Double> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        TableColumn<Car, String> colCreated = new TableColumn<>("Created");
        colCreated.setCellValueFactory(cellData -> cellData.getValue().createdProperty());

        TableColumn<Car, String> colUpdated = new TableColumn<>("Updated");
        colUpdated.setCellValueFactory(cellData -> cellData.getValue().updatedProperty());

        // Добавляем колонки в таблицу
        tableView.getColumns().addAll(colId, colBrand, colModel, colYear, colPrice, colCreated, colUpdated);

        // Устанавливаем список автомобилей в таблицу
        tableView.setItems(carList);
    }

    // Метод, вызываемый при нажатии на кнопку "Add"
    @FXML
    public void addCar() {
        // Добавляем автомобиль с текущими значениями
        Car newCar = new Car(5, "Ford", "Mustang", 2025, 55000.00);
        CarDatabase.addCar(newCar);  // Добавляем автомобиль в базу данных
        carList.add(newCar);  // Добавляем автомобиль в таблицу

        // Оповещаем пользователя
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Car added successfully!");
        alert.showAndWait();
    }

    // Метод для удаления выбранного автомобиля
    @FXML
    public void deleteCar() {
        Car selectedCar = tableView.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            CarDatabase.deleteCar(selectedCar.getId());  // Удаляем автомобиль из базы данных
            carList.remove(selectedCar);  // Удаляем автомобиль из таблицы
        }
    }

    // Метод для поиска автомобиля
    @FXML
    public void searchCar() {
        String searchText = searchField.getText().toLowerCase();
        ObservableList<Car> filteredCars = FXCollections.observableArrayList();
        for (Car car : CarDatabase.getAllCars()) {
            if (car.getBrand().toLowerCase().contains(searchText) || car.getModel().toLowerCase().contains(searchText)) {
                filteredCars.add(car);
            }
        }
        tableView.setItems(filteredCars);
    }

    // Метод для редактирования выбранного автомобиля
    @FXML
    public void editCar(ActionEvent actionEvent) {
        Car selectedCar = tableView.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            // Открываем диалог для редактирования
            TextInputDialog dialog = new TextInputDialog(selectedCar.getBrand());
            dialog.setTitle("Edit Car");
            dialog.setHeaderText("Edit details of the selected car.");
            dialog.setContentText("Brand:");

            dialog.showAndWait().ifPresent(newBrand -> {
                selectedCar.setBrand(newBrand);
                CarDatabase.updateCar(selectedCar);  // Обновляем данные в базе данных
                tableView.refresh();  // Обновляем таблицу
                showConfirmationAlert("Car updated successfully!");
            });
        } else {
            showErrorAlert("No car selected!", "Please select a car to edit.");
        }
    }

    private void showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}

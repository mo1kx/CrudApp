package com.example.crudapp;

import java.util.ArrayList;
import java.util.List;

public class CarDatabase {
    // Список для хранения автомобилей
    private static List<Car> cars = new ArrayList<>();

    // Инициализация базы данных с несколькими автомобилями
    public static void initializeDatabase() {
        // Добавляем несколько автомобилей при запуске приложения
        cars.add(new Car(1, "Tesla", "Model S", 2024, 79999.99));
        cars.add(new Car(2, "BMW", "X5", 2023, 59999.99));
        cars.add(new Car(3, "Audi", "A4", 2022, 39999.99));
        cars.add(new Car(4, "Mercedes", "C-Class", 2021, 45000.00));
    }

    // Получение всех автомобилей
    public static List<Car> getAllCars() {
        return cars;
    }

    // Метод для добавления автомобиля в базу данных
    public static void addCar(Car car) {
        cars.add(car);
    }

    // Метод для удаления автомобиля по ID
    public static void deleteCar(int id) {
        cars.removeIf(car -> car.getId() == id);
    }

    // Метод для обновления информации об автомобиле
    public static void updateCar(Car updatedCar) {
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            if (car.getId() == updatedCar.getId()) {
                // Обновляем информацию о выбранном автомобиле
                cars.set(i, updatedCar);
                break;
            }
        }
    }
}

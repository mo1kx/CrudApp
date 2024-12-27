module com.example.crudapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.base; // Обязательно для правильной работы базовых компонентов JavaFX

    // Другие зависимости
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    // Открытие пакета для FXML
    opens com.example.crudapp to javafx.fxml;

    // Экспорт пакета для использования в других модулях
    exports com.example.crudapp;
}

module com.example.workshopoop {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.finalproject to javafx.fxml;
    exports com.example.finalproject;

    opens com.example.finalproject.controllers to javafx.fxml;
    exports com.example.finalproject.controllers;
}
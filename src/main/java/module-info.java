module com.example.workshopoop {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive java.sql;


    opens com.example.finalproject to javafx.fxml;
    exports com.example.finalproject;
}
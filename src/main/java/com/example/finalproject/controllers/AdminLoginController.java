package com.example.finalproject.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLoginController {

    @FXML
    public void goBackToMain(ActionEvent event) {
        // Routes the user back to the Main Portal
        switchScene(event, "/com/example/finalproject/views/user/MainPortal.fxml");
    }

    // This method handles the "Login" button
    @FXML
    public void handleLogin(ActionEvent event) {
        // Placeholder for future database authentication logic - JDBC will be used to connect to the database and verify credentials
        
        System.out.println("Login button clicked! Awaiting database integration...");
        // For now, we'll just show an alert to confirm the button works
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("System Notice");
        alert.setHeaderText("Authentication Placeholder");
        alert.setContentText("The frontend button works! Once the database is connected, this will verify the password and route to the Admin Dashboard.");
        alert.showAndWait();
    }

    private void switchScene(ActionEvent event, String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("CRITICAL ERROR: Could not load the FXML file at " + fxmlPath);
            e.printStackTrace();
        }
    }
}
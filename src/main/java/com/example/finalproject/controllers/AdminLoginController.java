package com.example.finalproject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * Controller for AdminLogin.fxml
 *
 * Required fx:id additions in AdminLogin.fxml:
 *   PasswordField  → fx:id="passwordField"
 */
public class AdminLoginController {

    // ── FXML bindings ─────────────────────────────────────────────────────────
    @FXML private PasswordField passwordField;

    // ── Constants ─────────────────────────────────────────────────────────────
    /** Replace with a hashed comparison in production; never hard-code plaintext. */
    private static final String ADMIN_PASSWORD = "admin123";

    // ── Action handlers ───────────────────────────────────────────────────────

    /** Validates the password and opens the Admin Dashboard on success. */
    @FXML
    private void handleLogin() {
        String entered = passwordField.getText();

        if (entered == null || entered.isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Password cannot be empty.");
            return;
        }

        if (entered.equals(ADMIN_PASSWORD)) {
            navigateTo("/com/example/finalproject/views/AdminDashboardSidebar.fxml",
                       "Admin Dashboard");
        } else {
            showAlert(Alert.AlertType.ERROR, "Access Denied", "Incorrect password. Please try again.");
            passwordField.clear();
        }
    }

    /** Closes the login window and returns to the main application screen. */
    @FXML
    private void goBackToMain() {
        navigateTo("/com/example/finalproject/views/Main.fxml", "FindIT");
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) passwordField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error",
                      "Could not load the next screen:\n" + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

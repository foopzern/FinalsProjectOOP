package com.example.findit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class UserDashboardController {

    // --- 1. THE PAGE TRACKER & BUTTON LINKS ---
    // This variable keeps track of which page is currently active, allowing us to apply dynamic styling to the sidebar buttons. By default, we start on the "Dashboard" page.
    private static String activePage = "Dashboard";

    // These @FXML annotations link the Java code to the corresponding buttons defined in the FXML file. They allow us to manipulate the button styles and handle click events.
    // Link the sidebar buttons from the FXML
    @FXML private Button btnNavDashboard;
    @FXML private Button btnNavItems;
    @FXML private Button btnNavClaims;
    @FXML private Button btnNavHelp;

    // Link the Images from the FXML
    @FXML private ImageView imgNavDashboard;
    @FXML private ImageView imgNavItems;
    @FXML private ImageView imgNavClaims;
    @FXML private ImageView imgNavHelp;

    // The initialize() method is called by JavaFX after the FXML elements are loaded, making it the perfect place to apply our dynamic styling logic
    @FXML
    public void initialize() {
        if (btnNavDashboard != null) {
            resetSidebarStyles();
            highlightActiveTab();
        }
    }
    // This method resets all sidebar buttons to the default "inactive" style
    private void resetSidebarStyles() {
        String defaultStyle = "-fx-background-color: transparent; -fx-text-fill: #FFFFFF; -fx-cursor: hand; -fx-border-color: transparent;";
        btnNavDashboard.setStyle(defaultStyle);
        btnNavItems.setStyle(defaultStyle);
        btnNavClaims.setStyle(defaultStyle);
        btnNavHelp.setStyle(defaultStyle);


        imgNavDashboard.setImage(new Image(getClass().getResourceAsStream("/com/example/findit/assets/dashboard.png")));
        imgNavItems.setImage(new Image(getClass().getResourceAsStream("/com/example/findit/assets/items.png")));
        imgNavClaims.setImage(new Image(getClass().getResourceAsStream("/com/example/findit/assets/report.png"))); 
        imgNavHelp.setImage(new Image(getClass().getResourceAsStream("/com/example/findit/assets/help.png")));
    }

    private void highlightActiveTab() {
        // The yellow border CSS
        String activeStyle = "-fx-background-color: transparent; -fx-text-fill: #FFCC00; -fx-cursor: hand; -fx-border-color: transparent transparent transparent #FFCC00; -fx-border-width: 0 0 0 4; -fx-padding: 0 0 0 4;";

        // Apply the CSS and swap to the YELLOW icon for the active page
        switch (activePage) {
            case "Dashboard":
                btnNavDashboard.setStyle(activeStyle);
                imgNavDashboard.setImage(new Image(getClass().getResourceAsStream("/com/example/findit/assets/yellow_icons/dashboard.png")));
                break;
            case "Items":
                btnNavItems.setStyle(activeStyle);
                imgNavItems.setImage(new Image(getClass().getResourceAsStream("/com/example/findit/assets/yellow_icons/items.png")));
                break;
            case "Claims":
                btnNavClaims.setStyle(activeStyle);
                imgNavClaims.setImage(new Image(getClass().getResourceAsStream("/com/example/findit/assets/yellow_icons/report.png")));
                break;
            case "Help":
                btnNavHelp.setStyle(activeStyle);
                imgNavHelp.setImage(new Image(getClass().getResourceAsStream("/com/example/findit/assets/yellow_icons/help.png")));
                break;
        }
    }

    // --- 3. SIDEBAR NAVIGATION METHODS ---
    @FXML
    public void goToDashboard(ActionEvent event) {
        activePage = "Dashboard";
        switchScene(event, "/com/example/findit/views/user/Dashboard.fxml");
    }

    @FXML
    public void goToItems(ActionEvent event) {
        activePage = "Items";
        System.out.println("Navigating to Items Gallery...");
        switchScene(event, "/com/example/findit/views/user/Items.fxml");
    }

    @FXML
    public void goToClaims(ActionEvent event) {
        activePage = "Claims"; 
        System.out.println("Navigating to Claims page...");
        switchScene(event, "/com/example/findit/views/user/Claims.fxml");
    }

    @FXML
    public void goToHelpPage(ActionEvent event) {
        activePage = "Help"; 
        switchScene(event, "/com/example/findit/views/user/Help.fxml");
    }

    // --- 4. CENTER DASHBOARD METHODS ---
    @FXML
    public void handleReportLost(ActionEvent event) {
        System.out.println("Opening Report Lost Form...");
        switchScene(event, "/com/example/findit/views/user/LostForm.fxml");
    }

    @FXML
    public void handleReportFound(ActionEvent event) {
        System.out.println("Opening Report Found Form...");
        switchScene(event, "/com/example/findit/views/user/FoundForm.fxml");
    }

    @FXML
    public void handleFilter(ActionEvent event) {
        System.out.println("Filter dropdown clicked!");
    }

    // --- 5. REUSABLE SCENE SWITCHER ---
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
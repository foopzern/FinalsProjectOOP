package com.example.findit.controllers.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for AdminDashboard.fxml
 *
 * Required fx:id additions in AdminDashboard.fxml:
 *   TableView        → fx:id="recentItemsTable"
 *   TableColumn IMAGE     → fx:id="colImage"
 *   TableColumn ITEM      → fx:id="colItem"
 *   TableColumn CATEGORY  → fx:id="colCategory"
 *   TableColumn LOCATION  → fx:id="colLocation"
 *   TableColumn STATUS    → fx:id="colStatus"
 *   TableColumn DATE      → fx:id="colDate"
 */
public class AdminDashboardController implements Initializable {

    // ── FXML bindings ─────────────────────────────────────────────────────────
    @FXML private TableView<ItemRow>      recentItemsTable;
    @FXML private TableColumn<ItemRow, String> colImage;
    @FXML private TableColumn<ItemRow, String> colItem;
    @FXML private TableColumn<ItemRow, String> colCategory;
    @FXML private TableColumn<ItemRow, String> colLocation;
    @FXML private TableColumn<ItemRow, String> colStatus;
    @FXML private TableColumn<ItemRow, String> colDate;

    // ── Lifecycle ─────────────────────────────────────────────────────────────
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableColumns();
        loadRecentItems();
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private void configureTableColumns() {
        colImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        colItem.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadRecentItems() {
        // TODO: Replace with real data-source / repository call
        ObservableList<ItemRow> data = FXCollections.observableArrayList(
                new ItemRow("", "Sample Lost Item",  "Electronics", "Library",    "Lost",  "2025-01-10"),
                new ItemRow("", "Sample Found Item", "Wallet",      "Cafeteria",  "Found", "2025-01-11")
        );
        recentItemsTable.setItems(data);
    }

    // ── Navigation (sidebar icon actions) ─────────────────────────────────────

    @FXML
    private void goToDashboard() {
        navigateTo("/com/example/finalproject/views/AdminDashboardSidebar.fxml", "Dashboard");
    }

    @FXML
    private void goToReportedItems() {
        navigateTo("/com/example/finalproject/views/ReportedItems.fxml", "Reported Items");
    }

    @FXML
    private void goToClaims() {
        navigateTo("/com/example/finalproject/views/Claims.fxml", "Claims");
    }

    @FXML
    private void goToMatchSuggestions() {
        navigateTo("/com/example/finalproject/views/MatchSuggestionPanel.fxml", "Match Suggestions");
    }

    @FXML
    private void handleLogout() {
        navigateTo("/com/example/finalproject/views/AdminLogin.fxml", "Admin Login");
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) recentItemsTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ── Inner model class ─────────────────────────────────────────────────────

    /** Simple row model for the Recent Items table. */
    public static class ItemRow {
        private final String image;
        private final String itemName;
        private final String category;
        private final String location;
        private final String status;
        private final String date;

        public ItemRow(String image, String itemName, String category,
                       String location, String status, String date) {
            this.image    = image;
            this.itemName = itemName;
            this.category = category;
            this.location = location;
            this.status   = status;
            this.date     = date;
        }

        public String getImage()    { return image; }
        public String getItemName() { return itemName; }
        public String getCategory() { return category; }
        public String getLocation() { return location; }
        public String getStatus()   { return status; }
        public String getDate()     { return date; }
    }
}

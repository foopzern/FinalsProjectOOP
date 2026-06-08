package com.example.findit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for ReportedItems.fxml
 *
 * Required fx:id additions in ReportedItems.fxml:
 *   TextField              → fx:id="searchField"
 *   ComboBox               → fx:id="typeFilter"
 *   TableView              → fx:id="itemsTable"
 *   TableColumn Type       → fx:id="colType"
 *   TableColumn Item Name  → fx:id="colItemName"
 *   TableColumn Category   → fx:id="colCategory"
 *   TableColumn Date       → fx:id="colDate"
 *   TableColumn Reported by→ fx:id="colReportedBy"
 *   TableColumn Location   → fx:id="colLocation"
 *   TableColumn Action     → fx:id="colAction"
 *
 *   Sidebar button onAction bindings:
 *     Button Dashboard         → onAction="#goToDashboard"
 *     Button Reported Items    → onAction="#goToReportedItems"
 *     Button Claims            → onAction="#goToClaims"
 *     Button Match Suggestions → onAction="#goToMatchSuggestions"
 *     Button Logout            → onAction="#handleLogout"
 */
public class ReportedItemsController implements Initializable {

    // ── FXML bindings ─────────────────────────────────────────────────────────
    @FXML private TextField  searchField;
    @FXML private ComboBox<String> typeFilter;

    @FXML private TableView<ReportedItem>           itemsTable;
    @FXML private TableColumn<ReportedItem, String> colType;
    @FXML private TableColumn<ReportedItem, String> colItemName;
    @FXML private TableColumn<ReportedItem, String> colCategory;
    @FXML private TableColumn<ReportedItem, String> colDate;
    @FXML private TableColumn<ReportedItem, String> colReportedBy;
    @FXML private TableColumn<ReportedItem, String> colLocation;
    @FXML private TableColumn<ReportedItem, String> colAction;

    // ── Data ──────────────────────────────────────────────────────────────────
    private final ObservableList<ReportedItem> masterData = FXCollections.observableArrayList();
    private FilteredList<ReportedItem> filteredData;

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTypeFilter();
        configureTableColumns();
        loadItems();
        wireSearchAndFilter();
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private void configureTypeFilter() {
        typeFilter.setItems(FXCollections.observableArrayList("All", "Lost", "Found"));
        typeFilter.getSelectionModel().selectFirst();
    }

    private void configureTableColumns() {
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colReportedBy.setCellValueFactory(new PropertyValueFactory<>("reportedBy"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

        // Action column: "View" button per row
        colAction.setCellFactory(col -> new TableCell<>() {
            private final Button viewBtn = new Button("View");
            {
                viewBtn.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-background-radius: 5;");
                viewBtn.setOnAction(e -> {
                    ReportedItem item = getTableView().getItems().get(getIndex());
                    handleViewItem(item);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : viewBtn);
            }
        });
    }

    private void loadItems() {
        // TODO: Replace with actual data-source call
        masterData.addAll(
                new ReportedItem("Lost",  "Laptop",      "Electronics", "2025-01-08", "Juan dela Cruz", "Library"),
                new ReportedItem("Found", "Black Wallet", "Wallet",      "2025-01-09", "Maria Santos",   "Cafeteria"),
                new ReportedItem("Lost",  "ID Card",     "Document",    "2025-01-10", "Jose Reyes",     "Gym")
        );
    }

    private void wireSearchAndFilter() {
        filteredData = new FilteredList<>(masterData, p -> true);
        itemsTable.setItems(filteredData);

        // Search listener
        searchField.textProperty().addListener((obs, oldVal, newVal) -> applyFilter());

        // Type filter listener
        typeFilter.valueProperty().addListener((obs, oldVal, newVal) -> applyFilter());
    }

    private void applyFilter() {
        String search    = searchField.getText().toLowerCase().trim();
        String typeValue = typeFilter.getValue();

        filteredData.setPredicate(item -> {
            boolean matchesSearch = search.isEmpty()
                    || item.getItemName().toLowerCase().contains(search)
                    || item.getCategory().toLowerCase().contains(search)
                    || item.getLocation().toLowerCase().contains(search)
                    || item.getReportedBy().toLowerCase().contains(search);

            boolean matchesType = "All".equals(typeValue)
                    || item.getType().equalsIgnoreCase(typeValue);

            return matchesSearch && matchesType;
        });
    }

    private void handleViewItem(ReportedItem item) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Item Details");
        alert.setHeaderText(item.getItemName());
        alert.setContentText(
                "Type: "        + item.getType()       + "\n" +
                "Category: "    + item.getCategory()   + "\n" +
                "Date: "        + item.getDate()        + "\n" +
                "Reported By: " + item.getReportedBy() + "\n" +
                "Location: "    + item.getLocation()
        );
        alert.showAndWait();
    }

    // ── Navigation ────────────────────────────────────────────────────────────

    @FXML private void goToDashboard()       { navigateTo("/com/example/finalproject/views/AdminDashboardSidebar.fxml", "Dashboard"); }
    @FXML private void goToReportedItems()   { /* already here */ }
    @FXML private void goToClaims()          { navigateTo("/com/example/finalproject/views/Claims.fxml", "Claims"); }
    @FXML private void goToMatchSuggestions(){ navigateTo("/com/example/finalproject/views/MatchSuggestionPanel.fxml", "Match Suggestions"); }
    @FXML private void handleLogout()        { navigateTo("/com/example/finalproject/views/AdminLogin.fxml", "Admin Login"); }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) itemsTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ── Inner model class ─────────────────────────────────────────────────────

    public static class ReportedItem {
        private final String type;
        private final String itemName;
        private final String category;
        private final String date;
        private final String reportedBy;
        private final String location;

        public ReportedItem(String type, String itemName, String category,
                            String date, String reportedBy, String location) {
            this.type       = type;
            this.itemName   = itemName;
            this.category   = category;
            this.date       = date;
            this.reportedBy = reportedBy;
            this.location   = location;
        }

        public String getType()       { return type; }
        public String getItemName()   { return itemName; }
        public String getCategory()   { return category; }
        public String getDate()       { return date; }
        public String getReportedBy() { return reportedBy; }
        public String getLocation()   { return location; }
    }
}

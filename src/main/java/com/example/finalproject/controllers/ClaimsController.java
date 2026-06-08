package com.example.finalproject.controllers;

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
 * Controller for Claims.fxml
 *
 * Required fx:id additions in Claims.fxml:
 *   TextField              → fx:id="searchField"
 *   ComboBox               → fx:id="statusFilter"
 *   TableView              → fx:id="claimsTable"
 *   TableColumn Type       → fx:id="colType"
 *   TableColumn Item Name  → fx:id="colItemName"
 *   TableColumn Category   → fx:id="colCategory"
 *   TableColumn Date       → fx:id="colDate"
 *   TableColumn Reported by→ fx:id="colReportedBy"
 *   TableColumn Location   → fx:id="colLocation"
 *   TableColumn Action     → fx:id="colAction"
 *
 *   Sidebar button onAction bindings (same as other views):
 *     Dashboard → onAction="#goToDashboard"  etc.
 */
public class ClaimsController implements Initializable {

    // ── FXML bindings ─────────────────────────────────────────────────────────
    @FXML private TextField        searchField;
    @FXML private ComboBox<String> statusFilter;

    @FXML private TableView<ClaimRow>           claimsTable;
    @FXML private TableColumn<ClaimRow, String> colType;
    @FXML private TableColumn<ClaimRow, String> colItemName;
    @FXML private TableColumn<ClaimRow, String> colCategory;
    @FXML private TableColumn<ClaimRow, String> colDate;
    @FXML private TableColumn<ClaimRow, String> colReportedBy;
    @FXML private TableColumn<ClaimRow, String> colLocation;
    @FXML private TableColumn<ClaimRow, String> colAction;

    // ── Data ──────────────────────────────────────────────────────────────────
    private final ObservableList<ClaimRow> masterData = FXCollections.observableArrayList();
    private FilteredList<ClaimRow> filteredData;

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureStatusFilter();
        configureTableColumns();
        loadClaims();
        wireSearchAndFilter();
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private void configureStatusFilter() {
        statusFilter.setItems(FXCollections.observableArrayList(
                "All Status", "Pending", "Approved", "Rejected"));
        statusFilter.getSelectionModel().selectFirst();
    }

    private void configureTableColumns() {
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colReportedBy.setCellValueFactory(new PropertyValueFactory<>("reportedBy"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

        // Action column: Approve / Reject buttons
        colAction.setCellFactory(col -> new TableCell<>() {
            private final Button approveBtn = new Button("Approve");
            private final Button rejectBtn  = new Button("Reject");

            {
                approveBtn.setStyle("-fx-background-color: #2E7D32; -fx-text-fill: white; -fx-background-radius: 5;");
                rejectBtn.setStyle("-fx-background-color: #C62828; -fx-text-fill: white; -fx-background-radius: 5;");

                approveBtn.setOnAction(e -> handleApprove(getTableView().getItems().get(getIndex())));
                rejectBtn.setOnAction(e  -> handleReject(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    javafx.scene.layout.HBox box = new javafx.scene.layout.HBox(5, approveBtn, rejectBtn);
                    setGraphic(box);
                }
            }
        });
    }

    private void loadClaims() {
        // TODO: Replace with actual data-source/service call
        masterData.addAll(
                new ClaimRow("Lost",  "Laptop",       "Electronics", "2025-01-08", "Juan dela Cruz", "Library",   "Pending"),
                new ClaimRow("Found", "Black Wallet",  "Wallet",      "2025-01-09", "Maria Santos",   "Cafeteria", "Pending"),
                new ClaimRow("Lost",  "ID Card",      "Document",    "2025-01-10", "Jose Reyes",     "Gym",       "Approved")
        );
    }

    private void wireSearchAndFilter() {
        filteredData = new FilteredList<>(masterData, p -> true);
        claimsTable.setItems(filteredData);

        searchField.textProperty().addListener((obs, o, n) -> applyFilter());
        statusFilter.valueProperty().addListener((obs, o, n) -> applyFilter());
    }

    private void applyFilter() {
        String search      = searchField.getText().toLowerCase().trim();
        String statusValue = statusFilter.getValue();

        filteredData.setPredicate(row -> {
            boolean matchesSearch = search.isEmpty()
                    || row.getItemName().toLowerCase().contains(search)
                    || row.getCategory().toLowerCase().contains(search)
                    || row.getLocation().toLowerCase().contains(search)
                    || row.getReportedBy().toLowerCase().contains(search);

            boolean matchesStatus = "All Status".equals(statusValue)
                    || row.getClaimStatus().equalsIgnoreCase(statusValue);

            return matchesSearch && matchesStatus;
        });
    }

    private void handleApprove(ClaimRow row) {
        row.setClaimStatus("Approved");
        claimsTable.refresh();
        showAlert(Alert.AlertType.INFORMATION, "Claim Approved",
                  "The claim for \"" + row.getItemName() + "\" has been approved.");
    }

    private void handleReject(ClaimRow row) {
        row.setClaimStatus("Rejected");
        claimsTable.refresh();
        showAlert(Alert.AlertType.WARNING, "Claim Rejected",
                  "The claim for \"" + row.getItemName() + "\" has been rejected.");
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    // ── Navigation ────────────────────────────────────────────────────────────

    @FXML private void goToDashboard()       { navigateTo("/com/example/finalproject/views/AdminDashboardSidebar.fxml", "Dashboard"); }
    @FXML private void goToReportedItems()   { navigateTo("/com/example/finalproject/views/ReportedItems.fxml", "Reported Items"); }
    @FXML private void goToClaims()          { /* already here */ }
    @FXML private void goToMatchSuggestions(){ navigateTo("/com/example/finalproject/views/MatchSuggestionPanel.fxml", "Match Suggestions"); }
    @FXML private void handleLogout()        { navigateTo("/com/example/finalproject/views/AdminLogin.fxml", "Admin Login"); }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) claimsTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ── Inner model class ─────────────────────────────────────────────────────

    public static class ClaimRow {
        private final String type;
        private final String itemName;
        private final String category;
        private final String date;
        private final String reportedBy;
        private final String location;
        private String claimStatus;

        public ClaimRow(String type, String itemName, String category,
                        String date, String reportedBy, String location, String claimStatus) {
            this.type        = type;
            this.itemName    = itemName;
            this.category    = category;
            this.date        = date;
            this.reportedBy  = reportedBy;
            this.location    = location;
            this.claimStatus = claimStatus;
        }

        public String getType()        { return type; }
        public String getItemName()    { return itemName; }
        public String getCategory()    { return category; }
        public String getDate()        { return date; }
        public String getReportedBy()  { return reportedBy; }
        public String getLocation()    { return location; }
        public String getClaimStatus() { return claimStatus; }
        public void   setClaimStatus(String s) { this.claimStatus = s; }
    }
}

package com.example.findit.controllers.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.util.ResourceBundle;

public class ClaimsController implements Initializable {

    // INJECTS THE SIDEBAR CONTROLLER!
    @FXML private AdminSidebarController sidebarController;

    @FXML private TextField searchField;
    @FXML private ComboBox<String> statusFilter; // FIXED: Renamed to statusFilter

    @FXML private TableView<ClaimRow> claimsTable; // FIXED: Renamed to claimsTable
    @FXML private TableColumn<ClaimRow, String> colType, colItemName, colCategory, colDate, colReportedBy, colLocation, colAction;

    private final ObservableList<ClaimRow> masterData = FXCollections.observableArrayList();
    private FilteredList<ClaimRow> filteredData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // FIXED: Tells the sidebar to highlight the CLAIMS button!
        if (sidebarController != null) { sidebarController.setActiveTab("Claims"); }
        
        // FIXED: Claims use Status filters, not Lost/Found filters
        statusFilter.setItems(FXCollections.observableArrayList("All Status", "Pending", "Approved", "Rejected"));
        statusFilter.getSelectionModel().selectFirst();
        
        configureTableColumns();
        loadItems();
        wireSearchAndFilter();
    }

    private void configureTableColumns() {
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colReportedBy.setCellValueFactory(new PropertyValueFactory<>("reportedBy"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        
        // FIXED: Restored the Approve/Reject Buttons for the Action Column!
        colAction.setCellFactory(col -> new TableCell<>() {
            private final Button approveBtn = new Button("Approve");
            private final Button rejectBtn  = new Button("Reject");

            {
                approveBtn.setStyle("-fx-background-color: #2E7D32; -fx-text-fill: white; -fx-background-radius: 5; -fx-cursor: hand;");
                rejectBtn.setStyle("-fx-background-color: #C62828; -fx-text-fill: white; -fx-background-radius: 5; -fx-cursor: hand;");

                approveBtn.setOnAction(e -> handleApprove(getTableView().getItems().get(getIndex())));
                rejectBtn.setOnAction(e  -> handleReject(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox box = new HBox(5, approveBtn, rejectBtn);
                    setGraphic(box);
                }
            }
        });
    }

    private void loadItems() {
        // FIXED: Using ClaimRow instead of ReportedItem so it has a "Status" field
        masterData.addAll(
            new ClaimRow("Lost", "Laptop", "Electronics", "2025-01-08", "Juan dela Cruz", "Library", "Pending"),
            new ClaimRow("Found", "Black Wallet", "Wallet", "2025-01-09", "Maria Santos", "Cafeteria", "Pending")
        );
    }

    private void wireSearchAndFilter() {
        filteredData = new FilteredList<>(masterData, p -> true);
        claimsTable.setItems(filteredData);

        searchField.textProperty().addListener((obs, o, n) -> applyFilter());
        statusFilter.valueProperty().addListener((obs, o, n) -> applyFilter());
    }

    private void applyFilter() {
        String search = searchField.getText().toLowerCase().trim();
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

    // Button actions
    private void handleApprove(ClaimRow row) {
        row.setClaimStatus("Approved");
        claimsTable.refresh();
    }

    private void handleReject(ClaimRow row) {
        row.setClaimStatus("Rejected");
        claimsTable.refresh();
    }

    // FIXED: Custom model to support Claim Status
    public static class ClaimRow {
        private final String type, itemName, category, date, reportedBy, location;
        private String claimStatus;

        public ClaimRow(String type, String itemName, String category, String date, String reportedBy, String location, String claimStatus) {
            this.type = type; this.itemName = itemName; this.category = category;
            this.date = date; this.reportedBy = reportedBy; this.location = location;
            this.claimStatus = claimStatus;
        }

        public String getType() { return type; } public String getItemName() { return itemName; }
        public String getCategory() { return category; } public String getDate() { return date; }
        public String getReportedBy() { return reportedBy; } public String getLocation() { return location; }
        public String getClaimStatus() { return claimStatus; }
        public void setClaimStatus(String s) { this.claimStatus = s; }
    }
}
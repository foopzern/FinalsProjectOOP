package com.example.findit.controllers.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportedItemsController implements Initializable {

    // INJECTS THE SIDEBAR CONTROLLER!
    @FXML private AdminSidebarController sidebarController;

    @FXML private TextField searchField;
    @FXML private ComboBox<String> typeFilter;

    @FXML private TableView<ReportedItem> itemsTable;
    @FXML private TableColumn<ReportedItem, String> colType, colItemName, colCategory, colDate, colReportedBy, colLocation, colAction;

    private final ObservableList<ReportedItem> masterData = FXCollections.observableArrayList();
    private FilteredList<ReportedItem> filteredData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Tells the sidebar to highlight the Reported button!
        if (sidebarController != null) { sidebarController.setActiveTab("Reported"); }
        
        typeFilter.setItems(FXCollections.observableArrayList("All", "Lost", "Found"));
        typeFilter.getSelectionModel().selectFirst();
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
        // (Action column logic remains unchanged)
    }

    private void loadItems() {
        masterData.addAll(new ReportedItem("Lost", "Laptop", "Electronics", "2025-01-08", "Juan dela Cruz", "Library"));
    }

    private void wireSearchAndFilter() {
        filteredData = new FilteredList<>(masterData, p -> true);
        itemsTable.setItems(filteredData);
    }

    public static class ReportedItem {
        private final String type, itemName, category, date, reportedBy, location;
        public ReportedItem(String typ, String itm, String cat, String dt, String rep, String loc) {
            type = typ; itemName = itm; category = cat; date = dt; reportedBy = rep; location = loc;
        }
        public String getType() { return type; } public String getItemName() { return itemName; }
        public String getCategory() { return category; } public String getDate() { return date; }
        public String getReportedBy() { return reportedBy; } public String getLocation() { return location; }
    }
}
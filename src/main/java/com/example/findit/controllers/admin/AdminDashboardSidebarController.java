package com.example.findit.controllers.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for AdminDashboardSidebar.fxml
 *
 * Required fx:id additions in AdminDashboardSidebar.fxml:
 *
 *   Stat card labels:
 *     Label "0" (Found Items)   → fx:id="foundItemsCount"
 *     Label "0" (Lost Reports)  → fx:id="lostReportsCount"
 *     Label "0" (Matched)       → fx:id="matchedCount"
 *
 *   Category progress bars & item-count labels:
 *     ProgressBar Electronics   → fx:id="pbElectronics"
 *     Label "0 items" (Elec)    → fx:id="lblElectronics"
 *     ProgressBar Wallet        → fx:id="pbWallet"
 *     Label "0 items" (Wallet)  → fx:id="lblWallet"
 *     ProgressBar Document      → fx:id="pbDocument"
 *     Label "0 items" (Doc)     → fx:id="lblDocument"
 *
 *   Recent Items table & columns:
 *     TableView                 → fx:id="recentItemsTable"
 *     TableColumn IMAGE         → fx:id="colImage"
 *     TableColumn ITEM          → fx:id="colItem"
 *     TableColumn CATEGORY      → fx:id="colCategory"
 *     TableColumn LOCATION      → fx:id="colLocation"
 *     TableColumn STATUS        → fx:id="colStatus"
 *     TableColumn DATE          → fx:id="colDate"
 *
 *   Sidebar navigation buttons:
 *     Button "Dashboard"        → onAction="#goToDashboard"
 *     Button "Reported Items"   → onAction="#goToReportedItems"
 *     Button "Claims"           → onAction="#goToClaims"
 *     Button "Match Suggestions"→ onAction="#goToMatchSuggestions"
 *     Button "Logout"           → onAction="#handleLogout"
 */
public class AdminDashboardSidebarController implements Initializable {

    // ── FXML bindings ─────────────────────────────────────────────────────────

    // Stat cards
    @FXML private Label foundItemsCount;
    @FXML private Label lostReportsCount;
    @FXML private Label matchedCount;

    // Category breakdown
    @FXML private ProgressBar pbElectronics;
    @FXML private Label       lblElectronics;
    @FXML private ProgressBar pbWallet;
    @FXML private Label       lblWallet;
    @FXML private ProgressBar pbDocument;
    @FXML private Label       lblDocument;

    // Recent items table
    @FXML private TableView<AdminDashboardController.ItemRow>           recentItemsTable;
    @FXML private TableColumn<AdminDashboardController.ItemRow, String> colImage;
    @FXML private TableColumn<AdminDashboardController.ItemRow, String> colItem;
    @FXML private TableColumn<AdminDashboardController.ItemRow, String> colCategory;
    @FXML private TableColumn<AdminDashboardController.ItemRow, String> colLocation;
    @FXML private TableColumn<AdminDashboardController.ItemRow, String> colStatus;
    @FXML private TableColumn<AdminDashboardController.ItemRow, String> colDate;

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableColumns();
        refreshDashboard();
    }

    // ── Public API (call after loading to push live data) ─────────────────────

    /** Refresh all dashboard widgets with latest counts from the data layer. */
    public void refreshDashboard() {
        // TODO: Replace with actual repository/service calls
        int found    = 0;
        int lost     = 0;
        int matched  = 0;
        int elecCount = 0, walletCount = 0, docCount = 0;
        int total = Math.max(found + lost, 1); // avoid divide-by-zero

        foundItemsCount.setText(String.valueOf(found));
        lostReportsCount.setText(String.valueOf(lost));
        matchedCount.setText(String.valueOf(matched));

        pbElectronics.setProgress((double) elecCount   / total);
        pbWallet.setProgress((double) walletCount / total);
        pbDocument.setProgress((double) docCount   / total);

        lblElectronics.setText(elecCount   + " items");
        lblWallet.setText(walletCount + " items");
        lblDocument.setText(docCount   + " items");

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
        // TODO: Replace with real data-source call
        ObservableList<AdminDashboardController.ItemRow> data =
                FXCollections.observableArrayList(
                        new AdminDashboardController.ItemRow(
                                "", "Sample Lost Item", "Electronics", "Library", "Lost", "2025-01-10"),
                        new AdminDashboardController.ItemRow(
                                "", "Sample Found Item", "Wallet", "Cafeteria", "Found", "2025-01-11")
                );
        recentItemsTable.setItems(data);
    }

    // ── Navigation ────────────────────────────────────────────────────────────

    @FXML
    private void goToDashboard() {
        // Already on dashboard; optionally refresh
        refreshDashboard();
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
}

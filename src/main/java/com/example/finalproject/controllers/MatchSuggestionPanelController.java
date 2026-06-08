package com.example.finalproject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for MatchSuggestionPanel.fxml
 *
 * Required fx:id additions in MatchSuggestionPanel.fxml:
 *   TextField              → fx:id="searchField"
 *   ComboBox               → fx:id="statusFilter"
 *   VBox (cards container) → fx:id="matchCardsContainer"  (the VBox inside ScrollPane)
 *   Button "View Match Details" on each card → onAction="#openMatchDetail"
 *     (for dynamic cards, wire via code in loadMatches())
 *
 *   Sidebar button onAction bindings:
 *     Dashboard → onAction="#goToDashboard"  etc.
 */
public class MatchSuggestionPanelController implements Initializable {

    // ── FXML bindings ─────────────────────────────────────────────────────────
    @FXML private TextField        searchField;
    @FXML private ComboBox<String> statusFilter;
    @FXML private VBox             matchCardsContainer;

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusFilter.setItems(FXCollections.observableArrayList("All Status", "Pending", "Confirmed", "Rejected"));
        statusFilter.getSelectionModel().selectFirst();

        searchField.textProperty().addListener((obs, o, n) -> applyFilter());
        statusFilter.valueProperty().addListener((obs, o, n) -> applyFilter());

        loadMatches();
    }

    // ── Data loading ──────────────────────────────────────────────────────────

    /**
     * Loads match suggestion cards into the scroll pane container.
     * Replace the sample data with real match objects from your service/repository.
     *
     * Each card's "View Match Details" button calls openMatchDetail(matchId).
     */
    private void loadMatches() {
        // TODO: fetch MatchSuggestion list from service and build cards dynamically.
        // The FXML already contains one static card as a design preview;
        // in production you would clear matchCardsContainer.getChildren()
        // and repopulate it here.
    }

    private void applyFilter() {
        String search = searchField.getText().toLowerCase().trim();
        String status = statusFilter.getValue();

        // TODO: filter matchCardsContainer.getChildren() based on search/status
        //       once cards are populated dynamically.
    }

    // ── Action handlers ───────────────────────────────────────────────────────

    /**
     * Opens the MatchSuggestion detail overlay for the given match.
     * Called from each card's "View Match Details" button.
     *
     * @param matchId the ID of the match to display
     */
    public void openMatchDetail(int matchId) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/finalproject/views/MatchSuggestion.fxml"));
            Parent overlay = loader.load();

            MatchSuggestionController ctrl = loader.getController();
            ctrl.loadMatch(matchId);

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(matchCardsContainer.getScene().getWindow());
            dialog.setTitle("Match Details");
            dialog.setScene(new Scene(overlay));
            dialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ── Navigation ────────────────────────────────────────────────────────────

    @FXML private void goToDashboard()       { navigateTo("/com/example/finalproject/views/AdminDashboardSidebar.fxml", "Dashboard"); }
    @FXML private void goToReportedItems()   { navigateTo("/com/example/finalproject/views/ReportedItems.fxml", "Reported Items"); }
    @FXML private void goToClaims()          { navigateTo("/com/example/finalproject/views/Claims.fxml", "Claims"); }
    @FXML private void goToMatchSuggestions(){ /* already here */ }
    @FXML private void handleLogout()        { navigateTo("/com/example/finalproject/views/AdminLogin.fxml", "Admin Login"); }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) matchCardsContainer.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

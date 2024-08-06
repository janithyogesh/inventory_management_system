package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StockChangeController {

    @FXML
    private TextField productIdField;
    @FXML
    private ListView<String> productIdListView;
    @FXML
    private Label statusLabel;

    private ObservableList<String> productIds;
    private Connection connect;

    public void initialize() {
        productIds = FXCollections.observableArrayList();
        productIdListView.setItems(productIds);
        connect = database.connectDb();
    }

    @FXML
    private void handleAddProductId() {
        String productId = productIdField.getText().trim();
        if (!productId.isEmpty() && !productIds.contains(productId)) {
            productIds.add(productId);
            productIdField.clear();
        }
    }

    @FXML
    private void handleRemoveSelectedProductId() {
        String selectedProductId = productIdListView.getSelectionModel().getSelectedItem();
        if (selectedProductId != null) {
            productIds.remove(selectedProductId);
        }
    }

    @FXML
    private void handleClearAllProductIds() {
        productIds.clear();
    }

    @FXML
    private void handleUpdateStatus() {
        if (productIds.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "No product IDs to update");
            return;
        }

        try {
            connect.setAutoCommit(false);  // Start transaction

            ObservableList<String> notFoundIds = FXCollections.observableArrayList();
            ObservableList<String> alreadyShowroomIds = FXCollections.observableArrayList();
            boolean canUpdate = true;

            for (String productId : productIds) {
                int checkStatus = checkProductStatus(productId);
                if (checkStatus == 0) {
                    notFoundIds.add(productId);
                    canUpdate = false;
                } else if (checkStatus == -1) {
                    alreadyShowroomIds.add(productId);
                    canUpdate = false;
                }
            }

            if (canUpdate) {
                for (String productId : productIds) {
                    updateProductStatus(productId);
                }
                connect.commit();
                showAlert(Alert.AlertType.INFORMATION, "Success", "All product statuses updated to showroom");
                productIds.clear();
            } else {
                connect.rollback();
                StringBuilder alertMessage = new StringBuilder();
                if (!notFoundIds.isEmpty()) {
                    alertMessage.append("The following product IDs were not found: ").append(notFoundIds.toString()).append("\n");
                }
                if (!alreadyShowroomIds.isEmpty()) {
                    alertMessage.append("The following product IDs are already in the showroom: ").append(alreadyShowroomIds.toString());
                }
                showAlert(Alert.AlertType.WARNING, "Warning", alertMessage.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                connect.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            showAlert(Alert.AlertType.ERROR, "Error", "Error updating product statuses");
        } finally {
            try {
                connect.setAutoCommit(true);  // Restore auto-commit mode
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private int checkProductStatus(String productId) throws SQLException {
        String[] tables = {"chain_details", "bracelet_details", "ring_details", "earring_details", "pendant_details", "sura_details", "pancha_details", "necklace_details", "bangle_details"};
        for (String table : tables) {
            String query = "SELECT status FROM " + table + " WHERE product_id = ?";
            try (PreparedStatement pstmt = connect.prepareStatement(query)) {
                pstmt.setString(1, productId);
                ResultSet result = pstmt.executeQuery();
                if (result.next()) {
                    String status = result.getString("status");
                    if ("showroom".equalsIgnoreCase(status)) {
                        return -1;  // Already in showroom
                    }
                    return 1;  // Found in stock
                }
            }
        }
        return 0;  // Not found
    }

    private void updateProductStatus(String productId) throws SQLException {
        String[] tables = {"chain_details", "bracelet_details", "ring_details", "earring_details", "pendant_details", "sura_details", "pancha_details", "necklace_details", "bangle_details"};
        for (String table : tables) {
            String query = "UPDATE " + table + " SET status = 'showroom' WHERE product_id = ? AND status = 'stock'";
            try (PreparedStatement pstmt = connect.prepareStatement(query)) {
                pstmt.setString(1, productId);
                pstmt.executeUpdate();
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

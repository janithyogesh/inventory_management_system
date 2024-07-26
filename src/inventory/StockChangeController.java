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
            ObservableList<String> notFoundIds = FXCollections.observableArrayList();
            ObservableList<String> alreadyShowroomIds = FXCollections.observableArrayList();
            boolean allUpdated = true;
            for (String productId : productIds) {
                int updateStatus = updateProductStatus(productId);
                if (updateStatus == 0) {
                    notFoundIds.add(productId);
                    allUpdated = false;
                } else if (updateStatus == -1) {
                    alreadyShowroomIds.add(productId);
                    allUpdated = false;
                }
            }

            if (allUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "All product statuses updated to showroom");
                productIds.clear();
            } else {
                StringBuilder alertMessage = new StringBuilder();
                if (!notFoundIds.isEmpty()) {
                    alertMessage.append("The following product IDs were not found: ").append(notFoundIds.toString()).append("\n");
                }
                if (!alreadyShowroomIds.isEmpty()) {
                    alertMessage.append("The following product IDs are already in the showroom: ").append(alreadyShowroomIds.toString());
                }
                showAlert(Alert.AlertType.WARNING, "Warning", alertMessage.toString());
                productIds.removeAll(notFoundIds);
                productIds.removeAll(alreadyShowroomIds);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error updating product statuses");
        }
    }

    private int updateProductStatus(String productId) throws Exception {
        String checkChainSql = "SELECT status FROM chain_details WHERE product_id = ?";
        String checkBraceletSql = "SELECT status FROM bracelet_details WHERE product_id = ?";
        String updateChainSql = "UPDATE chain_details SET status = 'showroom' WHERE product_id = ? AND status = 'stock'";
        String updateBraceletSql = "UPDATE bracelet_details SET status = 'showroom' WHERE product_id = ? AND status = 'stock'";

        try (PreparedStatement checkChainStmt = connect.prepareStatement(checkChainSql);
             PreparedStatement checkBraceletStmt = connect.prepareStatement(checkBraceletSql);
             PreparedStatement updateChainStmt = connect.prepareStatement(updateChainSql);
             PreparedStatement updateBraceletStmt = connect.prepareStatement(updateBraceletSql)) {

            checkChainStmt.setString(1, productId);
            checkBraceletStmt.setString(1, productId);

            ResultSet chainResult = checkChainStmt.executeQuery();
            ResultSet braceletResult = checkBraceletStmt.executeQuery();

            if (chainResult.next()) {
                String status = chainResult.getString("status");
                if ("showroom".equalsIgnoreCase(status)) {
                    return -1;  // Already in showroom
                }
                updateChainStmt.setString(1, productId);
                updateChainStmt.executeUpdate();
                return 1;  // Successfully updated
            } else if (braceletResult.next()) {
                String status = braceletResult.getString("status");
                if ("showroom".equalsIgnoreCase(status)) {
                    return -1;  // Already in showroom
                }
                updateBraceletStmt.setString(1, productId);
                updateBraceletStmt.executeUpdate();
                return 1;  // Successfully updated
            } else {
                return 0;  // Not found
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

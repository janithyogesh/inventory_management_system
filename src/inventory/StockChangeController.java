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
        String checkRingSql = "SELECT status FROM ring_details WHERE product_id = ?";
        String checkEarringSql = "SELECT status FROM earring_details WHERE product_id = ?";
        String checkPendantSql = "SELECT status FROM pendant_details WHERE product_id = ?";
        String checkSuraSql = "SELECT status FROM sura_details WHERE product_id = ?";
        String checkPanchaSql = "SELECT status FROM pancha_details WHERE product_id = ?";
        String checkNecklaceSql = "SELECT status FROM necklace_details WHERE product_id = ?";
        String checkBangleSql = "SELECT status FROM bangle_details WHERE product_id = ?";

        String updateChainSql = "UPDATE chain_details SET status = 'showroom' WHERE product_id = ? AND status = 'stock'";
        String updateBraceletSql = "UPDATE bracelet_details SET status = 'showroom' WHERE product_id = ? AND status = 'stock'";
        String updateRingSql = "UPDATE ring_details SET status = 'showroom' WHERE product_id = ? AND status = 'stock'";
        String updateEarringSql = "UPDATE earring_details SET status = 'showroom' WHERE product_id = ? AND status = 'stock'";
        String updatePendantSql = "UPDATE pendant_details SET status = 'showroom' WHERE product_id = ? AND status = 'stock'";
        String updateSuraSql = "UPDATE sura_details SET status = 'showroom' WHERE product_id = ? AND status = 'stock'";
        String updatePanchaSql = "UPDATE pancha_details SET status = 'showroom' WHERE product_id = ? AND status = 'stock'";
        String updateNecklaceSql = "UPDATE necklace_details SET status = 'showroom' WHERE product_id = ? AND status = 'stock'";
        String updateBangleSql = "UPDATE bangle_details SET status = 'showroom' WHERE product_id = ? AND status = 'stock'";

        try (PreparedStatement checkChainStmt = connect.prepareStatement(checkChainSql);
             PreparedStatement updateChainStmt = connect.prepareStatement(updateChainSql);
             PreparedStatement checkRingStmt = connect.prepareStatement(checkRingSql);
             PreparedStatement updateRingStmt = connect.prepareStatement(updateRingSql);
             PreparedStatement checkEarringStmt = connect.prepareStatement(checkEarringSql);
             PreparedStatement updateEarringStmt = connect.prepareStatement(updateEarringSql);
             PreparedStatement checkPendantStmt = connect.prepareStatement(checkPendantSql);
             PreparedStatement updatePendantStmt = connect.prepareStatement(updatePendantSql);
             PreparedStatement checkSuraStmt = connect.prepareStatement(checkSuraSql);
             PreparedStatement updateSuraStmt = connect.prepareStatement(updateSuraSql);
             PreparedStatement checkPanchaStmt = connect.prepareStatement(checkPanchaSql);
             PreparedStatement updatePanchaStmt = connect.prepareStatement(updatePanchaSql);
             PreparedStatement checkNecklaceStmt = connect.prepareStatement(checkNecklaceSql);
             PreparedStatement updateNecklaceStmt = connect.prepareStatement(updateNecklaceSql);
             PreparedStatement checkBangleStmt = connect.prepareStatement(checkBangleSql);
             PreparedStatement updateBangleStmt = connect.prepareStatement(updateBangleSql);
             PreparedStatement checkBraceletStmt = connect.prepareStatement(checkBraceletSql);
             PreparedStatement updateBraceletStmt = connect.prepareStatement(updateBraceletSql)) {

            checkChainStmt.setString(1, productId);
            checkBraceletStmt.setString(1, productId);
            checkRingStmt.setString(1, productId);
            checkEarringStmt.setString(1, productId);
            checkPendantStmt.setString(1, productId);
            checkSuraStmt.setString(1, productId);
            checkPanchaStmt.setString(1, productId);
            checkNecklaceStmt.setString(1, productId);
            checkBangleStmt.setString(1, productId);

            ResultSet chainResult = checkChainStmt.executeQuery();
            ResultSet braceletResult = checkBraceletStmt.executeQuery();
            ResultSet ringResult = checkRingStmt.executeQuery();
            ResultSet earringResult = checkEarringStmt.executeQuery();
            ResultSet pendantResult = checkPendantStmt.executeQuery();
            ResultSet suraResult = checkSuraStmt.executeQuery();
            ResultSet panchaResult = checkPanchaStmt.executeQuery();
            ResultSet necklaceResult = checkNecklaceStmt.executeQuery();
            ResultSet bangleResult = checkBangleStmt.executeQuery();


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
            } else if (ringResult.next()) {
                String status = ringResult.getString("status");
                if ("showroom".equalsIgnoreCase(status)) {
                    return -1;  // Already in showroom
                }
                updateRingStmt.setString(1, productId);
                updateRingStmt.executeUpdate();
                return 1;  // Successfully updated
            }else if (earringResult.next()) {
                String status = earringResult.getString("status");
                if ("showroom".equalsIgnoreCase(status)) {
                    return -1;  // Already in showroom
                }
                updateEarringStmt.setString(1, productId);
                updateEarringStmt.executeUpdate();
                return 1;  // Successfully updated
            }else if (pendantResult.next()) {
                String status = pendantResult.getString("status");
                if ("showroom".equalsIgnoreCase(status)) {
                    return -1;  // Already in showroom
                }
                updatePendantStmt.setString(1, productId);
                updatePendantStmt.executeUpdate();
                return 1;  // Successfully updated
            }else if (suraResult.next()) {
                String status = suraResult.getString("status");
                if ("showroom".equalsIgnoreCase(status)) {
                    return -1;  // Already in showroom
                }
                updateSuraStmt.setString(1, productId);
                updateSuraStmt.executeUpdate();
                return 1;  // Successfully updated
            }else if (panchaResult.next()) {
                String status = panchaResult.getString("status");
                if ("showroom".equalsIgnoreCase(status)) {
                    return -1;  // Already in showroom
                }
                updatePanchaStmt.setString(1, productId);
                updatePanchaStmt.executeUpdate();
                return 1;  // Successfully updated
            }else if (necklaceResult.next()) {
                String status = necklaceResult.getString("status");
                if ("showroom".equalsIgnoreCase(status)) {
                    return -1;  // Already in showroom
                }
                updateNecklaceStmt.setString(1, productId);
                updateNecklaceStmt.executeUpdate();
                return 1;  // Successfully updated
            }else if (bangleResult.next()) {
                String status = bangleResult.getString("status");
                if ("showroom".equalsIgnoreCase(status)) {
                    return -1;  // Already in showroom
                }
                updateBangleStmt.setString(1, productId);
                updateBangleStmt.executeUpdate();
                return 1;  // Successfully updated
            }else {
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

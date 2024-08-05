package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RingCategoryEditorController {

    @FXML
    private ListView<String> categoryListView;
    @FXML
    private TextField categoryTextField;

    private ObservableList<String> categories;
    private ObservableList<String> addedCategories = FXCollections.observableArrayList();
    private ObservableList<String> removedCategories = FXCollections.observableArrayList();

    public void initialize() {
        categories = FXCollections.observableArrayList();
        loadCategories();
        categoryListView.setItems(categories);
    }

    private void loadCategories() {
        String sql = "SELECT category_name FROM ri_category";
        try (Connection connect = database.connectDb(); PreparedStatement prepare = connect.prepareStatement(sql); ResultSet result = prepare.executeQuery()) {
            while (result.next()) {
                categories.add(result.getString("category_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeUpdate(String sql, String value) {
        try (Connection connect = database.connectDb(); PreparedStatement prepare = connect.prepareStatement(sql)) {
            prepare.setString(1, value);
            prepare.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void applyChanges() {
        // Apply category changes
        for (String category : addedCategories) {
            String sql = "INSERT INTO ri_category (category_name) VALUES (?)";
            executeUpdate(sql, category);
        }
        for (String category : removedCategories) {
            String sql = "DELETE FROM ri_category WHERE category_name = ?";
            executeUpdate(sql, category);
        }

        // Clear the lists
        addedCategories.clear();
        removedCategories.clear();
    }

    @FXML
    private void handleAddCategory() {
        String newCategory = categoryTextField.getText();
        if (newCategory != null && !newCategory.trim().isEmpty() && !categories.contains(newCategory)) {
            categories.add(newCategory); // Update the ListView immediately
            addedCategories.add(newCategory); // Track as added
            removedCategories.remove(newCategory); // Remove from removed if exists
            categoryTextField.clear();
        }
    }

    @FXML
    private void handleRemoveCategory() {
        String selectedCategory = categoryListView.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            categories.remove(selectedCategory); // Update the ListView immediately
            removedCategories.add(selectedCategory); // Track as removed
            addedCategories.remove(selectedCategory); // Remove from added if exists
        }
    }

    @FXML
    private void handleApplyChanges() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Changes");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to apply these changes?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Apply the changes
            applyChanges();

            // Close the window
            Stage stage = (Stage) categoryListView.getScene().getWindow();
            stage.close();
        }
    }

    public ObservableList<String> getCategories() {
        return categories;
    }

    @FXML
    private void handleClose() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit without saving changes?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) categoryListView.getScene().getWindow();
            stage.close();
        }
    }
}

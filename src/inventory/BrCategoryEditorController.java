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

public class BrCategoryEditorController {

    @FXML
    private ListView<String> categoryListView;
    @FXML
    private TextField categoryTextField;
    @FXML
    private ListView<String> weightListView;
    @FXML
    private TextField weightTextField;
    @FXML
    private ListView<String> lengthListView;
    @FXML
    private TextField lengthTextField;


    private ObservableList<String> categories;
    private ObservableList<String> weights;
    private ObservableList<String> lengths;

    private ObservableList<String> addedCategories = FXCollections.observableArrayList();
    private ObservableList<String> removedCategories = FXCollections.observableArrayList();
    private ObservableList<String> addedWeights = FXCollections.observableArrayList();
    private ObservableList<String> removedWeights = FXCollections.observableArrayList();
    private ObservableList<String> addedLengths = FXCollections.observableArrayList();
    private ObservableList<String> removedLengths = FXCollections.observableArrayList();

    public void initialize() {
        categories = FXCollections.observableArrayList();
        weights = FXCollections.observableArrayList();
        lengths = FXCollections.observableArrayList();

        loadCategories();
        loadWeights();
        loadLengths();

        categoryListView.setItems(categories);
        weightListView.setItems(weights);
        lengthListView.setItems(lengths);
    }

    private void loadCategories() {
        String sql = "SELECT category_name FROM br_category";
        try (Connection connect = database.connectDb(); PreparedStatement prepare = connect.prepareStatement(sql); ResultSet result = prepare.executeQuery()) {
            while (result.next()) {
                categories.add(result.getString("category_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadWeights() {
        String sql = "SELECT weight_name FROM br_weight";
        try (Connection connect = database.connectDb(); PreparedStatement prepare = connect.prepareStatement(sql); ResultSet result = prepare.executeQuery()) {
            while (result.next()) {
                weights.add(result.getString("weight_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadLengths() {
        String sql = "SELECT length_name FROM br_length";
        try (Connection connect = database.connectDb(); PreparedStatement prepare = connect.prepareStatement(sql); ResultSet result = prepare.executeQuery()) {
            while (result.next()) {
                lengths.add(result.getString("length_name"));
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
            String sql = "INSERT INTO br_category (category_name) VALUES (?)";
            executeUpdate(sql, category);
        }
        for (String category : removedCategories) {
            String sql = "DELETE FROM br_category WHERE category_name = ?";
            executeUpdate(sql, category);
        }

        // Apply weight changes
        for (String weight : addedWeights) {
            String sql = "INSERT INTO br_weight (weight_name) VALUES (?)";
            executeUpdate(sql, weight);
        }
        for (String weight : removedWeights) {
            String sql = "DELETE FROM br_weight WHERE weight_name = ?";
            executeUpdate(sql, weight);
        }

        // Apply length changes
        for (String length : addedLengths) {
            String sql = "INSERT INTO br_length (length_name) VALUES (?)";
            executeUpdate(sql, length);
        }
        for (String length : removedLengths) {
            String sql = "DELETE FROM br_length WHERE length_name = ?";
            executeUpdate(sql, length);
        }

        // Clear the lists
        addedCategories.clear();
        removedCategories.clear();
        addedWeights.clear();
        removedWeights.clear();
        addedLengths.clear();
        removedLengths.clear();
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
    private void handleAddWeight() {
        String newWeight = weightTextField.getText();
        if (newWeight != null && !newWeight.trim().isEmpty() && !weights.contains(newWeight)) {
            weights.add(newWeight); // Update the ListView immediately
            addedWeights.add(newWeight); // Track as added
            removedWeights.remove(newWeight); // Remove from removed if exists
            weightTextField.clear();
        }
    }

    @FXML
    private void handleRemoveWeight() {
        String selectedWeight = weightListView.getSelectionModel().getSelectedItem();
        if (selectedWeight != null) {
            weights.remove(selectedWeight); // Update the ListView immediately
            removedWeights.add(selectedWeight); // Track as removed
            addedWeights.remove(selectedWeight); // Remove from added if exists
        }
    }

    @FXML
    private void handleAddLength() {
        String newLength = lengthTextField.getText();
        if (newLength != null && !newLength.trim().isEmpty() && !lengths.contains(newLength)) {
            lengths.add(newLength); // Update the ListView immediately
            addedLengths.add(newLength); // Track as added
            removedLengths.remove(newLength); // Remove from removed if exists
            lengthTextField.clear();
        }
    }

    @FXML
    private void handleRemoveLength() {
        String selectedLength = lengthListView.getSelectionModel().getSelectedItem();
        if (selectedLength != null) {
            lengths.remove(selectedLength); // Update the ListView immediately
            removedLengths.add(selectedLength); // Track as removed
            addedLengths.remove(selectedLength); // Remove from added if exists
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

    public ObservableList<String> getWeights() {
        return weights;
    }

    public ObservableList<String> getLengths() {
        return lengths;
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

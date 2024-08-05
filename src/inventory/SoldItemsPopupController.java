package inventory;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class SoldItemsPopupController implements Initializable {

    @FXML
    private TableView<salesData> soldItemsTableView;
    @FXML
    private TableColumn<salesData, String> soldItems_col_productID, soldItems_col_category, soldItems_col_weight, soldItems_col_length, soldItems_col_status;
    @FXML
    private TableColumn<salesData, Double> soldItems_col_netWeight, soldItems_col_goldRate, soldItems_col_price, soldItems_col_returnValue;
    @FXML
    private TableColumn<salesData, Integer> soldItems_col_customerID;
    @FXML
    private TableColumn<salesData, java.sql.Date> soldItems_col_date;
    @FXML
    private TextField searchField;
    @FXML
    private Label totalSoldItemsLabel;
    @FXML
    private Label totalChainsLabel;
    @FXML
    private Label totalBraceletsLabel;

    private FilteredList<salesData> filteredData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the columns with the appropriate cell value factories
        soldItems_col_productID.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        soldItems_col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        soldItems_col_weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        soldItems_col_netWeight.setCellValueFactory(new PropertyValueFactory<>("net_weight"));
        soldItems_col_length.setCellValueFactory(new PropertyValueFactory<>("length"));
        //soldItems_col_karat.setCellValueFactory(new PropertyValueFactory<>("karat"));
        soldItems_col_goldRate.setCellValueFactory(new PropertyValueFactory<>("gold_rate"));
        soldItems_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        soldItems_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        soldItems_col_returnValue.setCellValueFactory(new PropertyValueFactory<>("return_value"));
        soldItems_col_customerID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        soldItems_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Set custom cell factories to format double values to two decimal places
        soldItems_col_netWeight.setCellFactory(getDoubleCellFactory());
        soldItems_col_goldRate.setCellFactory(getDoubleCellFactory());
        soldItems_col_price.setCellFactory(getDoubleCellFactory());
        soldItems_col_returnValue.setCellFactory(getDoubleCellFactory());

        // Add listener to search field to filter the table data
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(salesData -> {
                // If filter text is empty, display all data
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare data with filter text
                String lowerCaseFilter = newValue.toLowerCase();
                if (salesData.getProduct_id().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (salesData.getCategory().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(salesData.getNet_weight()).contains(lowerCaseFilter)) {
                    return true;
                } else if (salesData.getStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(salesData.getGold_rate()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(salesData.getPrice()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(salesData.getReturn_value()).contains(lowerCaseFilter)) {
                    return true;
                } else if (salesData.getDate().toString().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
            updateTotals(filteredData);
        });
    }

    public void setSalesData(ObservableList<salesData> salesData) {
        filteredData = new FilteredList<>(salesData, p -> true);
        soldItemsTableView.setItems(filteredData);
        updateTotals(filteredData);
    }

    private <T> Callback<TableColumn<T, Double>, TableCell<T, Double>> getDoubleCellFactory() {
        return column -> new TableCell<T, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item));
                }
            }
        };
    }

    private void updateTotals(FilteredList<salesData> salesData) {
        long totalItems = salesData.size();
        long totalChains = salesData.stream().filter(data -> data.getProduct_id().startsWith("C")).count();
        long totalBracelets = salesData.stream().filter(data -> data.getProduct_id().startsWith("B")).count();

        totalSoldItemsLabel.setText("Total Sold Items: " + totalItems);
        totalChainsLabel.setText("Total Chains: " + totalChains);
        totalBraceletsLabel.setText("Total Bracelets: " + totalBracelets);
    }
}

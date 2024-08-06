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
    private Label relatedItemsLabel;

    private FilteredList<salesData> filteredData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the columns with the appropriate cell value factories
        soldItems_col_productID.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        soldItems_col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        soldItems_col_weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        soldItems_col_netWeight.setCellValueFactory(new PropertyValueFactory<>("net_weight"));
        soldItems_col_length.setCellValueFactory(new PropertyValueFactory<>("length"));
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
                return itemMatchesFilter(salesData, newValue.toLowerCase());
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
        totalSoldItemsLabel.setText("Total Sold Items: " + totalItems);

        // Update the count of related items
        long relatedItems = salesData.stream().filter(this::itemMatchesFilter).count();
        relatedItemsLabel.setText("Related Items: " + relatedItems);
    }

    private boolean itemMatchesFilter(salesData item, String filter) {
        if (filter == null || filter.isEmpty()) {
            return true;
        }
        if (item.getProduct_id() != null && item.getProduct_id().toLowerCase().contains(filter)) {
            return true;
        }
        if (item.getCategory() != null && item.getCategory().toLowerCase().contains(filter)) {
            return true;
        }
        if (item.getWeight() != null && String.valueOf(item.getWeight()).toLowerCase().contains(filter)) {
            return true;
        }
        if (item.getNet_weight() != null && String.valueOf(item.getNet_weight()).contains(filter)) {
            return true;
        }
        if (item.getStatus() != null && item.getStatus().toLowerCase().contains(filter)) {
            return true;
        }
        if (item.getGold_rate() != null && String.valueOf(item.getGold_rate()).contains(filter)) {
            return true;
        }
        if (item.getPrice() != null && String.valueOf(item.getPrice()).contains(filter)) {
            return true;
        }
        if (item.getReturn_value() != null && String.valueOf(item.getReturn_value()).contains(filter)) {
            return true;
        }
        if (item.getDate() != null && item.getDate().toString().contains(filter)) {
            return true;
        }
        return false;
    }

    private boolean itemMatchesFilter(salesData item) {
        String filter = searchField.getText();
        return itemMatchesFilter(item, filter != null ? filter.toLowerCase() : "");
    }
}

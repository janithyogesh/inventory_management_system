package inventory;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class SoldItemsPopupController implements Initializable {

    @FXML
    private TableView<salesData> soldItemsTableView;
    @FXML
    private TableColumn<salesData, String> soldItems_col_productID, soldItems_col_category, soldItems_col_weight, soldItems_col_length, soldItems_col_karat;
    @FXML
    private TableColumn<salesData, Double> soldItems_col_netWeight, soldItems_col_goldRate, soldItems_col_price, soldItems_col_returnValue;
    @FXML
    private TableColumn<salesData, Integer> soldItems_col_customerID;
    @FXML
    private TableColumn<salesData, java.sql.Date> soldItems_col_date;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the columns with the appropriate cell value factories
        soldItems_col_productID.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        soldItems_col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        soldItems_col_weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        soldItems_col_netWeight.setCellValueFactory(new PropertyValueFactory<>("net_weight"));
        soldItems_col_length.setCellValueFactory(new PropertyValueFactory<>("length"));
        soldItems_col_karat.setCellValueFactory(new PropertyValueFactory<>("karat"));
        soldItems_col_goldRate.setCellValueFactory(new PropertyValueFactory<>("gold_rate"));
        soldItems_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        soldItems_col_returnValue.setCellValueFactory(new PropertyValueFactory<>("return_value"));
        soldItems_col_customerID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        soldItems_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Set custom cell factories to format double values to two decimal places
        soldItems_col_netWeight.setCellFactory(getDoubleCellFactory());
        soldItems_col_goldRate.setCellFactory(getDoubleCellFactory());
        soldItems_col_price.setCellFactory(getDoubleCellFactory());
        soldItems_col_returnValue.setCellFactory(getDoubleCellFactory());
    }

    public void setSalesData(ObservableList<salesData> salesData) {
        soldItemsTableView.setItems(salesData);
    }

    private <T> javafx.util.Callback<TableColumn<T, Double>, TableCell<T, Double>> getDoubleCellFactory() {
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
}

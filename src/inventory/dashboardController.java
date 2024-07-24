package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.SQLException;


import static inventory.database.connectDb;

public class dashboardController implements Initializable {

    @FXML
    private Button addChain_addBtn, addChain_deleteBtn, addChain_importBtn, addChain_resetBtn, addChain_updateBtn;
    @FXML
    private ComboBox<String> addChain_category, addChain_karat, addChain_length, addChain_status, addChain_weight;
    @FXML
    private TableColumn<chainData, String> addChain_col_category, addChain_col_karat, addChain_col_length, addChain_col_productID, addChain_col_status, addChain_col_supplier, addChain_col_weight;
    @FXML
    private TableColumn<chainData, Double> addChain_col_goldRate, addChain_col_netWeight;
    @FXML
    private TextField addChain_id, addChain_netWeight, addChain_rate, addChain_search, addChain_supplier;
    @FXML
    private ImageView addChain_img;
    @FXML
    private TableView<chainData> addChain_tableView;
    @FXML
    private Button chainBtn, close, homeBtn, logout, minimize, salesBtn;
    @FXML
    private AnchorPane chain_form, home_availableStock, home_form, home_numberSales, home_totalIncome, main_form, sales_form;
    @FXML
    private AreaChart<String, Number> home_incomeChart;
    @FXML
    private BarChart<String, Number> home_salesChart;
    @FXML
    private TextField sales_amount, sales_id, sales_price, sales_return;
    @FXML
    private Label sales_balance, sales_total, username;
    @FXML
    private TableColumn<salesData, String> sales_col_category, sales_col_id, sales_col_karat, sales_col_length, sales_col_weight;
    @FXML
    private TableColumn<salesData, Double> sales_col_netWeight, sales_col_price, sales_col_rate, sales_col_returnValue;
    @FXML
    private TableView<salesData> sales_tableView;
    @FXML
    private Button sales_payBtn, sales_receiptBtn, sales_soldBtn, sales_addBtn, sales_removeBtn;
    @FXML
    private Label home_sales,home_income,home_stock;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private Image image;

    // Declare customerid as a class-level variable
    private int customerid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();
        defaultNav();
        // Initialize data and views
        addChainShowListData();
        salesShowListData();

        addChainListCategory();
        addChainListWeight();
        addChainListLength();
        addChainListKarat();
        addChainListStatus();

        addChainSearch();

        // Add listener to sales_amount to update sales_balance when it changes
        sales_amount.textProperty().addListener((observable, oldValue, newValue) -> updateSalesBalance());

        // Display today's total sales count, total payable amount, and total stock count
        displayTodaySales();
        displayTodayIncome();
        displayTotalStock();

        displayIncomeChart();
        displaySalesChart();
    }

    private void displayTodaySales() {
        int totalSalesToday = countTodaySales();
        home_sales.setText(String.valueOf(totalSalesToday));
    }

    private int countTodaySales() {
        int totalSales = 0;

        // Get current date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());

        String query = "SELECT COUNT(*) AS total_sales FROM sales WHERE DATE(date) = ?";

        connect = connectDb();
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            // Set the current date parameter
            pstmt.setString(1, currentDate);

            // Execute the query and get the result
            result = pstmt.executeQuery();
            if (result.next()) {
                totalSales = result.getInt("total_sales");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return totalSales;
    }

    private void displayTodayIncome() {
        double totalIncomeToday = countTodayIncome();
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        home_income.setText("Rs. " + formatter.format(totalIncomeToday));
    }


    private double countTodayIncome() {
        double totalIncome = 0.0;

        // Get current date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());

        String query = "SELECT SUM(payable) AS total_income FROM sales_receipt WHERE DATE(date) = ?";

        connect = connectDb();
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            // Set the current date parameter
            pstmt.setString(1, currentDate);

            // Execute the query and get the result
            result = pstmt.executeQuery();
            if (result.next()) {
                totalIncome = result.getDouble("total_income");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return totalIncome;
    }

    private void displayTotalStock() {
        int totalStock = countTotalStock();
        home_stock.setText(String.valueOf(totalStock));
    }

    private int countTotalStock() {
        int totalStock = 0;

        String query = "SELECT COUNT(*) AS total_stock FROM chain_details";

        connect = connectDb();
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            // Execute the query and get the result
            result = pstmt.executeQuery();
            if (result.next()) {
                totalStock = result.getInt("total_stock");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return totalStock;
    }

    private void displayIncomeChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Income");

        String query = "SELECT DATE(date) as date, SUM(payable) as total_income FROM sales_receipt GROUP BY DATE(date)";
        connect = connectDb();
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            result = pstmt.executeQuery();
            while (result.next()) {
                String date = result.getString("date");
                double income = result.getDouble("total_income");
                series.getData().add(new XYChart.Data<>(date, income));
            }
            home_incomeChart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void displaySalesChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sales");

        String query = "SELECT DATE(date) as date, COUNT(*) as total_sales FROM sales GROUP BY DATE(date)";
        connect = connectDb();
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            result = pstmt.executeQuery();
            while (result.next()) {
                String date = result.getString("date");
                int sales = result.getInt("total_sales");
                series.getData().add(new XYChart.Data<>(date, sales));
            }
            home_salesChart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleSoldItemsButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SoldItemsPopup.fxml"));
            Parent root = loader.load();

            SoldItemsPopupController popupController = loader.getController();
            popupController.setSalesData(getAllSalesData());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableList<salesData> getAllSalesData() {
        ObservableList<salesData> salesDataList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM sales";
        connect = connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                salesData sale = new salesData(
                        result.getInt("customer_id"),
                        result.getString("product_id"),
                        result.getString("category"),
                        result.getString("weight"),
                        result.getDouble("net_weight"),
                        result.getString("length"),
                        result.getString("karat"),
                        result.getDouble("gold_rate"),
                        result.getDouble("price"),
                        result.getDouble("return_value"),
                        result.getDate("date")
                );
                salesDataList.add(sale);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salesDataList;
    }

    // Method to add products to the sales table
    @FXML
    private void handleAddButtonClick() {
        String productId = sales_id.getText();
        String priceStr = sales_price.getText();
        String returnValueStr = sales_return.getText();

        if (productId.isEmpty() || priceStr.isEmpty() || returnValueStr.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill all fields");
            return;
        }

        double price = Double.parseDouble(priceStr);
        double returnValue = Double.parseDouble(returnValueStr);

        chainData chainDetails = getChainDetails(productId);
        if (chainDetails == null) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Product ID not found");
            return;
        }

        double minPrice = chainDetails.getGold_rate() * chainDetails.getNet_weight() / 8;
        if (price < minPrice) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Sales price must be greater than " + minPrice);
            return;
        }

        if (returnValue > price) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid Exchange Value!");
            return;
        }

        customerId();  // Ensure customer ID is set

        salesData newSalesData = new salesData(
                customerid,
                chainDetails.getProductId(),
                chainDetails.getCategory(),
                chainDetails.getWeight(),
                chainDetails.getNet_weight(),
                chainDetails.getLength(),
                chainDetails.getKarat(),
                chainDetails.getGold_rate(),
                price,
                returnValue,
                new java.sql.Date(new Date().getTime())
        );

        sales_tableView.getItems().add(newSalesData);
        clearSalesInputFields();
        updateSalesTotal();
    }

    private boolean hasDuplicateProductIDs() {
        Set<String> productIDs = new HashSet<>();
        for (salesData sale : sales_tableView.getItems()) {
            if (!productIDs.add(sale.getProduct_id())) {
                return true;
            }
        }
        return false;
    }

    // Method to handle the Remove button click
    @FXML
    private void handleRemoveButtonClick() {
        salesData selectedItem = sales_tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            sales_tableView.getItems().remove(selectedItem);
            updateSalesTotal();
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an item to remove.");
        }
    }

    @FXML
    private void handlePayButtonClick() {
        ObservableList<salesData> salesDataList = sales_tableView.getItems();

        if (salesDataList.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "No sales data to process");
            return;
        }

        // Check if there are duplicate product IDs in the sales data list
        if (hasDuplicateProductIDs()) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Duplicate product IDs found. Please ensure each product ID is unique.");
            return;
        }

        // Check if any product ID in the sales data list is already in the database
        for (salesData sale : salesDataList) {
            if (isProductIdInSalesTable(sale.getProduct_id())) {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Product ID " + sale.getProduct_id() + " is already sold. Cannot proceed with the sale.");
                return;
            }
        }

        // Calculate total, amount, and payable
        double total = salesDataList.stream().mapToDouble(sale -> sale.getPrice()).sum();
        double exchange = salesDataList.stream().mapToDouble(sale -> sale.getReturn_value()).sum();
        double payable = total - exchange;

        double amount = 0.0;
        if (!sales_amount.getText().isEmpty()) {
            try {
                amount = Double.parseDouble(sales_amount.getText());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid amount!");
                return;
            }
        }

        if (amount < payable) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid amount!");
            return;
        }

        // Verify passcode before proceeding
        if (!verifyPaymentPasscode()) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid passcode. Payment canceled.");
            return;
        }

        connect = connectDb();
        String sql = "INSERT INTO sales (customer_id, product_id, category, weight, net_weight, length, karat, gold_rate, price, return_value, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            prepare = connect.prepareStatement(sql);

            for (salesData sale : salesDataList) {
                prepare.setInt(1, sale.getCustomer_id());
                prepare.setString(2, sale.getProduct_id());
                prepare.setString(3, sale.getCategory());
                prepare.setString(4, sale.getWeight());
                prepare.setDouble(5, sale.getNet_weight());
                prepare.setString(6, sale.getLength());
                prepare.setString(7, sale.getKarat());
                prepare.setDouble(8, sale.getGold_rate());
                prepare.setDouble(9, sale.getPrice());
                prepare.setDouble(10, sale.getReturn_value());
                prepare.setDate(11, sale.getDate());
                prepare.addBatch();
            }

            prepare.executeBatch();

            // Remove sold items from chain_details table
            for (salesData sale : salesDataList) {
                removeChainDetails(sale.getProduct_id());
            }

            // Insert into sales_receipt table
            String insertReceiptSQL = "INSERT INTO sales_receipt (customer_id, total, exchange, payable, amount, balance, date, time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            prepare = connect.prepareStatement(insertReceiptSQL);

            double balance = amount - payable;
            Timestamp currentTimestamp = new Timestamp(new Date().getTime());

            prepare.setInt(1, customerid);
            prepare.setDouble(2, total);
            prepare.setDouble(3, exchange);
            prepare.setDouble(4, payable);
            prepare.setDouble(5, amount);
            prepare.setDouble(6, balance);
            prepare.setDate(7, new java.sql.Date(new Date().getTime()));
            prepare.setTimestamp(8, currentTimestamp);
            prepare.executeUpdate();

            sales_tableView.getItems().clear();
            customerid++; // Increment customer ID for the next sale

            showAlert(Alert.AlertType.INFORMATION, "Success", "Payment Successful!");

            // Clear the total and balance labels
            sales_total.setText("0.00");
            sales_balance.setText("0.00");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeChainDetails(String productId) {
        String sql = "DELETE FROM chain_details WHERE product_id = ?";
        connect = connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, productId);
            prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isProductIdInSalesTable(String productId) {
        String sql = "SELECT COUNT(*) FROM sales WHERE product_id = ?";
        connect = connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, productId);
            result = prepare.executeQuery();
            if (result.next()) {
                int count = result.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to update the sales total
    private void updateSalesTotal() {
        double total = 0.0;
        for (salesData sale : sales_tableView.getItems()) {
            total += sale.getPrice() - sale.getReturn_value();
        }
        sales_total.setText(String.format("%.2f", total));
        updateSalesBalance();
    }

    // Method to update the sales balance
    private void updateSalesBalance() {
        double total = Double.parseDouble(sales_total.getText());
        double amount = 0.0;
        if (!sales_amount.getText().isEmpty()) {
            try {
                amount = Double.parseDouble(sales_amount.getText());
            } catch (NumberFormatException e) {
                amount = 0.0;
            }
        }
        double balance = amount - total;
        sales_balance.setText(String.format("%.2f", balance));
    }

    private void clearSalesInputFields() {
        sales_id.setText("");
        sales_price.setText("");
        sales_return.setText("");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private Optional<String> showCustomerIdInputDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Customer ID Input");
        dialog.setHeaderText("Enter Customer ID");
        dialog.setContentText("Customer ID:");

        return dialog.showAndWait();
    }

    // Method to fetch the last customer ID from the database
    private int getLastCustomerId() {
        String sql = "SELECT MAX(customer_id) AS max_id FROM sales";
        connect = connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                return result.getInt("max_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1; // Default to 1 if no customers exist
    }

    @FXML
    private void salesReceipt() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Customer ID Required");
        dialog.setHeaderText("Enter Customer ID");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        TextField customerIdField = new TextField();
        customerIdField.setPromptText("Customer ID");

        // Pre-fill the customerIdField with the last customer ID
        int lastCustomerId = getLastCustomerId();
        customerIdField.setText(String.valueOf(lastCustomerId));

        VBox vbox = new VBox();
        vbox.getChildren().add(customerIdField);
        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return customerIdField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String inputCustomerId = result.get();
            if (inputCustomerId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Customer ID cannot be empty");
                return;
            }

            int customerId;
            try {
                customerId = Integer.parseInt(inputCustomerId);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid Customer ID");
                return;
            }

            if (!isCustomerIdExists(customerId)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Customer ID not found");
                return;
            }

            generateReceipt(customerId);
        }
    }

    private boolean isCustomerIdExists(int customerId) {
        String checkCustomerIdSql = "SELECT COUNT(*) AS count FROM sales_receipt WHERE customer_id = ?";
        boolean exists = false;
        connect = connectDb();

        try {
            prepare = connect.prepareStatement(checkCustomerIdSql);
            prepare.setInt(1, customerId);
            result = prepare.executeQuery();

            if (result.next()) {
                exists = result.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exists;
    }

    private void generateReceipt(int customerId) {
        connect = connectDb();
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("inventoryP", customerId);

        try {
            // Load the .jrxml file
            JasperDesign jDesign = JRXmlLoader.load("D:\\my java files\\Inventory\\src\\inventory\\reportjrxml.jrxml");
            // Compile the .jrxml file
            JasperReport jReport = JasperCompileManager.compileReport(jDesign);
            // Fill the report with data
            JasperPrint jPrint = JasperFillManager.fillReport(jReport, parameters, connect);
            // View the report
            JasperViewer.viewReport(jPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate receipt: " + e.getMessage());
        }
    }

    // Method to get chain details from the database
    private chainData getChainDetails(String productId) {
        String sql = "SELECT * FROM chain_details WHERE product_id = ?";
        connect = connectDb();
        chainData chainDetails = null;
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, productId);
            result = prepare.executeQuery();
            if (result.next()) {
                chainDetails = new chainData(
                        result.getString("product_id"),
                        result.getString("category"),
                        result.getString("weight"),
                        result.getDouble("net_weight"),
                        result.getString("length"),
                        result.getString("karat"),
                        result.getDouble("gold_rate"),
                        result.getString("supplier"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDate("date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainDetails;
    }

    // Method to manage customer ID
    public void customerId() {
        String customId = "SELECT MAX(customer_id) AS max_id FROM sales";

        connect = connectDb();

        try {
            prepare = connect.prepareStatement(customId);
            result = prepare.executeQuery();

            if (result.next()) {
                customerid = result.getInt("max_id") + 1;
            } else {
                customerid = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addChainAdd() {
        String sql = "INSERT INTO chain_details (product_id, category, weight, net_weight, length, karat, gold_rate, supplier, status, image, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        connect = connectDb();
        try {
            Alert alert;

            if (addChain_id.getText().isEmpty()
                    || addChain_category.getSelectionModel().getSelectedItem() == null
                    || addChain_weight.getSelectionModel().getSelectedItem() == null
                    || addChain_netWeight.getText().isEmpty()
                    || addChain_length.getSelectionModel().getSelectedItem() == null
                    || addChain_karat.getSelectionModel().getSelectedItem() == null
                    || addChain_rate.getText().isEmpty()
                    || addChain_supplier.getText().isEmpty()
                    || addChain_status.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else if (!isProductIdValid(addChain_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID format is incorrect. It should start with 'C' followed by 4 digits.");
                alert.showAndWait();
            } else if (isProductIdExist(addChain_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID already exists. Please use a unique Product ID.");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                String productId = addChain_id.getText();
                prepare.setString(1, productId);
                prepare.setString(2, addChain_category.getSelectionModel().getSelectedItem());
                prepare.setString(3, addChain_weight.getSelectionModel().getSelectedItem());
                prepare.setString(4, addChain_netWeight.getText());
                prepare.setString(5, addChain_length.getSelectionModel().getSelectedItem());
                prepare.setString(6, addChain_karat.getSelectionModel().getSelectedItem());
                prepare.setString(7, addChain_rate.getText());
                prepare.setString(8, addChain_supplier.getText());
                prepare.setString(9, addChain_status.getSelectionModel().getSelectedItem());

                String uri = getData.path;
                if (uri != null && !uri.isEmpty()) {
                    uri = uri.replace("\\", "\\\\");
                    prepare.setString(10, uri);
                } else {
                    prepare.setString(10, null);
                }

                Date date = new Date();
                java.sql.Date sqldate = new java.sql.Date(date.getTime());

                prepare.setString(11, String.valueOf(sqldate));

                prepare.executeUpdate();

                addChainShowListData();
                addChainReset();

                // Show success message
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText(productId + " entered successfully!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addChainUpdate() {
        // Check if the status is selected
        if (addChain_status.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the new status");
            alert.showAndWait();
            return;
        }

        // Ensure the Product ID field is filled
        if (addChain_id.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Product ID is required to update status");
            alert.showAndWait();
            return;
        }

        // Prompt for confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation Message");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to UPDATE the status for Product_ID: " + addChain_id.getText() + "?");

        Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();
        if (confirmationResult.isPresent() && confirmationResult.get().equals(ButtonType.OK)) {
            // Verify passcode
            if (!verifyPasscode()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid passcode. Update canceled.");
                alert.showAndWait();
                return;
            }

            // Prepare the SQL query to update only the status field
            String sql = "UPDATE chain_details SET status = ? WHERE product_id = ?";

            connect = connectDb();
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addChain_status.getSelectionModel().getSelectedItem());
                prepare.setString(2, addChain_id.getText());

                int rowsUpdated = prepare.executeUpdate();

                if (rowsUpdated > 0) {
                    addChainShowListData();
                    addChainReset();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Status updated successfully!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to update status. Please check the Product ID.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addChainDelete() {
        // Ensure the Product ID field is filled
        if (addChain_id.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        }

        // Check if the Product ID exists
        if (!isProductIdExist(addChain_id.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Product ID not found. Deletion canceled.");
            alert.showAndWait();
            return;
        }

        // Prompt for confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation Message");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to DELETE Product_ID: " + addChain_id.getText() + "?");

        Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();
        if (confirmationResult.isPresent() && confirmationResult.get().equals(ButtonType.OK)) {
            // Verify passcode
            if (!verifyPasscode()) {
                Alert passcodeAlert = new Alert(Alert.AlertType.ERROR);
                passcodeAlert.setTitle("Error Message");
                passcodeAlert.setHeaderText(null);
                passcodeAlert.setContentText("Invalid passcode. Deletion canceled.");
                passcodeAlert.showAndWait();
                return;
            }

            // Proceed with deletion
            String sql = "DELETE FROM chain_details WHERE product_id = ?";
            connect = connectDb();
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addChain_id.getText());

                int rowsDeleted = prepare.executeUpdate();
                if (rowsDeleted > 0) {
                    addChainShowListData();
                    addChainReset();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Data deleted successfully!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to delete data. Please try again.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addChainReset() {
        addChain_id.setText("");
        addChain_category.getSelectionModel().clearSelection();
        addChain_weight.getSelectionModel().clearSelection();
        addChain_netWeight.setText("");
        addChain_length.getSelectionModel().clearSelection();
        addChain_karat.getSelectionModel().clearSelection();
        addChain_rate.setText("");
        addChain_supplier.setText("");
        addChain_status.getSelectionModel().clearSelection();
        addChain_img.setImage(null);
        getData.path = "";
    }

    public void addChainImportImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            getData.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 90, 100, false, true);
            addChain_img.setImage(image);
        }
    }

    private String[] listCategory = {"Box Chain", "Double Box", "Dragon Box", "Bismark Chain", "KDM Chain", "Double Albert", "Albert Chain", "Lotus Chain", "Lazer Chain", "Link Chain", "Rope Chain", "Sapna Chain", "Ball Chain", "Diamond Chain", "Lee Chain", "SP Chain", "18K Chain"};

    public void addChainListCategory() {
        List<String> listC = new ArrayList<>();

        for(String data:listCategory){
            listC.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listCategory);
        addChain_category.setItems(listData);
    }

    private String[] listWeight = {"40g", "32g", "24g", "20g", "16g", "14g", "12g", "10g", "8g", "6g", "5g", "4.5g", "4g", "3.5g", "3g", "2.5g", "2g", "1.5g", "1g"};

    public void addChainListWeight() {
        List<String> listW = new ArrayList<>();

        for(String data:listWeight){
            listW.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listWeight);
        addChain_weight.setItems(listData);
    }

    private String[] listLength = {"24 in", "22 in", "20 in", "18 in", "16 in", "14 in"};

    public void addChainListLength() {
        List<String> listL = new ArrayList<>();

        for(String data:listLength){
            listL.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listLength);
        addChain_length.setItems(listData);
    }

    private String[] listKarat = {"24K", "23K", "22K", "21K", "20K", "19K", "18K"};

    public void addChainListKarat() {
        List<String> listK = new ArrayList<>();

        for(String data:listKarat){
            listK.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listKarat);
        addChain_karat.setItems(listData);
    }

    private String[] listStatus = {"Stock", "Showroom"};

    public void addChainListStatus() {
        List<String> listS = new ArrayList<>();

        for(String data:listStatus){
            listS.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listStatus);
        addChain_status.setItems(listData);
    }

    public void addChainSearch() {
        FilteredList<chainData> filter = new FilteredList<>(addChainList, e -> true);

        addChain_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateChainData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();

                if (predicateChainData.getProductId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateChainData.getCategory().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateChainData.getWeight().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateChainData.getNet_weight().toString().contains(searchKey)) {
                    return true;
                } else if (predicateChainData.getLength().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateChainData.getKarat().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateChainData.getGold_rate().toString().contains(searchKey)) {
                    return true;
                } else if (predicateChainData.getSupplier().toString().contains(searchKey)) {
                    return true;
                } else if (predicateChainData.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<chainData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(addChain_tableView.comparatorProperty());
        addChain_tableView.setItems(sortList);
    }

    public ObservableList<chainData> addChainsListData() {
        ObservableList<chainData> chainList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM chain_details";
        connect = connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            chainData chD;

            while (result.next()) {
                chD = new chainData(result.getString("product_id"),
                        result.getString("category"),
                        result.getString("weight"),
                        result.getDouble("net_weight"),
                        result.getString("length"),
                        result.getString("karat"),
                        result.getDouble("gold_rate"),
                        result.getString("supplier"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDate("date"));

                chainList.add(chD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainList;
    }

    private ObservableList<chainData> addChainList;

    public void addChainShowListData() {
        addChainList = addChainsListData();

        addChain_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        addChain_col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        addChain_col_weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        addChain_col_netWeight.setCellValueFactory(new PropertyValueFactory<>("net_weight"));
        addChain_col_length.setCellValueFactory(new PropertyValueFactory<>("length"));
        addChain_col_karat.setCellValueFactory(new PropertyValueFactory<>("karat"));
        addChain_col_goldRate.setCellValueFactory(new PropertyValueFactory<>("gold_rate"));
        addChain_col_supplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        addChain_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set custom cell factories to format double values to two decimal places
        addChain_col_netWeight.setCellFactory(getDoubleCellFactory());
        addChain_col_goldRate.setCellFactory(getDoubleCellFactory());

        addChain_tableView.setItems(addChainList);
    }

    public void addChainSelect() {
        chainData chD = addChain_tableView.getSelectionModel().getSelectedItem();
        int num = addChain_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addChain_id.setText(String.valueOf(chD.getProductId()));
        setComboBoxValue(addChain_category, chD.getCategory());
        setComboBoxValue(addChain_weight, chD.getWeight());
        addChain_netWeight.setText(String.valueOf(chD.getNet_weight()));
        setComboBoxValue(addChain_length, chD.getLength());
        setComboBoxValue(addChain_karat, chD.getKarat());
        addChain_rate.setText(String.valueOf(chD.getGold_rate()));
        addChain_supplier.setText(String.valueOf(chD.getSupplier()));
        setComboBoxValue(addChain_status, chD.getStatus());

        if (chD.getImage() != null && !chD.getImage().isEmpty()) {
            String uri = "file:" + chD.getImage();
            image = new Image(uri, 90, 100, false, true);
            addChain_img.setImage(image);
            getData.path = chD.getImage();
        } else {
            addChain_img.setImage(null);
        }
    }

    private void setComboBoxValue(ComboBox<String> comboBox, String value) {
        Platform.runLater(() -> {
            if (!comboBox.getItems().contains(value)) {
                comboBox.getItems().add(value);
            }
            comboBox.setValue(value);
        });
    }

    public ObservableList<salesData> salesListdata() {
        customerId();
        ObservableList<salesData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM sales WHERE customer_id='" + customerid + "'";

        connect = connectDb();

        try {
            salesData customerD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                customerD = new salesData(result.getInt("customer_id"),
                        result.getString("product_id"),
                        result.getString("category"),
                        result.getString("weight"),
                        result.getDouble("net_weight"),
                        result.getString("length"),
                        result.getString("karat"),
                        result.getDouble("gold_rate"),
                        result.getDouble("price"),
                        result.getDouble("return_value"),
                        result.getDate("date"));
                listData.add(customerD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<salesData> salesList;

    public void salesShowListData() {
        salesList = salesListdata();

        sales_col_id.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        sales_col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        sales_col_weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        sales_col_netWeight.setCellValueFactory(new PropertyValueFactory<>("net_weight"));
        sales_col_length.setCellValueFactory(new PropertyValueFactory<>("length"));
        sales_col_karat.setCellValueFactory(new PropertyValueFactory<>("karat"));
        sales_col_rate.setCellValueFactory(new PropertyValueFactory<>("gold_rate"));
        sales_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        sales_col_returnValue.setCellValueFactory(new PropertyValueFactory<>("return_value"));

        // Set custom cell factories to format double values to two decimal places
        sales_col_netWeight.setCellFactory(getDoubleCellFactory());
        sales_col_rate.setCellFactory(getDoubleCellFactory());
        sales_col_price.setCellFactory(getDoubleCellFactory());
        sales_col_returnValue.setCellFactory(getDoubleCellFactory());

        sales_tableView.setItems(salesList);
        sales_tableView.refresh();
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == homeBtn) {
            home_form.setVisible(true);
            chain_form.setVisible(false);
            sales_form.setVisible(false);

            homeBtn.setStyle("-fx-background-color:linear-gradient(to bottom ,#004cc5, #011b4a);");
            chainBtn.setStyle("-fx-background-color:transparent");
            salesBtn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == chainBtn) {
            home_form.setVisible(false);
            chain_form.setVisible(true);
            sales_form.setVisible(false);

            homeBtn.setStyle("-fx-background-color:transparent");
            chainBtn.setStyle("-fx-background-color:linear-gradient(to bottom ,#004cc5, #011b4a);");
            salesBtn.setStyle("-fx-background-color:transparent");

            addChainShowListData();
            addChainListCategory();
            addChainListWeight();
            addChainListLength();
            addChainListKarat();
            addChainListStatus();
            addChainSearch();

        } else if (event.getSource() == salesBtn) {
            home_form.setVisible(false);
            chain_form.setVisible(false);
            sales_form.setVisible(true);

            homeBtn.setStyle("-fx-background-color:transparent");
            chainBtn.setStyle("-fx-background-color:transparent");
            salesBtn.setStyle("-fx-background-color:linear-gradient(to bottom ,#004cc5, #011b4a);");

            salesShowListData();
        }
    }

    public void defaultNav(){
        homeBtn.setStyle("-fx-background-color:linear-gradient(to bottom ,#004cc5, #011b4a);");
    }


    public void displayUsername() {
        if (getData.username != null) {
            username.setText(getData.username);
        } else {
            username.setText("Guest"); // Default value if username is null
        }
    }


    private double x = 0;
    private double y = 0;

    public void logout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                logout.getScene().getWindow().hide();
                // LINK MY LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            } else {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void close() {
        System.exit(0);
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

    private boolean isProductIdExist(String productId) {
        String sql = "SELECT COUNT(*) FROM chain_details WHERE product_id = ?";
        connect = connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, productId);
            result = prepare.executeQuery();
            if (result.next()) {
                int count = result.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isProductIdValid(String productId) {
        // Check if productId starts with 'C' and has exactly 4 digits
        return productId.matches("^C\\d{4}$");
    }

    private boolean verifyPasscode() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Passcode Required");
        dialog.setHeaderText("Enter Passcode");

        ButtonType loginButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        PasswordField passcodeField = new PasswordField();
        passcodeField.setPromptText("Passcode");

        VBox vbox = new VBox();
        vbox.getChildren().add(passcodeField);
        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return passcodeField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        return result.isPresent() && "1234".equals(result.get());
    }

    // Utility method to verify the payment passcode
    private boolean verifyPaymentPasscode() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Payment Passcode Required");
        dialog.setHeaderText("Enter Payment Passcode");

        ButtonType loginButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        PasswordField passcodeField = new PasswordField();
        passcodeField.setPromptText("Payment Passcode");

        VBox vbox = new VBox();
        vbox.getChildren().add(passcodeField);
        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return passcodeField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        return result.isPresent() && "1212".equals(result.get()); // You can change this passcode as needed
    }
}

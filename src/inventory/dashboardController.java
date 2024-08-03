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
    private Button addBr_addBtn, addBr_deleteBtn, addBr_importBtn, addBr_resetBtn, addBr_updateBtn;
    @FXML
    private Button addRi_addBtn, addRi_deleteBtn, addRi_importBtn, addRi_resetBtn, addRi_updateBtn;
    @FXML
    private Button addEr_addBtn, addEr_deleteBtn, addEr_importBtn, addEr_resetBtn, addEr_updateBtn;
    @FXML
    private Button addPe_addBtn, addPe_deleteBtn, addPe_importBtn, addPe_resetBtn, addPe_updateBtn;
    @FXML
    private Button addSu_addBtn, addSu_deleteBtn, addSu_importBtn, addSu_resetBtn, addSu_updateBtn;
    @FXML
    private Button addPa_addBtn, addPa_deleteBtn, addPa_importBtn, addPa_resetBtn, addPa_updateBtn;
    @FXML
    private Button addNe_addBtn, addNe_deleteBtn, addNe_importBtn, addNe_resetBtn, addNe_updateBtn;
    @FXML
    private Button addBa_addBtn, addBa_deleteBtn, addBa_importBtn, addBa_resetBtn, addBa_updateBtn;


    @FXML
    private ComboBox<String> addChain_category, addChain_karat, addChain_length, addChain_status, addChain_weight;
    @FXML
    private ComboBox<String> addBr_category, addBr_karat, addBr_length, addBr_status, addBr_weight;
    @FXML
    private ComboBox<String> addRi_category, addRi_karat, addRi_status;
    @FXML
    private ComboBox<String> addEr_category, addEr_karat, addEr_status;
    @FXML
    private ComboBox<String> addPe_category, addPe_karat, addPe_status;
    @FXML
    private ComboBox<String> addSu_category, addSu_karat, addSu_status;
    @FXML
    private ComboBox<String> addPa_category, addPa_karat, addPa_status;
    @FXML
    private ComboBox<String> addNe_category, addNe_karat, addNe_status;
    @FXML
    private ComboBox<String> addBa_category, addBa_karat, addBa_status;


    @FXML
    private TableColumn<chainData, String> addChain_col_category, addChain_col_karat, addChain_col_length, addChain_col_productID, addChain_col_status, addChain_col_supplier, addChain_col_weight;
    @FXML
    private TableColumn<chainData, Double> addChain_col_goldRate, addChain_col_netWeight;
    @FXML
    private TableColumn<braceletData, String> addBr_col_category, addBr_col_karat, addBr_col_length, addBr_col_productID, addBr_col_status, addBr_col_supplier, addBr_col_weight;
    @FXML
    private TableColumn<braceletData, Double> addBr_col_goldRate, addBr_col_netWeight;
    @FXML
    private TableColumn<ringData, String> addRi_col_category, addRi_col_karat, addRi_col_length, addRi_col_productID, addRi_col_status, addRi_col_supplier, addRi_col_weight;
    @FXML
    private TableColumn<ringData, Double> addRi_col_goldRate, addRi_col_netWeight;
    @FXML
    private TableColumn<earringData, String> addEr_col_category, addEr_col_karat, addEr_col_length, addEr_col_productID, addEr_col_status, addEr_col_supplier, addEr_col_weight;
    @FXML
    private TableColumn<earringData, Double> addEr_col_goldRate, addEr_col_netWeight;
    @FXML
    private TableColumn<pendantData, String> addPe_col_category, addPe_col_karat, addPe_col_length, addPe_col_productID, addPe_col_status, addPe_col_supplier, addPe_col_weight;
    @FXML
    private TableColumn<pendantData, Double> addPe_col_goldRate, addPe_col_netWeight;
    @FXML
    private TableColumn<suraData, String> addSu_col_category, addSu_col_karat, addSu_col_length, addSu_col_productID, addSu_col_status, addSu_col_supplier, addSu_col_weight;
    @FXML
    private TableColumn<suraData, Double> addSu_col_goldRate, addSu_col_netWeight;
    @FXML
    private TableColumn<panchaData, String> addPa_col_category, addPa_col_karat, addPa_col_length, addPa_col_productID, addPa_col_status, addPa_col_supplier, addPa_col_weight;
    @FXML
    private TableColumn<panchaData, Double> addPa_col_goldRate, addPa_col_netWeight;
    @FXML
    private TableColumn<necklaceData, String> addNe_col_category, addNe_col_karat, addNe_col_length, addNe_col_productID, addNe_col_status, addNe_col_supplier, addNe_col_weight;
    @FXML
    private TableColumn<necklaceData, Double> addNe_col_goldRate, addNe_col_netWeight;
    @FXML
    private TableColumn<bangleData, String> addBa_col_category, addBa_col_karat, addBa_col_length, addBa_col_productID, addBa_col_status, addBa_col_supplier, addBa_col_weight;
    @FXML
    private TableColumn<bangleData, Double> addBa_col_goldRate, addBa_col_netWeight;


    @FXML
    private TextField addChain_id, addChain_netWeight, addChain_rate, addChain_search, addChain_supplier;
    @FXML
    private TextField addBr_id, addBr_netWeight, addBr_rate, addBr_search, addBr_supplier;
    @FXML
    private TextField addRi_id, addRi_netWeight, addRi_rate, addRi_search, addRi_supplier;
    @FXML
    private TextField addEr_id, addEr_netWeight, addEr_rate, addEr_search, addEr_supplier;
    @FXML
    private TextField addPe_id, addPe_netWeight, addPe_rate, addPe_search, addPe_supplier;
    @FXML
    private TextField addSu_id, addSu_netWeight, addSu_rate, addSu_search, addSu_supplier;
    @FXML
    private TextField addPa_id, addPa_netWeight, addPa_rate, addPa_search, addPa_supplier;
    @FXML
    private TextField addNe_id, addNe_netWeight, addNe_rate, addNe_search, addNe_supplier;
    @FXML
    private TextField addBa_id, addBa_netWeight, addBa_rate, addBa_search, addBa_supplier;


    @FXML
    private ImageView addChain_img;
    @FXML
    private ImageView addBr_img;
    @FXML
    private ImageView addRi_img;
    @FXML
    private ImageView addEr_img;
    @FXML
    private ImageView addPe_img;
    @FXML
    private ImageView addSu_img;
    @FXML
    private ImageView addPa_img;
    @FXML
    private ImageView addNe_img;
    @FXML
    private ImageView addBa_img;


    @FXML
    private TableView<chainData> addChain_tableView;
    @FXML
    private TableView<braceletData> addBr_tableView;
    @FXML
    private TableView<ringData> addRi_tableView;
    @FXML
    private TableView<earringData> addEr_tableView;
    @FXML
    private TableView<pendantData> addPe_tableView;
    @FXML
    private TableView<suraData> addSu_tableView;
    @FXML
    private TableView<panchaData> addPa_tableView;
    @FXML
    private TableView<necklaceData> addNe_tableView;
    @FXML
    private TableView<bangleData> addBa_tableView;


    @FXML
    private Button chainBtn, close, homeBtn, logout, minimize, salesBtn, brBtn, riBtn, eaBtn, peBtn, suBtn, paBtn, neBtn, baBtn;
    @FXML
    private AnchorPane chain_form, home_availableStock, home_form, home_numberSales, home_totalIncome, main_form, sales_form, br_form, ri_form, ea_form, pe_form, su_form, pa_form, ne_form, ba_form;
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
    private Button sales_payBtn, sales_receiptBtn, sales_soldBtn, sales_addBtn, sales_removeBtn, sales_stockBtn;
    @FXML
    private Label home_sales,home_income,home_stock;


    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;


    private Image chainImage;
    private Image brImage;
    private Image riImage;
    private Image erImage;
    private Image peImage;
    private Image suImage;
    private Image paImage;
    private Image neImage;
    private Image baImage;


    private int customerid;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();
        defaultNav();
        salesShowListData();

        // Add listener to sales_amount to update sales_balance when it changes
        sales_amount.textProperty().addListener((observable, oldValue, newValue) -> updateSalesBalance());

        // Display today's total sales count, total payable amount, and total stock count
        displayTodaySales();
        displayTodayIncome();
        displayTotalStock();
        displayIncomeChart();
        displaySalesChart();

        addChainShowListData();
        addChainListCategory();
        addChainListWeight();
        addChainListLength();
        addChainListKarat();
        addChainListStatus();
        addChainSearch();

        addBraceletShowListData();
        addBrListCategory();
        addBrListWeight();
        addBrListLength();
        addBrListKarat();
        addBrListStatus();
        addBraceletSearch();

        addRingShowListData();
        addRiListCategory();
        addRiListKarat();
        addRiListStatus();
        addRingSearch();

        addEarringShowListData();
        addErListCategory();
        addErListKarat();
        addErListStatus();
        addEarringSearch();

        addPendantShowListData();
        addPeListCategory();
        addPeListKarat();
        addPeListStatus();
        addPendantSearch();

        addSuraShowListData();
        addSuListCategory();
        addSuListKarat();
        addSuListStatus();
        addSuraSearch();

        addPanchaShowListData();
        addPaListCategory();
        addPaListKarat();
        addPaListStatus();
        addPanchaSearch();
    }

    //********************************HOME PAGE REALTED*****************************************************************

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

    //************************************SALES TABLE DISPLAY***********************************************************

    @FXML
    private void handleSoldItemsButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SoldItemsPopup.fxml"));
            Parent root = loader.load();

            SoldItemsPopupController popupController = loader.getController();
            popupController.setSalesData(getAllSalesData());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sold Items");
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

    //************************************NEW SALES*********************************************************************

    // Method to add products to the sales table
    @FXML
    private void handleAddButtonClick() {
        String productId = sales_id.getText();

        //******************CHANGE THIS WITH PAGES************************************************

        chainData chainDetails = getChainDetails(productId);
        braceletData braceletDetails = getBraceletDetails(productId);
        ringData ringDetails = getRingDetails(productId);
        earringData earringDetails = getEarringDetails(productId);
        pendantData pendantDetails = getPendantDetails(productId);
        suraData suraDetails = getSuraDetails(productId);
        panchaData panchaDetails = getPanchaDetails(productId);

        if (chainDetails == null && braceletDetails == null && ringDetails == null && earringDetails == null && pendantDetails == null
         && suraDetails == null  && panchaDetails == null) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Product ID not found");
            return;
        }

        //****************************************************************************************

        String priceStr = sales_price.getText();
        String returnValueStr = sales_return.getText();

        if (productId.isEmpty() || priceStr.isEmpty() || returnValueStr.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill all fields");
            return;
        }

        double price = Double.parseDouble(priceStr);
        double returnValue = Double.parseDouble(returnValueStr);

        //****************************************Change this also******************************************************
        if (chainDetails != null) {
            handleChainData(chainDetails, price, returnValue);
        } else if (braceletDetails != null) {
            handleBraceletData(braceletDetails, price, returnValue);
        } else if (ringDetails != null) {
            handleRingData(ringDetails, price, returnValue);
        } else if (earringDetails != null) {
            handleEarringData(earringDetails, price, returnValue);
        } else if (pendantDetails != null) {
            handlePendantData(pendantDetails, price, returnValue);
        } else if (suraDetails != null) {
            handleSuraData(suraDetails, price, returnValue);
        } else if (panchaDetails != null) {
            handlePanchaData(panchaDetails, price, returnValue);
        }
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
                removeProductDetails(sale.getProduct_id());
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

    //*******************************REMOVING DATA AFTER THE PAYMENT****************************************************

    private void removeProductDetails(String productId) {
        String deleteChainSql = "DELETE FROM chain_details WHERE product_id = ?";
        String deleteBraceletSql = "DELETE FROM bracelet_details WHERE product_id = ?";
        String deleteRingSql = "DELETE FROM ring_details WHERE product_id = ?";
        String deleteEarringSql = "DELETE FROM earring_details WHERE product_id = ?";
        String deletePendantSql = "DELETE FROM pendant_details WHERE product_id = ?";
        String deleteSuraSql = "DELETE FROM sura_details WHERE product_id = ?";
        String deletePanchaSql = "DELETE FROM pancha_details WHERE product_id = ?";

        connect = connectDb();
        try {
            // Delete chain details
            prepare = connect.prepareStatement(deleteChainSql);
            prepare.setString(1, productId);
            prepare.executeUpdate();

            // Delete bracelet details
            prepare = connect.prepareStatement(deleteBraceletSql);
            prepare.setString(1, productId);
            prepare.executeUpdate();

            // Delete ring details
            prepare = connect.prepareStatement(deleteRingSql);
            prepare.setString(1, productId);
            prepare.executeUpdate();

            // Delete earring details
            prepare = connect.prepareStatement(deleteEarringSql);
            prepare.setString(1, productId);
            prepare.executeUpdate();

            // Delete pendant details
            prepare = connect.prepareStatement(deletePendantSql);
            prepare.setString(1, productId);
            prepare.executeUpdate();

            // Delete sura details
            prepare = connect.prepareStatement(deleteSuraSql);
            prepare.setString(1, productId);
            prepare.executeUpdate();

            // Delete pancha details
            prepare = connect.prepareStatement(deletePanchaSql);
            prepare.setString(1, productId);
            prepare.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //******************************************************************************************************************


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

    private boolean isProductIdExistInSales(String productId) {
        String checkSql = "SELECT COUNT(*) FROM sales WHERE product_id = ?";
        try {
            prepare = connect.prepareStatement(checkSql);
            prepare.setString(1, productId);
            result = prepare.executeQuery();
            if (result.next()) {
                return result.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    private void handleSalesStockButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StockChange.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Stock Changing");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] listKarat = {"24K", "23K", "22K", "21K", "20K", "19K", "18K"};
    private String[] listStatus = {"Stock", "Showroom"};
    //*************************************************CHAIN RELATED****************************************************

    private String[] listCategory = {"Box Chain", "Double Box", "Dragon Box", "Bismark Chain", "KDM Chain", "Double Albert", "Albert Chain", "Lotus Chain", "Lazer Chain", "Link Chain", "Rope Chain", "Sapna Chain", "Ball Chain", "Diamond Chain", "Lee Chain", "SP Chain", "18K Chain"};
    private String[] listWeight = {"40g", "32g", "24g", "20g", "16g", "14g", "12g", "10g", "8g", "6g", "5g", "4.5g", "4g", "3.5g", "3g", "2.5g", "2g", "1.5g", "1g"};
    private String[] listLength = {"24 in", "22 in", "20 in", "18 in", "16 in", "14 in"};

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
            } else if (!isChainIdValid(addChain_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID format is incorrect. It should start with 'C' followed by 4 digits.");
                alert.showAndWait();
            } else if (isChainIdExist(addChain_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID already exists. Please use a unique Product ID.");
                alert.showAndWait();
            } else if (isProductIdExistInSales(addChain_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID already exists in sales table. Please use a unique Product ID.");
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
        if (!isChainIdExist(addChain_id.getText())) {
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
            chainImage = new Image(file.toURI().toString(), 90, 100, false, true);
            addChain_img.setImage(chainImage);
        }
    }

    public void addChainListCategory() {
        List<String> listC = new ArrayList<>();

        for(String data:listCategory){
            listC.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listCategory);
        addChain_category.setItems(listData);
    }

    public void addChainListWeight() {
        List<String> listW = new ArrayList<>();

        for(String data:listWeight){
            listW.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listWeight);
        addChain_weight.setItems(listData);
    }

    public void addChainListLength() {
        List<String> listL = new ArrayList<>();

        for(String data:listLength){
            listL.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listLength);
        addChain_length.setItems(listData);
    }

    public void addChainListKarat() {
        List<String> listK = new ArrayList<>();

        for(String data:listKarat){
            listK.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listKarat);
        addChain_karat.setItems(listData);
    }

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

        if (chD.getImage() != null && !chD.getImage().isEmpty()) {
            String uri = "file:" + chD.getImage();
            chainImage = new Image(uri, 90, 100, false, true);
            addChain_img.setImage(chainImage);
            getData.path = chD.getImage();
        } else {
            addChain_img.setImage(null);
        }
    }

    private boolean isChainIdExist(String productId) {
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

    private boolean isChainIdValid(String productId) {
        // Check if productId starts with 'C' and has exactly 4 digits
        return productId.matches("^C\\d{4}$");
    }

    // Method to handle chain data
    private void handleChainData(chainData chainDetails, double price, double returnValue) {
        double minPrice = chainDetails.getGold_rate() * chainDetails.getNet_weight() / 8;

        if (price < minPrice) {
            // Show alert with OK and Continue Anyway options
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Sales price must be greater than " + minPrice);

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType continueButton = new ButtonType("Continue Anyway", ButtonBar.ButtonData.OTHER);
            alert.getButtonTypes().setAll(okButton, continueButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == continueButton) {
                boolean passcodeVerified = verifyPasscode();
                if (!passcodeVerified) {
                    showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid passcode");
                    return;
                }
            } else {
                return;
            }
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

    // **************************************BRACELET RELATED***********************************************************

    private String[] listBrCategory = {"Price","Lazer"};
    private String[] listBrWeight = {"40g", "32g"};
    private String[] listBrLength = {"24 in", "22 in"};

    // Method to get chain details from the database
    private braceletData getBraceletDetails(String productId) {
        String sql = "SELECT * FROM bracelet_details WHERE product_id = ?";
        connect = connectDb();
        braceletData braceletDetails = null;
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, productId);
            result = prepare.executeQuery();
            if (result.next()) {
                braceletDetails = new braceletData(
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
        return braceletDetails;
    }

    public void addBraceletAdd() {
        String sql = "INSERT INTO bracelet_details (product_id, category, weight, net_weight, length, karat, gold_rate, supplier, status, image, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        connect = connectDb();
        try {
            Alert alert;

            if (addBr_id.getText().isEmpty()
                    || addBr_category.getSelectionModel().getSelectedItem() == null
                    || addBr_weight.getSelectionModel().getSelectedItem() == null
                    || addBr_netWeight.getText().isEmpty()
                    || addBr_length.getSelectionModel().getSelectedItem() == null
                    || addBr_karat.getSelectionModel().getSelectedItem() == null
                    || addBr_rate.getText().isEmpty()
                    || addBr_supplier.getText().isEmpty()
                    || addBr_status.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else if (!isBraceletIdValid(addBr_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID format is incorrect. It should start with 'B' followed by 4 digits.");
                alert.showAndWait();
            } else if (isBraceletIdExist(addBr_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID already exists. Please use a unique Product ID.");
                alert.showAndWait();
            } else if (isProductIdExistInSales(addBr_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID already exists in sales table. Please use a unique Product ID.");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                String productId = addBr_id.getText();
                prepare.setString(1, productId);
                prepare.setString(2, addBr_category.getSelectionModel().getSelectedItem());
                prepare.setString(3, addBr_weight.getSelectionModel().getSelectedItem());
                prepare.setString(4, addBr_netWeight.getText());
                prepare.setString(5, addBr_length.getSelectionModel().getSelectedItem());
                prepare.setString(6, addBr_karat.getSelectionModel().getSelectedItem());
                prepare.setString(7, addBr_rate.getText());
                prepare.setString(8, addBr_supplier.getText());
                prepare.setString(9, addBr_status.getSelectionModel().getSelectedItem());

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

                addBraceletShowListData();
                addBraceletReset();

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

    public void addBraceletUpdate() {
        // Check if the status is selected
        if (addBr_status.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the new status");
            alert.showAndWait();
            return;
        }

        // Ensure the Product ID field is filled
        if (addBr_id.getText().isEmpty()) {
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
        confirmationAlert.setContentText("Are you sure you want to UPDATE the status for Product_ID: " + addBr_id.getText() + "?");

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
            String sql = "UPDATE bracelet_details SET status = ? WHERE product_id = ?";

            connect = connectDb();
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addBr_status.getSelectionModel().getSelectedItem());
                prepare.setString(2, addBr_id.getText());

                int rowsUpdated = prepare.executeUpdate();

                if (rowsUpdated > 0) {
                    addBraceletShowListData();
                    addBraceletReset();

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

    public void addBraceletDelete() {
        // Ensure the Product ID field is filled
        if (addBr_id.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        }

        // Check if the Product ID exists
        if (!isBraceletIdExist(addBr_id.getText())) {
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
        confirmationAlert.setContentText("Are you sure you want to DELETE Product_ID: " + addBr_id.getText() + "?");

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
            String sql = "DELETE FROM bracelet_details WHERE product_id = ?";
            connect = connectDb();
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addBr_id.getText());

                int rowsDeleted = prepare.executeUpdate();
                if (rowsDeleted > 0) {
                    addBraceletShowListData();
                    addBraceletReset();

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

    public void addBraceletReset() {
        addBr_id.setText("");
        addBr_category.getSelectionModel().clearSelection();
        addBr_weight.getSelectionModel().clearSelection();
        addBr_netWeight.setText("");
        addBr_length.getSelectionModel().clearSelection();
        addBr_karat.getSelectionModel().clearSelection();
        addBr_rate.setText("");
        addBr_supplier.setText("");
        addBr_status.getSelectionModel().clearSelection();
        addBr_img.setImage(null);
        getData.path = "";
    }

    public void addBrImportImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            getData.path = file.getAbsolutePath();
            brImage = new Image(file.toURI().toString(), 90, 100, false, true);
            addBr_img.setImage(brImage);
        }
    }

    public void addBrListCategory() {
        List<String> listC = new ArrayList<>();

        for(String data:listBrCategory){
            listC.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listBrCategory);
        addBr_category.setItems(listData);
    }

    public void addBrListWeight() {
        List<String> listW = new ArrayList<>();

        for(String data:listBrWeight){
            listW.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listBrWeight);
        addBr_weight.setItems(listData);
    }

    public void addBrListLength() {
        List<String> listL = new ArrayList<>();

        for(String data:listBrLength){
            listL.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listBrLength);
        addBr_length.setItems(listData);
    }

    public void addBrListKarat() {
        List<String> listK = new ArrayList<>();

        for(String data:listKarat){
            listK.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listKarat);
        addBr_karat.setItems(listData);
    }

    public void addBrListStatus() {
        List<String> listS = new ArrayList<>();

        for(String data:listStatus){
            listS.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listStatus);
        addBr_status.setItems(listData);
    }

    public void addBraceletSearch() {
        FilteredList<braceletData> filter = new FilteredList<>(addBraceletList, e -> true);

        addBr_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateBrData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();

                if (predicateBrData.getProductId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateBrData.getCategory().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateBrData.getWeight().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateBrData.getNet_weight().toString().contains(searchKey)) {
                    return true;
                } else if (predicateBrData.getLength().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateBrData.getKarat().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateBrData.getGold_rate().toString().contains(searchKey)) {
                    return true;
                } else if (predicateBrData.getSupplier().toString().contains(searchKey)) {
                    return true;
                } else if (predicateBrData.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<braceletData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(addBr_tableView.comparatorProperty());
        addBr_tableView.setItems(sortList);
    }

    public ObservableList<braceletData> addBraceletListData() {
        ObservableList<braceletData> braceletList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM bracelet_details";
        connect = connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            braceletData brD;

            while (result.next()) {
                brD = new braceletData(result.getString("product_id"),
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

                braceletList.add(brD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return braceletList;
    }

    private ObservableList<braceletData> addBraceletList;

    public void addBraceletShowListData() {
        addBraceletList = addBraceletListData();

        addBr_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        addBr_col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        addBr_col_weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        addBr_col_netWeight.setCellValueFactory(new PropertyValueFactory<>("net_weight"));
        addBr_col_length.setCellValueFactory(new PropertyValueFactory<>("length"));
        addBr_col_karat.setCellValueFactory(new PropertyValueFactory<>("karat"));
        addBr_col_goldRate.setCellValueFactory(new PropertyValueFactory<>("gold_rate"));
        addBr_col_supplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        addBr_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set custom cell factories to format double values to two decimal places
        addBr_col_netWeight.setCellFactory(getDoubleCellFactory());
        addBr_col_goldRate.setCellFactory(getDoubleCellFactory());

        addBr_tableView.setItems(addBraceletList);
    }

    public void addBraceletSelect() {
        braceletData brD = addBr_tableView.getSelectionModel().getSelectedItem();
        int num = addBr_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addBr_id.setText(String.valueOf(brD.getProductId()));

        if (brD.getImage() != null && !brD.getImage().isEmpty()) {
            String uri = "file:" + brD.getImage();
            brImage = new Image(uri, 90, 100, false, true);
            addBr_img.setImage(brImage);
            getData.path = brD.getImage();
        } else {
            addBr_img.setImage(null);
        }
    }

    private boolean isBraceletIdExist(String productId) {
        String sql = "SELECT COUNT(*) FROM bracelet_details WHERE product_id = ?";
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

    private boolean isBraceletIdValid(String productId) {
        // Check if productId starts with 'C' and has exactly 4 digits
        return productId.matches("^B\\d{4}$");
    }

    // Method to handle bracelet data
    private void handleBraceletData(braceletData braceletDetails, double price, double returnValue) {
        double minPrice = braceletDetails.getGold_rate() * braceletDetails.getNet_weight() / 8;

        if (price < minPrice) {
            // Show alert with OK and Continue Anyway options
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Sales price must be greater than " + minPrice);

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType continueButton = new ButtonType("Continue Anyway", ButtonBar.ButtonData.OTHER);
            alert.getButtonTypes().setAll(okButton, continueButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == continueButton) {
                boolean passcodeVerified = verifyPasscode();
                if (!passcodeVerified) {
                    showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid passcode");
                    return;
                }
            } else {
                return;
            }
        }

        if (returnValue > price) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid Exchange Value!");
            return;
        }

        customerId();  // Ensure customer ID is set

        salesData newSalesData = new salesData(
                customerid,
                braceletDetails.getProductId(),
                braceletDetails.getCategory(),
                braceletDetails.getWeight(),
                braceletDetails.getNet_weight(),
                braceletDetails.getLength(),
                braceletDetails.getKarat(),
                braceletDetails.getGold_rate(),
                price,
                returnValue,
                new java.sql.Date(new Date().getTime())
        );

        sales_tableView.getItems().add(newSalesData);
        clearSalesInputFields();
        updateSalesTotal();
    }

    // **************************************RING RELATED***********************************************************

    private String[] listRiCategory = {"Stone Ring","Wedding Ring"};

    // Method to get ring details from the database
    private ringData getRingDetails(String productId) {
        String sql = "SELECT * FROM ring_details WHERE product_id = ?";
        connect = connectDb();
        ringData ringDetails = null;
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, productId);
            result = prepare.executeQuery();
            if (result.next()) {
                ringDetails = new ringData(
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
        return ringDetails;
    }

    public void addRingAdd() {
        String sql = "INSERT INTO ring_details (product_id, category, net_weight, karat, gold_rate, supplier, status, image, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        connect = connectDb();
        try {
            Alert alert;

            if (addRi_id.getText().isEmpty()
                    || addRi_category.getSelectionModel().getSelectedItem() == null
                    || addRi_netWeight.getText().isEmpty()
                    || addRi_karat.getSelectionModel().getSelectedItem() == null
                    || addRi_rate.getText().isEmpty()
                    || addRi_supplier.getText().isEmpty()
                    || addRi_status.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else if (!isRingIdValid(addRi_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID format is incorrect. It should start with 'R' followed by 4 digits.");
                alert.showAndWait();
            } else if (isRingIdExist(addRi_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID already exists. Please use a unique Product ID.");
                alert.showAndWait();
            } else if (isProductIdExistInSales(addRi_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID already exists in sales table. Please use a unique Product ID.");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                String productId = addRi_id.getText();
                prepare.setString(1, productId);
                prepare.setString(2, addRi_category.getSelectionModel().getSelectedItem());
                //prepare.setString(3, addRi_weight.getSelectionModel().getSelectedItem());
                prepare.setString(3, addRi_netWeight.getText());
                //prepare.setString(5, addRi_length.getSelectionModel().getSelectedItem());
                prepare.setString(4, addRi_karat.getSelectionModel().getSelectedItem());
                prepare.setString(5, addRi_rate.getText());
                prepare.setString(6, addRi_supplier.getText());
                prepare.setString(7, addRi_status.getSelectionModel().getSelectedItem());

                String uri = getData.path;
                if (uri != null && !uri.isEmpty()) {
                    uri = uri.replace("\\", "\\\\");
                    prepare.setString(8, uri);
                } else {
                    prepare.setString(8, null);
                }

                Date date = new Date();
                java.sql.Date sqldate = new java.sql.Date(date.getTime());

                prepare.setString(9, String.valueOf(sqldate));

                prepare.executeUpdate();

                addRingShowListData();
                addRingReset();

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

    public void addRingUpdate() {
        // Check if the status is selected
        if (addRi_status.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the new status");
            alert.showAndWait();
            return;
        }

        // Ensure the Product ID field is filled
        if (addRi_id.getText().isEmpty()) {
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
        confirmationAlert.setContentText("Are you sure you want to UPDATE the status for Product_ID: " + addRi_id.getText() + "?");

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
            String sql = "UPDATE ring_details SET status = ? WHERE product_id = ?";

            connect = connectDb();
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addRi_status.getSelectionModel().getSelectedItem());
                prepare.setString(2, addRi_id.getText());

                int rowsUpdated = prepare.executeUpdate();

                if (rowsUpdated > 0) {
                    addRingShowListData();
                    addRingReset();

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

    public void addRingDelete() {
        // Ensure the Product ID field is filled
        if (addRi_id.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        }

        // Check if the Product ID exists
        if (!isRingIdExist(addRi_id.getText())) {
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
        confirmationAlert.setContentText("Are you sure you want to DELETE Product_ID: " + addRi_id.getText() + "?");

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
            String sql = "DELETE FROM ring_details WHERE product_id = ?";
            connect = connectDb();
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addRi_id.getText());

                int rowsDeleted = prepare.executeUpdate();
                if (rowsDeleted > 0) {
                    addRingShowListData();
                    addRingReset();

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

    public void addRingReset() {
        addRi_id.setText("");
        addRi_category.getSelectionModel().clearSelection();
        //addRi_weight.getSelectionModel().clearSelection();
        addRi_netWeight.setText("");
        //addRi_length.getSelectionModel().clearSelection();
        addRi_karat.getSelectionModel().clearSelection();
        addRi_rate.setText("");
        addRi_supplier.setText("");
        addRi_status.getSelectionModel().clearSelection();
        addRi_img.setImage(null);
        getData.path = "";
    }

    public void addRiImportImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            getData.path = file.getAbsolutePath();
            riImage = new Image(file.toURI().toString(), 90, 100, false, true);
            addRi_img.setImage(riImage);
        }
    }

    public void addRiListCategory() {
        List<String> listC = new ArrayList<>();

        for(String data:listRiCategory){
            listC.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listRiCategory);
        addRi_category.setItems(listData);
    }

    public void addRiListKarat() {
        List<String> listK = new ArrayList<>();

        for(String data:listKarat){
            listK.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listKarat);
        addRi_karat.setItems(listData);
    }

    public void addRiListStatus() {
        List<String> listS = new ArrayList<>();

        for(String data:listStatus){
            listS.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listStatus);
        addRi_status.setItems(listData);
    }

    public void addRingSearch() {
        FilteredList<ringData> filter = new FilteredList<>(addRingList, e -> true);

        addRi_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateRiData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();

                if (predicateRiData.getProductId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateRiData.getCategory().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateRiData.getNet_weight().toString().contains(searchKey)) {
                    return true;
                } else if (predicateRiData.getKarat().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateRiData.getGold_rate().toString().contains(searchKey)) {
                    return true;
                } else if (predicateRiData.getSupplier().toString().contains(searchKey)) {
                    return true;
                } else if (predicateRiData.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<ringData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(addRi_tableView.comparatorProperty());
        addRi_tableView.setItems(sortList);
    }

    public ObservableList<ringData> addRingListData() {
        ObservableList<ringData> ringList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM ring_details";
        connect = connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            ringData riD;

            while (result.next()) {
                riD = new ringData(result.getString("product_id"),
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

                ringList.add(riD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ringList;
    }

    private ObservableList<ringData> addRingList;

    public void addRingShowListData() {
        addRingList = addRingListData();

        addRi_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        addRi_col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        addRi_col_weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        addRi_col_netWeight.setCellValueFactory(new PropertyValueFactory<>("net_weight"));
        addRi_col_length.setCellValueFactory(new PropertyValueFactory<>("length"));
        addRi_col_karat.setCellValueFactory(new PropertyValueFactory<>("karat"));
        addRi_col_goldRate.setCellValueFactory(new PropertyValueFactory<>("gold_rate"));
        addRi_col_supplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        addRi_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set custom cell factories to format double values to two decimal places
        addRi_col_netWeight.setCellFactory(getDoubleCellFactory());
        addRi_col_goldRate.setCellFactory(getDoubleCellFactory());

        addRi_tableView.setItems(addRingList);
    }

    public void addRingSelect() {
        ringData riD = addRi_tableView.getSelectionModel().getSelectedItem();
        int num = addRi_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addRi_id.setText(String.valueOf(riD.getProductId()));

        if (riD.getImage() != null && !riD.getImage().isEmpty()) {
            String uri = "file:" + riD.getImage();
            riImage = new Image(uri, 90, 100, false, true);
            addRi_img.setImage(riImage);
            getData.path = riD.getImage();
        } else {
            addRi_img.setImage(null);
        }
    }

    private boolean isRingIdExist(String productId) {
        String sql = "SELECT COUNT(*) FROM ring_details WHERE product_id = ?";
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

    private boolean isRingIdValid(String productId) {
        // Check if productId starts with 'C' and has exactly 4 digits
        return productId.matches("^R\\d{4}$");
    }

    // Method to handle bracelet data
    private void handleRingData(ringData ringDetails, double price, double returnValue) {
        double minPrice = ringDetails.getGold_rate() * ringDetails.getNet_weight() / 8;

        if (price < minPrice) {
            // Show alert with OK and Continue Anyway options
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Sales price must be greater than " + minPrice);

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType continueButton = new ButtonType("Continue Anyway", ButtonBar.ButtonData.OTHER);
            alert.getButtonTypes().setAll(okButton, continueButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == continueButton) {
                boolean passcodeVerified = verifyPasscode();
                if (!passcodeVerified) {
                    showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid passcode");
                    return;
                }
            } else {
                return;
            }
        }

        if (returnValue > price) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid Exchange Value!");
            return;
        }

        customerId();  // Ensure customer ID is set

        salesData newSalesData = new salesData(
                customerid,
                ringDetails.getProductId(),
                ringDetails.getCategory(),
                ringDetails.getWeight(),
                ringDetails.getNet_weight(),
                ringDetails.getLength(),
                ringDetails.getKarat(),
                ringDetails.getGold_rate(),
                price,
                returnValue,
                new java.sql.Date(new Date().getTime())
        );

        sales_tableView.getItems().add(newSalesData);
        clearSalesInputFields();
        updateSalesTotal();
    }

    // **************************************EARRING RELATED***********************************************************

    private String[] listErCategory = {"Baby Earring","Ladies Earring"};

    // Method to get ring details from the database
    private earringData getEarringDetails(String productId) {
        String sql = "SELECT * FROM earring_details WHERE product_id = ?";
        connect = connectDb();
        earringData earringDetails = null;
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, productId);
            result = prepare.executeQuery();
            if (result.next()) {
                earringDetails = new earringData(
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
        return earringDetails;
    }

    public void addEarringAdd() {
        String sql = "INSERT INTO earring_details (product_id, category, net_weight, karat, gold_rate, supplier, status, image, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        connect = connectDb();
        try {
            Alert alert;

            if (addEr_id.getText().isEmpty()
                    || addEr_category.getSelectionModel().getSelectedItem() == null
                    || addEr_netWeight.getText().isEmpty()
                    || addEr_karat.getSelectionModel().getSelectedItem() == null
                    || addEr_rate.getText().isEmpty()
                    || addEr_supplier.getText().isEmpty()
                    || addEr_status.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else if (!isEarringIdValid(addEr_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID format is incorrect. It should start with 'E' followed by 4 digits.");
                alert.showAndWait();
            } else if (isEarringIdExist(addEr_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID already exists. Please use a unique Product ID.");
                alert.showAndWait();
            } else if (isProductIdExistInSales(addEr_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID already exists in sales table. Please use a unique Product ID.");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                String productId = addEr_id.getText();
                prepare.setString(1, productId);
                prepare.setString(2, addEr_category.getSelectionModel().getSelectedItem());
                prepare.setString(3, addEr_netWeight.getText());
                prepare.setString(4, addEr_karat.getSelectionModel().getSelectedItem());
                prepare.setString(5, addEr_rate.getText());
                prepare.setString(6, addEr_supplier.getText());
                prepare.setString(7, addEr_status.getSelectionModel().getSelectedItem());

                String uri = getData.path;
                if (uri != null && !uri.isEmpty()) {
                    uri = uri.replace("\\", "\\\\");
                    prepare.setString(8, uri);
                } else {
                    prepare.setString(8, null);
                }

                Date date = new Date();
                java.sql.Date sqldate = new java.sql.Date(date.getTime());

                prepare.setString(9, String.valueOf(sqldate));

                prepare.executeUpdate();

                addEarringShowListData();
                addEarringReset();

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

    public void addEarringUpdate() {
        // Check if the status is selected
        if (addEr_status.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the new status");
            alert.showAndWait();
            return;
        }

        // Ensure the Product ID field is filled
        if (addEr_id.getText().isEmpty()) {
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
        confirmationAlert.setContentText("Are you sure you want to UPDATE the status for Product_ID: " + addEr_id.getText() + "?");

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
            String sql = "UPDATE earring_details SET status = ? WHERE product_id = ?";

            connect = connectDb();
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addEr_status.getSelectionModel().getSelectedItem());
                prepare.setString(2, addEr_id.getText());

                int rowsUpdated = prepare.executeUpdate();

                if (rowsUpdated > 0) {
                    addEarringShowListData();
                    addEarringReset();

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

    public void addEarringDelete() {
        // Ensure the Product ID field is filled
        if (addEr_id.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        }

        // Check if the Product ID exists
        if (!isEarringIdExist(addEr_id.getText())) {
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
        confirmationAlert.setContentText("Are you sure you want to DELETE Product_ID: " + addEr_id.getText() + "?");

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
            String sql = "DELETE FROM earring_details WHERE product_id = ?";
            connect = connectDb();
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addEr_id.getText());

                int rowsDeleted = prepare.executeUpdate();
                if (rowsDeleted > 0) {
                    addEarringShowListData();
                    addEarringReset();

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

    public void addEarringReset() {
        addEr_id.setText("");
        addEr_category.getSelectionModel().clearSelection();
        addEr_netWeight.setText("");
        addEr_karat.getSelectionModel().clearSelection();
        addEr_rate.setText("");
        addEr_supplier.setText("");
        addEr_status.getSelectionModel().clearSelection();
        addEr_img.setImage(null);
        getData.path = "";
    }

    public void addErImportImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            getData.path = file.getAbsolutePath();
            erImage = new Image(file.toURI().toString(), 90, 100, false, true);
            addEr_img.setImage(erImage);
        }
    }

    public void addErListCategory() {
        List<String> listC = new ArrayList<>();

        for(String data:listErCategory){
            listC.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listErCategory);
        addEr_category.setItems(listData);
    }

    public void addErListKarat() {
        List<String> listK = new ArrayList<>();

        for(String data:listKarat){
            listK.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listKarat);
        addEr_karat.setItems(listData);
    }

    public void addErListStatus() {
        List<String> listS = new ArrayList<>();

        for(String data:listStatus){
            listS.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listStatus);
        addEr_status.setItems(listData);
    }

    public void addEarringSearch() {
        FilteredList<earringData> filter = new FilteredList<>(addEarringList, e -> true);

        addEr_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateErData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();

                if (predicateErData.getProductId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateErData.getCategory().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateErData.getNet_weight().toString().contains(searchKey)) {
                    return true;
                } else if (predicateErData.getKarat().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateErData.getGold_rate().toString().contains(searchKey)) {
                    return true;
                } else if (predicateErData.getSupplier().toString().contains(searchKey)) {
                    return true;
                } else if (predicateErData.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<earringData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(addEr_tableView.comparatorProperty());
        addEr_tableView.setItems(sortList);
    }

    public ObservableList<earringData> addEarringListData() {
        ObservableList<earringData> earringList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM earring_details";
        connect = connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            earringData erD;

            while (result.next()) {
                erD = new earringData(result.getString("product_id"),
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

                earringList.add(erD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return earringList;
    }

    private ObservableList<earringData> addEarringList;

    public void addEarringShowListData() {
        addEarringList = addEarringListData();

        addEr_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        addEr_col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        addEr_col_weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        addEr_col_netWeight.setCellValueFactory(new PropertyValueFactory<>("net_weight"));
        addEr_col_length.setCellValueFactory(new PropertyValueFactory<>("length"));
        addEr_col_karat.setCellValueFactory(new PropertyValueFactory<>("karat"));
        addEr_col_goldRate.setCellValueFactory(new PropertyValueFactory<>("gold_rate"));
        addEr_col_supplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        addEr_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set custom cell factories to format double values to two decimal places
        addEr_col_netWeight.setCellFactory(getDoubleCellFactory());
        addEr_col_goldRate.setCellFactory(getDoubleCellFactory());

        addEr_tableView.setItems(addEarringList);
    }

    public void addEarringSelect() {
        earringData erD = addEr_tableView.getSelectionModel().getSelectedItem();
        int num = addEr_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addEr_id.setText(String.valueOf(erD.getProductId()));

        if (erD.getImage() != null && !erD.getImage().isEmpty()) {
            String uri = "file:" + erD.getImage();
            erImage = new Image(uri, 90, 100, false, true);
            addEr_img.setImage(erImage);
            getData.path = erD.getImage();
        } else {
            addEr_img.setImage(null);
        }
    }

    private boolean isEarringIdExist(String productId) {
        String sql = "SELECT COUNT(*) FROM earring_details WHERE product_id = ?";
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

    private boolean isEarringIdValid(String productId) {
        // Check if productId starts with 'C' and has exactly 4 digits
        return productId.matches("^E\\d{4}$");
    }

    // Method to handle earring data
    private void handleEarringData(earringData earringDetails, double price, double returnValue) {
        double minPrice = earringDetails.getGold_rate() * earringDetails.getNet_weight() / 8;

        if (price < minPrice) {
            // Show alert with OK and Continue Anyway options
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Sales price must be greater than " + minPrice);

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType continueButton = new ButtonType("Continue Anyway", ButtonBar.ButtonData.OTHER);
            alert.getButtonTypes().setAll(okButton, continueButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == continueButton) {
                boolean passcodeVerified = verifyPasscode();
                if (!passcodeVerified) {
                    showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid passcode");
                    return;
                }
            } else {
                return;
            }
        }

        if (returnValue > price) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid Exchange Value!");
            return;
        }

        customerId();  // Ensure customer ID is set

        salesData newSalesData = new salesData(
                customerid,
                earringDetails.getProductId(),
                earringDetails.getCategory(),
                earringDetails.getWeight(),
                earringDetails.getNet_weight(),
                earringDetails.getLength(),
                earringDetails.getKarat(),
                earringDetails.getGold_rate(),
                price,
                returnValue,
                new java.sql.Date(new Date().getTime())
        );

        sales_tableView.getItems().add(newSalesData);
        clearSalesInputFields();
        updateSalesTotal();
    }

    // **************************************PENDANT RELATED***********************************************************

    private String[] listPeCategory = {"Ladies Pendant","Gents Pendant"};

    // Method to get pendant details from the database
    private pendantData getPendantDetails(String productId) {
        String sql = "SELECT * FROM pendant_details WHERE product_id = ?";
        connect = connectDb();
        pendantData pendantDetails = null;
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, productId);
            result = prepare.executeQuery();
            if (result.next()) {
                pendantDetails = new pendantData(
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
        return pendantDetails;
    }

    public void addPendantAdd() {
        String sql = "INSERT INTO pendant_details (product_id, category, net_weight, karat, gold_rate, supplier, status, image, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        connect = connectDb();
        try {
            Alert alert;

            if (addPe_id.getText().isEmpty()
                    || addPe_category.getSelectionModel().getSelectedItem() == null
                    || addPe_netWeight.getText().isEmpty()
                    || addPe_karat.getSelectionModel().getSelectedItem() == null
                    || addPe_rate.getText().isEmpty()
                    || addPe_supplier.getText().isEmpty()
                    || addPe_status.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else if (!isPendantIdValid(addPe_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID format is incorrect. It should start with 'P' followed by 4 digits.");
                alert.showAndWait();
            } else if (isPendantIdExist(addPe_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID already exists. Please use a unique Product ID.");
                alert.showAndWait();
            } else if (isProductIdExistInSales(addPe_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID already exists in sales table. Please use a unique Product ID.");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                String productId = addPe_id.getText();
                prepare.setString(1, productId);
                prepare.setString(2, addPe_category.getSelectionModel().getSelectedItem());
                prepare.setString(3, addPe_netWeight.getText());
                prepare.setString(4, addPe_karat.getSelectionModel().getSelectedItem());
                prepare.setString(5, addPe_rate.getText());
                prepare.setString(6, addPe_supplier.getText());
                prepare.setString(7, addPe_status.getSelectionModel().getSelectedItem());

                String uri = getData.path;
                if (uri != null && !uri.isEmpty()) {
                    uri = uri.replace("\\", "\\\\");
                    prepare.setString(8, uri);
                } else {
                    prepare.setString(8, null);
                }

                Date date = new Date();
                java.sql.Date sqldate = new java.sql.Date(date.getTime());

                prepare.setString(9, String.valueOf(sqldate));

                prepare.executeUpdate();

                addPendantShowListData();
                addPendantReset();

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

    public void addPendantUpdate() {
        // Check if the status is selected
        if (addPe_status.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the new status");
            alert.showAndWait();
            return;
        }

        // Ensure the Product ID field is filled
        if (addPe_id.getText().isEmpty()) {
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
        confirmationAlert.setContentText("Are you sure you want to UPDATE the status for Product_ID: " + addPe_id.getText() + "?");

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
            String sql = "UPDATE pendant_details SET status = ? WHERE product_id = ?";

            connect = connectDb();
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addPe_status.getSelectionModel().getSelectedItem());
                prepare.setString(2, addPe_id.getText());

                int rowsUpdated = prepare.executeUpdate();

                if (rowsUpdated > 0) {
                    addPendantShowListData();
                    addPendantReset();

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

    public void addPendantDelete() {
        // Ensure the Product ID field is filled
        if (addPe_id.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        }

        // Check if the Product ID exists
        if (!isPendantIdExist(addPe_id.getText())) {
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
        confirmationAlert.setContentText("Are you sure you want to DELETE Product_ID: " + addPe_id.getText() + "?");

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
            String sql = "DELETE FROM pendant_details WHERE product_id = ?";
            connect = connectDb();
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addPe_id.getText());

                int rowsDeleted = prepare.executeUpdate();
                if (rowsDeleted > 0) {
                    addPendantShowListData();
                    addPendantReset();

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

    public void addPendantReset() {
        addPe_id.setText("");
        addPe_category.getSelectionModel().clearSelection();
        addPe_netWeight.setText("");
        addPe_karat.getSelectionModel().clearSelection();
        addPe_rate.setText("");
        addPe_supplier.setText("");
        addPe_status.getSelectionModel().clearSelection();
        addPe_img.setImage(null);
        getData.path = "";
    }

    public void addPeImportImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            getData.path = file.getAbsolutePath();
            peImage = new Image(file.toURI().toString(), 90, 100, false, true);
            addPe_img.setImage(peImage);
        }
    }

    public void addPeListCategory() {
        List<String> listC = new ArrayList<>();

        for(String data:listPeCategory){
            listC.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listPeCategory);
        addPe_category.setItems(listData);
    }

    public void addPeListKarat() {
        List<String> listK = new ArrayList<>();

        for(String data:listKarat){
            listK.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listKarat);
        addPe_karat.setItems(listData);
    }

    public void addPeListStatus() {
        List<String> listS = new ArrayList<>();

        for(String data:listStatus){
            listS.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listStatus);
        addPe_status.setItems(listData);
    }

    public void addPendantSearch() {
        FilteredList<pendantData> filter = new FilteredList<>(addPendantList, e -> true);

        addPe_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicatePeData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();

                if (predicatePeData.getProductId().toString().contains(searchKey)) {
                    return true;
                } else if (predicatePeData.getCategory().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicatePeData.getNet_weight().toString().contains(searchKey)) {
                    return true;
                } else if (predicatePeData.getKarat().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicatePeData.getGold_rate().toString().contains(searchKey)) {
                    return true;
                } else if (predicatePeData.getSupplier().toString().contains(searchKey)) {
                    return true;
                } else if (predicatePeData.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<pendantData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(addPe_tableView.comparatorProperty());
        addPe_tableView.setItems(sortList);
    }

    public ObservableList<pendantData> addPendantListData() {
        ObservableList<pendantData> pendantList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM pendant_details";
        connect = connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            pendantData peD;

            while (result.next()) {
                peD = new pendantData(result.getString("product_id"),
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

                pendantList.add(peD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pendantList;
    }

    private ObservableList<pendantData> addPendantList;

    public void addPendantShowListData() {
        addPendantList = addPendantListData();

        addPe_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        addPe_col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        addPe_col_weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        addPe_col_netWeight.setCellValueFactory(new PropertyValueFactory<>("net_weight"));
        addPe_col_length.setCellValueFactory(new PropertyValueFactory<>("length"));
        addPe_col_karat.setCellValueFactory(new PropertyValueFactory<>("karat"));
        addPe_col_goldRate.setCellValueFactory(new PropertyValueFactory<>("gold_rate"));
        addPe_col_supplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        addPe_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set custom cell factories to format double values to two decimal places
        addPe_col_netWeight.setCellFactory(getDoubleCellFactory());
        addPe_col_goldRate.setCellFactory(getDoubleCellFactory());

        addPe_tableView.setItems(addPendantList);
    }

    public void addPendantSelect() {
        pendantData peD = addPe_tableView.getSelectionModel().getSelectedItem();
        int num = addPe_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addPe_id.setText(String.valueOf(peD.getProductId()));

        if (peD.getImage() != null && !peD.getImage().isEmpty()) {
            String uri = "file:" + peD.getImage();
            peImage = new Image(uri, 90, 100, false, true);
            addPe_img.setImage(peImage);
            getData.path = peD.getImage();
        } else {
            addPe_img.setImage(null);
        }
    }

    private boolean isPendantIdExist(String productId) {
        String sql = "SELECT COUNT(*) FROM pendant_details WHERE product_id = ?";
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

    private boolean isPendantIdValid(String productId) {
        // Check if productId starts with 'C' and has exactly 4 digits
        return productId.matches("^P\\d{4}$");
    }

    // Method to handle earring data
    private void handlePendantData(pendantData pendantDetails, double price, double returnValue) {
        double minPrice = pendantDetails.getGold_rate() * pendantDetails.getNet_weight() / 8;

        if (price < minPrice) {
            // Show alert with OK and Continue Anyway options
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Sales price must be greater than " + minPrice);

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType continueButton = new ButtonType("Continue Anyway", ButtonBar.ButtonData.OTHER);
            alert.getButtonTypes().setAll(okButton, continueButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == continueButton) {
                boolean passcodeVerified = verifyPasscode();
                if (!passcodeVerified) {
                    showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid passcode");
                    return;
                }
            } else {
                return;
            }
        }

        if (returnValue > price) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid Exchange Value!");
            return;
        }

        customerId();  // Ensure customer ID is set

        salesData newSalesData = new salesData(
                customerid,
                pendantDetails.getProductId(),
                pendantDetails.getCategory(),
                pendantDetails.getWeight(),
                pendantDetails.getNet_weight(),
                pendantDetails.getLength(),
                pendantDetails.getKarat(),
                pendantDetails.getGold_rate(),
                price,
                returnValue,
                new java.sql.Date(new Date().getTime())
        );

        sales_tableView.getItems().add(newSalesData);
        clearSalesInputFields();
        updateSalesTotal();
    }

    // **************************************SURA RELATED***********************************************************

    private String[] listSuCategory = {"Lock Sura","Thread Sura"};

    // Method to get pendant details from the database
    private suraData getSuraDetails(String productId) {
        String sql = "SELECT * FROM sura_details WHERE product_id = ?";
        connect = connectDb();
        suraData suraDetails = null;
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, productId);
            result = prepare.executeQuery();
            if (result.next()) {
                suraDetails = new suraData(
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
        return suraDetails;
    }

    public void addSuraAdd() {
        String sql = "INSERT INTO sura_details (product_id, category, net_weight, karat, gold_rate, supplier, status, image, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        connect = connectDb();
        try {
            Alert alert;

            if (addSu_id.getText().isEmpty()
                    || addSu_category.getSelectionModel().getSelectedItem() == null
                    || addSu_netWeight.getText().isEmpty()
                    || addSu_karat.getSelectionModel().getSelectedItem() == null
                    || addSu_rate.getText().isEmpty()
                    || addSu_supplier.getText().isEmpty()
                    || addSu_status.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else if (!isSuraIdValid(addSu_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID format is incorrect. It should start with 'S' followed by 4 digits.");
                alert.showAndWait();
            } else if (isSuraIdExist(addSu_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID already exists. Please use a unique Product ID.");
                alert.showAndWait();
            } else if (isProductIdExistInSales(addSu_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID already exists in sales table. Please use a unique Product ID.");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                String productId = addSu_id.getText();
                prepare.setString(1, productId);
                prepare.setString(2, addSu_category.getSelectionModel().getSelectedItem());
                prepare.setString(3, addSu_netWeight.getText());
                prepare.setString(4, addSu_karat.getSelectionModel().getSelectedItem());
                prepare.setString(5, addSu_rate.getText());
                prepare.setString(6, addSu_supplier.getText());
                prepare.setString(7, addSu_status.getSelectionModel().getSelectedItem());

                String uri = getData.path;
                if (uri != null && !uri.isEmpty()) {
                    uri = uri.replace("\\", "\\\\");
                    prepare.setString(8, uri);
                } else {
                    prepare.setString(8, null);
                }

                Date date = new Date();
                java.sql.Date sqldate = new java.sql.Date(date.getTime());

                prepare.setString(9, String.valueOf(sqldate));

                prepare.executeUpdate();

                addSuraShowListData();
                addSuraReset();

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

    public void addSuraUpdate() {
        // Check if the status is selected
        if (addSu_status.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the new status");
            alert.showAndWait();
            return;
        }

        // Ensure the Product ID field is filled
        if (addSu_id.getText().isEmpty()) {
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
        confirmationAlert.setContentText("Are you sure you want to UPDATE the status for Product_ID: " + addSu_id.getText() + "?");

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
            String sql = "UPDATE sura_details SET status = ? WHERE product_id = ?";

            connect = connectDb();
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addSu_status.getSelectionModel().getSelectedItem());
                prepare.setString(2, addSu_id.getText());

                int rowsUpdated = prepare.executeUpdate();

                if (rowsUpdated > 0) {
                    addSuraShowListData();
                    addSuraReset();

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

    public void addSuraDelete() {
        // Ensure the Product ID field is filled
        if (addSu_id.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        }

        // Check if the Product ID exists
        if (!isSuraIdExist(addSu_id.getText())) {
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
        confirmationAlert.setContentText("Are you sure you want to DELETE Product_ID: " + addSu_id.getText() + "?");

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
            String sql = "DELETE FROM sura_details WHERE product_id = ?";
            connect = connectDb();
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addSu_id.getText());

                int rowsDeleted = prepare.executeUpdate();
                if (rowsDeleted > 0) {
                    addSuraShowListData();
                    addSuraReset();

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

    public void addSuraReset() {
        addSu_id.setText("");
        addSu_category.getSelectionModel().clearSelection();
        addSu_netWeight.setText("");
        addSu_karat.getSelectionModel().clearSelection();
        addSu_rate.setText("");
        addSu_supplier.setText("");
        addSu_status.getSelectionModel().clearSelection();
        addSu_img.setImage(null);
        getData.path = "";
    }

    public void addSuImportImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            getData.path = file.getAbsolutePath();
            suImage = new Image(file.toURI().toString(), 90, 100, false, true);
            addSu_img.setImage(suImage);
        }
    }

    public void addSuListCategory() {
        List<String> listC = new ArrayList<>();

        for(String data:listSuCategory){
            listC.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listSuCategory);
        addSu_category.setItems(listData);
    }

    public void addSuListKarat() {
        List<String> listK = new ArrayList<>();

        for(String data:listKarat){
            listK.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listKarat);
        addSu_karat.setItems(listData);
    }

    public void addSuListStatus() {
        List<String> listS = new ArrayList<>();

        for(String data:listStatus){
            listS.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listStatus);
        addSu_status.setItems(listData);
    }

    public void addSuraSearch() {
        FilteredList<suraData> filter = new FilteredList<>(addSuraList, e -> true);

        addSu_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateSuData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();

                if (predicateSuData.getProductId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateSuData.getCategory().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateSuData.getNet_weight().toString().contains(searchKey)) {
                    return true;
                } else if (predicateSuData.getKarat().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateSuData.getGold_rate().toString().contains(searchKey)) {
                    return true;
                } else if (predicateSuData.getSupplier().toString().contains(searchKey)) {
                    return true;
                } else if (predicateSuData.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<suraData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(addSu_tableView.comparatorProperty());
        addSu_tableView.setItems(sortList);
    }

    public ObservableList<suraData> addSuraListData() {
        ObservableList<suraData> suraList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM sura_details";
        connect = connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            suraData suD;

            while (result.next()) {
                suD = new suraData(result.getString("product_id"),
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

                suraList.add(suD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suraList;
    }

    private ObservableList<suraData> addSuraList;

    public void addSuraShowListData() {
        addSuraList = addSuraListData();

        addSu_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        addSu_col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        addSu_col_weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        addSu_col_netWeight.setCellValueFactory(new PropertyValueFactory<>("net_weight"));
        addSu_col_length.setCellValueFactory(new PropertyValueFactory<>("length"));
        addSu_col_karat.setCellValueFactory(new PropertyValueFactory<>("karat"));
        addSu_col_goldRate.setCellValueFactory(new PropertyValueFactory<>("gold_rate"));
        addSu_col_supplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        addSu_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set custom cell factories to format double values to two decimal places
        addSu_col_netWeight.setCellFactory(getDoubleCellFactory());
        addSu_col_goldRate.setCellFactory(getDoubleCellFactory());

        addSu_tableView.setItems(addSuraList);
    }

    public void addSuraSelect() {
        suraData suD = addSu_tableView.getSelectionModel().getSelectedItem();
        int num = addSu_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addSu_id.setText(String.valueOf(suD.getProductId()));

        if (suD.getImage() != null && !suD.getImage().isEmpty()) {
            String uri = "file:" + suD.getImage();
            suImage = new Image(uri, 90, 100, false, true);
            addSu_img.setImage(suImage);
            getData.path = suD.getImage();
        } else {
            addSu_img.setImage(null);
        }
    }

    private boolean isSuraIdExist(String productId) {
        String sql = "SELECT COUNT(*) FROM sura_details WHERE product_id = ?";
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

    private boolean isSuraIdValid(String productId) {
        // Check if productId starts with 'S' and has exactly 4 digits
        return productId.matches("^S\\d{4}$");
    }

    // Method to handle sura data
    private void handleSuraData(suraData suraDetails, double price, double returnValue) {
        double minPrice = suraDetails.getGold_rate() * suraDetails.getNet_weight() / 8;

        if (price < minPrice) {
            // Show alert with OK and Continue Anyway options
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Sales price must be greater than " + minPrice);

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType continueButton = new ButtonType("Continue Anyway", ButtonBar.ButtonData.OTHER);
            alert.getButtonTypes().setAll(okButton, continueButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == continueButton) {
                boolean passcodeVerified = verifyPasscode();
                if (!passcodeVerified) {
                    showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid passcode");
                    return;
                }
            } else {
                return;
            }
        }

        if (returnValue > price) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid Exchange Value!");
            return;
        }

        customerId();  // Ensure customer ID is set

        salesData newSalesData = new salesData(
                customerid,
                suraDetails.getProductId(),
                suraDetails.getCategory(),
                suraDetails.getWeight(),
                suraDetails.getNet_weight(),
                suraDetails.getLength(),
                suraDetails.getKarat(),
                suraDetails.getGold_rate(),
                price,
                returnValue,
                new java.sql.Date(new Date().getTime())
        );

        sales_tableView.getItems().add(newSalesData);
        clearSalesInputFields();
        updateSalesTotal();
    }

    // **************************************PANCHAYUDA RELATED***********************************************************

    private String[] listPaCategory = {"Dharmachakra","Heart"};

    // Method to get pendant details from the database
    private panchaData getPanchaDetails(String productId) {
        String sql = "SELECT * FROM pancha_details WHERE product_id = ?";
        connect = connectDb();
        panchaData panchaDetails = null;
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, productId);
            result = prepare.executeQuery();
            if (result.next()) {
                panchaDetails = new panchaData(
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
        return panchaDetails;
    }

    public void addPanchaAdd() {
        String sql = "INSERT INTO pancha_details (product_id, category, net_weight, karat, gold_rate, supplier, status, image, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        connect = connectDb();
        try {
            Alert alert;

            if (addPa_id.getText().isEmpty()
                    || addPa_category.getSelectionModel().getSelectedItem() == null
                    || addPa_netWeight.getText().isEmpty()
                    || addPa_karat.getSelectionModel().getSelectedItem() == null
                    || addPa_rate.getText().isEmpty()
                    || addPa_supplier.getText().isEmpty()
                    || addPa_status.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else if (!isPanchaIdValid(addPa_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID format is incorrect. It should start with 'A' followed by 4 digits.");
                alert.showAndWait();
            } else if (isPanchaIdExist(addPa_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID already exists. Please use a unique Product ID.");
                alert.showAndWait();
            } else if (isProductIdExistInSales(addPa_id.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID already exists in sales table. Please use a unique Product ID.");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                String productId = addPa_id.getText();
                prepare.setString(1, productId);
                prepare.setString(2, addPa_category.getSelectionModel().getSelectedItem());
                prepare.setString(3, addPa_netWeight.getText());
                prepare.setString(4, addPa_karat.getSelectionModel().getSelectedItem());
                prepare.setString(5, addPa_rate.getText());
                prepare.setString(6, addPa_supplier.getText());
                prepare.setString(7, addPa_status.getSelectionModel().getSelectedItem());

                String uri = getData.path;
                if (uri != null && !uri.isEmpty()) {
                    uri = uri.replace("\\", "\\\\");
                    prepare.setString(8, uri);
                } else {
                    prepare.setString(8, null);
                }

                Date date = new Date();
                java.sql.Date sqldate = new java.sql.Date(date.getTime());

                prepare.setString(9, String.valueOf(sqldate));

                prepare.executeUpdate();

                addPanchaShowListData();
                addPanchaReset();

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

    public void addPanchaUpdate() {
        // Check if the status is selected
        if (addPa_status.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the new status");
            alert.showAndWait();
            return;
        }

        // Ensure the Product ID field is filled
        if (addPa_id.getText().isEmpty()) {
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
        confirmationAlert.setContentText("Are you sure you want to UPDATE the status for Product_ID: " + addPa_id.getText() + "?");

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
            String sql = "UPDATE pancha_details SET status = ? WHERE product_id = ?";

            connect = connectDb();
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addPa_status.getSelectionModel().getSelectedItem());
                prepare.setString(2, addPa_id.getText());

                int rowsUpdated = prepare.executeUpdate();

                if (rowsUpdated > 0) {
                    addPanchaShowListData();
                    addPanchaReset();

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

    public void addPanchaDelete() {
        // Ensure the Product ID field is filled
        if (addPa_id.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        }

        // Check if the Product ID exists
        if (!isPanchaIdExist(addPa_id.getText())) {
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
        confirmationAlert.setContentText("Are you sure you want to DELETE Product_ID: " + addPa_id.getText() + "?");

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
            String sql = "DELETE FROM pancha_details WHERE product_id = ?";
            connect = connectDb();
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addPa_id.getText());

                int rowsDeleted = prepare.executeUpdate();
                if (rowsDeleted > 0) {
                    addPanchaShowListData();
                    addPanchaReset();

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

    public void addPanchaReset() {
        addPa_id.setText("");
        addPa_category.getSelectionModel().clearSelection();
        addPa_netWeight.setText("");
        addPa_karat.getSelectionModel().clearSelection();
        addPa_rate.setText("");
        addPa_supplier.setText("");
        addPa_status.getSelectionModel().clearSelection();
        addPa_img.setImage(null);
        getData.path = "";
    }

    public void addPaImportImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            getData.path = file.getAbsolutePath();
            paImage = new Image(file.toURI().toString(), 90, 100, false, true);
            addPa_img.setImage(paImage);
        }
    }

    public void addPaListCategory() {
        List<String> listC = new ArrayList<>();

        for(String data:listPaCategory){
            listC.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listPaCategory);
        addPa_category.setItems(listData);
    }

    public void addPaListKarat() {
        List<String> listK = new ArrayList<>();

        for(String data:listKarat){
            listK.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listKarat);
        addPa_karat.setItems(listData);
    }

    public void addPaListStatus() {
        List<String> listS = new ArrayList<>();

        for(String data:listStatus){
            listS.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listStatus);
        addPa_status.setItems(listData);
    }

    public void addPanchaSearch() {
        FilteredList<panchaData> filter = new FilteredList<>(addPanchaList, e -> true);

        addPa_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicatePaData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();

                if (predicatePaData.getProductId().toString().contains(searchKey)) {
                    return true;
                } else if (predicatePaData.getCategory().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicatePaData.getNet_weight().toString().contains(searchKey)) {
                    return true;
                } else if (predicatePaData.getKarat().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicatePaData.getGold_rate().toString().contains(searchKey)) {
                    return true;
                } else if (predicatePaData.getSupplier().toString().contains(searchKey)) {
                    return true;
                } else if (predicatePaData.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<panchaData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(addPa_tableView.comparatorProperty());
        addPa_tableView.setItems(sortList);
    }

    public ObservableList<panchaData> addPanchaListData() {
        ObservableList<panchaData> panchaList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM pancha_details";
        connect = connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            panchaData paD;

            while (result.next()) {
                paD = new panchaData(result.getString("product_id"),
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

                panchaList.add(paD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return panchaList;
    }

    private ObservableList<panchaData> addPanchaList;

    public void addPanchaShowListData() {
        addPanchaList = addPanchaListData();

        addPa_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        addPa_col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        addPa_col_weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        addPa_col_netWeight.setCellValueFactory(new PropertyValueFactory<>("net_weight"));
        addPa_col_length.setCellValueFactory(new PropertyValueFactory<>("length"));
        addPa_col_karat.setCellValueFactory(new PropertyValueFactory<>("karat"));
        addPa_col_goldRate.setCellValueFactory(new PropertyValueFactory<>("gold_rate"));
        addPa_col_supplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        addPa_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set custom cell factories to format double values to two decimal places
        addPa_col_netWeight.setCellFactory(getDoubleCellFactory());
        addPa_col_goldRate.setCellFactory(getDoubleCellFactory());

        addPa_tableView.setItems(addPanchaList);
    }

    public void addPanchaSelect() {
        panchaData paD = addPa_tableView.getSelectionModel().getSelectedItem();
        int num = addPa_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addPa_id.setText(String.valueOf(paD.getProductId()));

        if (paD.getImage() != null && !paD.getImage().isEmpty()) {
            String uri = "file:" + paD.getImage();
            paImage = new Image(uri, 90, 100, false, true);
            addPa_img.setImage(paImage);
            getData.path = paD.getImage();
        } else {
            addPa_img.setImage(null);
        }
    }

    private boolean isPanchaIdExist(String productId) {
        String sql = "SELECT COUNT(*) FROM pancha_details WHERE product_id = ?";
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

    private boolean isPanchaIdValid(String productId) {
        // Check if productId starts with 'S' and has exactly 4 digits
        return productId.matches("^A\\d{4}$");
    }

    // Method to handle sura data
    private void handlePanchaData(panchaData panchaDetails, double price, double returnValue) {
        double minPrice = panchaDetails.getGold_rate() * panchaDetails.getNet_weight() / 8;

        if (price < minPrice) {
            // Show alert with OK and Continue Anyway options
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Sales price must be greater than " + minPrice);

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType continueButton = new ButtonType("Continue Anyway", ButtonBar.ButtonData.OTHER);
            alert.getButtonTypes().setAll(okButton, continueButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == continueButton) {
                boolean passcodeVerified = verifyPasscode();
                if (!passcodeVerified) {
                    showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid passcode");
                    return;
                }
            } else {
                return;
            }
        }

        if (returnValue > price) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid Exchange Value!");
            return;
        }

        customerId();  // Ensure customer ID is set

        salesData newSalesData = new salesData(
                customerid,
                panchaDetails.getProductId(),
                panchaDetails.getCategory(),
                panchaDetails.getWeight(),
                panchaDetails.getNet_weight(),
                panchaDetails.getLength(),
                panchaDetails.getKarat(),
                panchaDetails.getGold_rate(),
                price,
                returnValue,
                new java.sql.Date(new Date().getTime())
        );

        sales_tableView.getItems().add(newSalesData);
        clearSalesInputFields();
        updateSalesTotal();
    }

    //******************************************************************************************************************

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
            sales_form.setVisible(false);
            chain_form.setVisible(false);
            br_form.setVisible(false);
            ri_form.setVisible(false);
            ea_form.setVisible(false);
            pe_form.setVisible(false);
            su_form.setVisible(false);
            pa_form.setVisible(false);
            ne_form.setVisible(false);
            ba_form.setVisible(false);


            homeBtn.setStyle("-fx-background-color:linear-gradient(to bottom ,#004cc5, #011b4a);");
            salesBtn.setStyle("-fx-background-color:transparent");
            chainBtn.setStyle("-fx-background-color:transparent");
            brBtn.setStyle("-fx-background-color:transparent");
            riBtn.setStyle("-fx-background-color:transparent");
            eaBtn.setStyle("-fx-background-color:transparent");
            peBtn.setStyle("-fx-background-color:transparent");
            suBtn.setStyle("-fx-background-color:transparent");
            paBtn.setStyle("-fx-background-color:transparent");
            neBtn.setStyle("-fx-background-color:transparent");
            baBtn.setStyle("-fx-background-color:transparent");

            displayTodaySales();
            displayTodayIncome();
            displayTotalStock();

        } else if (event.getSource() == chainBtn) {
            home_form.setVisible(false);
            sales_form.setVisible(false);
            chain_form.setVisible(true);
            br_form.setVisible(false);
            ri_form.setVisible(false);
            ea_form.setVisible(false);
            pe_form.setVisible(false);
            su_form.setVisible(false);
            pa_form.setVisible(false);
            ne_form.setVisible(false);
            ba_form.setVisible(false);

            homeBtn.setStyle("-fx-background-color:transparent");
            salesBtn.setStyle("-fx-background-color:transparent");
            chainBtn.setStyle("-fx-background-color:linear-gradient(to bottom ,#004cc5, #011b4a);");
            brBtn.setStyle("-fx-background-color:transparent");
            riBtn.setStyle("-fx-background-color:transparent");
            eaBtn.setStyle("-fx-background-color:transparent");
            peBtn.setStyle("-fx-background-color:transparent");
            suBtn.setStyle("-fx-background-color:transparent");
            paBtn.setStyle("-fx-background-color:transparent");
            neBtn.setStyle("-fx-background-color:transparent");
            baBtn.setStyle("-fx-background-color:transparent");

            addChainShowListData();
            addChainListCategory();
            addChainListWeight();
            addChainListLength();
            addChainListKarat();
            addChainListStatus();
            addChainSearch();

        } else if (event.getSource() == brBtn) {
            home_form.setVisible(false);
            sales_form.setVisible(false);
            chain_form.setVisible(false);
            br_form.setVisible(true);
            ri_form.setVisible(false);
            ea_form.setVisible(false);
            pe_form.setVisible(false);
            su_form.setVisible(false);
            pa_form.setVisible(false);
            ne_form.setVisible(false);
            ba_form.setVisible(false);


            homeBtn.setStyle("-fx-background-color:transparent");
            salesBtn.setStyle("-fx-background-color:transparent");
            chainBtn.setStyle("-fx-background-color:transparent");
            brBtn.setStyle("-fx-background-color:linear-gradient(to bottom ,#004cc5, #011b4a);");
            riBtn.setStyle("-fx-background-color:transparent");
            eaBtn.setStyle("-fx-background-color:transparent");
            peBtn.setStyle("-fx-background-color:transparent");
            suBtn.setStyle("-fx-background-color:transparent");
            paBtn.setStyle("-fx-background-color:transparent");
            neBtn.setStyle("-fx-background-color:transparent");
            baBtn.setStyle("-fx-background-color:transparent");

            addBraceletShowListData();
            addBrListCategory();
            addBrListWeight();
            addBrListLength();
            addBrListKarat();
            addBrListStatus();
            addBraceletSearch();

        } else if (event.getSource() == riBtn) {
            home_form.setVisible(false);
            sales_form.setVisible(false);
            chain_form.setVisible(false);
            br_form.setVisible(false);
            ri_form.setVisible(true);
            ea_form.setVisible(false);
            pe_form.setVisible(false);
            su_form.setVisible(false);
            pa_form.setVisible(false);
            ne_form.setVisible(false);
            ba_form.setVisible(false);


            homeBtn.setStyle("-fx-background-color:transparent");
            salesBtn.setStyle("-fx-background-color:transparent");
            chainBtn.setStyle("-fx-background-color:transparent");
            brBtn.setStyle("-fx-background-color:transparent");
            riBtn.setStyle("-fx-background-color:linear-gradient(to bottom ,#004cc5, #011b4a);");
            eaBtn.setStyle("-fx-background-color:transparent");
            peBtn.setStyle("-fx-background-color:transparent");
            suBtn.setStyle("-fx-background-color:transparent");
            paBtn.setStyle("-fx-background-color:transparent");
            neBtn.setStyle("-fx-background-color:transparent");
            baBtn.setStyle("-fx-background-color:transparent");

            addRingShowListData();
            addRiListCategory();
            addRiListKarat();
            addRiListStatus();
            addRingSearch();

        } else if (event.getSource() == eaBtn) {
            home_form.setVisible(false);
            sales_form.setVisible(false);
            chain_form.setVisible(false);
            br_form.setVisible(false);
            ri_form.setVisible(false);
            ea_form.setVisible(true);
            pe_form.setVisible(false);
            su_form.setVisible(false);
            pa_form.setVisible(false);
            ne_form.setVisible(false);
            ba_form.setVisible(false);


            homeBtn.setStyle("-fx-background-color:transparent");
            salesBtn.setStyle("-fx-background-color:transparent");
            chainBtn.setStyle("-fx-background-color:transparent");
            brBtn.setStyle("-fx-background-color:transparent");
            riBtn.setStyle("-fx-background-color:transparent");
            eaBtn.setStyle("-fx-background-color:linear-gradient(to bottom ,#004cc5, #011b4a);");
            peBtn.setStyle("-fx-background-color:transparent");
            suBtn.setStyle("-fx-background-color:transparent");
            paBtn.setStyle("-fx-background-color:transparent");
            neBtn.setStyle("-fx-background-color:transparent");
            baBtn.setStyle("-fx-background-color:transparent");

            addEarringShowListData();
            addErListCategory();
            addErListKarat();
            addErListStatus();
            addEarringSearch();

        } else if (event.getSource() == peBtn) {
            home_form.setVisible(false);
            sales_form.setVisible(false);
            chain_form.setVisible(false);
            br_form.setVisible(false);
            ri_form.setVisible(false);
            ea_form.setVisible(false);
            pe_form.setVisible(true);
            su_form.setVisible(false);
            pa_form.setVisible(false);
            ne_form.setVisible(false);
            ba_form.setVisible(false);


            homeBtn.setStyle("-fx-background-color:transparent");
            salesBtn.setStyle("-fx-background-color:transparent");
            chainBtn.setStyle("-fx-background-color:transparent");
            brBtn.setStyle("-fx-background-color:transparent");
            riBtn.setStyle("-fx-background-color:transparent");
            eaBtn.setStyle("-fx-background-color:transparent");
            peBtn.setStyle("-fx-background-color:linear-gradient(to bottom ,#004cc5, #011b4a);");
            suBtn.setStyle("-fx-background-color:transparent");
            paBtn.setStyle("-fx-background-color:transparent");
            neBtn.setStyle("-fx-background-color:transparent");
            baBtn.setStyle("-fx-background-color:transparent");

            addPendantShowListData();
            addPeListCategory();
            addPeListKarat();
            addPeListStatus();
            addPendantSearch();

        } else if (event.getSource() == suBtn) {
            home_form.setVisible(false);
            sales_form.setVisible(false);
            chain_form.setVisible(false);
            br_form.setVisible(false);
            ri_form.setVisible(false);
            ea_form.setVisible(false);
            pe_form.setVisible(false);
            su_form.setVisible(true);
            pa_form.setVisible(false);
            ne_form.setVisible(false);
            ba_form.setVisible(false);


            homeBtn.setStyle("-fx-background-color:transparent");
            salesBtn.setStyle("-fx-background-color:transparent");
            chainBtn.setStyle("-fx-background-color:transparent");
            brBtn.setStyle("-fx-background-color:transparent");
            riBtn.setStyle("-fx-background-color:transparent");
            eaBtn.setStyle("-fx-background-color:transparent");
            peBtn.setStyle("-fx-background-color:transparent");
            suBtn.setStyle("-fx-background-color:linear-gradient(to bottom ,#004cc5, #011b4a);");
            paBtn.setStyle("-fx-background-color:transparent");
            neBtn.setStyle("-fx-background-color:transparent");
            baBtn.setStyle("-fx-background-color:transparent");

            addSuraShowListData();
            addSuListCategory();
            addSuListKarat();
            addSuListStatus();
            addSuraSearch();

        } else if (event.getSource() == paBtn) {
            home_form.setVisible(false);
            sales_form.setVisible(false);
            chain_form.setVisible(false);
            br_form.setVisible(false);
            ri_form.setVisible(false);
            ea_form.setVisible(false);
            pe_form.setVisible(false);
            su_form.setVisible(false);
            pa_form.setVisible(true);
            ne_form.setVisible(false);
            ba_form.setVisible(false);


            homeBtn.setStyle("-fx-background-color:transparent");
            salesBtn.setStyle("-fx-background-color:transparent");
            chainBtn.setStyle("-fx-background-color:transparent");
            brBtn.setStyle("-fx-background-color:transparent");
            riBtn.setStyle("-fx-background-color:transparent");
            eaBtn.setStyle("-fx-background-color:transparent");
            peBtn.setStyle("-fx-background-color:transparent");
            suBtn.setStyle("-fx-background-color:transparent");
            paBtn.setStyle("-fx-background-color:linear-gradient(to bottom ,#004cc5, #011b4a);");
            neBtn.setStyle("-fx-background-color:transparent");
            baBtn.setStyle("-fx-background-color:transparent");

            addPanchaShowListData();
            addPaListCategory();
            addPaListKarat();
            addPaListStatus();
            addPanchaSearch();

        } else if (event.getSource() == neBtn) {
            home_form.setVisible(false);
            sales_form.setVisible(false);
            chain_form.setVisible(false);
            br_form.setVisible(false);
            ri_form.setVisible(false);
            ea_form.setVisible(false);
            pe_form.setVisible(false);
            su_form.setVisible(false);
            pa_form.setVisible(false);
            ne_form.setVisible(true);
            ba_form.setVisible(false);


            homeBtn.setStyle("-fx-background-color:transparent");
            salesBtn.setStyle("-fx-background-color:transparent");
            chainBtn.setStyle("-fx-background-color:transparent");
            brBtn.setStyle("-fx-background-color:transparent");
            riBtn.setStyle("-fx-background-color:transparent");
            eaBtn.setStyle("-fx-background-color:transparent");
            peBtn.setStyle("-fx-background-color:transparent");
            suBtn.setStyle("-fx-background-color:transparent");
            paBtn.setStyle("-fx-background-color:transparent");
            neBtn.setStyle("-fx-background-color:linear-gradient(to bottom ,#004cc5, #011b4a);");
            baBtn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == baBtn) {
            home_form.setVisible(false);
            sales_form.setVisible(false);
            chain_form.setVisible(false);
            br_form.setVisible(false);
            ri_form.setVisible(false);
            ea_form.setVisible(false);
            pe_form.setVisible(false);
            su_form.setVisible(false);
            pa_form.setVisible(false);
            ne_form.setVisible(false);
            ba_form.setVisible(true);


            homeBtn.setStyle("-fx-background-color:transparent");
            salesBtn.setStyle("-fx-background-color:transparent");
            chainBtn.setStyle("-fx-background-color:transparent");
            brBtn.setStyle("-fx-background-color:transparent");
            riBtn.setStyle("-fx-background-color:transparent");
            eaBtn.setStyle("-fx-background-color:transparent");
            peBtn.setStyle("-fx-background-color:transparent");
            suBtn.setStyle("-fx-background-color:transparent");
            paBtn.setStyle("-fx-background-color:transparent");
            neBtn.setStyle("-fx-background-color:transparent");
            baBtn.setStyle("-fx-background-color:linear-gradient(to bottom ,#004cc5, #011b4a);");

        } else if (event.getSource() == salesBtn) {
            home_form.setVisible(false);
            sales_form.setVisible(true);
            chain_form.setVisible(false);
            br_form.setVisible(false);
            ri_form.setVisible(false);
            ea_form.setVisible(false);
            pe_form.setVisible(false);
            su_form.setVisible(false);
            pa_form.setVisible(false);
            ne_form.setVisible(false);
            ba_form.setVisible(false);


            homeBtn.setStyle("-fx-background-color:transparent");
            salesBtn.setStyle("-fx-background-color:linear-gradient(to bottom ,#004cc5, #011b4a);");
            chainBtn.setStyle("-fx-background-color:transparent");
            brBtn.setStyle("-fx-background-color:transparent");
            riBtn.setStyle("-fx-background-color:transparent");
            eaBtn.setStyle("-fx-background-color:transparent");
            peBtn.setStyle("-fx-background-color:transparent");
            suBtn.setStyle("-fx-background-color:transparent");
            paBtn.setStyle("-fx-background-color:transparent");
            neBtn.setStyle("-fx-background-color:transparent");
            baBtn.setStyle("-fx-background-color:transparent");

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

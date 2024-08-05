package inventory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Author: yogesh_rj
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private FontAwesomeIcon close;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    //DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private double x = 0;
    private double y = 0;

    public void loginAdmin() {
        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";
        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());

            result = prepare.executeQuery();
            Alert alert;

            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                if (result.next()) {
                    // IF THE USER NAME AND PASSWORD CORRECT
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");

                    // Add "Update Gold Rate" button
                    ButtonType updateGoldRateButton = new ButtonType("Update Gold Rate", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().add(updateGoldRateButton);

                    alert.showAndWait().ifPresent(response -> {
                        if (response == updateGoldRateButton) {
                            showGoldRateDialog();
                        } else {
                            proceedToDashboard();
                        }
                    });

                } else {
                    // IF WRONG THEN ERROR MESSAGE
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong username or password");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void proceedToDashboard() {
        try {
            // Set the username
            getData.username = username.getText();

            // HIDE THE LOGIN FORM
            loginBtn.getScene().getWindow().hide();

            // LINK TO THE NEW TAB
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inventory/dashbord.fxml"));
            Parent root = loader.load();

            dashboardController dashboardController = loader.getController();
            dashboardController.updateGoldRateLabel(); // Update the gold rate label

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            root.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });

            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showGoldRateDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Gold Rate");
        dialog.setHeaderText("Enter Today's Gold Rate");
        dialog.setContentText("Gold Rate:");

        dialog.showAndWait().ifPresent(rate -> {
            try {
                int goldRate = Integer.parseInt(rate);
                updateGoldRateInDatabase(goldRate);
                proceedToDashboard();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid gold rate value. Please enter an integer.");
                alert.showAndWait();
            }
        });
    }

    private void updateGoldRateInDatabase(int goldRate) {
        String sql = "INSERT INTO gold_rate (rate, date, timestamp) VALUES (?, CURRENT_DATE, CURRENT_TIMESTAMP)";
        connect = database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, goldRate);
            prepare.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Gold rate updated successfully!");
            alert.showAndWait();

            // Ensure the gold rate label is updated
            // Call updateGoldRateLabel() on the DashboardController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inventory/dashbord.fxml"));
            Parent root = loader.load();

            dashboardController dashboardController = loader.getController();
            dashboardController.updateGoldRateLabel();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // No need to update the gold rate label here since it's in DashboardController
    }
}

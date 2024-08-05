package inventory;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;

import java.util.Optional;

public class CustomerInfoDialog {
    public static Optional<CustomerInfo> showAndWait() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Customer Information");
        dialog.setHeaderText("Enter Customer Information");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField telephoneField = new TextField();
        telephoneField.setPromptText("Telephone");
        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        ComboBox<String> paymentMethodComboBox = new ComboBox<>(FXCollections.observableArrayList("Cash", "Card"));
        paymentMethodComboBox.setPromptText("Select Payment Method");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Telephone:"), 0, 1);
        grid.add(telephoneField, 1, 1);
        grid.add(new Label("Address:"), 0, 2);
        grid.add(addressField, 1, 2);
        grid.add(new Label("Payment Method:"), 0, 3);
        grid.add(paymentMethodComboBox, 1, 3);

        grid.setHgrow(nameField, Priority.ALWAYS);
        grid.setHgrow(telephoneField, Priority.ALWAYS);
        grid.setHgrow(addressField, Priority.ALWAYS);
        grid.setHgrow(paymentMethodComboBox, Priority.ALWAYS);

        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return Optional.of(new CustomerInfo(
                    nameField.getText(),
                    telephoneField.getText(),
                    addressField.getText(),
                    paymentMethodComboBox.getValue()
            ));
        }

        return Optional.empty();
    }
}

class CustomerInfo {
    private String name;
    private String telephone;
    private String address;
    private String paymentMethod;

    public CustomerInfo(String name, String telephone, String address, String paymentMethod) {
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.paymentMethod = paymentMethod;
    }

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAddress() {
        return address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}

package org.example.customermanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private TextField customerName;

    @FXML
    private ComboBox<String> province;

    @FXML
    private TableView<Customer> customerTable;

    private final ObservableList<Customer> customers =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        province.getItems().addAll(
                "Central",
                "Copperbelt",
                "Eastern",
                "Luapula",
                "Lusaka",
                "Muchinga",
                "Northern",
                "North-Western",
                "Southern",
                "Western"
        );

        customerTable.setItems(customers);
    }

    @FXML
    protected void onSaveCustomerClick() {
        String name = customerName.getText();
        String selectedProvince = province.getValue();

        if (!name.isBlank() && selectedProvince != null) {
            customers.add(new Customer(name, selectedProvince));

            customerName.clear();
            province.setValue(null);
        }
    }

    @FXML
    protected void onDeleteCustomerClick() {
        Customer selectedCustomer =
                customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            customers.remove(selectedCustomer);
        }
    }
}


package org.example.customermanager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    ObservableList<Customer> customers =
            FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {

        // Customer table
        TableView<Customer> table = new TableView<>();
        table.setItems(customers);

        TableColumn<Customer, String> nameCol =
                new TableColumn<>("Customer name");

        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> provinceCol =
                new TableColumn<>("Province");

        provinceCol.setCellValueFactory(
                new PropertyValueFactory<>("province"));

        table.getColumns().addAll(nameCol, provinceCol);

        // Customer name
        Label nameLabel = new Label("Customer name");

        TextField nameField = new TextField();
        nameField.setPromptText("e.g., Mary Banda");
        nameLabel.setLabelFor(nameField);

        // Province
        Label provinceLabel = new Label("Province");

        ComboBox<String> provinceBox = new ComboBox<>();

        provinceBox.getItems().addAll(
                "Central",
                "Lusaka",
                "Copperbelt"
        );

        provinceBox.setPromptText("Choose a province");
        provinceLabel.setLabelFor(provinceBox);

        // Save button
        Button saveButton = new Button("Save customer");
        saveButton.setDefaultButton(true);

        // Status message
        Label status = new Label();

        // Save action
        saveButton.setOnAction(event -> {

            String name = nameField.getText().trim();

            if (name.isEmpty()) {
                status.setText("Enter the customer name.");
                nameField.requestFocus();
                return;
            }

            String province = provinceBox.getValue();

            if (province == null) {
                status.setText("Choose a province.");
                provinceBox.requestFocus();
                return;
            }

            customers.add(new Customer(name, province));

            status.setText("Customer saved.");

            nameField.clear();
            provinceBox.getSelectionModel().clearSelection();
            nameField.requestFocus();
        });

        // Delete button
        Button deleteButton = new Button("Delete customer");

        deleteButton.setOnAction(event -> {

            Customer selected =
                    table.getSelectionModel().getSelectedItem();

            if (selected == null) {
                status.setText("Select a customer first.");
                return;
            }

            ButtonType delete =
                    new ButtonType("Delete");

            Alert ask = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Delete the selected customer?",
                    delete,
                    ButtonType.CANCEL
            );

            ask.setHeaderText("Confirm deletion");

            if (ask.showAndWait().orElse(ButtonType.CANCEL)
                    == delete) {

                customers.remove(selected);
                status.setText("Customer deleted.");
            }
        });

        // Layout
        HBox nameRow = new HBox(
                10,
                nameLabel,
                nameField
        );

        HBox provinceRow = new HBox(
                10,
                provinceLabel,
                provinceBox
        );

        HBox buttonRow = new HBox(
                10,
                saveButton,
                deleteButton
        );

        VBox root = new VBox(
                10,
                nameRow,
                provinceRow,
                buttonRow,
                status,
                table
        );

        Scene scene = new Scene(root, 600, 450);

        stage.setTitle("Customer Manager");
        stage.setScene(scene);
        stage.show();

        nameField.requestFocus();
    }

    public static void main(String[] args) {
        launch();
    }
}
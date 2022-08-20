package duc.nhh.storemanager.screen;

import duc.nhh.storemanager.controller.InvoiceController;
import duc.nhh.storemanager.controller.ProductController;
import duc.nhh.storemanager.exception.InvalidInfoException;
import duc.nhh.storemanager.exception.NotEnoughProductException;
import duc.nhh.storemanager.model.Employee;
import duc.nhh.storemanager.utils.Path;
import duc.nhh.storemanager.utils.Validator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CreateInvoiceScreenHandler extends BaseScreenHandler {
    @FXML private TextField phoneNumberField;
    @FXML private TextField customerNameField;
    @FXML private Button addButton, saveButton, cancelButton;
    @FXML private GridPane pane;
    private final List<ComboBox<String>> name = new ArrayList<>();
    private final List<TextField> quantity = new ArrayList<>();

    private int currentRow = 0;
    private List<String> productType;

    private final InvoiceController invoiceController = new InvoiceController();
    private final ProductController productController = new ProductController();

    public CreateInvoiceScreenHandler(Stage stage, Employee employee) {
        super(stage, Path.CREATE_INVOICE_SCREEN_PATH);
        this.employee = employee;
        setScreenTitle("Create Invoice");
        productType = productController.findAllType();
    }

    @FXML
    public void initialize() {
        addButton.setOnAction(e -> addNewProduct());
        saveButton.setOnAction(e -> saveInvoice());
        cancelButton.setOnAction(e -> cancel());
    }

    private void addNewProduct() {
        ComboBox<String> tmpType = new ComboBox<>(FXCollections.observableList(productType));
        tmpType.resize(120, 28);
        ComboBox<String> tmpName = new ComboBox<>();
        tmpName.resize(298, 28);
        name.add(tmpName);
        Label tmpPrice = new Label();
        TextField tmpQuantity = new TextField();
        tmpQuantity.setAlignment(Pos.CENTER);
        quantity.add(tmpQuantity);
        tmpType.setOnAction(e -> {
            String selectedType = tmpType.getValue();
            tmpName.setItems(FXCollections.observableList(productController.findAllNameByType(selectedType)));
        });
        tmpName.setOnAction(e -> {
            String selectedName = tmpName.getValue();
            tmpPrice.setText("$ " + productController.findByName(selectedName).calcPrice());
        });
        pane.addRow(++currentRow, tmpType, tmpName, tmpPrice, tmpQuantity);
    }

    private void saveInvoice() {
        try {
            String customerName = customerNameField.getText();
            String phoneNumber = phoneNumberField.getText();
            Validator.checkInvoice(customerName, phoneNumber);
            invoiceController.save(employee, customerName, phoneNumber, name, quantity);
            CreateInvoiceScreenHandler screenHandler = new CreateInvoiceScreenHandler(this.stage, this.employee);
            screenHandler.show();
        } catch (InvalidInfoException | NotEnoughProductException e) {
            popup("Error", e.getMessage());
        }
    }

    private void cancel() {
        CreateInvoiceScreenHandler screenHandler = new CreateInvoiceScreenHandler(this.stage, this.employee);
        screenHandler.show();
    }
}

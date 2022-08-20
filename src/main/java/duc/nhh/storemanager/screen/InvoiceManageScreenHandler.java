package duc.nhh.storemanager.screen;

import duc.nhh.storemanager.controller.InvoiceController;
import duc.nhh.storemanager.exception.InvalidInfoException;
import duc.nhh.storemanager.model.Employee;
import duc.nhh.storemanager.model.Invoice;
import duc.nhh.storemanager.utils.Path;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class InvoiceManageScreenHandler extends ManagerHomeScreenHandler {
    @FXML private ComboBox<Integer> yearField;
    @FXML private ComboBox<Integer> monthField;
    @FXML private ComboBox<Integer> dayField;
    @FXML private GridPane pane;
    @FXML private TextField creatorField;
    @FXML private TextField customerField;
    @FXML private Button searchButton;

    private final InvoiceController invoiceController = new InvoiceController();
    public InvoiceManageScreenHandler(Stage stage, Employee employee) {
        super(stage, Path.INVOICE_MANAGE_SCREEN_PATH, employee);
        fillGridpane(invoiceController.findAll());
    }

    @Override
    @FXML
    public void initialize() {
        for(int i = LocalDate.now().getYear(); i > 2000; --i) {
            yearField.getItems().add(i);
        }
        yearField.setOnAction(e -> {
            if(monthField.getItems().isEmpty()) monthField.getItems().addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
            fillGridpane(invoiceController.findByYear(yearField.getValue()));
        });
        monthField.setOnAction(e -> {
            if(dayField.getItems().isEmpty()) {
                for (int i = 1; i <= LocalDate.of(yearField.getValue(), monthField.getValue(), 1).lengthOfMonth(); ++i) {
                    dayField.getItems().add(i);
                }
            }
            fillGridpane(invoiceController.findByYearAndMonth(yearField.getValue(), monthField.getValue()));
        });
        dayField.setOnAction(e -> fillGridpane(invoiceController.findByDate(yearField.getValue(),
                monthField.getValue(), dayField.getValue())));
        searchButton.setOnAction(e -> search());
    }

    private void fillGridpane(List<Invoice> listInvoice) {
        pane.getChildren().removeIf(node -> {
            Integer rowIndex = GridPane.getRowIndex(node);
            return rowIndex != null && rowIndex > 0;
        });
        int row = 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        for(Invoice invoice : listInvoice) {
            Button tmpID = new Button(invoice.getId() + "");
            tmpID.setPrefWidth(98);
            tmpID.setPrefHeight(28);
            tmpID.setOnAction(e -> {
                InvoiceDetailScreenHandler screenHandler = new InvoiceDetailScreenHandler(this.stage, this, invoice);
                screenHandler.show();
            });
            Label tmpCreator = new Label(invoice.getCreator().getName());
            Label tmpCustomer = new Label(invoice.getCustomer().getName());
            Label tmpDate = new Label(dateFormat.format(invoice.getDateCreated()));
            pane.addRow(row++, tmpID, tmpCreator, tmpCustomer, tmpDate);
        }
    }

    private void search() {
        String creatorName = creatorField.getText();
        String customerName = customerField.getText();
        if(!creatorName.isEmpty() && !customerName.isEmpty()) fillGridpane(invoiceController.findByCreatorAndCustomer(creatorName, customerName));
        else if(!creatorName.isEmpty()) fillGridpane(invoiceController.findByCreator(creatorName));
        else if(!customerName.isEmpty()) fillGridpane(invoiceController.findByCustomer(customerName));
        else throw new InvalidInfoException("Creator");
    }
}

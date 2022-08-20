package duc.nhh.storemanager.screen;

import duc.nhh.storemanager.controller.InvoiceController;
import duc.nhh.storemanager.model.Invoice;
import duc.nhh.storemanager.model.Product;
import duc.nhh.storemanager.utils.Path;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.text.SimpleDateFormat;
import java.util.List;

public class InvoiceDetailScreenHandler extends BaseScreenHandler {
    @FXML private Label invoiceLabel;
    @FXML private Label creatorLabel;
    @FXML private Label customerLabel;
    @FXML private Label dateLabel;
    @FXML private Button okButton;
    @FXML private Label totalLabel;
    @FXML private GridPane pane;

    private final InvoiceManageScreenHandler preScreenHandler;

    public InvoiceDetailScreenHandler(Stage stage, InvoiceManageScreenHandler screenHandler, Invoice invoice) {
        super(stage, Path.INVOICE_DETAIL_SCREEN_PATH);
        this.preScreenHandler = screenHandler;
        invoiceLabel.setText("Invoice " + invoice.getId());
        creatorLabel.setText(invoice.getCreator().getName());
        customerLabel.setText(invoice.getCustomer().getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        dateLabel.setText(dateFormat.format(invoice.getDateCreated()));
        InvoiceController invoiceController = new InvoiceController();
        fillGridpane(invoiceController.getListProduct(invoice.getId()));
    }
    
    @FXML
    public void initialize() {
        okButton.setOnAction(e -> preScreenHandler.show());
    }

    private void fillGridpane(List<Pair<Product, Integer>> listProduct) {
        pane.getChildren().removeIf(node -> {
            Integer rowIndex = GridPane.getRowIndex(node);
            return rowIndex != null && rowIndex > 0;
        });
        int row = 1;
        double total = 0.0;
        for(Pair<Product, Integer> pair : listProduct) {
            Label tmpType = new Label(pair.getKey().getType());
            Label tmpName = new Label(pair.getKey().getName());
            Label tmpPrice = new Label("$ " + pair.getKey().calcPrice());
            Label tmpQuantity = new Label(pair.getValue() + "");
            double curTotal = pair.getKey().calcPrice() * pair.getValue();
            total += curTotal;
            Label tmpTotal = new Label("$ " + curTotal);
            pane.addRow(row++, tmpType, tmpName, tmpPrice, tmpQuantity, tmpTotal);
        }
        totalLabel.setText("$ " + total);
    }
}

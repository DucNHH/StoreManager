package duc.nhh.storemanager.screen;

import duc.nhh.storemanager.controller.ProductController;
import duc.nhh.storemanager.exception.InvalidInfoException;
import duc.nhh.storemanager.exception.WrongInfoException;
import duc.nhh.storemanager.model.Employee;
import duc.nhh.storemanager.model.Product;
import duc.nhh.storemanager.utils.Path;
import duc.nhh.storemanager.utils.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductManageScreenHandler extends ManagerHomeScreenHandler {
    @FXML private TextField nameField;
    @FXML private ComboBox<String> typeField;
    @FXML private GridPane pane;
    @FXML private Button searchButton;
    @FXML private Button addButton;
    @FXML private Button changeButton;
    @FXML private Button deleteButton;

    private final ProductController productController = new ProductController();

    public ProductManageScreenHandler(Stage stage, Employee employee) {
        super(stage, Path.PRODUCT_MANAGE_SCREEN_PATH, employee);
        List<String> productType = productController.findAllType();
        typeField.getItems().addAll(productType);
        fillGridpane(productController.findAll());
        typeField.setOnAction(e -> fillGridpane(productController.findByType(typeField.getValue())));
        setScreenTitle("Manage Product");
    }

    @Override
    @FXML
    public void initialize() {
        super.initialize();
        searchButton.setOnAction(e -> search());
        addButton.setOnAction(e -> addNewProduct());
        changeButton.setOnAction(e -> changeProduct());
        deleteButton.setOnAction(e -> deleteProduct());
    }

    private void fillGridpane(List<Product> listProduct) {
        pane.getChildren().removeIf(node -> {
            Integer rowIndex = GridPane.getRowIndex(node);
            return rowIndex != null && rowIndex > 0;
        });
        int row = 1;
        for(Product product : listProduct) {
            Label tmpID = new Label(product.getId() + "");
            Label tmpType = new Label(product.getType());
            Label tmpName = new Label(product.getName());
            Label tmpPrice = new Label("$ " + product.calcPrice());
            Label tmpDiscount = new Label((product.getDiscount() * 100) + "%");
            Label tmpAvailable = new Label(product.getAvailable() + "");
            pane.addRow(row++, tmpID, tmpType, tmpName, tmpPrice, tmpDiscount, tmpAvailable);
        }
    }

    private void search() {
        String name = nameField.getText();
        String type = typeField.getValue();
        List<Product> listProduct = productController.findByNameAndType(name, type);
        fillGridpane(listProduct);
    }

    private void addNewProduct() {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Add product");

        GridPane grid = new GridPane();
        TextField typeText = new TextField();
        typeText.setPromptText("Type");
        TextField nameText = new TextField();
        nameText.setPromptText("Name");
        TextField priceText = new TextField();
        priceText.setPromptText("Price");
        TextField discountText = new TextField();
        discountText.setPromptText("Discount");
        TextField availableText = new TextField();
        availableText.setPromptText("Available");
        grid.addRow(0, new Label("Type:  "), typeText);
        grid.addRow(1, new Label("Name:  "), nameText);
        grid.addRow(2, new Label("Price:  "), priceText);
        grid.addRow(3, new Label("Discount:  "), discountText);
        grid.addRow(4, new Label("Available:  "), availableText);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    List<String> list = new ArrayList<>();
                    list.add(typeText.getText());
                    list.add(nameText.getText());
                    list.add(priceText.getText());
                    list.add(discountText.getText());
                    list.add(availableText.getText());
                    Validator.checkAddProduct(list);
                    return list;
                } catch (InvalidInfoException e) {
                    popup("Error", e.getMessage());
                    return null;
                }
            }
            return null;
        });
        Optional<List<String>> result = dialog.showAndWait();
        result.ifPresent(productController::addNewProduct);
        fillGridpane(productController.findAll());
    }

    private void changeProduct() {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Change Information");

        GridPane grid = new GridPane();
        TextField idText = new TextField();
        idText.setPromptText("ID");
        TextField typeText = new TextField();
        typeText.setPromptText("Type");
        TextField nameText = new TextField();
        nameText.setPromptText("Name");
        TextField priceText = new TextField();
        priceText.setPromptText("Price");
        TextField discountText = new TextField();
        discountText.setPromptText("Discount");
        TextField availableText = new TextField();
        availableText.setPromptText("Available");
        grid.addRow(0, new Label("ID:  "), idText);
        grid.addRow(1, new Label("Type:  "), typeText);
        grid.addRow(2, new Label("Name:  "), nameText);
        grid.addRow(3, new Label("Price:  "), priceText);
        grid.addRow(4, new Label("Discount:  "), discountText);
        grid.addRow(5, new Label("Available:  "), availableText);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    List<String> list = new ArrayList<>();
                    list.add(idText.getText());
                    list.add(typeText.getText());
                    list.add(nameText.getText());
                    list.add(priceText.getText());
                    list.add(discountText.getText());
                    list.add(availableText.getText());
                    Validator.checkChangeProduct(list);
                    return list;
                } catch (InvalidInfoException e) {
                    popup("Error", e.getMessage());
                    return null;
                }
            }
            return null;
        });
        Optional<List<String>> result = dialog.showAndWait();
        result.ifPresent(productController::updateProduct);
        fillGridpane(productController.findAll());
    }

    private void deleteProduct() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Delete Product");

        HBox box = new HBox();
        TextField idText = new TextField();
        idText.setPromptText("ID");
        box.getChildren().addAll(new Label("ID:  "), idText);

        dialog.getDialogPane().setContent(box);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    String id = idText.getText();
                    Validator.checkID(id);
                    return id;
                } catch (InvalidInfoException e) {
                    popup("Error", e.getMessage());
                    return null;
                }
            }
            return null;
        });
        Optional<String> result = dialog.showAndWait();
        try {
            result.ifPresent(productController::deleteProduct);
        } catch (WrongInfoException e) {
            popup("Error", e.getMessage());
        }
        fillGridpane(productController.findAll());
    }
}

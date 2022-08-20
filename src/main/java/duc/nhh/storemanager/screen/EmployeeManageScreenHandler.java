package duc.nhh.storemanager.screen;

import duc.nhh.storemanager.controller.EmployeeController;
import duc.nhh.storemanager.exception.InvalidInfoException;
import duc.nhh.storemanager.exception.InsufficientPermissionException;
import duc.nhh.storemanager.exception.WrongInfoException;
import duc.nhh.storemanager.model.Employee;
import duc.nhh.storemanager.utils.Path;
import duc.nhh.storemanager.utils.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EmployeeManageScreenHandler extends ManagerHomeScreenHandler {
    @FXML
    private TextField nameField;
    @FXML
    private Button searchButton;
    @FXML
    private Button addButton;
    @FXML
    private Button changeButton;
    @FXML
    private Button deleteButton;
    @FXML
    private GridPane pane;
    private final EmployeeController employeeController = new EmployeeController();;

    public EmployeeManageScreenHandler(Stage stage, Employee employee) {
        super(stage, Path.EMPLOYEE_MANAGE_SCREEN_PATH, employee);
        setScreenTitle("Manage Employee");
        fillGridpane(employeeController.findAll(this.employee));
    }

    @Override
    @FXML
    public void initialize() {
        super.initialize();
        searchButton.setOnAction(e -> search());
        addButton.setOnAction(e -> addNewEmployee());
        changeButton.setOnAction(e -> changeEmployee());
        deleteButton.setOnAction(e -> deleteEmployee());
    }

    private void fillGridpane(List<Employee> listEmployee) {
        pane.getChildren().removeIf(node -> {
            Integer rowIndex = GridPane.getRowIndex(node);
            return rowIndex != null && rowIndex > 0;
        });
        int row = 1;
        for(Employee employee : listEmployee) {
            Label tmpID = new Label(employee.getId() + "");
            Label tmpName = new Label(employee.getName());
            Label tmpPermission = new Label(employee.getPermission() + "");
            pane.addRow(row++, tmpID, tmpName, tmpPermission);
        }
    }
    private void search() {
        String name = nameField.getText();
        List<Employee> listEmployee = employeeController.findByName(name, employee);
        fillGridpane(listEmployee);
    }

    private void addNewEmployee() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add Employee");

        GridPane grid = new GridPane();
        TextField nameText = new TextField();
        nameText.setPromptText("Name");
        TextField permissionText = new TextField();
        permissionText.setPromptText("Permission");
        grid.addRow(0, new Label("Name:  "), nameText);
        grid.addRow(1, new Label("Permission:  "), permissionText);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    String name = nameText.getText();
                    String permission = permissionText.getText();
                    Validator.checkAddEmployee(name, permission);
                    return new Pair<>(name, permission);
                } catch (InvalidInfoException e) {
                    popup("Error", e.getMessage());
                    return null;
                }
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        try {
            result.ifPresent(string -> employeeController.addNewEmployee(string.getKey(), string.getValue(), employee));
        } catch (InsufficientPermissionException e) {
            popup("Error", e.getMessage());
        }
        fillGridpane(employeeController.findAll(employee));
    }

    private void changeEmployee() {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Change Information");

        GridPane grid = new GridPane();
        TextField idText = new TextField();
        idText.setPromptText("ID");
        TextField nameText = new TextField();
        nameText.setPromptText("Name");
        TextField permissionText = new TextField();
        permissionText.setPromptText("Permission");
        grid.addRow(0, new Label("ID:  "), idText);
        grid.addRow(1, new Label("Name:  "), nameText);
        grid.addRow(2, new Label("Permission:  "), permissionText);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    String id = idText.getText();
                    String name = nameText.getText();
                    String permission = permissionText.getText();
                    Validator.checkChangeEmployee(id, name, permission);
                    return Arrays.asList(id, name, permission);
                } catch (InvalidInfoException e) {
                    popup("Error", e.getMessage());
                    return null;
                }
            }
            return null;
        });
        Optional<List<String>> result = dialog.showAndWait();
        try {
            result.ifPresent(list -> employeeController.updateEmployee(list.get(0), list.get(1), list.get(2), employee));
        } catch (WrongInfoException | InsufficientPermissionException e) {
            popup("Error", e.getMessage());
        }
        fillGridpane(employeeController.findAll(employee));
    }

    private void deleteEmployee() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Delete Employee");

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
            result.ifPresent(string -> employeeController.deleteEmployee(string, employee));
        } catch (WrongInfoException | InsufficientPermissionException e) {
            popup("Error", e.getMessage());
        }
        fillGridpane(employeeController.findAll(employee));
    }
}

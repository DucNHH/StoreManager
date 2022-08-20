package duc.nhh.storemanager.screen;

import duc.nhh.storemanager.controller.EmployeeController;
import duc.nhh.storemanager.exception.InvalidInfoException;
import duc.nhh.storemanager.exception.WrongInfoException;
import duc.nhh.storemanager.model.Employee;
import duc.nhh.storemanager.utils.Path;
import duc.nhh.storemanager.utils.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class LoginScreenHandler extends BaseScreenHandler {
    @FXML
    private TextField userIDField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    private final EmployeeController employeeController = new EmployeeController();

    public LoginScreenHandler(Stage stage) {
        super(stage, Path.LOGIN_SCREEN_PATH);
        setScreenTitle("Login");
    }

    @FXML
    public void initialize() {
        loginButton.setOnAction(e -> login());
        passwordField.setOnKeyPressed(key -> {
            if(key.getCode() == KeyCode.ENTER) login();
        });
    }

    private void login() {
        String id = userIDField.getText();
        String password = passwordField.getText();
        try {
            Validator.checkLogin(id, password);
            Employee employee = employeeController.login(id, password);
            if (employee == null) throw new WrongInfoException("ID or password");
            BaseScreenHandler screenHandler;
            if (employee.getPermission() == 1) {
                screenHandler = new CreateInvoiceScreenHandler(this.stage, employee);
            } else {
                screenHandler = new ManagerHomeScreenHandler(this.stage, employee);
            }
            screenHandler.show();
        } catch (InvalidInfoException | WrongInfoException e) {
            popup("Error", e.getMessage());
        }
    }
}

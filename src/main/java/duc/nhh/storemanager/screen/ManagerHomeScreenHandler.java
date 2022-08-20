package duc.nhh.storemanager.screen;

import duc.nhh.storemanager.model.Employee;
import duc.nhh.storemanager.utils.Path;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ManagerHomeScreenHandler extends BaseScreenHandler {
    @FXML protected MenuItem revenueStatistic;
    @FXML protected MenuItem productStatistic;
    @FXML protected MenuItem employeeManage;
    @FXML protected MenuItem productManage;
    @FXML protected MenuItem invoiceManage;
    protected Employee employee;
    public ManagerHomeScreenHandler(Stage stage, Employee employee) {
        super(stage, Path.MANAGER_HOME_SCREEN_PATH);
        this.employee = employee;
        setScreenTitle("Manager Home");
    }

    public ManagerHomeScreenHandler(Stage stage, String screenPath, Employee employee) {
        super(stage, screenPath);
        this.employee = employee;
    }

    @FXML
    public void initialize() {
        revenueStatistic.setOnAction(e -> revenueStatistic());
        productStatistic.setOnAction(e -> productStatistic());
        employeeManage.setOnAction(e -> employeeManage());
        productManage.setOnAction(e -> productManage());
        invoiceManage.setOnAction(e -> invoiceManage());
    }

    protected void revenueStatistic() {
        RevenueStatisticScreenHandler screenHandler = new RevenueStatisticScreenHandler(this.stage, this.employee);
        screenHandler.show();
    }

    protected void productStatistic() {
        ProductStatisticScreenHandler screenHandler = new ProductStatisticScreenHandler(this.stage, this.employee);
        screenHandler.show();
    }

    protected void employeeManage() {
        EmployeeManageScreenHandler screenHandler = new EmployeeManageScreenHandler(this.stage, this.employee);
        screenHandler.show();
    }

    protected void productManage() {
        ProductManageScreenHandler screenHandler = new ProductManageScreenHandler(this.stage, this.employee);
        screenHandler.show();
    }

    protected void invoiceManage() {
        InvoiceManageScreenHandler screenHandler = new InvoiceManageScreenHandler(this.stage, this.employee);
        screenHandler.show();
    }
}

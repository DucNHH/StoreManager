package duc.nhh.storemanager.screen;

import duc.nhh.storemanager.model.Employee;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseScreenHandler {
    protected FXMLLoader loader;
    protected AnchorPane content;
    protected Stage stage;
    private Scene scene;
    protected Employee employee;

    public BaseScreenHandler(Stage stage, String screenPath) {
        try {
            this.loader = new FXMLLoader(getClass().getResource(screenPath));
            this.loader.setController(this);
            this.content = loader.load();
            this.stage = stage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        if (this.scene == null) {
            this.scene = new Scene(this.content);
        }
        this.stage.setScene(this.scene);
        this.stage.centerOnScreen();
        this.stage.show();
    }

    public void setScreenTitle(String string) {
        this.stage.setTitle(string);
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void popup(String type, String content) {
        Alert alert = new Alert(Alert.AlertType.NONE, content, ButtonType.OK);
        alert.setTitle(type);
        alert.showAndWait();
    }
}

package duc.nhh.storemanager;

import duc.nhh.storemanager.screen.LoginScreenHandler;
import duc.nhh.storemanager.utils.Path;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        LoginScreenHandler handler = new LoginScreenHandler(stage);
        handler.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
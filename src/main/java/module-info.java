module duc.nhh.storemanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens duc.nhh.storemanager.screen to javafx.fxml;
    exports duc.nhh.storemanager;
}
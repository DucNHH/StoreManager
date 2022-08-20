package duc.nhh.storemanager.screen;

import duc.nhh.storemanager.controller.InvoiceController;
import duc.nhh.storemanager.controller.ProductController;
import duc.nhh.storemanager.model.Employee;
import duc.nhh.storemanager.utils.Path;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductStatisticScreenHandler extends ManagerHomeScreenHandler {
    @FXML
    private PieChart pieChart1, pieChart2, pieChart3;

    public ProductStatisticScreenHandler(Stage stage, Employee employee) {
        super(stage, Path.PRODUCT_STATISTIC_SCREEN_PATH, employee);
    }

    @Override
    @FXML
    public void initialize() {
        super.initialize();
        InvoiceController invoiceController = new InvoiceController();
        ProductController productController = new ProductController();
        int year = LocalDate.now().getYear() - 2;
        PieChart[] pie = new PieChart[]{pieChart1, pieChart2, pieChart3};
        List<String> listProductType = productController.findAllType();
        for(int i = 0; i < 3; ++i) {
            List<Pair<String, Double>> list = new ArrayList<>();
            for(String s : listProductType) {
                list.add(new Pair<>(s, invoiceController.getRevenue(s, year + i)));
            }
            List<PieChart.Data> data = new ArrayList<>();
            list.forEach(pair -> data.add(new PieChart.Data(pair.getKey(), pair.getValue())));
            pie[i].getData().addAll(data);
            pie[i].setTitle(year + i + "");
        }
    }
}

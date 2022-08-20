package duc.nhh.storemanager.screen;

import duc.nhh.storemanager.controller.InvoiceController;
import duc.nhh.storemanager.model.Employee;
import duc.nhh.storemanager.utils.Path;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.time.LocalDate;

public class RevenueStatisticScreenHandler extends ManagerHomeScreenHandler {
    @FXML
    private BarChart barChart;

    private int year;
    private double revenue[][];

    private InvoiceController invoiceController;

    public RevenueStatisticScreenHandler(Stage stage, Employee employee) {
        super(stage, Path.REVENUE_STATISTIC_SCREEN_PATH, employee);
    }

    @Override
    @FXML
    public void initialize() {
        super.initialize();
        invoiceController = new InvoiceController();
        year = LocalDate.now().getYear() - 2;
        revenue = new double[3][12];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 12; ++j) {
                revenue[i][j] = invoiceController.getRevenue(i + year, j + 1);
            }
        }
        monthlyStatistic();
    }

    private void monthlyStatistic() {
        XYChart.Series[] series = new XYChart.Series[3];
        for (int i = 0; i < 3; ++i) {
            series[i] = new XYChart.Series();
            series[i].setName((year + i) + "");
            for (int j = 0; j < 12; ++j) {
                series[i].getData().add(new XYChart.Data(j + 1 + "", revenue[i][j]));
            }
        }
        barChart.getData().clear();
        barChart.getXAxis().autosize();
        barChart.getXAxis().setLabel("Month");
        barChart.getYAxis().setLabel("Revenue");
        barChart.getData().addAll(series);
    }
}

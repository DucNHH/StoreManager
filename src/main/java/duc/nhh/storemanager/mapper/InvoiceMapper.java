package duc.nhh.storemanager.mapper;

import duc.nhh.storemanager.controller.EmployeeController;
import duc.nhh.storemanager.controller.InvoiceController;
import duc.nhh.storemanager.model.Invoice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class InvoiceMapper implements RowMapper<Invoice> {
    @Override
    public Invoice mapRow(ResultSet resultSet) {
        Invoice invoice = new Invoice();
        EmployeeController employeeController = new EmployeeController();
        InvoiceController invoiceController = new InvoiceController();
        try {
            invoice.setId(resultSet.getInt("id"));
            invoice.setCreator(employeeController.findByID(resultSet.getInt("creatorID")));
            invoice.setCustomer(invoiceController.findCustomerByID(resultSet.getInt("customerID")));
            invoice.setDateCreated(new Date(resultSet.getTimestamp("dateCreated").getTime()));
            return invoice;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

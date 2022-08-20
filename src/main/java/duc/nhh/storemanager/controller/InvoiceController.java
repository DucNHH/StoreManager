package duc.nhh.storemanager.controller;

import duc.nhh.storemanager.dao.CustomerDAO;
import duc.nhh.storemanager.dao.InvoiceDAO;
import duc.nhh.storemanager.dao.ProductDAO;
import duc.nhh.storemanager.exception.NotEnoughProductException;
import duc.nhh.storemanager.exception.WrongInfoException;
import duc.nhh.storemanager.model.Customer;
import duc.nhh.storemanager.model.Employee;
import duc.nhh.storemanager.model.Invoice;
import duc.nhh.storemanager.model.Product;
import duc.nhh.storemanager.utils.Validator;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceController {
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final ProductDAO productDAO = new ProductDAO();

    public List<Invoice> findAll() {
        return invoiceDAO.findAll();
    }

    public List<Invoice> findByYear(int year) {
        return invoiceDAO.findByYear(year);
    }

    public List<Invoice> findByYearAndMonth(int year, int month) {
        return invoiceDAO.findByYearAndMonth(year, month);
    }

    public List<Invoice> findByDate(int year, int month, int day) {
        return invoiceDAO.findByDate(year, month, day);
    }

    public List<Invoice> findByCreator(String creatorName) {
        return invoiceDAO.findByCreator(creatorName);
    }

    public List<Invoice> findByCustomer(String customerName) {
        return invoiceDAO.findByCustomer(customerName);
    }

    public List<Invoice> findByCreatorAndCustomer(String creatorName, String customerName) {
        return invoiceDAO.findByCreatorAndCustomer(creatorName, customerName);
    }

    public Customer findCustomerByID(int id) {
        List<Customer> list = customerDAO.findByID(id);
        if(list.isEmpty()) throw new WrongInfoException("ID");
        return list.get(0);
    }

    public List<Pair<Product, Integer>> getListProduct(int invoiceId) {
        List<Pair<Integer, Integer>> list = invoiceDAO.getListProduct(invoiceId);
        List<Pair<Product, Integer>> listProduct = new ArrayList<>();
        for(Pair<Integer, Integer> pair : list) {
            List<Product> product = productDAO.findByID(pair.getKey());
            if(product.isEmpty()) throw new WrongInfoException("ID");
            listProduct.add(new Pair<>(product.get(0), pair.getValue()));
        }
        return listProduct;
    }

    public double getRevenue(int year, int month) {
        return invoiceDAO.getRevenue(year, month);
    }

    public double getRevenue(String type, int year) {
        return invoiceDAO.getRevenue(type, year);
    }

    public void save(Employee employee, String customerName, String customerPhone, List<ComboBox<String>> productName, List<TextField> productQuantity) {
        List<Pair<Product, Integer>> listProduct = new ArrayList<>();
        double value = 0.0;
        for (int i = 0; i < productName.size(); ++i) {
            Product product = productDAO.findByName(productName.get(i).getValue()).get(0);
            int available = product.getAvailable();
            String tmpQuantity = productQuantity.get(i).getText();
            Validator.checkQuantity(tmpQuantity);
            int quantity = Integer.parseInt(tmpQuantity);
            if (available < quantity) throw new NotEnoughProductException(product.getName());
            product.setAvailable(available - quantity);
            value += product.calcPrice() * quantity;
            listProduct.add(new Pair<>(product, quantity));
        }
        int customerId;
        List<Customer> listCustomer = customerDAO.findByNameAndPhone(customerName, customerPhone);
        if (listCustomer.isEmpty()) {
            Customer customer = new Customer();
            customer.setName(customerName);
            customer.setPhoneNumber(customerPhone);
            customerId = customerDAO.insert(customer);
        } else customerId = listCustomer.get(0).getId();
        int invoiceId = invoiceDAO.insert(employee.getId(), customerId, new Date(), value);
        for(Pair<Product, Integer> pair : listProduct) {
            invoiceDAO.insertProduct(invoiceId, pair.getKey().getId(), pair.getValue());
            productDAO.update(pair.getKey());
        }
    }
}

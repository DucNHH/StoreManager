package duc.nhh.storemanager.dao;

import duc.nhh.storemanager.mapper.DoubleMapper;
import duc.nhh.storemanager.mapper.InvoiceMapper;
import duc.nhh.storemanager.mapper.PairMapper;
import duc.nhh.storemanager.model.Invoice;
import javafx.util.Pair;

import java.util.Date;
import java.util.List;

public class InvoiceDAO extends ModelDAO {
    public List<Invoice> findAll() {
        String sql = "SELECT * FROM invoice";
        return query(sql, new InvoiceMapper());
    }

    public List<Invoice> findByYear(int year) {
        String sql = "SELECT * FROM invoice WHERE YEAR(dateCreated) = ?";
        return query(sql, new InvoiceMapper(), year);
    }

    public List<Invoice> findByYearAndMonth(int year, int month) {
        String sql = "SELECT * FROM invoice WHERE YEAR(dateCreated) = ? AND MONTH(dateCreated) = ?";
        return query(sql, new InvoiceMapper(), year, month);
    }

    public List<Invoice> findByDate(int year, int month, int day) {
        String sql = "SELECT * FROM invoice WHERE YEAR(dateCreated) = ? AND MONTH(dateCreated) = ? AND DAY(dateCreated) = ?";
        return query(sql, new InvoiceMapper(), year, month, day);
    }

    public List<Invoice> findByCreator(String creatorName) {
        String sql = "SELECT * FROM invoice WHERE creatorID IN (SELECT id FROM employee WHERE permission = 1 AND name LIKE ?)";
        return query(sql, new InvoiceMapper(), "%" + creatorName + "%");
    }

    public List<Invoice> findByCustomer(String customerName) {
        String sql = "SELECT * FROM invoice WHERE customerID IN (SELECT id FROM customer WHERE name LIKE ?)";
        return query(sql, new InvoiceMapper(), "%" + customerName + "%");
    }

    public List<Invoice> findByCreatorAndCustomer(String creatorName, String customerName) {
        String sql = "SELECT * FROM invoice WHERE creatorID IN (SELECT id FROM employee WHERE permission = 1 AND name LIKE ?)" +
                "AND customerID IN (SELECT id FROM customer WHERE name LIKE ?)";
        return query(sql, new InvoiceMapper(), "%" + creatorName + "%", "%" + customerName + "%");
    }

    public List<Pair<Integer, Integer>> getListProduct(int invoiceID) {
        String sql = "SELECT productID, quantity FROM invoice_product WHERE invoiceID = ?";
        return query(sql, new PairMapper(), invoiceID);
    }

    public double getRevenue(int year, int month) {
        String sql = "SELECT SUM(value) FROM invoice WHERE YEAR(dateCreated) = ? AND MONTH(dateCreated) = ?";
        return query(sql, new DoubleMapper(), year, month).get(0);
    }

    public double getRevenue(String type, int year) {
        String sql = "SELECT SUM(price * quantity) FROM invoice i INNER JOIN invoice_product ip ON i.id = ip.invoiceID INNER JOIN product p " +
                "ON ip.productID = p.id WHERE type = ? AND YEAR(dateCreated) = ?";
        return query(sql, new DoubleMapper(), type, year).get(0);
    }

    public int insert(int creatorID, int customerID, Date dateCreated, double value) {
        String sql = "INSERT INTO invoice (creatorID, customerID, dateCreated, value) VALUES (?, ?, ?, ?) ";
        return insert(sql, creatorID, customerID, dateCreated, value);
    }

    public void insertProduct(int invoiceID, int productID, int quantity) {
        String sql = "INSERT INTO invoice_product VALUES (?, ?, ?)";
        insert(sql, invoiceID, productID, quantity);
    }
}

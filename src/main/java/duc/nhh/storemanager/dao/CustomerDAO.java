package duc.nhh.storemanager.dao;

import duc.nhh.storemanager.mapper.CustomerMapper;
import duc.nhh.storemanager.model.Customer;

import java.util.List;

public class CustomerDAO extends ModelDAO {
    public List<Customer> findByID(int id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        return query(sql, new CustomerMapper(), id);
    }
    public List<Customer> findByNameAndPhone(String name, String phone) {
        String sql = "SELECT * FROM customer WHERE phoneNumber = ? AND name = ?";
        return query(sql, new CustomerMapper(), phone, name);
    }

    public int insert(Customer customer) {
        String sql = "INSERT INTO customer (name, phoneNumber) VALUES (?, ?)";
        return insert(sql, customer.getName(), customer.getPhoneNumber());
    }
}

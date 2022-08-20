package duc.nhh.storemanager.mapper;

import duc.nhh.storemanager.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet resultSet) {
        Customer customer = new Customer();
        try {
            customer.setId(resultSet.getInt("id"));
            customer.setName(resultSet.getString("name"));
            customer.setPhoneNumber(resultSet.getString("phoneNumber"));
            return customer;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

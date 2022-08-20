package duc.nhh.storemanager.mapper;

import duc.nhh.storemanager.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product>{
    @Override
    public Product mapRow(ResultSet resultSet) {
        Product product = new Product();
        try {
            product.setId(resultSet.getInt("id"));
            product.setType(resultSet.getString("type"));
            product.setName(resultSet.getString("name"));
            product.setPrice(resultSet.getDouble("price"));
            product.setDiscount(resultSet.getDouble("discount"));
            product.setAvailable(resultSet.getInt("available"));
            return product;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

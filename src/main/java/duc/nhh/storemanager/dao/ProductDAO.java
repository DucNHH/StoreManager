package duc.nhh.storemanager.dao;

import duc.nhh.storemanager.mapper.ProductMapper;
import duc.nhh.storemanager.mapper.StringMapper;
import duc.nhh.storemanager.model.Product;

import java.util.List;

public class ProductDAO extends ModelDAO {
    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        return query(sql, new ProductMapper());
    }

    public List<String> findAllType() {
        String sql = "SELECT DISTINCT type FROM product";
        return query(sql, new StringMapper());
    }

    public List<String> findAllNameByType(String type) {
        String sql = "SELECT name FROM product WHERE type = ?";
        return query(sql, new StringMapper(), type);
    }

    public List<Product> findByID(int id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        return query(sql, new ProductMapper(), id);
    }

    public List<Product> findByName(String name) {
        String sql = "SELECT * FROM product WHERE name LIKE ?";
        return query(sql, new ProductMapper(), "%" + name + "%");
    }

    public List<Product> findByNameAndType(String name, String type) {
        String sql = "SELECT * FROM product WHERE name = ? AND type = ?";
        return query(sql, new ProductMapper(), "%" + name + "%", type);
    }

    public List<Product> findByType(String type) {
        String sql = "SELECT * FROM product WHERE type = ?";
        return query(sql, new ProductMapper(), type);
    }

    public void insert(Product product) {
        String sql = "INSERT INTO product (type, name, price, discount, available) VALUES (?, ?, ?, ?, ?)";
        insert(sql, product.getType(), product.getName(), product.getPrice(), product.getDiscount(), product.getAvailable());
    }

    public void update(Product product) {
        String sql = "UPDATE product SET type = ?, name = ?, price = ?, discount = ?, available = ? WHERE id = ?";
        update(sql, product.getType(), product.getName(), product.getPrice(), product.getDiscount(), product.getAvailable(), product.getId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM product WHERE id = ?";
        delete(sql, id);
    }
}

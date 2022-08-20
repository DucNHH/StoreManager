package duc.nhh.storemanager.controller;

import duc.nhh.storemanager.dao.ProductDAO;
import duc.nhh.storemanager.exception.WrongInfoException;
import duc.nhh.storemanager.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductController {
    private final ProductDAO productDAO = new ProductDAO();

    public List<Product> findAll() {
        return productDAO.findAll();
    }

    public List<String> findAllType() {
        return productDAO.findAllType();
    }

    public List<String> findAllNameByType(String type) {
        return productDAO.findAllNameByType(type);
    }

    public Product findByName(String name) {
        return productDAO.findByName(name).get(0);
    }

    public List<Product> findByNameAndType(String name, String type) {
        if(type == null) return productDAO.findByName(name);
        return productDAO.findByNameAndType(name, type);
    }

    public List<Product> findByType(String type) {
        return productDAO.findByType(type);
    }

    public void addNewProduct(List<String> list) {
        Product product = new Product();
        product.setType(list.get(0));
        product.setName(list.get(1));
        product.setPrice(Double.parseDouble(list.get(2)));
        product.setDiscount(Double.parseDouble(list.get(3)));
        product.setAvailable(Integer.parseInt(list.get(4)));
        productDAO.insert(product);
    }

    public void updateProduct(List<String> list) {
        List<Product> listProduct = productDAO.findByID(Integer.parseInt(list.get(0)));
        if(listProduct.isEmpty()) throw new WrongInfoException("ID");
        Product product = listProduct.get(0);
        if(!list.get(1).isEmpty()) product.setType(list.get(1));
        if(!list.get(2).isEmpty()) product.setName(list.get(2));
        if(!list.get(3).isEmpty()) product.setPrice(Double.parseDouble(list.get(3)));
        if(!list.get(4).isEmpty()) product.setDiscount(Double.parseDouble(list.get(4)));
        if(!list.get(5).isEmpty()) product.setAvailable(Integer.parseInt(list.get(5)));
        productDAO.update(product);
    }

    public void deleteProduct(String id) {
        List<Product> list = productDAO.findByID(Integer.parseInt(id));
        if(list.isEmpty()) throw new WrongInfoException("ID");
        productDAO.delete(Integer.parseInt(id));
    }
}

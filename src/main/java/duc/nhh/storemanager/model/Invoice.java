package duc.nhh.storemanager.model;

import javafx.util.Pair;

import java.util.Date;
import java.util.List;

public class Invoice {
    private int id;
    private Employee creator;
    private Customer customer;
    private Date dateCreated;
    private List<Pair<Product, Integer>> listProducts;
    private long value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getCreator() {
        return creator;
    }

    public void setCreator(Employee creator) {
        this.creator = creator;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Pair<Product, Integer>> getListProducts() {
        return listProducts;
    }

    public void setListProducts(List<Pair<Product, Integer>> listProducts) {
        this.listProducts = listProducts;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}

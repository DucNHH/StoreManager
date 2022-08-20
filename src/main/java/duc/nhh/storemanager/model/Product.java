package duc.nhh.storemanager.model;

public class Product {
    private int id;
    private String type;
    private String name;
    private double price;
    private double discount;
    private int available;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double calcPrice() {
        return (int)(price * (1.0 - discount) * 100) / 100.0;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}

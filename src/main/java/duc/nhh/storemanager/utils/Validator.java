package duc.nhh.storemanager.utils;

import duc.nhh.storemanager.exception.InvalidInfoException;

import java.util.List;
import java.util.regex.Pattern;

public class Validator {

    public static void checkID(String id) {
        if (!Pattern.matches("\\d+", id)) throw new InvalidInfoException("ID");
    }

    public static void checkName(String name) {
        if (!Pattern.matches("[A-Z][a-z]+( [A-Z][a-z]+)*", name)) throw new InvalidInfoException("name");
    }

    public static void checkPassword(String password) {
        if (!Pattern.matches("[a-zA-Z\\d]+", password)) throw new InvalidInfoException("password");
    }

    public static void checkPermission(String permission) {
        if (!Pattern.matches("[1-3]", permission)) throw new InvalidInfoException("permission");
    }

    public static void checkPhoneNumber(String phoneNumber) {
        if (!Pattern.matches("0\\d{9}", phoneNumber)) throw new InvalidInfoException("phone number");
    }

    public static void checkType(String type) {
        if (!Pattern.matches("[a-z]+", type)) throw new InvalidInfoException("type");
    }

    public static void checkProductName(String productName) {
        if (!Pattern.matches("[a-z\\d]+( [a-z\\d]+)*", productName)) throw new InvalidInfoException("product name");
    }

    public static void checkQuantity(String quantity) {
        if (!Pattern.matches("\\d+", quantity)) throw new InvalidInfoException("quantity");
    }

    public static void checkPrice(String price) {
        if(!Pattern.matches("\\d+[.]\\d+", price)) throw new InvalidInfoException("price");
    }

    public static void checkDiscount(String discount) {
        if(!Pattern.matches("0[.]\\d{1,2}", discount)) throw new InvalidInfoException("discount");
    }

    public static void checkAvailable(String available) {
        if (!Pattern.matches("\\d+", available)) throw new InvalidInfoException("available");
    }

    public static void checkAddEmployee(String name, String permission) {
        checkName(name);
        checkPermission(permission);
    }

    public static void checkAddProduct(List<String> list) {
        checkType(list.get(0));
        checkProductName(list.get(1));
        checkPrice(list.get(2));
        checkDiscount(list.get(3));
        checkAvailable(list.get(4));
    }

    public static void checkChangeEmployee(String id, String name, String permission) {
        checkID(id);
        if (!name.isEmpty()) checkName(name);
        if (!permission.isEmpty()) checkPermission(permission);
        if (name.isEmpty() && permission.isEmpty()) checkName(name);
    }

    public static void checkChangeProduct(List<String> list) {
        checkID(list.get(0));
        boolean changed = false;
        if(!list.get(1).isEmpty()) {
            checkType(list.get(1));
            changed = true;
        }
        if(!list.get(2).isEmpty()) {
            changed = true;
        }
        if(!list.get(3).isEmpty()) {
            checkPrice(list.get(3));
            changed = true;
        }
        if(!list.get(4).isEmpty()) {
            checkDiscount(list.get(4));
            changed = true;
        }
        if(!list.get(5).isEmpty()) {
            checkAvailable(list.get(5));
            changed = true;
        }
        if(!changed) checkType(list.get(1));
    }

    public static void checkInvoice(String customerName, String phoneNumber) {
        checkName(customerName);
        checkPhoneNumber(phoneNumber);
    }
    public static void checkLogin(String id, String password) {
        checkID(id);
        checkPassword(password);
    }
}

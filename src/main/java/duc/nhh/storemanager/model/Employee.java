package duc.nhh.storemanager.model;

import java.util.Random;

public class Employee {
    private int id;
    private String name;
    private String password;
    private int permission;

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

    public String getPassword() {
        return password;
    }

    public void genPassword() {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for(int i = 0; i < 4; ++i) {
            sb.append((char)(rand.nextInt(26) + 'a'));
        }
        for(int i = 0; i < 2; ++i) {
            sb.append(rand.nextInt(10));
        }
        this.password = sb.toString();
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }
}

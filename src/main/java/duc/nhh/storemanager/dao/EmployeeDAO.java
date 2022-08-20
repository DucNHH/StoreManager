package duc.nhh.storemanager.dao;

import duc.nhh.storemanager.mapper.EmployeeMapper;
import duc.nhh.storemanager.model.Employee;

import java.util.List;

public class EmployeeDAO extends ModelDAO {
    public List<Employee> findByIDAndPassword(int id, String password) {
        String sql = "SELECT * FROM employee WHERE id = ? AND password = ?";
        return query(sql, new EmployeeMapper(), id, password);
    }

    public List<Employee> findByID(int id) {
        String sql = "SELECT * FROM employee WHERE id = ?";
        return query(sql, new EmployeeMapper(), id);
    }

    public List<Employee> findByName(String name, int permission) {
        String sql = "SELECT * FROM employee WHERE name LIKE ? AND permission < ?";
        return query(sql, new EmployeeMapper(), "%" + name + "%", permission);
    }

    public List<Employee> findAll(int permission) {
        String sql = "SELECT * FROM employee WHERE permission < ?";
        return query(sql, new EmployeeMapper(), permission);
    }

    public void insert(Employee employee) {
        String sql = "INSERT INTO employee (name, password, permission) VALUES (?, ?, ?)";
        insert(sql, employee.getName(), employee.getPassword(), employee.getPermission());
    }

    public void update(Employee employee) {
        String sql = "UPDATE employee SET name=?, permission=? WHERE id=?";
        update(sql, employee.getName(), employee.getPermission(), employee.getId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM employee WHERE id = ?";
        delete(sql, id);
    }
}

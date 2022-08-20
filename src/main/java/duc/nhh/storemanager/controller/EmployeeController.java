package duc.nhh.storemanager.controller;

import duc.nhh.storemanager.dao.EmployeeDAO;
import duc.nhh.storemanager.exception.InsufficientPermissionException;
import duc.nhh.storemanager.exception.WrongInfoException;
import duc.nhh.storemanager.model.Employee;

import java.util.List;

public class EmployeeController {
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    public Employee findByID(int id) {
        List<Employee> list = employeeDAO.findByID(id);
        if(list.isEmpty()) throw new WrongInfoException("ID");
        return list.get(0);
    }

    public List<Employee> findByName(String name, Employee employee) {
        return employeeDAO.findByName(name, employee.getPermission());
    }

    public List<Employee> findAll(Employee employee) {
        return employeeDAO.findAll(employee.getPermission());
    }

    public Employee login(String id, String password) {
        List<Employee> list = employeeDAO.findByIDAndPassword(Integer.parseInt(id), password);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    public void addNewEmployee(String name, String permission, Employee employee) {
        if (employee.getPermission() <= Integer.parseInt(permission)) throw new InsufficientPermissionException();
        Employee newEmployee = new Employee();
        newEmployee.setName(name);
        newEmployee.genPassword();
        newEmployee.setPermission(Integer.parseInt(permission));
        employeeDAO.insert(newEmployee);
    }

    public void updateEmployee(String id, String name, String permission, Employee employee) {
        Employee updateEmployee = findByID(Integer.parseInt(id));
        if (updateEmployee.getPermission() >= employee.getPermission()) throw new InsufficientPermissionException();
        if (!name.isEmpty()) updateEmployee.setName(name);
        if (!permission.isEmpty()) updateEmployee.setPermission(Integer.parseInt(permission));
        employeeDAO.update(employee);
    }

    public void deleteEmployee(String id, Employee employee) {
        Employee deleteEmployee = findByID(Integer.parseInt(id));
        if (deleteEmployee.getPermission() >= employee.getPermission()) throw new InsufficientPermissionException();
        employeeDAO.delete(Integer.parseInt(id));
    }
}

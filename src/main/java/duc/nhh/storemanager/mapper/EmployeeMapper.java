package duc.nhh.storemanager.mapper;

import duc.nhh.storemanager.model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet resultSet) {
        Employee employee = new Employee();
        try {
            employee.setId(resultSet.getInt("id"));
            employee.setName(resultSet.getString("name"));
            employee.setPermission(resultSet.getInt("permission"));
            return employee;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

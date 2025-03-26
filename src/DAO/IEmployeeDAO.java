package DAO;

import Model.Employee;
import java.util.List;

public interface IEmployeeDAO {
    List<Employee> getAllEmployees();
    void addEmployee(Employee emp);
    void updateEmployee(Employee emp);
    void deleteEmployee(int employeeId);
    Employee checkLogin(String username, String password);
    Employee getEmployeeById(int employeeId);
}

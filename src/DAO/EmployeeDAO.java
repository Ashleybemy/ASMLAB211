package DAO;
import Model.Employee;
import Utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    // Lấy danh sách nhân viên
    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM Employee";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setEmployeeId(rs.getInt("employee_id"));
                emp.setName(rs.getString("name"));
                emp.setRole(rs.getString("role"));
                emp.setSalary(rs.getDouble("salary"));
                emp.setUsername(rs.getString("username"));
                // Mật khẩu không nên đưa vào đối tượng khi hiển thị danh sách
                list.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm nhân viên
    public void addEmployee(Employee emp) {
        String sql = "INSERT INTO Employee(name, role, salary, username, password) VALUES (?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, emp.getName());
            pst.setString(2, emp.getRole());
            pst.setDouble(3, emp.getSalary());
            pst.setString(4, emp.getUsername());
            pst.setString(5, emp.getPassword());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật nhân viên
    public void updateEmployee(Employee emp) { /* tương tự add, dùng UPDATE ... WHERE employee_id = ? */ }

    // Xóa nhân viên
    public void deleteEmployee(int employeeId) {
        String sql = "DELETE FROM Employee WHERE employee_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, employeeId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Kiểm tra đăng nhập (trả về Employee nếu thành công, ngược lại null)
    public Employee checkLogin(String username, String password) {
        Employee emp = null;
        String sql = "SELECT * FROM Employee WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, username);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    emp = new Employee();
                    emp.setEmployeeId(rs.getInt("employee_id"));
                    emp.setName(rs.getString("name"));
                    emp.setRole(rs.getString("role"));
                    // Không nhất thiết lấy lương, password ở đây
                    emp.setUsername(rs.getString("username"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emp;
    }

    public Employee getEmployeeById(int employeeId) {
        Employee emp = null;
        String sql = "SELECT * FROM Employee WHERE employee_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, employeeId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    emp = new Employee();
                    emp.setEmployeeId(rs.getInt("employee_id"));
                    emp.setName(rs.getString("name"));
                    emp.setRole(rs.getString("role"));
                    emp.setSalary(rs.getDouble("salary"));
                    emp.setUsername(rs.getString("username"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emp;
    }

}

package DAO;
import Model.Customer;
import Utils.DBConnection;
import java.sql.*;

public class CustomerDAO {
    // Thêm khách hàng mới
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO Customer(name, phone, birth_date, points) VALUES(?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, customer.getName());
            pst.setString(2, customer.getPhone());
            pst.setDate(3, new java.sql.Date(customer.getBirthDate().getTime()));
            pst.setInt(4, customer.getPoints());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tìm kiếm khách hàng theo SĐT
    public Customer findByPhone(String phone) {
        Customer cust = null;
        String sql = "SELECT * FROM Customer WHERE phone = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, phone);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    cust = new Customer(rs.getInt("customer_id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getDate("birth_date"),
                            rs.getInt("points"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cust;
    }

    // Cập nhật thông tin khách hàng
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE Customer SET name=?, birth_date=?, points=? WHERE phone=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, customer.getName());
            pst.setDate(2, new java.sql.Date(customer.getBirthDate().getTime()));
            pst.setInt(3, customer.getPoints());
            pst.setString(4, customer.getPhone());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // ... các phương thức khác như xóa khách hàng (theo id hoặc phone) nếu cần
}

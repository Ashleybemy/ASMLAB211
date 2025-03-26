package DAO;
import Model.Customer;
import Utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements ICustomerDAO {

    @Override
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

    @Override
    public Customer getCustomerById(int customerId) {
        Customer customer = null;
        String sql = "SELECT * FROM Customer WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, customerId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    customer = new Customer();
                    customer.setCustomerId(rs.getInt("customer_id"));
                    customer.setName(rs.getString("name"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setBirthDate(rs.getDate("birth_date"));
                    customer.setPoints(rs.getInt("points"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customer";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone"));
                customer.setBirthDate(rs.getDate("birth_date"));
                customer.setPoints(rs.getInt("points"));
                list.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE Customer SET name=?, phone=?, birth_date=?, points=? WHERE customer_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, customer.getName());
            pst.setString(2, customer.getPhone());
            pst.setDate(3, new java.sql.Date(customer.getBirthDate().getTime()));
            pst.setInt(4, customer.getPoints());
            pst.setInt(5, customer.getCustomerId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomer(int customerId) {
        String sql = "DELETE FROM Customer WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, customerId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức findByPhone không có trong interface nhưng hữu ích cho việc tìm kiếm theo SĐT
    public Customer findByPhone(String phone) {
        Customer cust = null;
        String sql = "SELECT * FROM Customer WHERE phone = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, phone);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // Nếu lớp Customer có constructor đầy đủ thì có thể dùng trực tiếp
                    cust = new Customer(
                            rs.getInt("customer_id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getDate("birth_date"),
                            rs.getInt("points")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cust;
    }
}
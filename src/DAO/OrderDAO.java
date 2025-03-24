package DAO;
import Model.Order;
import Model.OrderItem;
import Utils.DBConnection;
import java.sql.*;
import java.util.List;

public class OrderDAO {
    // Thêm đơn hàng mới và trả về ID (nếu cần)
    public int createOrder(Order order) {
        int orderId = -1;
        String sqlOrder = "INSERT INTO `Order`(customer_id, employee_id, total_amount) VALUES (?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, order.getCustomer() != null ? order.getCustomer().getCustomerId() : null);
            pst.setInt(2, order.getEmployee().getEmployeeId());
            pst.setDouble(3, order.getTotalAmount());
            pst.executeUpdate();
            // Lấy khóa sinh tự động (order_id mới)
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    orderId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Chèn các OrderItem tương ứng
        if (orderId != -1) {
            String sqlItem = "INSERT INTO OrderItem(order_id, product_id, quantity, price) VALUES (?,?,?,?)";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstItem = conn.prepareStatement(sqlItem)) {
                for (OrderItem item : order.getItems()) {
                    pstItem.setInt(1, orderId);
                    pstItem.setInt(2, item.getProduct().getProductId());
                    pstItem.setInt(3, item.getQuantity());
                    pstItem.setDouble(4, item.getPrice());
                    pstItem.addBatch();
                }
                pstItem.executeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orderId;
    }

    // Lấy danh sách hóa đơn (đơn hàng) - có thể dùng cho thống kê
    public List<Order> getOrdersByDate(Date date) { /* SELECT * FROM Order WHERE DATE(order_date) = ? ... */ }

    // Tính tổng doanh thu theo ngày/tháng/năm
    public double getRevenueByDate(Date date) {
        double revenue = 0;
        String sql = "SELECT SUM(total_amount) FROM `Order` WHERE DATE(order_date) = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setDate(1, new java.sql.Date(date.getTime()));
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    revenue = rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenue;
    }
    // Tương tự có thể viết phương thức getRevenueByMonth(year, month), getRevenueByYear(year)
}

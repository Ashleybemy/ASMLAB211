package DAO;
import Model.Order;
import Model.OrderItem;
import Model.Customer;
import Model.Employee;
import Model.Product;
import Utils.DBConnection;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


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
    public List<Order> getOrdersByDate(java.util.Date date) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM `Order` WHERE DATE(order_date) = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            // Chuyển java.util.Date thành java.sql.Date
            pst.setDate(1, new java.sql.Date(date.getTime()));

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setOrderDate(rs.getTimestamp("order_date")); // Nếu bạn cần thời gian đầy đủ
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    // Nếu cần lấy thông tin thêm (ví dụ: customer, employee, list order items)
                    // bạn có thể gọi thêm các phương thức hoặc set các thuộc tính tương ứng.
                    int customerId = rs.getInt("customer_id");
                    if (!rs.wasNull()) {
                        CustomerDAO customerDAO = new CustomerDAO();
                        Customer customer = customerDAO.getCustomerById(customerId);
                        order.setCustomer(customer);
                    }

                    // Lấy thông tin Employee (thu ngân) nếu có
                    int employeeId = rs.getInt("employee_id");
                    if (!rs.wasNull()) {
                        EmployeeDAO employeeDAO = new EmployeeDAO();
                        Employee employee = employeeDAO.getEmployeeById(employeeId);
                        order.setEmployee(employee);
                    }

                    // Lấy danh sách OrderItem cho đơn hàng này
                    List<OrderItem> orderItems = getOrderItemsByOrderId(order.getOrderId());
                    order.setItems(orderItems);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM OrderItem WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, orderId);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id")); // Nếu có cột id
                    int productId = rs.getInt("product_id");

                    // Lấy thông tin sản phẩm từ ProductDAO
                    ProductDAO productDAO = new ProductDAO();
                    Product product = productDAO.getProductById(productId);
                    item.setProduct(product);

                    item.setQuantity(rs.getInt("quantity"));
                    item.setPrice(rs.getDouble("price"));
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

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
    public double getRevenueByMonth(int year, int month) {
        double revenue = 0;
        String sql = "SELECT SUM(total_amount) FROM `Order` WHERE YEAR(order_date) = ? AND MONTH(order_date) = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, year);
            pst.setInt(2, month);
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

    public double getRevenueByYear(int year) {
        double revenue = 0;
        String sql = "SELECT SUM(total_amount) FROM `Order` WHERE YEAR(order_date) = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, year);
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

}

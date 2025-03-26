package Utils;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {
    // Chuỗi kết nối database
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=drinkstore_db;user=sa;password=quan0123;encrypt=false;trustServerCertificate=true";
    // Phương thức main để chạy chương trình
    public static void main(String[] args) throws SQLException {
        // Đặt encoding cho System.out là UTF-8
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        try (Connection conn = DriverManager.getConnection(URL)) {
            System.out.println("Kết nối SQL Server thành công!");
            Statement stmt = conn.createStatement();

            // 1. In bảng Product
            System.out.println("\n=== Bảng Product ===");
            System.out.println("product_id | name               | price    | stock");
            System.out.println("---------------------------------------------");
            ResultSet rsProduct = stmt.executeQuery("SELECT * FROM Product");
            while (rsProduct.next()) {
                int productId = rsProduct.getInt("product_id");
                String name = rsProduct.getString("name");
                double price = rsProduct.getDouble("price");
                int stock = rsProduct.getInt("stock");
                System.out.printf("%-10d | %-18s | %-8.2f | %-5d%n", productId, name, price, stock);
            }
            rsProduct.close();

            // 2. In bảng Customer
            System.out.println("\n=== Bảng Customer ===");
            System.out.println("customer_id | name               | phone        | birth_date  | points");
            System.out.println("-------------------------------------------------------------");
            ResultSet rsCustomer = stmt.executeQuery("SELECT * FROM Customer");
            while (rsCustomer.next()) {
                int customerId = rsCustomer.getInt("customer_id");
                String name = rsCustomer.getString("name");
                String phone = rsCustomer.getString("phone");
                String birthDate = rsCustomer.getString("birth_date");
                int points = rsCustomer.getInt("points");
                System.out.printf("%-11d | %-18s | %-12s | %-11s | %-6d%n", customerId, name, phone, birthDate, points);
            }
            rsCustomer.close();

            // 3. In bảng Employee
            System.out.println("\n=== Bảng Employee ===");
            System.out.println("employee_id | name               | role               | salary      | username      | password");
            System.out.println("--------------------------------------------------------------------------------");
            ResultSet rsEmployee = stmt.executeQuery("SELECT * FROM Employee");
            while (rsEmployee.next()) {
                int employeeId = rsEmployee.getInt("employee_id");
                String name = rsEmployee.getString("name");
                String role = rsEmployee.getString("role");
                double salary = rsEmployee.getDouble("salary");
                String username = rsEmployee.getString("username");
                String password = rsEmployee.getString("password");
                System.out.printf("%-11d | %-18s | %-18s | %-11.2f | %-13s | %-10s%n", employeeId, name, role, salary, username, password);
            }
            rsEmployee.close();

            // 4. In bảng [Order]
            System.out.println("\n=== Bảng Order ===");
            System.out.println("order_id | order_date          | customer_id | employee_id | total_amount");
            System.out.println("----------------------------------------------------------------");
            ResultSet rsOrder = stmt.executeQuery("SELECT * FROM [Order]");
            while (rsOrder.next()) {
                int orderId = rsOrder.getInt("order_id");
                String orderDate = rsOrder.getString("order_date");
                int customerId = rsOrder.getInt("customer_id");
                int employeeId = rsOrder.getInt("employee_id");
                double totalAmount = rsOrder.getDouble("total_amount");
                System.out.printf("%-8d | %-19s | %-11d | %-11d | %-12.2f%n", orderId, orderDate, customerId, employeeId, totalAmount);
            }
            rsOrder.close();

            // 5. In bảng OrderItem
            System.out.println("\n=== Bảng OrderItem ===");
            System.out.println("id | order_id | product_id | quantity | price");
            System.out.println("---------------------------------------------");
            ResultSet rsOrderItem = stmt.executeQuery("SELECT * FROM OrderItem");
            while (rsOrderItem.next()) {
                int id = rsOrderItem.getInt("id");
                int orderId = rsOrderItem.getInt("order_id");
                int productId = rsOrderItem.getInt("product_id");
                int quantity = rsOrderItem.getInt("quantity");
                double price = rsOrderItem.getDouble("price");
                System.out.printf("%-2d | %-8d | %-10d | %-8d | %-6.2f%n", id, orderId, productId, quantity, price);
            }
            rsOrderItem.close();

            // 6. In bảng Ingredient
            System.out.println("\n=== Bảng Ingredient ===");
            System.out.println("ingredient_id | name               | quantity");
            System.out.println("---------------------------------------------");
            ResultSet rsIngredient = stmt.executeQuery("SELECT * FROM Ingredient");
            while (rsIngredient.next()) {
                int ingredientId = rsIngredient.getInt("ingredient_id");
                String name = rsIngredient.getString("name");
                int quantity = rsIngredient.getInt("quantity");
                System.out.printf("%-13d | %-18s | %-8d%n", ingredientId, name, quantity);
            }
            rsIngredient.close();
        }
    }
}
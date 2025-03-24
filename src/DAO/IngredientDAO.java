package DAO;
import Model.Ingredient;
import Utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO {
    // Lấy danh sách nguyên liệu
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> list = new ArrayList<>();
        String sql = "SELECT * FROM Ingredient";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ingredient ing = new Ingredient();
                ing.setIngredientId(rs.getInt("ingredient_id"));
                ing.setName(rs.getString("name"));
                ing.setQuantity(rs.getInt("quantity"));
                list.add(ing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    // Cập nhật số lượng nguyên liệu (nhập hoặc xuất kho)
    public void updateQuantity(int ingredientId, int newQty) {
        String sql = "UPDATE Ingredient SET quantity = ? WHERE ingredient_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, newQty);
            pst.setInt(2, ingredientId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Thêm nguyên liệu mới, xóa nguyên liệu ... (nếu cần)
}

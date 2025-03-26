package DAO;

import Model.Ingredient;
import Utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO implements IIngredientDAO {

    @Override
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

    @Override
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

    @Override
    public void addIngredient(Ingredient ingredient) {
        String sql = "INSERT INTO Ingredient(name, quantity) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, ingredient.getName());
            pst.setInt(2, ingredient.getQuantity());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteIngredient(int ingredientId) {
        String sql = "DELETE FROM Ingredient WHERE ingredient_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, ingredientId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

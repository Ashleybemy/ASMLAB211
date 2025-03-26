package DAO;

import Model.Ingredient;
import java.util.List;

public interface IIngredientDAO {
    List<Ingredient> getAllIngredients();

    void updateQuantity(int ingredientId, int newQty);

    void addIngredient(Ingredient ingredient);

    void deleteIngredient(int ingredientId);
}

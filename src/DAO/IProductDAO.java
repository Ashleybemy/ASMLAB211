package DAO;

import Model.Product;
import java.util.List;

public interface IProductDAO {
    List<Product> getAllProducts();
    Product getProductById(int id);
    void updateStock(int productId, int newStock);
    void insertProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(int productId);
}

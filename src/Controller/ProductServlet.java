package Controller;
import DAO.ProductDAO;
import Model.Product;
import Model.Order;
import Model.OrderItem;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy danh sách sản phẩm từ CSDL
        List<Product> productList = productDAO.getAllProducts();
        // Đính kèm vào request và forward đến JSP hiển thị danh sách
        request.setAttribute("products", productList);
        request.getRequestDispatcher("productList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Thêm sản phẩm vào giỏ hàng (đơn hàng tạm thời lưu trong session)
        HttpSession session = request.getSession();
        Order currentOrder = (Order) session.getAttribute("currentOrder");
        if (currentOrder == null) {
            currentOrder = new Order();
            // Khởi tạo danh sách OrderItem rỗng
            currentOrder.setItems(new ArrayList<OrderItem>());
        }
        // Lấy thông tin sản phẩm từ form (ID và quantity)
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Product product = productDAO.getProductById(productId);
        if (product != null && quantity > 0) {
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setPrice(product.getPrice());
            // Thêm vào danh sách mặt hàng trong đơn
            currentOrder.getItems().add(item);
        }
        // Cập nhật đơn hàng trong session
        session.setAttribute("currentOrder", currentOrder);
        // Quay lại trang danh sách sản phẩm hoặc giỏ hàng
        response.sendRedirect("orderCart.jsp");
    }
}

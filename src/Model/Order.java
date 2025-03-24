package Model;
import java.util.Date;
import java.util.List;

public class Order {
    private int orderId;
    private Date orderDate;
    private Customer customer;
    private Employee employee;
    private List<OrderItem> items;
    private double totalAmount;
    // Constructors, getters, setters...


    public Order(int orderId, Date orderDate, Customer customer, Employee employee, List<OrderItem> items, double totalAmount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
        this.employee = employee;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        // Tính tổng dựa trên items nếu cần
        if (items != null) {
            totalAmount = 0;
            for (OrderItem item : items) {
                totalAmount += item.getPrice() * item.getQuantity();
            }
        }
        return totalAmount;
    }
    // ... các phương thức khác nếu cần
}

package DAO;

import Model.Order;
import java.util.Date;
import java.util.List;

public interface IOrderDAO {
    int createOrder(Order order);
    List<Order> getOrdersByDate(Date date);
    double getRevenueByDate(Date date);
    double getRevenueByMonth(int year, int month);
    double getRevenueByYear(int year);
}

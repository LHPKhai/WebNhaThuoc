/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.DAOs;

/**
 *
 * @author hello
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.entities.Customer;
import model.entities.Order;
import model.entities.OrderItem;
import model.entities.Product;

public class OrderDAO {
    private Connection connection;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm đơn hàng mới
    public boolean addOrder(Order order) throws SQLException {
        String sqlOrder = "INSERT INTO [Order] (CustomerID, OrderDate, Status, Description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmtOrder = connection.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {
            stmtOrder.setInt(1, order.getCustomer().getId());
            stmtOrder.setTimestamp(2, new Timestamp(order.getOrderDate().getTime()));
            stmtOrder.setString(3, order.getStatus());
            stmtOrder.setString(4, order.getDescription());
            int affectedRows = stmtOrder.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            // Lấy OrderID được tạo tự động
            ResultSet generatedKeys = stmtOrder.getGeneratedKeys();
            if (generatedKeys.next()) {
                order.setOrderID(generatedKeys.getInt(1));
            }

            // Thêm các sản phẩm trong đơn hàng
            for (OrderItem item : order.getOrderItems()) {
                addOrderItem(order.getOrderID(), item);
            }
            return true;
        }
    }

    // Thêm sản phẩm vào đơn hàng
    private boolean addOrderItem(int orderID, OrderItem item) throws SQLException {
        String sql = "INSERT INTO OrderItem (OrderID, ProductID, Quantity, UnitPrice) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderID);
            stmt.setInt(2, item.getProduct().getId());
            stmt.setInt(3, item.getQuantity());
            stmt.setBigDecimal(4, item.getUnitPrice());
            return stmt.executeUpdate() > 0;
        }
    }

    // Lấy danh sách đơn hàng theo CustomerID
    public List<Order> getOrdersByCustomer(int customerId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM [Order] WHERE CustomerID = ? ORDER BY OrderDate DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setOrderID(rs.getInt("OrderID"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setStatus(rs.getString("Status"));
                order.setDescription(rs.getString("Description"));

                // Lấy thông tin khách hàng
                CustomerDAO customerDAO = new CustomerDAO(connection);
                Customer customer = customerDAO.getCustomerById(customerId);
                order.setCustomer(customer);

                // Lấy danh sách sản phẩm trong đơn hàng
                order.setOrderItems(getOrderItemsByOrderId(order.getOrderID()));

                orders.add(order);
            }
        }
        return orders;
    }

    // Lấy danh sách sản phẩm trong đơn hàng
    private List<OrderItem> getOrderItemsByOrderId(int orderID) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM OrderItem WHERE OrderID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                ProductDAO productDAO = new ProductDAO(connection);
                item.setProduct((Product) productDAO.getProductById(rs.getInt("ProductID")));
                item.setQuantity(rs.getInt("Quantity"));
                item.setUnitPrice(rs.getBigDecimal("UnitPrice"));
                items.add(item);
            }
        }
        return items;
    }
}

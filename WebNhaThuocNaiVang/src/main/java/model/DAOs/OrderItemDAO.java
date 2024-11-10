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
import java.util.List;
import model.entities.Order;
import model.entities.OrderItem;
import model.entities.Product;

public class OrderItemDAO {
    private Connection connection;

    public OrderItemDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm sản phẩm vào đơn hàng
    public boolean addOrderItem(OrderItem orderItem) throws SQLException {
        String sql = "INSERT INTO OrderItem (OrderID, ProductID, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderItem.getOrder().getOrderID());
            stmt.setInt(2, orderItem.getProduct().getId());
            stmt.setInt(3, orderItem.getQuantity());
            return stmt.executeUpdate() > 0;
        }
    }

    // Lấy danh sách sản phẩm theo OrderID
    public List<OrderItem> getOrderItemsByOrderId(int orderId) throws SQLException {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM OrderItem WHERE OrderID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                ProductDAO productDAO = new ProductDAO(connection);
                Product product = productDAO.getProductById(rs.getInt("ProductID"));

                orderItem.setOrder(new Order());
                orderItem.getOrder().setOrderID(orderId);
                orderItem.setProduct(product);
                orderItem.setQuantity(rs.getInt("Quantity"));

                orderItems.add(orderItem);
            }
        }
        return orderItems;
    }

    // Cập nhật số lượng sản phẩm trong đơn hàng
    public boolean updateOrderItemQuantity(int orderItemId, int quantity) throws SQLException {
        String sql = "UPDATE OrderItem SET Quantity = ? WHERE OrderItemID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, orderItemId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Xóa sản phẩm khỏi đơn hàng
    public boolean deleteOrderItem(int orderItemId) throws SQLException {
        String sql = "DELETE FROM OrderItem WHERE OrderItemID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderItemId);
            return stmt.executeUpdate() > 0;
        }
    }
}

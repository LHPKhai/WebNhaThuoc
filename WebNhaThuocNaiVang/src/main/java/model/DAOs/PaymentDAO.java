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
import model.entities.Order;
import model.entities.Payment;

public class PaymentDAO {
    private Connection connection;

    public PaymentDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm thanh toán mới
    public boolean addPayment(Payment payment) throws SQLException {
        String sql = "INSERT INTO Payment (OrderID, PaymentMethod, Price, PaymentDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, payment.getOrder().getOrderID());
            stmt.setString(2, payment.getPaymentMethod());
            stmt.setDouble(3, payment.getPrice());
            stmt.setTimestamp(4, new Timestamp(payment.getPaymentDate().getTime()));
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            // Lấy PaymentID được tạo tự động
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                payment.setPaymentID(generatedKeys.getInt(1));
            }
            return true;
        }
    }

    // Lấy thông tin thanh toán theo OrderID
    public Payment getPaymentByOrderId(int orderId) throws SQLException {
        String sql = "SELECT * FROM Payment WHERE OrderID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentID(rs.getInt("PaymentID"));
                payment.setPaymentMethod(rs.getString("PaymentMethod"));
                payment.setPrice(rs.getDouble("Price"));
                payment.setPaymentDate(rs.getTimestamp("PaymentDate"));

                Order order = new Order();
                order.setOrderID(orderId);
                payment.setOrder(order);

                return payment;
            }
        }
        return null;
    }

    // Cập nhật thông tin thanh toán
    public boolean updatePayment(Payment payment) throws SQLException {
        String sql = "UPDATE Payment SET PaymentMethod = ?, Price = ?, PaymentDate = ? WHERE PaymentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, payment.getPaymentMethod());
            stmt.setDouble(2, payment.getPrice());
            stmt.setTimestamp(3, new Timestamp(payment.getPaymentDate().getTime()));
            stmt.setInt(4, payment.getPaymentID());
            return stmt.executeUpdate() > 0;
        }
    }

    // Xóa thông tin thanh toán theo PaymentID
    public boolean deletePayment(int paymentID) throws SQLException {
        String sql = "DELETE FROM Payment WHERE PaymentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, paymentID);
            return stmt.executeUpdate() > 0;
        }
    }
}

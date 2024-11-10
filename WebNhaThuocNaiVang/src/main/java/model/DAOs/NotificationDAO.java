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
import model.entities.Notification;
import model.entities.User;

public class NotificationDAO {
    private Connection connection;

    public NotificationDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm thông báo mới
    public boolean addNotification(Notification notification) throws SQLException {
        String sqlNotification = "INSERT INTO Notification (CreatorID, Message, DateTime) VALUES (?, ?, ?)";
        try (PreparedStatement stmtNotification = connection.prepareStatement(sqlNotification, Statement.RETURN_GENERATED_KEYS)) {
            stmtNotification.setInt(1, notification.getCreator().getId());
            stmtNotification.setString(2, notification.getMessage());
            stmtNotification.setTimestamp(3, new Timestamp(notification.getDateTime().getTime()));
            int affectedRows = stmtNotification.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            // Lấy NotificationID được tự động tạo
            ResultSet generatedKeys = stmtNotification.getGeneratedKeys();
            if (generatedKeys.next()) {
                notification.setNotificationID(generatedKeys.getInt(1));
            }

            // Thêm danh sách người nhận
            for (User user : notification.getRecipient()) {
                addRecipient(notification.getNotificationID(), user.getId());
            }
            return true;
        }
    }

    // Thêm người nhận thông báo
    private boolean addRecipient(int notificationID, int userID) throws SQLException {
        String sql = "INSERT INTO NotificationRecipient (NotificationID, RecipientID) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, notificationID);
            stmt.setInt(2, userID);
            return stmt.executeUpdate() > 0;
        }
    }

    // Lấy danh sách thông báo cho một người dùng
    public List<Notification> getNotificationsByUser(int userID) throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT n.NotificationID, n.CreatorID, n.Message, n.DateTime " +
                     "FROM Notification n JOIN NotificationRecipient nr ON n.NotificationID = nr.NotificationID " +
                     "WHERE nr.RecipientID = ? ORDER BY n.DateTime DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UserDAO userDAO = new UserDAO(connection);
                User creator = userDAO.getUserById(rs.getInt("CreatorID"));
                String message = rs.getString("Message");
                Date dateTime = rs.getTimestamp("DateTime");
                int notificationID = rs.getInt("NotificationID");

                Notification notification = new Notification();
                notification.setNotificationID(notificationID);
                notification.setCreator(creator);
                notification.setMessage(message);
                notification.setDateTime(dateTime);

                notifications.add(notification);
            }
        }
        return notifications;
    }

    // Xóa thông báo theo NotificationID
    public boolean deleteNotification(int notificationID) throws SQLException {
        String sql = "DELETE FROM Notification WHERE NotificationID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, notificationID);
            return stmt.executeUpdate() > 0;
        }
    }
}

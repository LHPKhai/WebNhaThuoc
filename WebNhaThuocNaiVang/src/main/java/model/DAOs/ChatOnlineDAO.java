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
import model.entities.ChatOnline;
import model.entities.User;

public class ChatOnlineDAO {
    private Connection connection;

    public ChatOnlineDAO(Connection connection) {
        this.connection = connection;
    }

    // Phương thức gửi tin nhắn
    public boolean sendMessage(ChatOnline message) throws SQLException {
        String sql = "INSERT INTO Message (CreatorID, RecipientID, MessageDate, Description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, message.getCreator().getId());
            stmt.setInt(2, message.getRecipient().getId());
            stmt.setTimestamp(3, new Timestamp(message.getDate().getTime()));
            stmt.setString(4, message.getDescription());
            return stmt.executeUpdate() > 0;
        }
    }

    // Phương thức lấy tất cả tin nhắn giữa hai người dùng
    public List<ChatOnline> viewMessages(int creatorId, int recipientId) throws SQLException {
        List<ChatOnline> messages = new ArrayList<>();
        String sql = "SELECT * FROM Message WHERE (CreatorID = ? AND RecipientID = ?) OR (CreatorID = ? AND RecipientID = ?) ORDER BY MessageDate";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, creatorId);
            stmt.setInt(2, recipientId);
            stmt.setInt(3, recipientId);
            stmt.setInt(4, creatorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UserDAO userDAO = new UserDAO(connection);
                User creator = userDAO.getUserById(rs.getInt("CreatorID"));
                User recipient = userDAO.getUserById(rs.getInt("RecipientID"));
                Date date = rs.getTimestamp("MessageDate");
                String description = rs.getString("Description");
                int messageID = rs.getInt("MessageID");

                ChatOnline message = new ChatOnline(creator, date, recipient, description, messageID);
                messages.add(message);
            }
        }
        return messages;
    }

    // Phương thức lấy thông tin một tin nhắn theo MessageID
    public ChatOnline viewMessageInChatOnline(int messageID) throws SQLException {
        String sql = "SELECT * FROM Message WHERE MessageID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, messageID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UserDAO userDAO = new UserDAO(connection);
                User creator = userDAO.getUserById(rs.getInt("CreatorID"));
                User recipient = userDAO.getUserById(rs.getInt("RecipientID"));
                Date date = rs.getTimestamp("MessageDate");
                String description = rs.getString("Description");

                return new ChatOnline(creator, date, recipient, description, messageID);
            }
        }
        return null;
    }
}

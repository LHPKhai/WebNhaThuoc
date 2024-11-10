/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

/**
 *
 * @author hello
 */
import java.util.Date;
import java.util.List;

public class ChatOnline {
    private User creator;               // Người tạo tin nhắn
    private Date date;                  // Ngày gửi tin nhắn
    private User recipient;             // Người nhận tin nhắn
    private String description;         // Nội dung tin nhắn
    private int messageID;           // ID của tin nhắn

    // Constructor
    public ChatOnline(User creator, Date date, User recipient, String description, int messageID) {
        this.creator = creator;
        this.date = date;
        this.recipient = recipient;
        this.description = description;
        this.messageID = messageID;
    }

    // Getters and Setters
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    // Method to view messages in a chat
    public void viewMessages(List<User> users) {
        // Logic để hiển thị các tin nhắn giữa người dùng
    }

    // Method to view a specific message in a chat
    public void viewMessageInChatOnline(String messageID) {
        // Logic để hiển thị tin nhắn cụ thể trong một cuộc trò chuyện dựa trên ID
    }

    // Method to send a message in a chat
    public void sendMessage(String message) {
        // Logic để gửi tin nhắn
    }
}


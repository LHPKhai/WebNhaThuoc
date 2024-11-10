/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.BOs;

/**
 *
 * @author hello
 */
import java.util.List;
import model.entities.User;

public class NotificationBO {
    public void sendNotification(List<User> recipients, String message) {
        for (User user : recipients) {
            System.out.println("Notification sent to: " + user.getUsername());
        }
    }
}


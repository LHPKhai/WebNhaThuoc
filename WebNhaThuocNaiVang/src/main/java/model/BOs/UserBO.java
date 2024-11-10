/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.BOs;

import model.entities.Address;
import model.entities.User;

/**
 *
 * @author hello
 */
public class UserBO {
    public boolean login(User user, String email, String password) {
        return email.equals(user.getEmail()) && password.equals(user.getPassword());
    }

    public void logOut(User user) {
        System.out.println("User logged out: " + user.getUsername());
    }

    public boolean resetPassword(User user, String newPassword) {
        if (newPassword.length() >= 8) {
            user.setPassword(newPassword);
            return true;
        }
        return false;
    }

    public void updateProfile(User user, String name, String email, String phoneNumber, String address) {
        user.setUsername(name);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(new Address());
        System.out.println("Profile updated for user: " + user.getUsername());
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.BOs;

import model.entities.Order;

/**
 *
 * @author hello
 */
public class OrderBO {
    public void viewOrderDetails(Order order) {
        System.out.println("Viewing order details for Order ID: " + order.getOrderID());
    }

    public void trackOrder(Order order) {
        System.out.println("Tracking order ID: " + order.getOrderID());
    }

    public void updateOrderStatus(Order order, String newStatus) {
        order.setStatus(newStatus);
        System.out.println("Order status updated to: " + newStatus);
    }
}


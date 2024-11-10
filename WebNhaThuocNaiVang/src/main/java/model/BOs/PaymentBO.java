/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.BOs;

import model.entities.Payment;

/**
 *
 * @author hello
 */
public class PaymentBO {
    public void processPayment(Payment payment) {
        System.out.println("Processing payment for Order ID: " + payment.getOrder().getOrderID());
    }

    public void checkPaymentStatus(Payment payment) {
        System.out.println("Checking payment status for Order ID: " + payment.getOrder().getOrderID());
    }
}

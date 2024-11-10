/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.BOs;

import model.entities.Cart;
import model.entities.Customer;
import model.entities.Order;
import model.entities.Product;

/**
 *
 * @author hello
 */
public class CustomerBO {
    public void register(Customer customer) {
        System.out.println("Customer registered: " + customer.getUsername());
    }

    public void login(Customer customer) {
        System.out.println("Customer logged in: " + customer.getUsername());
    }

    public void placeOrder(Customer customer, Order order) {
        System.out.println("Order placed by customer: " + customer.getUsername());
    }

    public void addToCart(Customer customer, Product product, Cart cart) {
        cart.getProducts().add(product);
        System.out.println("Product added to cart by customer: " + customer.getUsername());
    }
}

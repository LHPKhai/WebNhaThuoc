/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.BOs;

import model.entities.Cart;
import model.entities.Product;

/**
 *
 * @author hello
 */
public class CartBO {
    public void addProduct(Cart cart, Product product) {
        cart.getProducts().add(product);
        System.out.println("Product added to cart: " + product.getProductName());
    }

    public void removeProduct(Cart cart, Product product) {
        cart.getProducts().remove(product);
        System.out.println("Product removed from cart: " + product.getProductName());
    }

    public double calculateTotal(Cart cart) {
        double total = 0;
        for (Product product : cart.getProducts()) {
            total += product.getPrice();
        }
        System.out.println("Total cart value: " + total);
        return total;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.BOs;

import model.entities.Product;

/**
 *
 * @author hello
 */
public class ProductBO {
    public Product viewDetails(Product product) {
        System.out.println("Viewing details for product: " + product.getProductName());
        return product;
    }

    public boolean checkAvailability(Product product) {
        return product.getPrice() > 0;
    }
}


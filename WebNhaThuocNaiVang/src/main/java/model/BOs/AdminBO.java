/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.BOs;

import model.entities.Admin;
import model.entities.Product;

/**
 *
 * @author hello
 */
public class AdminBO {
    public void addProduct(Admin admin, Product product) {
        System.out.println("Product added by admin: " + product.getProductName());
    }

    public void editProduct(Admin admin, Product product) {
        System.out.println("Product edited by admin: " + product.getProductName());
    }

    public void deleteProduct(Admin admin, Product product) {
        System.out.println("Product deleted by admin: " + product.getProductName());
    }

    public void viewRevenue(Admin admin) {
        System.out.println("Viewing revenue by admin: " + admin.getUsername());
    }
}
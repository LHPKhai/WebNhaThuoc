/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.BOs;

import model.entities.Comment;
import model.entities.Product;

/**
 *
 * @author hello
 */
public class CommentBO {
    public void submitComment(Comment comment, Product product) {
        System.out.println("Comment submitted for product: " + product.getProductName());
    }
}

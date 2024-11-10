/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.DAOs;

/**
 *
 * @author hello
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.entities.Comment;
import model.entities.Customer;
import model.entities.Product;

public class CommentDAO {
    private Connection connection;

    public CommentDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm bình luận mới
    public boolean addComment(Comment comment) throws SQLException {
        String sql = "INSERT INTO Comment (CustomerID, ProductID, Content, Stars, CommentDate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, comment.getCustomer().getId());
            stmt.setInt(2, comment.getProduct().getId());
            stmt.setString(3, comment.getContent());
            stmt.setInt(4, comment.getStars());
            stmt.setTimestamp(5, new Timestamp(comment.getDate().getTime()));
            return stmt.executeUpdate() > 0;
        }
    }

    // Lấy danh sách bình luận theo ProductID
    public List<Comment> getCommentsByProduct(int productId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM Comment WHERE ProductID = ? ORDER BY CommentDate DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CustomerDAO customerDAO = new CustomerDAO(connection);
                Customer customer = customerDAO.getCustomerById(rs.getInt("CustomerID"));

                ProductDAO productDAO = new ProductDAO(connection);
                Product product = productDAO.getProductById(rs.getInt("ProductID"));

                String content = rs.getString("Content");
                int stars = rs.getInt("Stars");
                Date date = rs.getTimestamp("CommentDate");

                Comment comment = new Comment();
                comment.setCustomer(customer);
                comment.setProduct(product);
                comment.setContent(content);
                comment.setStars(stars);
                comment.setDate(date);

                comments.add(comment);
            }
        }
        return comments;
    }

    // Xóa bình luận theo CommentID
    public boolean deleteComment(int commentId) throws SQLException {
        String sql = "DELETE FROM Comment WHERE CommentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, commentId);
            return stmt.executeUpdate() > 0;
        }
    }
}

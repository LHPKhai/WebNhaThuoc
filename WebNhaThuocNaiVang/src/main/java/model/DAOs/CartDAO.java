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
import java.util.List;
import model.entities.Cart;
import model.entities.Product;

public class CartDAO {
    private Connection connection;

    public CartDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm giỏ hàng mới
    public boolean insertCart(Cart cart) throws SQLException {
        String sqlCart = "INSERT INTO Cart (CustomerID) VALUES (?)";
        try (PreparedStatement stmtCart = connection.prepareStatement(sqlCart, Statement.RETURN_GENERATED_KEYS)) {
            stmtCart.setInt(1, cart.getCustomer().getId());
            int affectedRows = stmtCart.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            // Lấy CartID được tự động tạo
            ResultSet generatedKeys = stmtCart.getGeneratedKeys();
            if (generatedKeys.next()) {
                cart.setCartID(generatedKeys.getInt(1));
            }

            // Thêm sản phẩm vào giỏ hàng
            for (Product product : cart.getProducts()) {
                insertCartProduct(cart.getCartID(), product);
            }
            return true;
        }
    }

    // Thêm sản phẩm vào giỏ hàng
    private boolean insertCartProduct(int cartID, Product product) throws SQLException {
        String sql = "INSERT INTO CartProduct (CartID, ProductID, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cartID);
            stmt.setInt(2, product.getId());
            stmt.setInt(3, 1); // Mặc định số lượng là 1
            return stmt.executeUpdate() > 0;
        }
    }

    // Lấy thông tin giỏ hàng theo CartID
    public Cart getCartById(int cartID) throws SQLException {
        String sqlCart = "SELECT * FROM Cart WHERE CartID = ?";
        try (PreparedStatement stmtCart = connection.prepareStatement(sqlCart)) {
            stmtCart.setInt(1, cartID);
            ResultSet rsCart = stmtCart.executeQuery();

            if (rsCart.next()) {
                Cart cart = new Cart();
                cart.setCartID(cartID);
                CustomerDAO customerDAO = new CustomerDAO(connection);
                cart.setCustomer(customerDAO.getCustomerById(rsCart.getInt("CustomerID")));
                cart.setProducts(getProductsByCartId(cartID));
                return cart;
            }
        }
        return null;
    }

    // Lấy danh sách sản phẩm trong giỏ hàng
    private List<Product> getProductsByCartId(int cartID) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductName, p.Price, cp.Quantity " +
                     "FROM CartProduct cp JOIN Product p ON cp.ProductID = p.ProductID " +
                     "WHERE cp.CartID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cartID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setPrice(rs.getDouble("Price"));
                products.add(product);
            }
        }
        return products;
    }

    // Xóa giỏ hàng
    public boolean deleteCart(int cartID) throws SQLException {
        String sql = "DELETE FROM Cart WHERE CartID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cartID);
            return stmt.executeUpdate() > 0;
        }
    }
}

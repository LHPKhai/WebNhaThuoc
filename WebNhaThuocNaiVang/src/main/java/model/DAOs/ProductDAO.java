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
import model.entities.Product;

public class ProductDAO {
    private Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm sản phẩm mới
    public boolean addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO Product (ProductName, Price, Indications, ProductionDate, ExpiryDate, Description, Manufacturer, Contraindication) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getProductName());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getIndications());
            stmt.setString(4, product.getProductionDate());
            stmt.setString(5, product.getExpiryDate());
            stmt.setString(6, product.getDescription());
            stmt.setString(7, product.getManufacturer());
            stmt.setString(8, product.getContraindication());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            // Lấy ProductID được tạo tự động
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setProductID(generatedKeys.getInt(1));
            }
            return true;
        }
    }

    // Lấy thông tin sản phẩm theo ProductID
    public Product getProductById(int productId) throws SQLException {
        String sql = "SELECT * FROM Product WHERE ProductID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setPrice(rs.getDouble("Price"));
                product.setIndications(rs.getString("Indications"));
                product.setProductionDate(rs.getString("ProductionDate"));
                product.setExpiryDate(rs.getString("ExpiryDate"));
                product.setDescription(rs.getString("Description"));
                product.setManufacturer(rs.getString("Manufacturer"));
                product.setContraindication(rs.getString("Contraindication"));
                return product;
            }
        }
        return null;
    }

    // Cập nhật thông tin sản phẩm
    public boolean updateProduct(Product product) throws SQLException {
        String sql = "UPDATE Product SET ProductName = ?, Price = ?, Indications = ?, ProductionDate = ?, ExpiryDate = ?, " +
                     "Description = ?, Manufacturer = ?, Contraindication = ? WHERE ProductID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getProductName());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getIndications());
            stmt.setString(4, product.getProductionDate());
            stmt.setString(5, product.getExpiryDate());
            stmt.setString(6, product.getDescription());
            stmt.setString(7, product.getManufacturer());
            stmt.setString(8, product.getContraindication());
            stmt.setInt(9, product.getProductID());
            return stmt.executeUpdate() > 0;
        }
    }

    // Xóa sản phẩm theo ProductID
    public boolean deleteProduct(int productId) throws SQLException {
        String sql = "DELETE FROM Product WHERE ProductID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Lấy danh sách tất cả sản phẩm
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setPrice(rs.getDouble("Price"));
                product.setIndications(rs.getString("Indications"));
                product.setProductionDate(rs.getString("ProductionDate"));
                product.setExpiryDate(rs.getString("ExpiryDate"));
                product.setDescription(rs.getString("Description"));
                product.setManufacturer(rs.getString("Manufacturer"));
                product.setContraindication(rs.getString("Contraindication"));
                products.add(product);
            }
        }
        return products;
    }
}

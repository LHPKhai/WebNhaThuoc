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
import model.entities.Address;
import model.entities.Admin;
import model.entities.Customer;
import model.entities.Pharmacist;
import model.entities.User;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm người dùng mới
    public boolean addUser(User user) throws SQLException {
        String sql = "INSERT INTO [User] (Username, Email, Password, PhoneNumber, Address, Role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setInt(5, insertAddress(user.getAddress()));
            stmt.setString(6, user.getRole());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
            return true;
        }
    }
    
    public boolean insertUser(User user) throws SQLException {
        String sql = "INSERT INTO [User] (Username, Email, Password, AddressID, PhoneNumber, Role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, insertAddress(user.getAddress())); // Thêm địa chỉ trước và lấy AddressID
            stmt.setString(5, user.getPhoneNumber());
            stmt.setString(6, user.getRole());
            return stmt.executeUpdate() > 0;
        }
    }

    // Cập nhật người dùng
    public boolean updateUser(int userId, User user) throws SQLException {
        String sql = "UPDATE [User] SET Username = ?, Email = ?, Password = ?, AddressID = ?, PhoneNumber = ?, Role = ? WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, insertAddress(user.getAddress()));
            stmt.setString(5, user.getPhoneNumber());
            stmt.setString(6, user.getRole());
            stmt.setInt(7, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Xóa người dùng
    public boolean deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM [User] WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Lấy thông tin người dùng theo ID
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM [User] WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("Role");
                User user;
                Address address = new Address();
                address.setProvinceOrCity(rs.getString("ProvinceOrCity"));
                address.setDistrict(rs.getString("District"));
                address.setCommuneOrWardOrTown(rs.getString("CommuneOrWardOrTown"));
                address.setStreet(rs.getString("Street"));
                address.setHouseNumber(rs.getString("HouseNumber"));
                
                switch (role) {
                    case "Admin":
                        user = new Admin();
                        ((Admin) user).setPermissions("full_access");
                        break;
                    case "Pharmacist":
                        user = new Pharmacist();
                        ((Pharmacist) user).setLicenseNumber("default_license");
                        break;
                    case "Customer":
                    default:
                        user = new Customer();
                        ((Customer) user).setMembershipLevel("Silver");
                        break;
                }

                user.setId(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setAddress(address);
                
                user.setRole(role);

                return user;
            }
        }
        return null;
    }

    // Thêm địa chỉ và trả về AddressID
    private int insertAddress(Address address) throws SQLException {
        String sql = "INSERT INTO Address (ProvinceOrCity, District, CommuneOrWardOrTown, Street, HouseNumber) OUTPUT INSERTED.AddressID VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, address.getProvinceOrCity());
            stmt.setString(2, address.getDistrict());
            stmt.setString(3, address.getCommuneOrWardOrTown());
            stmt.setString(4, address.getStreet());
            stmt.setString(5, address.getHouseNumber());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}

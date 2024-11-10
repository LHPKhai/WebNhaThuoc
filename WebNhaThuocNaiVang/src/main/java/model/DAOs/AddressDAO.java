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

public class AddressDAO {
    private Connection connection;

    public AddressDAO(Connection connection) {
        this.connection = connection;
    }

    // Phương thức thêm địa chỉ mới
    public boolean insertAddress(Address address) throws SQLException {
        String sql = "INSERT INTO Address (ProvinceOrCity, District, CommuneOrWardOrTown, Street, HouseNumber) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, address.getProvinceOrCity());
            stmt.setString(2, address.getDistrict());
            stmt.setString(3, address.getCommuneOrWardOrTown());
            stmt.setString(4, address.getStreet());
            stmt.setString(5, address.getHouseNumber());
            return stmt.executeUpdate() > 0;
        }
    }

    // Phương thức cập nhật địa chỉ
    public boolean updateAddress(int addressId, Address address) throws SQLException {
        String sql = "UPDATE Address SET ProvinceOrCity = ?, District = ?, CommuneOrWardOrTown = ?, Street = ?, HouseNumber = ? WHERE AddressID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, address.getProvinceOrCity());
            stmt.setString(2, address.getDistrict());
            stmt.setString(3, address.getCommuneOrWardOrTown());
            stmt.setString(4, address.getStreet());
            stmt.setString(5, address.getHouseNumber());
            stmt.setInt(6, addressId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Phương thức xóa địa chỉ
    public boolean deleteAddress(int addressId) throws SQLException {
        String sql = "DELETE FROM Address WHERE AddressID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, addressId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Phương thức lấy địa chỉ theo ID
    public Address getAddressById(int addressId) throws SQLException {
        String sql = "SELECT * FROM Address WHERE AddressID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, addressId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Address address = new Address();
                address.setProvinceOrCity(rs.getString("ProvinceOrCity"));
                address.setDistrict(rs.getString("District"));
                address.setCommuneOrWardOrTown(rs.getString("CommuneOrWardOrTown"));
                address.setStreet(rs.getString("Street"));
                address.setHouseNumber(rs.getString("HouseNumber"));
                return address;
            }
        }
        return null;
    }
}

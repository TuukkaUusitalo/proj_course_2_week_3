package org.example;

import db.DatabaseConnection;

import java.sql.*;

public class CartService {

    public int saveCartRecord(int totalItems, double totalCost, String language) {
        String sql = "INSERT INTO cart_records (total_items, total_cost, language) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, totalItems);
            ps.setDouble(2, totalCost);
            ps.setString(3, language);
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void saveCartItem(int cartRecordId, int itemNumber, double price, int qty, double subtotal) {
        String sql = """
                INSERT INTO cart_items 
                (cart_record_id, item_number, price, quantity, subtotal)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cartRecordId);
            ps.setInt(2, itemNumber);
            ps.setDouble(3, price);
            ps.setInt(4, qty);
            ps.setDouble(5, subtotal);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
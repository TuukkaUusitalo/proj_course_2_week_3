package org.example;

import db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class LocalizationService {

    public Map<String, String> loadStrings(String languageCode) {
        Map<String, String> map = new HashMap<>();

        // default fallbacks
        map.put("select_language", "Select Language");
        map.put("enter_items", "Items");
        map.put("create_fields", "Create");
        map.put("calculate", "Calculate");
        map.put("enter_price", "Price");
        map.put("enter_quantity", "Quantity");
        map.put("total_cost", "Total");

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT `key`, value FROM localization_strings WHERE language = ?";
            System.out.println("Running localization SQL for language: " + languageCode);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, languageCode);

            ResultSet rs = ps.executeQuery();

            int rows = 0;
            while (rs.next()) {
                rows++;
                map.put(rs.getString("key"), rs.getString("value"));
            }
            System.out.println("Localization rows loaded: " + rows);

        } catch (Exception e) {
            // if DB isn't ready or table missing, we keep defaults
            System.err.println("Localization DB read failed: " + e.getMessage());
        }
        return map;
    }
}

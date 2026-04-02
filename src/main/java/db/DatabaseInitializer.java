package db;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseInitializer {

    public static void initialize() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            InputStream is = DatabaseInitializer.class.getResourceAsStream("/init.sql");
            if (is == null) {
                System.err.println("init.sql not found in resources");
                return;
            }

            String sql = new BufferedReader(new InputStreamReader(is))
                    .lines()
                    .collect(Collectors.joining("\n"));

            // Split statements by semicolon; simple approach for this small script
            // Use a safe regex string literal
            String[] statements = sql.split(";\\s*");

            try (Statement st = conn.createStatement()) {
                for (String stmt : statements) {
                    String s = stmt.trim();
                    if (s.isEmpty()) continue;
                    st.execute(s);
                }
            }

            System.out.println("Database initialization complete");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

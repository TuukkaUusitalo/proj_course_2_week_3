package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mariadb://localhost:3306/shopping_cart_localization";
    private static final String USER = "calc_user";
    private static final String PASSWORD = "Test12"; //

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

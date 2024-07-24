package inventory;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {
    public static Connection connectDb() {
        try {
            // Use the new driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/inventory", "root", "mysql@453");
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

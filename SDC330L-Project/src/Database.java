/*****
 * Name: Brandy Christopher
 * Date: 11/1/25
 * Purpose: SQLite connection helper and schema bootstrap.
 */
import java.sql.*;

public class Database {
    private static final String URL = "jdbc:sqlite:rolodex.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initialize() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS contacts(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type TEXT NOT NULL," +
                "first_name TEXT NOT NULL," +
                "last_name TEXT NOT NULL," +
                "phone TEXT, email TEXT," +
                "street TEXT, city TEXT, state TEXT, zip TEXT," +
                "company_name TEXT, company_phone TEXT," +
                "job_title TEXT, relationship TEXT, how_we_met TEXT);";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {
            st.execute(sql);
        }
    }
}

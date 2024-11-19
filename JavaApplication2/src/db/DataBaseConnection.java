
package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author asma8
 */
public class DataBaseConnection {
  private static final String URL = "jdbc:mysql://localhost:3306/health";
  public static Connection connection;
  public static Connection connect(){
      // if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, "root", "");
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        //}
        return connection;
    }

    /*public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }*/
  }
 

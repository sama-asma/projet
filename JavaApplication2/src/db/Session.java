
package db;

/**
 *
 * @author asma8
 */
public class Session {
    private static int userId;  // Stocke l'ID de l'utilisateur
    private static String username; // Stocke le nom d'utilisateur 

    public static void setUserId(int id) {
        userId = id;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUsername(String user) {
        username = user;
    }

    public static String getUsername() {
        return username;
    }
}

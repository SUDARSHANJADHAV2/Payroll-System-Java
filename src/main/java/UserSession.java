/**
 * User Session Management for Payroll System
 * Manages current logged-in user information
 */
public class UserSession {
    private static String currentUsername;
    private static String currentUserRole;
    private static boolean isLoggedIn = false;

    /**
     * Set current user information
     * 
     * @param username Username of logged-in user
     * @param role     Role of logged-in user
     */
    public static void setCurrentUser(String username, String role) {
        currentUsername = username;
        currentUserRole = role;
        isLoggedIn = true;
    }

    /**
     * Get current username
     * 
     * @return Current username or null if not logged in
     */
    public static String getCurrentUsername() {
        return currentUsername;
    }

    /**
     * Get current user role
     * 
     * @return Current user role or null if not logged in
     */
    public static String getCurrentUserRole() {
        return currentUserRole;
    }

    /**
     * Check if user is logged in
     * 
     * @return true if user is logged in, false otherwise
     */
    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Logout current user
     */
    public static void logout() {
        currentUsername = null;
        currentUserRole = null;
        isLoggedIn = false;
    }

    /**
     * Check if current user has admin privileges
     * 
     * @return true if user is admin, false otherwise
     */
    public static boolean isAdmin() {
        return isLoggedIn && "admin".equals(currentUserRole);
    }

    /**
     * Get display name for current user
     * 
     * @return Formatted display name
     */
    public static String getDisplayName() {
        if (isLoggedIn) {
            return currentUsername + " (" + currentUserRole + ")";
        }
        return "Not logged in";
    }
}
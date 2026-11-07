
package Login;

import Hospital.DBConnection;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class AuthenticationService {

    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;

    // Possible registration outcomes
    public enum RegistrationResult {
        REGISTERED,
        USERNAME_EXISTS,
        INVALID_USERNAME,
        INVALID_PASSWORD
    }

    // Validate username: English letters only
    public static boolean isValidUsername(String username) {
        return username != null
                && username.matches("[A-Za-z]+");
    }

    // Validate password: digits only
    public static boolean isValidPassword(char[] password) {

        if (password == null || password.length == 0) {
            return false;
        }

        for (char ch : password) {
            if (ch < '0' || ch > '9') {
                return false;
            }
        }

        return true;
    }

    // Generate a secure password hash
    public static String hashPassword(char[] password)
            throws Exception {

        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        PBEKeySpec spec =
                new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);

        try {
            byte[] hash = SecretKeyFactory
                    .getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec)
                    .getEncoded();

            return ITERATIONS + ":"
                    + Base64.getEncoder().encodeToString(salt) + ":"
                    + Base64.getEncoder().encodeToString(hash);

        } finally {
            spec.clearPassword();
        }
    }

    // Verify an entered password against its stored hash
    private static boolean verifyPassword(
            char[] password, String storedHash) throws Exception {

        try {
            String[] parts = storedHash.split(":");

            if (parts.length != 3) {
                return false;
            }

            int iterations = Integer.parseInt(parts[0]);

            if (iterations < 10000 || iterations > 1000000) {
                return false;
            }

            byte[] salt =
                    Base64.getDecoder().decode(parts[1]);

            byte[] expectedHash =
                    Base64.getDecoder().decode(parts[2]);

            if (salt.length != 16 || expectedHash.length != 32) {
                return false;
            }

            PBEKeySpec spec =
                    new PBEKeySpec(password, salt, iterations, KEY_LENGTH);

            try {
                byte[] actualHash = SecretKeyFactory
                        .getInstance("PBKDF2WithHmacSHA256")
                        .generateSecret(spec)
                        .getEncoded();

                return MessageDigest.isEqual(
                        expectedHash, actualHash
                );

            } finally {
                spec.clearPassword();
            }

        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // Register a new user
    public static RegistrationResult registerUser(
            String username, char[] password) throws Exception {

        if (!isValidUsername(username)) {
            return RegistrationResult.INVALID_USERNAME;
        }

        if (!isValidPassword(password)) {
            return RegistrationResult.INVALID_PASSWORD;
        }

        String passwordHash = hashPassword(password);

        String query =
                "INSERT INTO users (username, password_hash) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, passwordHash);

            statement.executeUpdate();

            return RegistrationResult.REGISTERED;

        } catch (SQLException e) {

            // MySQL duplicate-entry error
            if (e.getErrorCode() == 1062
                    && "23000".equals(e.getSQLState())) {

                return RegistrationResult.USERNAME_EXISTS;
            }

            throw e;
        }
    }

    // Authenticate an existing user
    public static boolean authenticate(
            String username, char[] password) throws Exception {

        if (!isValidUsername(username)
                || !isValidPassword(password)) {
            return false;
        }

        String query =
                "SELECT password_hash, is_active FROM users WHERE username = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query)) {

            statement.setString(1, username);

            try (ResultSet result = statement.executeQuery()) {

                if (!result.next()) {
                    return false;
                }

                if (!result.getBoolean("is_active")) {
                    return false;
                }

                String storedHash =
                        result.getString("password_hash");

                return verifyPassword(password, storedHash);
            }
        }
    }
}

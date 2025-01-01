package com.example.demo.config;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

@NoArgsConstructor

public class PasswordUtil {

    // Hash a password
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Verify a password
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

}

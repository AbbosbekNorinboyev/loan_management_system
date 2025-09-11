package uz.pdp.loan_management_system.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordValidator {
    private PasswordValidator() {
    }

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static boolean validatePassword(String rawToken, String hashedToken) {
        return encoder.matches(rawToken, hashedToken);
    }
}

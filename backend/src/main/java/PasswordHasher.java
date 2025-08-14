import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "000000/*7Aa";  // your plain password
        String hashedPassword = encoder.encode(rawPassword);
        System.out.println("Hashed password: " + hashedPassword);
        System.out.println();
    }
}

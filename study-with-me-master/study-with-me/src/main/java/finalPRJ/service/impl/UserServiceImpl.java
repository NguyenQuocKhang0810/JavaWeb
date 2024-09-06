package finalPRJ.service.impl;

import java.util.UUID;

import finalPRJ.service.UserService;
import finalPRJ.utils.EmailUtil;

public class UserServiceImpl implements UserService {

    @Override
    public void sendPasswordResetEmail(String email) {
        // Generate a password reset token (you need to implement this)
        String token = generatePasswordResetToken(email);

        // Create reset link
        String resetLink = "http://forum-manage.com/reset-password?token=" + token;

        // Send email (you need to implement EmailUtil.sendEmail)
        EmailUtil.sendEmail(email, "Password Reset Request", "Click the link to reset your password: " + resetLink);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        // Verify token and expiry (you need to implement this)
        String email = verifyToken(token);

        // Reset the password (you need to implement this)
        updatePassword(email, newPassword);
    }

    private String generatePasswordResetToken(String email) {
        // Implement token generation logic (e.g., UUID + expiry time saved in DB)
        return UUID.randomUUID().toString();
    }

    private String verifyToken(String token) {
        // Implement token verification logic
        return "user@example.com"; // Return email associated with the token
    }

    private void updatePassword(String email, String newPassword) {
        // Implement password update logic
    }
}

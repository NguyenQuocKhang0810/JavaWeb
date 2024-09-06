package finalPRJ.service;

public interface UserService {
    void sendPasswordResetEmail(String email);
    void resetPassword(String token, String newPassword);
}

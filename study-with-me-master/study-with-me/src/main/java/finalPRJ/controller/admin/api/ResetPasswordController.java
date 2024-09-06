package finalPRJ.controller.admin.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import finalPRJ.service.UserService;
import finalPRJ.service.impl.UserServiceImpl;
import finalPRJ.utils.HttpUtil;
import finalPRJ.utils.ResponseUtil;

@WebServlet("/api/reset-password")
public class ResetPasswordController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserService userService;

    public void init() {
        userService = (UserService) new UserServiceImpl();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String token = HttpUtil.of(req.getReader()).getString("token");
            String newPassword = HttpUtil.of(req.getReader()).getString("password");
            userService.resetPassword(token, newPassword);
            ResponseUtil.success(resp, "Password reset successfully.");
        } catch (Exception e) {
            ResponseUtil.error(resp, e.getMessage());
        }
    }
}

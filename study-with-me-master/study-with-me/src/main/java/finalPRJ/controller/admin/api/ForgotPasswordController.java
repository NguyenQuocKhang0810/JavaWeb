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

@WebServlet("/api/forgot-password")
public class ForgotPasswordController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserService userService;

    public void init() {
        userService = (UserService) new UserServiceImpl();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String email = HttpUtil.of(req.getReader()).getString("email");
            userService.sendPasswordResetEmail(email);
            ResponseUtil.success(resp, "Password reset email sent.");
        } catch (Exception e) {
            ResponseUtil.error(resp, e.getMessage());
        }
    }
}

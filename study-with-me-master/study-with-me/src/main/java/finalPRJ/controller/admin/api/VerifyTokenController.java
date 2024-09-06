package finalPRJ.controller.admin.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import finalPRJ.constants.ErrorMessage;
import finalPRJ.utils.JwtTokenUtil;
import finalPRJ.utils.ResponseUtil;

@WebServlet("/api/verify-token")
public class VerifyTokenController extends HttpServlet {

	private static final long serialVersionUID = 2860215007883522580L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String accessToken = JwtTokenUtil.getJwtFromRequest(req);
			boolean isValidToken = JwtTokenUtil.validateToken(accessToken);
			if (isValidToken) {
				ResponseUtil.success(resp, true);
			} else {
				ResponseUtil.error(resp, ErrorMessage.TOKEN_INVALID);
			}
		} catch (Exception e) {
			ResponseUtil.error(resp, e.getMessage());
		}
	}
}

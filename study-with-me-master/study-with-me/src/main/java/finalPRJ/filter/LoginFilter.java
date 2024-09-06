package finalPRJ.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import finalPRJ.constants.ErrorMessage;
import finalPRJ.utils.JwtTokenUtil;
import finalPRJ.utils.ResponseUtil;
import io.jsonwebtoken.Claims;

@WebFilter(urlPatterns = { "/admin/*" })
public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String accessToken = JwtTokenUtil.getJwtFromRequest(req);
		boolean isValidToken = JwtTokenUtil.validateToken(accessToken);
		if (!isValidToken) {
			ResponseUtil.error(res, ErrorMessage.TOKEN_INVALID);
			return;
		}
		Claims claims = JwtTokenUtil.getAllClaimsFromToken(accessToken);
		String username = claims.get("username").toString();
		System.out.println("Request From: " + claims.get("username"));
		// TODO: Need query DB check user is active or not.
		req.setAttribute("username", username);
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {

	}

}

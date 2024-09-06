package finalPRJ.controller.admin.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import finalPRJ.constants.ErrorMessage;
import finalPRJ.constants.QueryType;
import finalPRJ.model.PaginationModel;
import finalPRJ.model.request.MessagesModelRequest;
import finalPRJ.model.response.MessagesModelResponse;
import finalPRJ.service.MessagesService;
import finalPRJ.service.impl.MessagesServiceImpl;
import finalPRJ.utils.HttpUtil;
import finalPRJ.utils.ResponseUtil;

@WebServlet(urlPatterns = { "/admin/api/messages" })
// Add @MultipartConfig for enable upload file.
@MultipartConfig
public class MessagesController extends HttpServlet {

	private static final long serialVersionUID = -331986167361646886L;

	private MessagesService MessagesService;

	public void init() {
		MessagesService = new MessagesServiceImpl();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			MessagesModelRequest requestBody = HttpUtil.of(req.getReader()).toModel(MessagesModelRequest.class);
			String username = req.getAttribute("username").toString();
			boolean result = MessagesService.create(requestBody, username);
			ResponseUtil.success(resp, result);
		} catch (Exception e) {
			ResponseUtil.error(resp, e.getMessage());
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			MessagesModelRequest requestBody = HttpUtil.of(req.getReader()).toModel(MessagesModelRequest.class);
			String username = req.getAttribute("username").toString();
			boolean result = MessagesService.update(req.getParameter("id"), requestBody, username);
			ResponseUtil.success(resp, result);
		} catch (Exception e) {
			ResponseUtil.error(resp, e.getMessage());
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			boolean result = MessagesService.deleteById(req.getParameter("id"));
			ResponseUtil.success(resp, result);
		} catch (Exception e) {
			ResponseUtil.error(resp, e.getMessage());
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String type = req.getParameter("type");
			switch (type) {
			case QueryType.FILTER:
				String name = req.getParameter("name");
				String page = req.getParameter("page");
				String size = req.getParameter("size");
				PaginationModel results = MessagesService.filter(page, size, name);
				ResponseUtil.success(resp, results);
				break;
			case QueryType.GET_ONE:
				String id = req.getParameter("id");
				MessagesModelResponse result = MessagesService.getById(id);
				ResponseUtil.success(resp, result);
				break;
			default:
				ResponseUtil.error(resp, ErrorMessage.TYPE_INVALID);
			}
		} catch (Exception e) {
			ResponseUtil.error(resp, e.getMessage());
		}
	}
}

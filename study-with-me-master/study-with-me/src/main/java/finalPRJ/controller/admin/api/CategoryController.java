package finalPRJ.controller.admin.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import finalPRJ.constants.ErrorMessage;
import finalPRJ.constants.QueryType;
import finalPRJ.model.PaginationModel;
import finalPRJ.model.request.CategoryModelRequest;
import finalPRJ.model.response.CategoryModelResponse;
import finalPRJ.service.CategoryService;
import finalPRJ.service.impl.CategoryServiceImpl;
import finalPRJ.utils.HttpUtil;
import finalPRJ.utils.ResponseUtil;

@WebServlet(urlPatterns = { "/admin/api/category" })
public class CategoryController extends HttpServlet {

	private static final long serialVersionUID = -331986167361646886L;

	private CategoryService categoryService;

	public void init() {
		categoryService = new CategoryServiceImpl();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			CategoryModelRequest requestBody = HttpUtil.of(req.getReader()).toModel(CategoryModelRequest.class);
			String username = req.getAttribute("username").toString();
			boolean result = categoryService.create(requestBody, username);
			ResponseUtil.success(resp, result);
		} catch (Exception e) {
			ResponseUtil.error(resp, e.getMessage());
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			CategoryModelRequest requestBody = HttpUtil.of(req.getReader()).toModel(CategoryModelRequest.class);
			String username = req.getAttribute("username").toString();
			boolean result = categoryService.update(req.getParameter("id"), requestBody, username);
			ResponseUtil.success(resp, result);
		} catch (Exception e) {
			ResponseUtil.error(resp, e.getMessage());
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			boolean result = categoryService.deleteById(req.getParameter("id"));
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
				PaginationModel results = categoryService.filter(page, size, name);
				ResponseUtil.success(resp, results);
				break;
			case QueryType.GET_ONE:
				String id = req.getParameter("id");
				CategoryModelResponse result = categoryService.getById(id);
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

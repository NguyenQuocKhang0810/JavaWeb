package finalPRJ.controller.admin.api;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import finalPRJ.constants.ErrorMessage;
import finalPRJ.constants.QueryType;
import finalPRJ.model.PaginationModel;
import finalPRJ.model.request.AdsModelRequest;
import finalPRJ.model.response.AdsModelResponse;
import finalPRJ.service.AdsService;
import finalPRJ.service.impl.AdsServiceImpl;
import finalPRJ.utils.HttpUtil;
import finalPRJ.utils.ResponseUtil;
@WebServlet(urlPatterns = { "/admin/api/ads" })
@MultipartConfig
public class AdsController extends HttpServlet {

    private static final long serialVersionUID = -331986167361646886L;

    private AdsService adsService;

    @Override
    public void init() throws ServletException {
        adsService = new AdsServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Get JSON payload from request.
            AdsModelRequest requestBody = HttpUtil.ofFormData(req.getPart("payload"))
                    .toModel(AdsModelRequest.class);
            // Get username from header request.
            String username = req.getAttribute("username").toString();
            // Handle file uploads
            Collection<Part> fileParts = req.getParts().stream()
                    .filter(part -> "images".equals(part.getName()) && part.getSize() > 0)
                    .collect(Collectors.toList());
            // Call service create a new Ads.
            boolean result = adsService.create(requestBody, fileParts, username);
            ResponseUtil.success(resp, result);
        } catch (Exception e) {
            ResponseUtil.error(resp, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Get JSON payload from request.
            AdsModelRequest requestBody = HttpUtil.ofFormData(req.getPart("payload"))
                    .toModel(AdsModelRequest.class);
            // Get username from header request.
            String username = req.getAttribute("username").toString();
            // Handle file uploads
            Collection<Part> fileParts = req.getParts().stream()
                    .filter(part -> "images".equals(part.getName()) && part.getSize() > 0)
                    .collect(Collectors.toList());
            // Call service update Ads.
            boolean result = adsService.update(req.getParameter("id"), requestBody, fileParts, username);
            ResponseUtil.success(resp, result);
        } catch (Exception e) {
            ResponseUtil.error(resp, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            boolean result = adsService.deleteById(req.getParameter("id"));
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
                    PaginationModel results = adsService.filter(page, size, name);
                    ResponseUtil.success(resp, results);
                    break;
                case QueryType.GET_ONE:
                    String id = req.getParameter("id");
                    AdsModelResponse result = adsService.getById(id);
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

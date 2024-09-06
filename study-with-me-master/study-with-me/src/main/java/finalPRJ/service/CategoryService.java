package finalPRJ.service;

import finalPRJ.model.PaginationModel;
import finalPRJ.model.request.CategoryModelRequest;
import finalPRJ.model.response.CategoryModelResponse;

public interface CategoryService {

	public boolean create(CategoryModelRequest request, String username);

	public boolean update(String id, CategoryModelRequest request, String username);

	public boolean deleteById(String id);

	public CategoryModelResponse getById(String id);

	public PaginationModel filter(String page, String size, String name);

}

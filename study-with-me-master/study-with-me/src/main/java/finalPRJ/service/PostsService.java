package finalPRJ.service;

import javax.servlet.http.Part;

import finalPRJ.model.PaginationModel;
import finalPRJ.model.request.PostsModelRequest;
import finalPRJ.model.response.PostsModelResponse;

public interface PostsService {

	public boolean create(PostsModelRequest request, Part banner, String username);

	public boolean update(String id, PostsModelRequest request, Part file, String username);

	public boolean deleteById(String id);

	public PostsModelResponse getById(String id);

	public PaginationModel filter(String page, String size, String name);

}

package finalPRJ.service;

import finalPRJ.model.PaginationModel;
import finalPRJ.model.request.MessagesModelRequest;
import finalPRJ.model.response.MessagesModelResponse;

public interface MessagesService {

	public boolean create(MessagesModelRequest request, String username);

	public boolean update(String id, MessagesModelRequest request, String username);

	public boolean deleteById(String id);

	public MessagesModelResponse getById(String id);

	public PaginationModel filter(String page, String size, String name);

}
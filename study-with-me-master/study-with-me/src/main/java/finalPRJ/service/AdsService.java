package finalPRJ.service;

import java.util.Collection;

import javax.servlet.http.Part;

import finalPRJ.model.PaginationModel;
import finalPRJ.model.request.AdsModelRequest;
import finalPRJ.model.response.AdsModelResponse;

public interface AdsService {
	public boolean create(AdsModelRequest request, Collection<Part> images, String username);

	public boolean update(String id, AdsModelRequest request, Collection<Part> images, String username);

	public boolean deleteById(String id);

	public AdsModelResponse getByPosition(String types);

	public AdsModelResponse getById(String id);

	public PaginationModel filter(String page, String size, String name);

}

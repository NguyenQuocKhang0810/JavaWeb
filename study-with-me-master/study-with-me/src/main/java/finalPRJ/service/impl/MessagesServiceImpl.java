package finalPRJ.service.impl;

import finalPRJ.constants.ErrorMessage;
import finalPRJ.model.PaginationModel;
import finalPRJ.model.request.MessagesModelRequest;
import finalPRJ.model.response.MessagesModelResponse;
import finalPRJ.repository.MessagesRepository;
import finalPRJ.service.MessagesService;
import finalPRJ.utils.StringUtil;

public class MessagesServiceImpl implements MessagesService {

	private MessagesRepository MessagesRepository = new MessagesRepository();

	@Override
	public boolean create(MessagesModelRequest request, String username) {
		return MessagesRepository.create(request, username);
	}

	/// loi 
	@Override
	public boolean update(String id, MessagesModelRequest request, String username) {
		// Parse String to Long.
		Long idNumber = StringUtil.parseLong("Id", id);
		// Get old Messages.
		MessagesModelResponse oldMessages = MessagesRepository.getById(idNumber);
		// If Messages is not exists cannot update so will throw Error.
		if (oldMessages == null) {
			throw new RuntimeException(ErrorMessage.RECORD_NOT_FOUND);
		}
		// Compare is subject change.
		if (!request.getSubject().equalsIgnoreCase(oldMessages.getSubject())) {
			// Compare new subject with other name in database.
			MessagesModelResponse otherMessages = MessagesRepository.getBySubject(request.getSubject());
			if (otherMessages != null) {
				throw new RuntimeException(ErrorMessage.NAME_IS_EXISTS);
			}
		}
		// Call repository saving file.
		return MessagesRepository.update(idNumber, request, username);
	}

	@Override
	public boolean deleteById(String id) {
		Long idNumber = StringUtil.parseLong("Id", id);
		MessagesModelResponse oldMessages = MessagesRepository.getById(idNumber);
		if (oldMessages == null) {
			throw new RuntimeException(ErrorMessage.RECORD_NOT_FOUND);
		}
		return MessagesRepository.deleteById(idNumber);
	}

	@Override
	public MessagesModelResponse getById(String id) {
		Long idNumber = StringUtil.parseLong("Id", id);
		MessagesModelResponse oldMessages = MessagesRepository.getById(idNumber);
		if (oldMessages == null) {
			throw new RuntimeException(ErrorMessage.RECORD_NOT_FOUND);
		}
		return oldMessages;
	}

	@Override
	public PaginationModel filter(String page, String size, String name) {
		int pageNumber = StringUtil.parseInt("Page", page);
		int sizeNumber = StringUtil.parseInt("Size", size);
		return MessagesRepository.filterByEmail(pageNumber, sizeNumber, name);
	}
}

package finalPRJ.model.response;

import finalPRJ.enums.MessagesStatusEnum;
import finalPRJ.model.BaseModel;

public class MessagesModelResponse extends BaseModel {

	private String subject;
	private String email;
	private MessagesStatusEnum status;
	private String message;

	public MessagesModelResponse() {
		super();
	}

	public MessagesModelResponse(String subject, String email, MessagesStatusEnum status, String message) {
		super();

		this.subject = subject;
		this.email = email;
		this.status = status;
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public MessagesStatusEnum getStatus() {
		return status;
	}

	public void setStatus(MessagesStatusEnum status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
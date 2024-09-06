package finalPRJ.model.request;

import finalPRJ.enums.MessagesStatusEnum;

public class MessagesModelRequest {

	private String subject;
	private String message;
	private MessagesStatusEnum status;
	private String email;

	public MessagesModelRequest() {
		super();
	}

	public MessagesModelRequest(String subject, String message, MessagesStatusEnum status, String email) {
		super();
		this.subject = subject;
		this.message = message;
		this.status = status;
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessagesStatusEnum getStatus() {
		return status;
	}

	public void setStatus(MessagesStatusEnum status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
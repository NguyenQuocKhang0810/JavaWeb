package finalPRJ.model.response;

import finalPRJ.model.BaseModel;

public class UserInfoModelResponse extends BaseModel{

	private String username;
	// TODO: define more than fields for user info.
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserInfoModelResponse(String username) {
		super();
		this.username = username;
	}

	public UserInfoModelResponse() {
		super();
	}
	
}

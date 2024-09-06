package finalPRJ.service;

import java.sql.SQLException;

import finalPRJ.model.request.LoginModelRequest;
import finalPRJ.model.response.LoginModelResponse;

public interface AuthService {

	public LoginModelResponse login(LoginModelRequest request) throws SQLException;

}

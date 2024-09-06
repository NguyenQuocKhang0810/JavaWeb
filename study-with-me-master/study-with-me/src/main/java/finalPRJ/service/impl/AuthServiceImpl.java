package finalPRJ.service.impl;

import java.sql.SQLException;

import finalPRJ.constants.ErrorMessage;
import finalPRJ.model.request.LoginModelRequest;
import finalPRJ.model.response.LoginModelResponse;
import finalPRJ.repository.UserRepository;
import finalPRJ.service.AuthService;

public class AuthServiceImpl implements AuthService {

	private UserRepository categoryRepository = new UserRepository();

	@Override
	public LoginModelResponse login(LoginModelRequest request) throws SQLException {
		LoginModelResponse validateLogin = categoryRepository.validateLogin(request);
		if (validateLogin == null) {
			throw new RuntimeException(ErrorMessage.LOGIN_FAIL);
		}
		return validateLogin;
	}

}

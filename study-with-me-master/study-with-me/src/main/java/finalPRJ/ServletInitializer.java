package finalPRJ;

import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import finalPRJ.enums.UserRoleEnum;
import finalPRJ.model.request.RegisterModelRequest;
import finalPRJ.model.response.UserInfoModelResponse;
import finalPRJ.repository.UserRepository;

public class ServletInitializer implements ServletContextListener {

	UserRepository userRepository = new UserRepository();

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			UserInfoModelResponse userInfo = userRepository.getByUsernameOrEmail("admin", "admin@study.com");
			if (userInfo == null) {
				userRepository.createAccount(
						new RegisterModelRequest("admin", "admin", "admin@study.com", UserRoleEnum.ADMIN));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}

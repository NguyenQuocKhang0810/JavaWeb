package finalPRJ.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import finalPRJ.constants.ErrorMessage;
import finalPRJ.enums.MessagesStatusEnum;
import finalPRJ.model.PaginationModel;
import finalPRJ.model.request.MessagesModelRequest;
import finalPRJ.model.response.MessagesModelResponse;
import finalPRJ.utils.DBUtil;

public class MessagesRepository {
	private static final String INSERT_SQL = "INSERT INTO messages (subject, email, createdBy, updatedBy, message) VALUES (?, ?, ?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE messages SET subject = ?, email = ?, status =?, message = ?, updatedBy = ? WHERE id = ?";
	private static final String GET_BY_ID_SQL = "SELECT * FROM messages WHERE id = ?";
	private static final String GET_BY_NAME_SQL = "SELECT * FROM messages WHERE subject = ?";
	private static final String DELETE_BY_ID_SQL = "DELETE FROM messages WHERE id = ?";
	private static final String SEARCH_LIST_SQL = "SELECT * FROM messages WHERE email LIKE ? LIMIT ? OFFSET ?";
	private static final String COUNT_BY_NAME_SQL = "SELECT COUNT(id) AS totalRecord FROM messages WHERE email LIKE ?";

	// chinh user thanh email
	public boolean create(MessagesModelRequest request, String username) {
		// Open connection and set SQL query into PreparedStatement.
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {
			// Set parameters.
			preparedStatement.setString(1, request.getSubject());
			preparedStatement.setString(2, request.getEmail());
			preparedStatement.setString(3, username);
			preparedStatement.setString(4, username);
			preparedStatement.setString(5, request.getMessage());
			// Show SQL query.
			System.out.println(preparedStatement);
			// Execute query.
			preparedStatement.executeUpdate();
			return true;
		} catch (Exception e) {
			throw new RuntimeException(ErrorMessage.SQL_ERROR + e.getMessage());
		}
	}

	public boolean update(Long id, MessagesModelRequest request, String username) {
		// Open connection and set SQL query into PreparedStatement.
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
			// Set parameters.
			preparedStatement.setString(1, request.getSubject());
			preparedStatement.setString(2, request.getEmail());
			MessagesStatusEnum Status = MessagesStatusEnum.CHANGED;
			preparedStatement.setString(3, Status.name());
			preparedStatement.setString(4, request.getMessage());
			preparedStatement.setString(5, username);
			preparedStatement.setLong(6, id);
			// Show SQL query.
			System.out.println(preparedStatement);
			// Execute query.
			preparedStatement.executeUpdate();
			return true;
		} catch (Exception e) {
			throw new RuntimeException(ErrorMessage.SQL_ERROR + e.getMessage());
		}
	}

	public MessagesModelResponse getById(Long id) {
		// Open connection and set SQL query into PreparedStatement.
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_SQL)) {
			// Set parameters.
			preparedStatement.setLong(1, id);
			// Show SQL query.
			System.out.println(preparedStatement);
			// Execute query.
			ResultSet rs = preparedStatement.executeQuery();
			if (!rs.isBeforeFirst()) {
				return null;
			}
			MessagesModelResponse response = new MessagesModelResponse();
			while (rs.next()) {
				response.setId(rs.getLong("id"));
				response.setSubject(rs.getString("subject"));
				response.setEmail(rs.getString("email"));
				response.setStatus(MessagesStatusEnum.valueOf(rs.getString("status")));
				response.setCreatedDate(rs.getString("createdDate"));
				response.setUpdatedDate(rs.getString("updatedDate"));
				response.setCreatedBy(rs.getString("createdBy"));
				response.setUpdatedBy(rs.getString("updatedBy"));
				response.setMessage(rs.getString("message"));
			}
			return response;
		} catch (Exception e) {
			throw new RuntimeException(ErrorMessage.SQL_ERROR + e.getMessage());
		}
	}

	public MessagesModelResponse getBySubject(String subject) {
		// Open connection and set SQL query into PreparedStatement.
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_NAME_SQL)) {
			// Set parameters.
			preparedStatement.setNString(1, subject);
			// Show SQL query.
			System.out.println(preparedStatement);
			// Execute query.
			ResultSet rs = preparedStatement.executeQuery();
			if (!rs.isBeforeFirst()) {
				return null;
			}
			MessagesModelResponse response = new MessagesModelResponse();
			while (rs.next()) {
				response.setId(rs.getLong("id"));
				response.setSubject(rs.getString("subject"));
				response.setEmail(rs.getString("email"));
				response.setStatus(MessagesStatusEnum.valueOf(rs.getString("status")));
				response.setCreatedDate(rs.getString("createdDate"));
				response.setUpdatedDate(rs.getString("updatedDate"));
				response.setCreatedBy(rs.getString("createdBy"));
				response.setUpdatedBy(rs.getString("updatedBy"));
				response.setMessage(rs.getString("message"));
			}
			return response;
		} catch (Exception e) {
			throw new RuntimeException(ErrorMessage.SQL_ERROR + e.getMessage());
		}
	}

	public boolean deleteById(Long id) {
		// Open connection and set SQL query into PreparedStatement.
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
			// Set parameters.
			preparedStatement.setLong(1, id);
			// Show SQL query.
			System.out.println(preparedStatement);
			// Execute query.
			preparedStatement.executeUpdate();
			return true;
		} catch (Exception e) {
			throw new RuntimeException(ErrorMessage.SQL_ERROR + e.getMessage());
		}
	}

	public PaginationModel filterByEmail(int page, int size, String email) {
		// Open connection and set SQL query into PreparedStatement.
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement stmtSelect = connection.prepareStatement(SEARCH_LIST_SQL);
				PreparedStatement stmtCount = connection.prepareStatement(COUNT_BY_NAME_SQL)) {
			// Set parameters.
			stmtSelect.setString(1, email != null ? "%" + email + "%" : "%%");
			stmtSelect.setInt(2, size);
			stmtSelect.setInt(3, page * size);
			// Show SQL query.
			System.out.println(stmtSelect);
			// Execute query.
			// Select records.
			ResultSet rs = stmtSelect.executeQuery();
			List<MessagesModelResponse> results = new ArrayList<MessagesModelResponse>();
			while (rs.next()) {
				MessagesModelResponse response = new MessagesModelResponse();
				response.setId(rs.getLong("id"));
				response.setSubject(rs.getString("subject"));
				response.setEmail(rs.getString("email"));
				response.setStatus(MessagesStatusEnum.valueOf(rs.getString("status")));
				response.setCreatedDate(rs.getString("createdDate"));
				response.setUpdatedDate(rs.getString("updatedDate"));
				response.setCreatedBy(rs.getString("createdBy"));
				response.setUpdatedBy(rs.getString("updatedBy"));
				response.setMessage(rs.getString("message"));
				results.add(response);
			}

			// Count records;
			stmtCount.setString(1, email != null ? "%" + email + "%" : "%%");
			ResultSet rsCount = stmtCount.executeQuery();
			int totalRecord = 0;
			while (rsCount.next()) {
				totalRecord = rsCount.getInt("totalRecord");
			}
			return new PaginationModel(page, size, totalRecord, results);
		} catch (Exception e) {
			throw new RuntimeException(ErrorMessage.SQL_ERROR + e.getMessage());
		}
	}
}
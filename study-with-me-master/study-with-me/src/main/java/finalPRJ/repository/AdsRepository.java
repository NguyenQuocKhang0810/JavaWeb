package finalPRJ.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import finalPRJ.constants.ErrorMessage;
import finalPRJ.enums.StatusEnum;
import finalPRJ.model.PaginationModel;
import finalPRJ.model.request.AdsModelRequest;
import finalPRJ.model.response.AdsModelResponse;
import finalPRJ.utils.DBUtil;

public class AdsRepository {
	private static final String INSERT_SQL = "INSERT INTO ads (images, createdBy, updatedBy, width, height, position, url) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE ads SET images = ?, updatedBy = ?, width = ?, height = ?, position = ?, url = ? WHERE id = ?";
	private static final String GET_BY_POSITION_SQL = "SELECT * FROM ads WHERE position = ? ";
	private static final String GET_BY_ID_SQL = "SELECT * FROM ads WHERE id = ?";
	private static final String DELETE_BY_ID_SQL = "DELETE FROM ads  WHERE id= ? ";
	private static final String SEARCH_LIST_SQL = "SELECT * FROM ads WHERE position LIKE ? LIMIT ? OFFSET ?";
	private static final String COUNT_BY_NAME_SQL = "SELECT COUNT(id) AS totalRecord FROM ads WHERE position LIKE ?";

	public boolean create(AdsModelRequest request, String username) {
		// Open connection and set SQL query into PreparedStatement.
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {
			// Set parameters.
			preparedStatement.setString(1, request.getImages());;
			preparedStatement.setString(2, username);
			preparedStatement.setString(3, username);
			preparedStatement.setInt(4, request.getWidth());
			preparedStatement.setInt(5, request.getHeight());
			preparedStatement.setString(6, request.getPosition());
			preparedStatement.setString(7, request.getUrl());
			// Show SQL query.
			System.out.println(preparedStatement);
			// Execute query.
			preparedStatement.executeUpdate();
			return true;
		} catch (Exception e) {
			throw new RuntimeException(ErrorMessage.SQL_ERROR + e.getMessage());
		}
	}

	public boolean update(Long id, AdsModelRequest request, String username) {
		// Open connection and set SQL query into PreparedStatement.
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
			// Set parameters.
			preparedStatement.setString(1, request.getImages());;
			preparedStatement.setString(2, username);
			preparedStatement.setInt(3, request.getWidth());
			preparedStatement.setInt(4, request.getHeight());
			preparedStatement.setString(5, request.getPosition());
			preparedStatement.setString(6, request.getUrl());
			preparedStatement.setLong(7, id);
		
			// Show SQL query.
			System.out.println(preparedStatement);
			// Execute query.
			preparedStatement.executeUpdate();
			return true;
		} catch (Exception e) {
			throw new RuntimeException(ErrorMessage.SQL_ERROR + e.getMessage());
		}
	}

	public AdsModelResponse getById(Long id) {
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
			AdsModelResponse response = new AdsModelResponse();
			while (rs.next()) {
				response.setId(rs.getLong("id"));
				response.setImages(rs.getString("images"));
				response.setCreatedDate(rs.getString("createdDate"));
				response.setUpdatedDate(rs.getString("updatedDate"));
				response.setCreatedBy(rs.getString("createdBy"));
				response.setUpdatedBy(rs.getString("updatedBy"));
				response.setWidth(rs.getInt("width"));
				response.setHeight(rs.getInt("height"));
				response.setPosition(rs.getString("position"));
				response.setUrl(rs.getString("url"));

			}
			return response;
		} catch (Exception e) {
			throw new RuntimeException(ErrorMessage.SQL_ERROR + e.getMessage());
		}
	}

	public AdsModelResponse getByPosition(String position) {
		// Open connection and set SQL query into PreparedStatement.
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_POSITION_SQL)) {
			// Set parameters.
			preparedStatement.setString(1, position);
			// Show SQL query.
			System.out.println(preparedStatement);
			// Execute query.
			ResultSet rs = preparedStatement.executeQuery();
			if (!rs.isBeforeFirst()) {
				return null;
			}
			AdsModelResponse response = new AdsModelResponse();
			while (rs.next()) {
				response.setId(rs.getLong("id"));
				response.setImages(rs.getString("images"));
				response.setCreatedDate(rs.getString("createdDate"));
				response.setUpdatedDate(rs.getString("updatedDate"));
				response.setCreatedBy(rs.getString("createdBy"));
				response.setUpdatedBy(rs.getString("updatedBy"));
				response.setWidth(rs.getInt("width"));
				response.setHeight(rs.getInt("height"));
				response.setPosition(rs.getString("position"));
				response.setUrl(rs.getString("url"));
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

	public PaginationModel filterByName(int page, int size, String name) {
		// Open connection and set SQL query into PreparedStatement.
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement stmtSelect = connection.prepareStatement(SEARCH_LIST_SQL);
				PreparedStatement stmtCount = connection.prepareStatement(COUNT_BY_NAME_SQL)) {
			// Set parameters.
			stmtSelect.setString(1, name != null ? "%" + name + "%" : "%%");
			stmtSelect.setInt(2, size);
			stmtSelect.setInt(3, page * size);
			// Show SQL query.
			System.out.println(stmtSelect);
			// Execute query.
			// Select records.
			ResultSet rs = stmtSelect.executeQuery();
			System.out.println(123);
			List<AdsModelResponse> results = new ArrayList<AdsModelResponse>();
			while (rs.next()) {
				AdsModelResponse response = new AdsModelResponse();
				response.setId(rs.getLong("id"));
				response.setImages(rs.getString("images"));
				response.setCreatedDate(rs.getString("createdDate"));
				response.setUpdatedDate(rs.getString("updatedDate"));
				response.setCreatedBy(rs.getString("createdBy"));
				response.setUpdatedBy(rs.getString("updatedBy"));
				response.setStatus(StatusEnum.valueOf(rs.getString("status")));
				response.setWidth(rs.getInt("width"));
				response.setHeight(rs.getInt("height"));
				response.setPosition(rs.getString("position"));
				response.setUrl(rs.getString("url"));
				results.add(response);
			}

			// Count records;
			stmtCount.setString(1, name != null ? "%" + name + "%" : "%%");
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

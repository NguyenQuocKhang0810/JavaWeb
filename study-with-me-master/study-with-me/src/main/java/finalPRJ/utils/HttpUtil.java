package finalPRJ.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import finalPRJ.constants.ErrorMessage;

public class HttpUtil {

	private String value;

	public HttpUtil(String value) {
		this.value = value;
	}

	public <T> T toModel(Class<T> tClass) {
		try {
			return new ObjectMapper().readValue(value, tClass);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return null;
	}

	public static HttpUtil of(BufferedReader reader) {
		StringBuilder sb = new StringBuilder();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
		return new HttpUtil(sb.toString());
	}

	public static HttpUtil ofFormData(Part part) {
		try {
			InputStream inputStream = part.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			return of(reader);
		} catch (Exception e) {
			throw new RuntimeException(ErrorMessage.MISSING_BODY_PAYLOAD);
		}

	}
	
	 public String getString(String key) {
	        try {
	            ObjectMapper mapper = new ObjectMapper();
	            JsonNode jsonNode = mapper.readTree(this.value);
	            return jsonNode.get(key).asText();
	        } catch (Exception e) {
	            System.out.print(e.getMessage());
	        }
	        return null;
	    }
}

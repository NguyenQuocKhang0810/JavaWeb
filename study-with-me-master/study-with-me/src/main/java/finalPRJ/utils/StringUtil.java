package finalPRJ.utils;

import finalPRJ.constants.ErrorMessage;

public class StringUtil {
	public static Long parseLong(String fieldName, String value) {
		if (value == null) {
			throw new RuntimeException(fieldName + ErrorMessage.FIELD_REQUIRED);
		}
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			throw new RuntimeException(fieldName + ErrorMessage.IS_NOT_A_NUMBER);
		}
	}
	
	public static Integer parseInt(String fieldName, String value) {
		if (value == null) {
			throw new RuntimeException(fieldName + ErrorMessage.FIELD_REQUIRED);
		}
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			throw new RuntimeException(fieldName + ErrorMessage.IS_NOT_A_NUMBER);
		}
	}
}

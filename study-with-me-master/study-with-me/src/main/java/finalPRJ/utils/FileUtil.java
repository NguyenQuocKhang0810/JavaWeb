package finalPRJ.utils;

import java.io.File;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;

public class FileUtil {

	public static File getFolderUpload() {
		// Set resource name want to get value (application.properties).
		ResourceBundle rb = ResourceBundle.getBundle("application");
		// Get value of key [uploadDir] from file properties.
		String uploadDir = rb.getString("uploadDir");
		File folderUpload = new File(uploadDir);
		// Check folder is exists.
		if (!folderUpload.exists()) {
			// If not exists will create new folder.
			folderUpload.mkdirs();
		}
		return folderUpload;
	}

	public static String saveFile(Part file) {
		try {
			// Get origin filename from request.
			String originFileName = file.getSubmittedFileName();
			// Get ext from filename -> .jpge or png ...
			// Can add function validate file upload here.
			String extension = FilenameUtils.getExtension(originFileName);
			// Random a new filename with UUID.
			// If using origin filename, maybe will replace old images the same name.
			String outputFileName = UUID.randomUUID().toString() + "." + extension;
			String outputFile = FileUtil.getFolderUpload().getAbsolutePath() + File.separator + outputFileName;
			// Write file.
			file.write(outputFile);
			return outputFileName;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static Boolean removeFile(String filename) {
		if (filename == null || filename.trim().length() == 0) {
			return false;
		}
		String outputFile = FileUtil.getFolderUpload().getAbsolutePath() + File.separator + filename;
		File file = new File(outputFile);
		if (file.delete()) {
			return true;
		} else {
			return false;
		}
	}
}

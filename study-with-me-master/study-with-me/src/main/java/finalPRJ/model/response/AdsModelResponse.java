package finalPRJ.model.response;

import finalPRJ.enums.StatusEnum;
import finalPRJ.model.BaseModel;

public class AdsModelResponse extends BaseModel {
	private String images;
	private StatusEnum status;
	private int width;
	private int height;
	private String position;
	private String url;

	public AdsModelResponse() {
		super();
	}

	public AdsModelResponse(String images, StatusEnum status, int width, int height, String position, String url) {
		super();
		this.images = images;
		this.status = status;
		this.width = width;
		this.height = height;
		this.position = position;
		this.url = url;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

package finalPRJ.model.request;



public class AdsModelRequest {
	private String images;
	private int width;
	private int height;
	private String position;
	private String url;
	
	public AdsModelRequest() {
		super();
	}

	public AdsModelRequest(String images, int width, int height, String position, String url) {
		super();
		this.images = images;
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
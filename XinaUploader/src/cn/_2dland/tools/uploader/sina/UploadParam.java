package cn._2dland.tools.uploader.sina;

public class UploadParam {
	
	/** 上传网址 */
	private String uploadURL = null;
	private String ip = null;
	private String expClose = null;
	private String srcType = null;

	public String getUploadURL() {
		return uploadURL;
	}
	public void setUploadURL(String uploadURL) {
		this.uploadURL = uploadURL;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getExpClose() {
		return expClose;
	}
	public void setExpClose(String expClose) {
		this.expClose = expClose;
	}

	public String getSrcType() {
		return srcType;
	}
	public void setSrcType(String srcType) {
		this.srcType = srcType;
	}

}

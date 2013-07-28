package cn._2dland.tools.uploader.exception;

public class HttpException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public HttpException(int statusCode) {
		super("HTTP Error: " + statusCode);
	}

}

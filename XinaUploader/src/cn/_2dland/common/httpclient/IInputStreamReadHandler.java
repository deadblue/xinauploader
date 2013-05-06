package cn._2dland.common.httpclient;

public interface IInputStreamReadHandler {

	/**
	 * 读取的字节数
	 * @param size
	 */
	public void onRead(long size);

}

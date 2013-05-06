package cn._2dland.tools.uploader.sina;

import java.util.List;

public class LoginResult {

	/** 操作结果 */
	private int retcode;
	/** 回调URL */
	private List<String> arrURL;

	public int getRetcode() {
		return retcode;
	}
	public void setRetcode(int retcode) {
		this.retcode = retcode;
	}

	public List<String> getArrURL() {
		return arrURL;
	}
	public void setArrURL(List<String> arrURL) {
		this.arrURL = arrURL;
	}

}

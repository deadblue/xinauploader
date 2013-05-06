package cn._2dland.tools.uploader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.params.HttpClientParams;

import com.google.gson.Gson;

/**
 * 上传客户端抽象类
 * @author deadblue
 */
public abstract class UploadClient {

	/** http客户端 */
	protected HttpClient client = new HttpClient();
	/** json解析器 */
	protected Gson gson = null;
	/** 上传监听器 */
	protected IUploadHandler handler = null;
	
	/** 账号 */
	protected String account = null;
	/** 密码 */
	protected String password = null;

	public UploadClient(String account, String password) {
		// 初始化httpclient
		client = new HttpClient();
		HttpClientParams params = client.getParams();
		params.setContentCharset("utf-8");
		params.setHttpElementCharset("utf-8");
		params.setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		params.setBooleanParameter(HttpClientParams.SINGLE_COOKIE_HEADER, true);
		params.setParameter(HttpClientParams.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
		// 初始化json
		gson = new Gson();
		// 保存账户信息
		this.account = account;
		this.password = password;
	}

	public void setHandler(IUploadHandler handler) {
		this.handler = handler;
	}

	/**
	 * 登陆
	 * @return
	 */
	abstract public boolean login();

	/**
	 * 上传视频
	 * @param videoFile 视频文件
	 * @return 视频ID
	 */
	abstract public String upload(String videoFile);

}

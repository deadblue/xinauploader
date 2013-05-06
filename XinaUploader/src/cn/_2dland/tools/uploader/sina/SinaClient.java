package cn._2dland.tools.uploader.sina;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import cn._2dland.common.httpclient.FilePartSourceWrapper;
import cn._2dland.common.httpclient.IInputStreamReadHandler;
import cn._2dland.tools.uploader.IUploadHandler;
import cn._2dland.tools.uploader.TagDB;
import cn._2dland.tools.uploader.TitleDB;
import cn._2dland.tools.uploader.UploadClient;
import cn._2dland.tools.uploader.util.FileUtils;

public class SinaClient extends UploadClient {

	/** 预登录地址 */
	private static final String API_PRELOGIN = "http://login.sina.com.cn/sso/prelogin.php";
	/** 登陆地址 */
	private static final String API_LOGIN = "http://login.sina.com.cn/sso/login.php?client=sinaSSOManager.js(v1.2.3)";
	/** 上传地址 */
	private static final String API_UPLOAD = "http://upload.video.sina.com.cn/u.php?m=1";
	/** 提交地址 */
	private static final String API_SUBMIT = "http://upload.video.sina.com.cn/upload_com_utf8.php";

	public SinaClient(String account, String password) {
		super(account, password);
	}

	@Override
	public boolean login() {
		boolean result = false;
		try {
			PreloginResult pr = doPrelogin();
			if(pr.getRetcode() != 0) 
				throw new Exception("预登录失败！");

			LoginResult lr = doLogin(pr);
			if(lr.getRetcode() != 0)
				throw new Exception("登录失败！");

			doAfterLogin(lr);
			result = true;
			handler.onLogin(this, account);
		} catch (Exception e) {
			result = false;
			handler.onError(this, e);
		}
		return result;
	}

	/**
	 * 预登陆
	 * @return
	 * @throws Exception
	 */
	private PreloginResult doPrelogin() throws Exception {
		GetMethod request = new GetMethod(API_PRELOGIN);
		request.setQueryString(new NameValuePair[]{
				new NameValuePair("entry", "vblog"),
				new NameValuePair("rsakt", "mod"),
				new NameValuePair("callback", "sinaSSOManager.preloginCallBack"),
				new NameValuePair("su", Utils.encodeAccount(account))
		});
		if(client.executeMethod(request) != HttpStatus.SC_OK) {
			throw new Exception("错误的http响应！");
		}
		String responseBody = request.getResponseBodyAsString();
		request.releaseConnection();
		responseBody = responseBody.substring(32, responseBody.length() - 1);
		PreloginResult result = gson.fromJson(responseBody, PreloginResult.class);
		return result;
	}

	/**
	 * 登陆
	 * @param pr
	 * @return
	 * @throws Exception
	 */
	private LoginResult doLogin(PreloginResult pr) throws Exception {
		GetMethod request = new GetMethod(API_LOGIN);
		request.setQueryString(new NameValuePair[]{
				new NameValuePair("entry", "vblog"),
				new NameValuePair("service", "vblog"),
				new NameValuePair("client", "sinaSSOManager.js(v1.2.3)"),
				new NameValuePair("encoding", "UTF-8"),
				new NameValuePair("gateway", "1"),
				new NameValuePair("setdomain", "1"),
				new NameValuePair("callback", "parent.sinaSSOManager.lb"),
				new NameValuePair("returntype", "IFRAME"),
				new NameValuePair("savestate", "30"),
				new NameValuePair("useticket", "0"),
				new NameValuePair("vsnf", "1"),
				new NameValuePair("pwencode", "rsa2"),
				new NameValuePair("rsakv", pr.getRsakv()),
				new NameValuePair("su", Utils.encodeAccount(account)),
				new NameValuePair("sp", Utils.encodePassword(password, pr.getPubkey(), pr.getServertime(), pr.getNonce()))
		});
		if(client.executeMethod(request) != HttpStatus.SC_OK) {
			throw new Exception("错误的http响应！");
		}
		String responseBody = request.getResponseBodyAsString();
		request.releaseConnection();
		responseBody = responseBody.substring(
				responseBody.indexOf("setCrossDomainUrlList") + 22, 
				responseBody.indexOf("parent.sinaSSOManager.lb") - 2);
		LoginResult result = gson.fromJson(responseBody, LoginResult.class);
		return result;
	}

	/**
	 * 登陆后操作
	 * @param lr
	 */
	private void doAfterLogin(LoginResult lr) {
		for(String url : lr.getArrURL()) {
			try {
				GetMethod request = new GetMethod(url);
				client.executeMethod(request);
				request.releaseConnection();
			} catch (Exception e) {}
		}
	}

	@Override
	public String upload(String videoFile) {
		String result = null;
		try {
			UploadParam param = getUploadParam();
			String attRetName = uploadFile(param.getUploadURL(), videoFile);
			result = submitInfo(param, attRetName);
		} catch (Exception e) {
			handler.onError(this, e);
		}
		return result;
	}

	/**
	 * 获取上传参数
	 * @return
	 * @throws Exception
	 */
	private UploadParam getUploadParam() throws Exception {
		// 请求上传页面
		GetMethod request = new GetMethod(API_UPLOAD);
		int respCode = client.executeMethod(request);
		if(respCode != HttpStatus.SC_OK) throw new Exception("Http请求出错！");
		String body = request.getResponseBodyAsString();
		request.releaseConnection();

		// 解析网页内容
		UploadParam param = new UploadParam();
		// 上传URL
		String regex = "(?<=var\\supload_url\\s\\=\\s\\\")http://.*(?=\\\";)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(body);
		param.setUploadURL(m.find() ? body.substring(m.start(), m.end()) : null);
		// ip参数
		regex = "(?<=<input type=\"hidden\" value=\")\\d+\\.\\d+\\.\\d+\\.\\d+(?=\" name=\"ip\">)";
		p = Pattern.compile(regex);
		m = p.matcher(body);
		param.setIp(m.find() ? body.substring(m.start(), m.end()) : null);
		// expClose参数
		regex = "(?<=<input type=\"hidden\" value=\")\\d+(?=\" name=\"expClose\" id=\"expClose\">)";
		p = Pattern.compile(regex);
		m = p.matcher(body);
		param.setExpClose(m.find() ? body.substring(m.start(), m.end()) : null);
		// src_type参数
		regex = "(?<=<input type=\"hidden\" value=\")\\d+(?=\" name=\"src_type\" id=\"src_type\">)";
		p = Pattern.compile(regex);
		m = p.matcher(body);
		param.setSrcType(m.find() ? body.substring(m.start(), m.end()) : null);

		return param;
	}

	/**
	 * 上传文件
	 * @param url
	 * @param filename
	 * @throws Exception
	 */
	private String uploadFile(String url, String filename) throws Exception {
		File file = new File(filename);
		PostMethod request = new PostMethod(url);
		MultipartRequestEntity mre = new MultipartRequestEntity(new Part[]{
				new StringPart("Filename", file.getName()),
				new FilePart("file", new FilePartSourceWrapper(file, new UploadHandler(this, file, handler))),
				new StringPart("Upload", "Submit Query")
		}, request.getParams());
		request.setRequestEntity(mre);
		if(handler != null) handler.onUploadBegin(this, file.getName());
		int respCode = client.executeMethod(request);
		if(handler != null) handler.onUploadEnd(this, file.getName());
		if(respCode != HttpStatus.SC_OK) throw new Exception();

		// 上传完成，返回上传结果
		String uploadFileName = url.substring(url.indexOf("file_name=") + 10);
		StringBuffer buf = new StringBuffer();
		buf.append(file.getName()).append("\"").append(uploadFileName).append(".")
			.append(FileUtils.getFileExtension(file.getName()));
		return buf.toString();
	}

	/**
	 * 提交信息
	 * @param param
	 * @param attRetName
	 * @return
	 * @throws Exception
	 */
	private String submitInfo(UploadParam param, String attRetName) throws Exception {
		PostMethod request = new PostMethod(API_SUBMIT);
		request.setRequestHeader("Referer", API_UPLOAD);
		// 本次提交必须用GB2312编码，傻逼渣浪
		request.getParams().setContentCharset("GB2312");

		// 随机生成提交内容
		String title = TitleDB.getRandomTitle();
		String tags = TagDB.getRandomTags(" ");
		String channelId = Channels.getRandomChannel();
		request.addParameters(new NameValuePair[] {
				// 上传返回结果
				new NameValuePair("attRetName", attRetName),
				// 表单参数
				new NameValuePair("ip", param.getIp()),
				new NameValuePair("expClose", param.getExpClose()),
				new NameValuePair("src_type", param.getSrcType()),
				// 下列内容随机生成
				new NameValuePair("title", title),
				new NameValuePair("description", title),
				new NameValuePair("tags", tags),
				new NameValuePair("channel", channelId),
				new NameValuePair("channel_text", Channels.getChannelName(channelId)),
				// 以下为固定内容，因此写死在代码中
				new NameValuePair("froms", "转载"),
				new NameValuePair("url", ""),
				new NameValuePair("m", "1"),
				new NameValuePair("special", ""),
				new NameValuePair("special_text", "------"),
				new NameValuePair("special_text_all", ""),
		});
		int respCode = client.executeMethod(request);
		if(respCode != HttpStatus.SC_OK) throw new Exception();
		String body = request.getResponseBodyAsString();
		request.releaseConnection();

		// 从结果中查找VID
		String vid = null;
		String regex = "(?<=parent\\.uploadSucc\\(\")\\d+(?=\",\")";
		Matcher m = Pattern.compile(regex).matcher(body);
		if(m.find()) {
			vid = body.substring(m.start(), m.end());
		}
		return vid;
	}

}

/**
 * 文件流读取监听器
 * @author deadblue
 */
class UploadHandler implements IInputStreamReadHandler {

	/** 客户端 */
	private UploadClient client = null;
	/** 上传文件 */
	private File file = null;
	/** 监听类 */
	private IUploadHandler handler = null;

	/** 已经上传的大小 */
	private long uploadedSize = 0;

	public UploadHandler(UploadClient client, File file, IUploadHandler handler) {
		this.client = client;
		this.file = file;
		this.handler = handler;

		uploadedSize = 0;
	}

	@Override
	public void onRead(long size) {
		uploadedSize += size;
		if(handler != null) handler.onUploading(client, file.getName(), uploadedSize, file.length());
	}

}
package cn._2dland.tools.uploader;

public interface IUploadHandler {

	/** 登陆成功 */
	void onLogin(UploadClient client, String account);
	/** 上传开始 */
	void onUploadBegin(UploadClient client, String filename);
	/** 上传中 */
	void onUploading(UploadClient client, String filename, long current, long total);
	/** 上传完成 */
	void onUploadEnd(UploadClient client, String filename);

	/** 发生异常 */
	void onError(UploadClient client, Throwable error);
}

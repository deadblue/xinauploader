package cn._2dland.tools.uploader.cui;

import java.util.HashMap;
import java.util.Map;

import cn._2dland.tools.uploader.IUploadHandler;
import cn._2dland.tools.uploader.Logger;
import cn._2dland.tools.uploader.UploadClient;
import cn._2dland.tools.uploader.util.StringUtils;

public class CUIUploadHanlder implements IUploadHandler {

	/** 线程局部变量 */
	private static ThreadLocal<Map<String, Object>> threadMap = new ThreadLocal<Map<String,Object>>(){
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<String, Object>();
		}
	};

	@Override
	public void onLogin(UploadClient client, String account) {
		Logger.print2c("账户 " + account + " 登陆成功！");
	}

	@Override
	public void onUploadBegin(UploadClient client, String filename) {
		Map<String, Object> localMap = threadMap.get();
		localMap.put("startTime", System.currentTimeMillis());
		Logger.print2c("开始上传文件：" + filename);
	}

	@Override
	public void onUploading(UploadClient client, String filename, long current, long total) {
		// 上传进度
		double percent = (double) current / total;
		// 上传速度和剩余时间
		long startTime = (Long) threadMap.get().get("startTime");
		long spentTime = System.currentTimeMillis() - startTime;
		double speed = (double) current * 1000 / spentTime;
		int remainSecond = (int) Math.ceil((double) (total - current) / speed);

		// 格式化输出内容
		StringBuffer buf = new StringBuffer("\r");
		buf.append("进度: ").append(String.format("%6.2f", percent * 100)).append("%")
			.append(" / 速度: ").append(StringUtils.formatSpeed(speed))
			.append(" / 剩余时间: ").append(StringUtils.formatTime(remainSecond));
		System.out.print(buf.toString());
	}

	@Override
	public void onUploadEnd(UploadClient client, String filename) {
		System.out.print("\n");
		Logger.print2c("文件上传完成！");
		threadMap.remove();
	}

	@Override
	public void onError(UploadClient client, Throwable error) {
		Logger.error(error);
	}
}

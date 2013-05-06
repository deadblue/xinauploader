package cn._2dland.tools.uploader.task;

/**
 * 任务节点
 * @author deadblue
 */
public class TaskNode {

	/** 下一个节点 */
	private TaskNode next = null;
	/** 视频文件 */
	private String videoFile = null;
	/** 上传次数 */
	private int times = 0;

	public TaskNode() {}
	public TaskNode(String videoFile, int times) {
		this.videoFile = videoFile;
		this.times = times;
	}

	public TaskNode getNext() {
		return next;
	}
	public void setNext(TaskNode next) {
		this.next = next;
	}

	public String getVideoFile() {
		return videoFile;
	}
	public void setVideoFile(String videoFile) {
		this.videoFile = videoFile;
	}

	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}

}

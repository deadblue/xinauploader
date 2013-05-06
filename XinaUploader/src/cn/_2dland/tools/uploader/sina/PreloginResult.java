package cn._2dland.tools.uploader.sina;

public class PreloginResult {

	/** 操作结果 */
	private int retcode;
	/** 服务器时间 */
	private long servertime;
	/** ？？？ */
	private String pcid;
	/** 会话标记 */
	private String nonce;
	/** 公钥 */
	private String pubkey;
	/** RSA */
	private String rsakv;
	/** 执行次数 */
	private int exectime;

	public int getRetcode() {
		return retcode;
	}
	public void setRetcode(int retcode) {
		this.retcode = retcode;
	}

	public long getServertime() {
		return servertime;
	}
	public void setServertime(long servertime) {
		this.servertime = servertime;
	}

	public String getPcid() {
		return pcid;
	}
	public void setPcid(String pcid) {
		this.pcid = pcid;
	}

	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getPubkey() {
		return pubkey;
	}
	public void setPubkey(String pubkey) {
		this.pubkey = pubkey;
	}

	public String getRsakv() {
		return rsakv;
	}
	public void setRsakv(String rsakv) {
		this.rsakv = rsakv;
	}

	public int getExectime() {
		return exectime;
	}
	public void setExectime(int exectime) {
		this.exectime = exectime;
	}

}

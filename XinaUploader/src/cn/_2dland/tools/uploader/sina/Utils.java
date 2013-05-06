package cn._2dland.tools.uploader.sina;

import org.apache.commons.codec.binary.Base64;

import cn._2dland.tools.uploader.util.RSA;

public class Utils {

	private static final String HEX_CHAR = "0123456789abcdef";

	public static String encodeAccount(String account) {
		return Base64.encodeBase64String(account.getBytes());
	}
	
	public static String encodePassword(String password, String pubkey, long servertime, String nonce) {
		RSA rsa = new RSA(pubkey, "10001");
		
		StringBuffer buf = new StringBuffer();
		buf.append(servertime).append("\t").append(nonce).append("\n").append(password);
		byte[] data = rsa.encrypt(buf.toString().getBytes());
		
		buf = new StringBuffer();
		for(byte b : data) {
			buf.append(HEX_CHAR.charAt((b & 0xf0) >> 4));
			buf.append(HEX_CHAR.charAt(b & 0x0f));
		}
		return buf.toString();
	}
}

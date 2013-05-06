package cn._2dland.tools.uploader.util;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA {

	private Cipher cipher = null;
	private PublicKey key = null;

	public RSA(String modules, String exponent) {
		BigInteger m = new BigInteger(modules, 16);
		BigInteger e = new BigInteger(exponent, 16);
		
		try {
			RSAPublicKeySpec spec = new RSAPublicKeySpec(m, e);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			key = kf.generatePublic(spec);

			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (InvalidKeySpecException e1) {
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			e1.printStackTrace();
		}
	}
	
	public byte[] encrypt(byte[] data) {
		byte[] result = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipher.update(data);
			result = cipher.doFinal();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return result;
	}
}

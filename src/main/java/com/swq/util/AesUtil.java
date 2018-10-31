package com.swq.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * 
 * @description: AES加密解密工具（单例模式调用）
 * @date: 2015-6-30 下午6:08:50
 * @author:
 */
public class AesUtil {
	private final String IV = "98516D446AF418112933CC64F9A98C05";
	private final String SALT = "1D2F0B381038C6E1";
	private final int keySize = 128;
	private final int iterationCount = 1;
	private AesUtil() {
	}
	private static AesUtil util = new AesUtil();
	/**
	 * 
	 * @return
	 * @description: 获取单例
	 * @date: 2015年11月27日 下午2:26:57
	 *
	 */
	public static AesUtil getInstance(){
		return util;
	}
	/**'
	 * 
	 * @param content 待加密内容
	 * @param key 密钥
	 * @return
	 * @description: 加密函数
	 * @date: 2015-6-30 下午6:09:13
	 *
	 */
	public String encrypt(String content, String key) {
		String encrypt = util.encrypt(SALT, IV, key, content);
		return hex(base64(encrypt));
	}
	/**
	 * 
	 * @param cipherText 密文
	 * @param key 密钥
	 * @return
	 * @description: 解密函数
	 * @date: 2015-6-30 下午6:09:28
	 *
	 */
	public String decrypt(String cipherText, String key) {
		cipherText = base64(hex(cipherText));
		String decrypt = util.decrypt(SALT, IV, key, cipherText);
		return decrypt;
	}
	
	//加密调用
	private String encrypt(String salt, String iv, String passphrase, String plaintext) {
		try {
			SecretKey key = generateKey(salt, passphrase);
			byte[] encrypted = doFinal(Cipher.ENCRYPT_MODE, key, iv, plaintext.getBytes("UTF-8"));
			return base64(encrypted);
		} catch (UnsupportedEncodingException e) {
			throw fail(e);
		}
	}
	//解密调用
	private String decrypt(String salt, String iv, String passphrase, String ciphertext) {
		try {
			SecretKey key = generateKey(salt, passphrase);
			byte[] decrypted = doFinal(Cipher.DECRYPT_MODE, key, iv, base64(ciphertext));
			return new String(decrypted, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw fail(e);
		}
	}
	//加解密执行
	private byte[] doFinal(int encryptMode, SecretKey key, String iv, byte[] bytes) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encryptMode, key, new IvParameterSpec(hex(iv)));
			return cipher.doFinal(bytes);
		} catch (InvalidKeyException e) {
			throw fail(e);
		} catch (InvalidAlgorithmParameterException e) {
			throw fail(e);
		} catch (IllegalBlockSizeException e) {
			throw fail(e);
		} catch (BadPaddingException e) {
			throw fail(e);
		} catch (Exception e) {
			throw fail(e);
		}

	}

	//生成密钥对象
	private SecretKey generateKey(String salt, String passphrase) {
		SecretKeyFactory factory;
		try {
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), hex(salt), iterationCount, keySize);
			SecretKey key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
			return key;
		} catch (NoSuchAlgorithmException e) {
			throw fail(e);
		} catch (InvalidKeySpecException e) {
			throw fail(e);
		}

	}

	//字节码转base64字符串
	private static String base64(byte[] bytes) {
		try {
			return new String(Base64.encodeBase64(bytes),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static byte[] base64(String str) {
		return Base64.decodeBase64(str.getBytes());
	}

	private static String hex(byte[] bytes) {
		return new String(Hex.encodeHex(bytes));
	}

	private static byte[] hex(String str) {
		try {
			return Hex.decodeHex(str.toCharArray());
		} catch (DecoderException e) {
			throw new IllegalStateException(e);
		}
	}

	private IllegalStateException fail(Exception e) {
		return new IllegalStateException(e);
	}
}

package Tool;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * ���빤����
 * ���base64ʵ�ּ���
 * 1.AES����
 * 2.AES����Ϊbase 64 code
 * 3.��base 64 code AES����
 * 4.AES����
 */
public class CreateAES  {
	
	public static void main(String[] args) throws Exception {
		String content = "����";
		System.out.println("����ǰ��" + content);

		String key = "111";
		System.out.println("������Կ�ͽ�����Կ��" + key);
		
		String encrypt = aesEncrypt(content, key);
		System.out.println("���ܺ�" + encrypt);
		
		String decrypt = aesDecrypt(encrypt, key);
		System.out.println("���ܺ�" + decrypt);
		
	}
	
	/**
	 * AES����
	 * @param content �����ܵ�����
	 * @param encryptKey ������Կ
	 * @return ���ܺ��String
	 * @throws Exception
	 */
	public static String aesEncrypt(String content, String encryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(encryptKey.getBytes()));

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
		BASE64Encoder base64encoder = new BASE64Encoder(); 
		String encode=base64encoder.encode(cipher.doFinal(content.getBytes()));
		return encode;
	}
	
	
	/**
	 * AES����
	 * @param encrypt �����ܵ�String
	 * @param decryptKey ������Կ
	 * @return ���ܺ��String
	 * @throws Exception
	 */
	public static String aesDecrypt(String encrypt, String decryptKey) throws Exception {
		BASE64Decoder base64decoder = new BASE64Decoder(); 
		byte[] encodeByte = base64decoder.decodeBuffer(encrypt); 
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(decryptKey.getBytes()));
		
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
		byte[] decryptBytes = cipher.doFinal(encodeByte);
		
		return new String(decryptBytes);
	}
	
}

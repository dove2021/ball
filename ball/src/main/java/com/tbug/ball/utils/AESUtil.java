package com.tbug.ball.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by liangpw on 2015/12/18.
 */
public class AESUtil {
    private static final String AES_CBC_PKCS5 = "AES/CBC/PKCS5Padding";
    private static final String IV = "1234567892546398";
    private static final String key = "TnNDLpbvCvuGEOGO";
  
    /**
     * AES加密
     * @param content 待加密
     * @param cipher
     * @return
     * @throws Exception
     */
    public static String encryptStr(String content) {
    	try{
    		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("utf-8"), "AES");
    		IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
    		Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5);
    		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
    		
    		byte[] byteArr = encrypt(content, cipher);
    		return Base64.encodeBase64String(byteArr);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return "";
    }

    /**
     * AES解密
     * @param content 待解密
     * @param cipher
     * @return
     * @throws Exception
     */
    public static String deccryptStr(String content) {
    	try{
    		
    		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("utf-8"), "AES");
    		IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
    		Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5);
    		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
    		
    		byte[] byteArr = decrypt(Base64.decodeBase64(content), cipher);
    		return new String(byteArr, "UTF-8");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return "";
    }
    
    private static byte[] encrypt(String content, Cipher cipher) throws Exception{
        return cipher.doFinal(content.getBytes("utf-8"));
    }

    private static byte[] decrypt(byte[] content, Cipher cipher) throws Exception {
        return cipher.doFinal(content);
    }
    
    
    public static void main(String[] args) {
    	try {
    		String context = "aa";
    		System.out.println("原字符：" + context);
    		String str = encryptStr(context);
			System.out.println("加密后：" + str);
			System.out.println("解 密后：" + deccryptStr(str));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

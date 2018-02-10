package com.servicecomb.common.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DES3 {
	 private static final String secretKey = "hzbc_ysb@201501000000000";
	    private static final String iv = "01234567";
	    private static final String encoding = "utf-8";

	    public DES3() {
	    }

	    public static String encode(String plainText) {
	        try {
	            SecretKey e = null;
	            DESedeKeySpec spec = new DESedeKeySpec("hzbc_ysb@201501000000000".getBytes());
	            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
	            e = keyfactory.generateSecret(spec);
	            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
	            IvParameterSpec ips = new IvParameterSpec("01234567".getBytes());
	            cipher.init(1, e, ips);
	            byte[] encryptData = cipher.doFinal(plainText.getBytes("utf-8"));
	            return Base64.encode(encryptData);
	        } catch (Exception var7) {
	            var7.printStackTrace();
	            return null;
	        }
	    }

	    public static String decode(String encryptText) {
	        try {
	            SecretKey e = null;
	            DESedeKeySpec spec = new DESedeKeySpec("hzbc_ysb@201501000000000".getBytes());
	            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
	            e = keyfactory.generateSecret(spec);
	            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
	            IvParameterSpec ips = new IvParameterSpec("01234567".getBytes());
	            cipher.init(2, e, ips);
	            byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
	            return new String(decryptData, "utf-8");
	        } catch (Exception var7) {
	            var7.printStackTrace();
	            return null;
	        }
	    }
}

package com.servicecomb.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Random;

import com.servicecomb.common.constant.Constant;
import com.servicecomb.common.model.vo.CurrentUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


public class CommonUtil {

	public static final Character[] character = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
			    'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};

	public static boolean  checkSignature(String signature, String timestamp,
			String nonce,String token) {
		String[] arr = new String[] { token, timestamp, nonce };
		Arrays.sort(arr);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			builder.append(arr[i]);
		}
		String temp = SHA1(builder.toString());
		return temp.equals(signature);
	}
	
	public static String SHA1(String decript) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte[] messageDigest = digest.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	/**
	 * 获取自定长度的字符串
	 * @param length
	 * @return
	 */
	public static String getStr(int length){
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for(int i=0;i<length;i++){
			int index = random.nextInt(character.length);
			sb.append(character[index]);
		}
		return sb.toString();
	}


	/**
	 * 获取请求头中的
	 * 当前用户信息
	 * @return
	 */
	public static CurrentUser getCurrentUser(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String result = request.getHeader(Constant.CommomKey.CURRENT_USER);
		CurrentUser currentUser = FastJsonUtil.toBean(result,CurrentUser.class);
		return currentUser;
	}
}

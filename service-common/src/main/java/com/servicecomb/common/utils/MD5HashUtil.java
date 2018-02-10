package com.servicecomb.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;

public class MD5HashUtil {
	private static final char[] HEX = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	public MD5HashUtil() {
	}

	private static String toHexString(byte[] bytes) {
		int length = bytes.length;
		StringBuffer sb = new StringBuffer(length * 2);
		boolean x = false;
		boolean n1 = false;
		boolean n2 = false;

		for (int i = 0; i < length; ++i) {
			int var7;
			if (bytes[i] >= 0) {
				var7 = bytes[i];
			} else {
				var7 = 256 + bytes[i];
			}

			int var8 = var7 >> 4;
			int var9 = var7 & 15;
			sb = sb.append(HEX[var8]);
			sb = sb.append(HEX[var9]);
		}

		return sb.toString();
	}

	public static String toMD5(String str) {
		try {
			MessageDigest e = MessageDigest.getInstance("MD5");
			if (StringUtils.isNotEmpty(str)) {
				byte[] buf = e.digest(str.getBytes());
				return toHexString(buf);
			} else {
				return "";
			}
		} catch (NoSuchAlgorithmException var3) {
			throw new RuntimeException(var3);
		}
	}
}

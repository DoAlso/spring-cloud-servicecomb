package com.servicecomb.common.utils;

import org.apache.log4j.Logger;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Hex;

public class AESTool {
	protected static final Logger log = Logger.getLogger(AESTool.class);
	private byte[] initVector = new byte[] { 50, 55, 54, 53, 52, 51, 50, 49, 56, 39, 54, 53, 51, 35, 50, 49 };

	public AESTool() {
	}

	public String findKeyById(String userid) {
		String key = "hzbc_ysb@2015010";
		return key;
	}

	public String encrypt(String content, String key) throws Exception {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null!");
		} else {
			String encrypted = null;
			byte[] keyBytes = key.getBytes();
			if (keyBytes.length != 32 && keyBytes.length != 24 && keyBytes.length != 16) {
				throw new IllegalArgumentException("Key length must be 128/192/256 bits!");
			} else {
				Object encryptedBytes = null;
				byte[] encryptedBytes1 = this.encrypt(content.getBytes(), keyBytes, this.initVector);
				encrypted = new String(Hex.encode(encryptedBytes1));
				return encrypted;
			}
		}
	}

	public String decrypt(String content, String key) throws Exception {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null!");
		} else {
			String decrypted = null;
			byte[] encryptedContent = Hex.decode(content);
			byte[] keyBytes = key.getBytes();
			Object decryptedBytes = null;
			if (keyBytes.length != 32 && keyBytes.length != 24 && keyBytes.length != 16) {
				throw new IllegalArgumentException("Key length must be 128/192/256 bits!");
			} else {
				byte[] decryptedBytes1 = this.decrypt(encryptedContent, keyBytes, this.initVector);
				decrypted = new String(decryptedBytes1);
				return decrypted;
			}
		}
	}

	public byte[] encrypt(byte[] plain, byte[] key, byte[] iv) throws Exception {
		PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESFastEngine()));
		ParametersWithIV ivAndKey = new ParametersWithIV(new KeyParameter(key), iv);
		aes.init(true, ivAndKey);
		return this.cipherData(aes, plain);
	}

	public byte[] decrypt(byte[] cipher, byte[] key, byte[] iv) throws Exception {
		PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESFastEngine()));
		ParametersWithIV ivAndKey = new ParametersWithIV(new KeyParameter(key), iv);
		aes.init(false, ivAndKey);
		return this.cipherData(aes, cipher);
	}

	private byte[] cipherData(PaddedBufferedBlockCipher cipher, byte[] data) throws Exception {
		int minSize = cipher.getOutputSize(data.length);
		byte[] outBuf = new byte[minSize];
		int length1 = cipher.processBytes(data, 0, data.length, outBuf, 0);
		int length2 = cipher.doFinal(outBuf, length1);
		int actualLength = length1 + length2;
		byte[] result = new byte[actualLength];
		System.arraycopy(outBuf, 0, result, 0, result.length);
		return result;
	}
}

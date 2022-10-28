package com.cantaur.practice.common.encryptor;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class GenToken {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

//		host uid를 기준으로 암호화 해준다.
		Long bizInfoUid = 9000000000000L;
		Long storeUid = 8000000000000L;
		Long catUid = 7000000000000L;


		// 숫자만 있으면 암호화 문자열이 짧기 때문에 뒤에 문자열을 만들어 붙여준다.복호화 후 버리는 문자열.
		String appendStr = "abcdefghijklmnopqrstuvwxyz1234567890";

		StringBuilder sb = new StringBuilder();

		sb.append(bizInfoUid.toString());
		sb.append("&");
		sb.append(storeUid.toString());
		sb.append("&");
		sb.append(catUid.toString());
		sb.append("&");
		sb.append(appendStr);

		String genToken = AES256Encryptor.encrypt(sb.toString());

		log.debug(genToken);

		String dec = AES256Encryptor.decrypt(genToken);

		String a[] = dec.split("&");

		log.debug(a[0]);
	}

}
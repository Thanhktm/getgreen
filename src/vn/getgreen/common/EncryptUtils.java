package vn.getgreen.common;

import java.math.BigInteger;
import java.security.MessageDigest;

public class EncryptUtils {

	public EncryptUtils() {
		// TODO Auto-generated constructor stub
	}
	public static String md5(String input)
	{
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(input.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			String hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while(hashtext.length() < 32 ){
			  hashtext = "0"+hashtext;
			}
			return hashtext;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

package it.poste.postspy.utility;

import org.apache.commons.codec.binary.Base64;


public class CryptographyUtil {
	
	public static String encrypt(String value) throws Exception {
	    try {
            return Base64.encodeBase64String(value.getBytes());
	    } catch(Exception e) {
	    	throw e;
	    }
	}

	public static String decrypt(String value) throws Exception{
		try {
            return new String(Base64.decodeBase64(value));
	    } catch(Exception e) {
	    	throw e;
	    }
	}

}

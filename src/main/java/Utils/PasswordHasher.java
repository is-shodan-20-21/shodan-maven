package Utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {

	/*
		Routine di crittografia per celare le password nel database utilizzando algoritmi predefiniti.
		Il digest utilizzato è SHA-256.
	*/
	public static String hash(String text) {
		MessageDigest md;
		
		try {
			md = MessageDigest.getInstance("SHA-256");
		    md.update(text.getBytes(StandardCharsets.UTF_8));
		    
		    byte[] digest = md.digest();

		    return String.format("%064x", new BigInteger(1, digest));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return text;
	}
	
}

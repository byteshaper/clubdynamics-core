package com.clubdynamics.core.auth;

import com.clubdynamics.core.exception.UnexpectedServerException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CryptoUtil {
  
  private static final int SALT_LENGTH = 25;
  
  private static final SecureRandom secureRandom = new SecureRandom();

  public static String encryptPassword(String password, String salt) {
    MessageDigest messageDigest = createMessageDigestInstance();    
    String plainText = password + salt;
    messageDigest.update(plainText.getBytes());
    BigInteger bigInt = new BigInteger(1, messageDigest.digest());
    return bigInt.toString(16);
  }
  
  public static String generateSalt() {
    StringBuilder salt = new StringBuilder();
    
    for(int i = 0; i < SALT_LENGTH; i++) {
      salt.append((char) (33 + secureRandom.nextInt(93))); 
    }
    
    return salt.toString(); 
  }
  
  private static MessageDigest createMessageDigestInstance() {
    try {
      return MessageDigest.getInstance("SHA-512");
    } catch(NoSuchAlgorithmException e) {
      throw new UnexpectedServerException("No SHA available on this host / platform: " + e.getMessage());
    }
  }

}

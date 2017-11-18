package com.clubdynamics.core.auth;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CryptoUtilTest {
  
  @Test
  public void salt() {
	  String salt0 = CryptoUtil.generateSalt();
	  String salt1 = CryptoUtil.generateSalt();
	  assertThat(salt0, not(equalTo(salt1)));
  }
  
  @Test
  public void encryptPassword() {
    final String plainTextPassword = "geheim";
    final String salt = CryptoUtil.generateSalt();
    final String encrypted0 = CryptoUtil.encryptPassword(plainTextPassword, salt);
    final String encrypted1 = CryptoUtil.encryptPassword(plainTextPassword, salt);
    assertThat(encrypted0, equalTo(encrypted1));
    assertThat(encrypted0.length(), greaterThanOrEqualTo(60));
  }
}



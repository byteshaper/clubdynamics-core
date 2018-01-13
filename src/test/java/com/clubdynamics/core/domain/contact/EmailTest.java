package com.clubdynamics.core.domain.contact;

import com.clubdynamics.core.testutil.VerifyValidation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

public class EmailTest {

  private static final List<String> VALID_ADDRESSES = Arrays.asList(
      "masters@bsv-k.de",
      "someone@example.com",
      "who-ever@example.com");
  
  private static final List<String> INVALID_ADDRESSES = Arrays.asList(
      "masters@()",
      "ab",
      "someone@ex@ample.com",
      "who\\ever@example.com");
  
  @Test
  public void validEmailAddresses() {
    VerifyValidation.assertValid(VALID_ADDRESSES
        .stream()
        .map(a -> new Email(a, ContactType.PRIVATE))
        .collect(Collectors.toList()));
  }
  
  @Test
  public void invalidEmailAddresses() {
    VerifyValidation.assertInvalid(INVALID_ADDRESSES
        .stream()
        .map(a -> new Email(a, ContactType.PRIVATE))
        .collect(Collectors.toList()), "address");
  }
  
}

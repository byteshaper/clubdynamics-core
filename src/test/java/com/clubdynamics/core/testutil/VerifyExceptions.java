package com.clubdynamics.core.testutil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.function.Supplier;

/**
 * For easy verification that a certain exception is thrown. Unlike using the "expected" parameter of JUnit Test-annotation,
 * the actual test does not have to end with the exception thrown.
 * 
 * @author Henning Schütz
 *
 */
public class VerifyExceptions {

  public static <T> T verifyWithExactMessage(
      Supplier<T> workload, 
      Class<? extends Exception> expectedExceptionClass, 
      String expectedMessage) {
    
    boolean exceptionThrown = false;
    Class<? extends Exception> actualExceptionClass = null;
    T result = null;
    String actualMessage = null;
    
    try {
       result = workload.get();
    } catch(Exception e) {
      exceptionThrown = true;
      actualExceptionClass = e.getClass();
      actualMessage = e.getMessage();
    }
   
    assertThat("Expected " + expectedExceptionClass.getName() + " but no exception thrown", exceptionThrown);
    assertThat(actualExceptionClass, equalTo(expectedExceptionClass));
    
    if(expectedMessage != null) {
      assertThat(actualMessage, equalTo(expectedMessage));
    }
    
    return result;
  }
  
  public static <T> T verify(Supplier<T> workload, Class<? extends Exception> expectedExceptionClass) {
    return verifyWithExactMessage(workload, expectedExceptionClass, null);
  }
}

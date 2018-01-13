package com.clubdynamics.core.testutil;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * Verifies validation achieved by using javax.validation annotations. 
 * 
 * @author Henning Schütz
 *
 */
public class VerifyValidation {

  private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
  
  public static <T> void assertValid(Collection<T> items) {
    items.forEach(item -> {
      Set<ConstraintViolation<T>> violations = validator.validate(item);
      assertThat(unexpectedViolationMessage(violations), violations.isEmpty());
    });
  }
  
  public static <T> void assertInvalid(Collection<T> items, String property) {
    items.forEach(item -> {
      Set<ConstraintViolation<T>> violations = validator.validate(item);
      assertThat(noViolationFoundMessage(item, property), !violations.isEmpty());
      boolean rightPropertyViolated = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals(property));
      assertThat(noMatchingViolationFoundMessage(violations, item, property), rightPropertyViolated);
    });
  }
  
  private static <T>String unexpectedViolationMessage(Set<ConstraintViolation<T>> violations) {
    return "Unexpected violations: " + violations
        .stream()
        .map(v -> v.getMessage() + " for property \"" + v.getPropertyPath() + "\" in object " + v.getRootBean())
        .collect(Collectors.joining("; "));
  }
  
  private static <T>String noViolationFoundMessage(T item, String property) {
    return "No violations found but expected one for property \"" + property + "\" in object " + item;
  }
  
  private static <T>String noMatchingViolationFoundMessage(Set<ConstraintViolation<T>> violations, T item, String property) {
    return "No violation found that matches property \"" 
        + property 
        + "\" in object " 
        + item + ", only found violations for: "
        + violations.stream().map(v -> v.getPropertyPath().toString()).collect(Collectors.joining("; "));
  }
}

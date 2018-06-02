package com.clubdynamics.core.testutil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Takes two objects of same class and verifies field by field whether they are equal no matter what the equals() method says.
 * Uses reflection so it's not superfast but fast enough for tests.
 * 
 * @author Henning Schütz
 *
 */
public class AssertAllFieldsEqual {

  public static <T> void assertEqual(T obj0, T obj1) {
    getAllFields(obj0.getClass()).forEach(f -> {
      f.setAccessible(true);
      
      try {
        Object value0 = f.get(obj0);
        Object value1 = f.get(obj1);
        assertThat(fieldError(f.getName(), value0, value1), value0, equalTo(value1));
        System.out.println(fieldSuccess(f.getName(), value0));
      } catch(Exception e) {
        throw new RuntimeException("Problem with reflection during validation in test: " + e.getMessage());
      }
    });
  }
  
  private static String fieldError(String fieldName, Object value0, Object value1) {
    return String.format("For '%s', one object has value '%s' while the other one has '%s'", fieldName, value0, value1);
  }
  
  private static String fieldSuccess(String fieldName, Object value) {
    return String.format("For '%s', both objects have value '%s'", fieldName, value);
  }
  
  private static List<Field> getAllFields(Class<?> clazz) {
    List<Field> fields = new ArrayList<>();
    Arrays.stream(clazz.getDeclaredFields()).forEach(f -> fields.add(f));
    
    if(clazz.equals(Object.class)) {
      throw new RuntimeException("Cannot check two instances of type java.lang.Object!");
    }
    
    Class<?> superClazz = clazz.getSuperclass(); 
    
    if(superClazz != null && !superClazz.equals(Object.class)) {
      fields.addAll(getAllFields(superClazz));
    }
    
    return fields;
  }
}

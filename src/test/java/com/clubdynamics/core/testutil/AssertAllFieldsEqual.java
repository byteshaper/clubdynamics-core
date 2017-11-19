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
        assertThat(f.get(obj0), equalTo(f.get(obj1)));
        System.out.println("Validated equality of field " + f.getName());
      } catch(Exception e) {
        throw new RuntimeException("Problem with reflection during validation in test: " + e.getMessage());
      }
    });
  }
  
  private static List<Field> getAllFields(Class<?> clazz) {
    List<Field> fields = new ArrayList<>();
    Arrays.stream(clazz.getDeclaredFields()).forEach(f -> fields.add(f));
    
    if(clazz.equals(Object.class)) {
      throw new RuntimeException("Cannot check to instances of type java.lang.Object!");
    }
    
    Class<?> superClazz = clazz.getSuperclass(); 
    
    if(superClazz != null && !superClazz.equals(Object.class)) {
      fields.addAll(getAllFields(superClazz));
    }
    
    return fields;
  }
}

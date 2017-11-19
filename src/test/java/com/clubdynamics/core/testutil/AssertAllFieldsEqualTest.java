package com.clubdynamics.core.testutil;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class AssertAllFieldsEqualTest {
  
  @SuppressWarnings("unused")
  private static class ParentClass {
    private String text;
    
    public int number;
    
    protected List<String> list; 
  }
  
  @SuppressWarnings("unused")
  private static class ChildClass extends ParentClass {
    int anotherNumber;
    public String anotherText;
  }
  
  @Test
  public void parentClassEqual() {
    ParentClass pc0 = new ParentClass();
    ParentClass pc1 = new ParentClass();
    pc0.text = "bla";
    pc1.text = "bla";
    pc0.number = 123;
    pc1.number = 123;
    pc0.list = Arrays.asList("a", "c", "b");
    pc1.list = Arrays.asList("a", "c", "b");
    AssertAllFieldsEqual.assertEqual(pc0, pc1);
  }
  
  @Test(expected = AssertionError.class)
  public void parentClassListUnequal() {
    ParentClass pc0 = new ParentClass();
    ParentClass pc1 = new ParentClass();
    pc0.text = "bla";
    pc1.text = "bla";
    pc0.number = 123;
    pc1.number = 123;
    pc0.list = Arrays.asList("a", "c", "b");
    pc1.list = Arrays.asList("a", "b", "c");
    AssertAllFieldsEqual.assertEqual(pc0, pc1);
  }
  
  @Test(expected = AssertionError.class)
  public void parentClassNumberUnequal() {
    ParentClass pc0 = new ParentClass();
    ParentClass pc1 = new ParentClass();
    pc0.text = "bla";
    pc1.text = "bla";
    pc0.number = 12;
    pc1.number = 123;
    pc0.list = Arrays.asList("a", "c", "b");
    pc1.list = Arrays.asList("a", "c", "b");
    AssertAllFieldsEqual.assertEqual(pc0, pc1);
  }
  
  @Test(expected = AssertionError.class)
  public void parentClassTextUnequal() {
    ParentClass pc0 = new ParentClass();
    ParentClass pc1 = new ParentClass();
    pc0.text = "bla";
    pc1.text = "blabla";
    pc0.number = 123;
    pc1.number = 123;
    pc0.list = Arrays.asList("a", "c", "b");
    pc1.list = Arrays.asList("a", "c", "b");
    AssertAllFieldsEqual.assertEqual(pc0, pc1);
  }
  
  @Test
  public void childClassEqual() {
    ChildClass cc0 = new ChildClass();
    ChildClass cc1 = new ChildClass();
    cc0.number = 123;
    cc1.number = 123;
    cc0.list = Arrays.asList("a", "c", "b");
    cc1.list = Arrays.asList("a", "c", "b");
    cc0.anotherNumber = 1523;
    cc1.anotherNumber = 1523;
    cc0.anotherText = "aölsekrfjösd";
    cc1.anotherText = "aölsekrfjösd";
    AssertAllFieldsEqual.assertEqual(cc0, cc1);
  }
  
  @Test(expected = AssertionError.class)
  public void childClassAnotherNumberUnequal() {
    ChildClass cc0 = new ChildClass();
    ChildClass cc1 = new ChildClass();
    cc0.number = 123;
    cc1.number = 123;
    cc0.list = Arrays.asList("a", "c", "b");
    cc1.list = Arrays.asList("a", "c", "b");
    cc0.anotherNumber = 15123;
    cc1.anotherNumber = 1523;
    cc0.anotherText = "aölsekrfjösd";
    cc1.anotherText = "aölsekrfjösd";
    AssertAllFieldsEqual.assertEqual(cc0, cc1);
  }
  
  @Test(expected = AssertionError.class)
  public void childClassAnotherTextUnequal() {
    ChildClass cc0 = new ChildClass();
    ChildClass cc1 = new ChildClass();
    cc0.number = 123;
    cc1.number = 123;
    cc0.list = Arrays.asList("a", "c", "b");
    cc1.list = Arrays.asList("a", "c", "b");
    cc0.anotherNumber = 1523;
    cc1.anotherNumber = 1523;
    cc0.anotherText = "aölsekrf2234234jösd";
    cc1.anotherText = "aölsekrfjösd";
    AssertAllFieldsEqual.assertEqual(cc0, cc1);
  }
  
  @Test(expected = AssertionError.class)
  public void childClassSuperClassNumberUnequal() {
    ChildClass cc0 = new ChildClass();
    ChildClass cc1 = new ChildClass();
    cc0.number = 123;
    cc1.number = 1234;
    cc0.list = Arrays.asList("a", "c", "b");
    cc1.list = Arrays.asList("a", "c", "b");
    cc0.anotherNumber = 1523;
    cc1.anotherNumber = 1523;
    cc0.anotherText = "aölsekrfjösd";
    cc1.anotherText = "aölsekrfjösd";
    AssertAllFieldsEqual.assertEqual(cc0, cc1);
  }
}


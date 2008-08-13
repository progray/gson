package com.google.gson;

import com.google.gson.reflect.TypeToken;

import junit.framework.TestCase;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Small test for the {@link TypeInfoArray}.
 *
 * @author Inderjeet Singh
 * @author Joel Leitch
 */
public class TypeInfoArrayTest extends TestCase {

  public void testArray() {
    String[] a = {"a", "b", "c"};
    TypeInfoArray typeInfo = new TypeInfoArray(a.getClass());
    assertEquals(a.getClass(), typeInfo.getRawClass());
    assertEquals(String.class, typeInfo.getComponentRawType());
  }

  public void testArrayOfArrays() {
    String[][] a = {
        new String[]{"a1", "a2", "a3"},
        new String[]{"b1", "b2", "b3"},
        new String[]{"c1", "c2", "c3"}};
    TypeInfoArray typeInfo = new TypeInfoArray(a.getClass());
    assertEquals(a.getClass(), typeInfo.getRawClass());
    assertEquals(String.class, typeInfo.getComponentRawType());
    assertEquals(String[].class, typeInfo.getSecondLevelClass());
  }

  public void testParameterizedArray() {
    Type type = new TypeToken<List<String>[]>() {}.getType();
    TypeInfoArray typeInfo = new TypeInfoArray(type);
    assertEquals(List[].class, typeInfo.getRawClass());
    assertEquals(List.class, typeInfo.getComponentRawType());
  }

  public void testParameterizedArrayOfArrays() {
    Type type = new TypeToken<List<String>[][]>() {}.getType();
    TypeInfoArray typeInfo = new TypeInfoArray(type);
    assertEquals(List[][].class, typeInfo.getRawClass());
    assertEquals(List[].class, typeInfo.getSecondLevelClass());
    assertEquals(List.class, typeInfo.getComponentRawType());
  }

  public void testNestedParameterizedArray() {
    Type type = new TypeToken<List<List<String>>[]>() {}.getType();
    TypeInfoArray typeInfo = new TypeInfoArray(type);
    assertEquals(List[].class, typeInfo.getRawClass());
    assertEquals(List.class, typeInfo.getComponentRawType());
    assertEquals(List.class, typeInfo.getSecondLevelClass());
  }

  public void testPrimitiveArray() throws Exception {
    TypeInfoArray arrayTypeInfo = new TypeInfoArray(int[].class);

    assertTrue(arrayTypeInfo.isArray());
    assertEquals(int.class, arrayTypeInfo.getSecondLevelClass());
    assertFalse(arrayTypeInfo.isPrimitiveOrStringAndNotAnArray());
  }

  public void testStringArray() throws Exception {
    TypeInfoArray arrayTypeInfo = new TypeInfoArray(String[].class);

    assertTrue(arrayTypeInfo.isArray());
    assertEquals(String.class, arrayTypeInfo.getSecondLevelClass());
    assertEquals(String[].class, arrayTypeInfo.getRawClass());
  }

  public void testPrimitiveArrayType() throws Exception {
    TypeInfoArray typeInfo = new TypeInfoArray(long[].class);
    assertTrue(typeInfo.isArray());
    assertEquals(long.class, typeInfo.getSecondLevelClass());
    assertEquals(long[].class, typeInfo.getRawClass());
  }

  public void testStringArrayType() throws Exception {
    TypeInfoArray typeInfo = new TypeInfoArray(String[].class);
    assertTrue(typeInfo.isArray());
    assertEquals(String[].class, typeInfo.getRawClass());
    assertEquals(String.class, typeInfo.getSecondLevelClass());
  }

  public void testArrayAsParameterizedTypes() throws Exception {
    Type type = new TypeToken<List<String>[]>() {}.getType();

    TypeInfoArray typeInfo = new TypeInfoArray(type);
    assertTrue(typeInfo.isArray());
    assertEquals(List[].class, typeInfo.getRawClass());
    assertEquals(List.class, typeInfo.getSecondLevelClass());

    Type actualType = typeInfo.getActualType();
    assertEquals(type, actualType);
    Type actualTypeForFirstTypeVariable = TypeUtils.getActualTypeForFirstTypeVariable(actualType);
    assertEquals(String.class, actualTypeForFirstTypeVariable);
  }
}

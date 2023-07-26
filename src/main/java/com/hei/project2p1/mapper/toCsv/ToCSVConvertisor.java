package com.hei.project2p1.mapper.toCsv;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ToCSVConvertisor {

  public static <T> String ObjectListToCsv(List<T> list) {
    if (list == null || list.isEmpty()) {
      throw new IllegalArgumentException("List must not be null or empty.");
    }

    StringBuilder csvData = new StringBuilder();

    csvData.append(ObjectToCsv(list.get(0), true)).append("\n");

    list.forEach(object -> {
      String row = ObjectToCsv(object, false);
      csvData.append(row).append("\n");
    });

    return csvData.toString();
  }

  public static String ObjectToCsv(Object object, boolean includeHeader) {
    Class<?> clazz = object.getClass();
    Field[] fields = clazz.getDeclaredFields();
    List<Field> fieldList = Arrays.asList(fields);
    StringBuilder csvData = new StringBuilder();

    if (includeHeader) {
      fieldList.forEach(field -> csvData.append(field.getName()).append(","));
      csvData.deleteCharAt(csvData.length() - 1);
      csvData.append("\n");
    }

    fieldList.forEach(field -> {
      field.setAccessible(true);
      try {
        csvData.append(field.get(object)).append(",");
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    });

    csvData.deleteCharAt(csvData.length() - 1);
    return csvData.toString();
  }
}

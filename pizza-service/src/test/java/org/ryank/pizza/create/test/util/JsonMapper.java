package org.ryank.pizza.create.test.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {

  public static String objectToJson(final Object obj) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
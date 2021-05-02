package io.github.xanderwang.notnullgson;

public class SimpleNullParser implements NullParser {
  @Override
  public String nullString() {
    return "";
  }

  @Override
  public Integer nullInteger() {
    return 0;
  }


  @Override
  public Long nullLong() {
    return 0L;
  }


  @Override
  public Float nullFloat() {
    return 0F;
  }


  @Override
  public Boolean nullBoolean(String value) {
    if (null == value) {
      return Boolean.FALSE;
    }
    if ("true".equalsIgnoreCase(value)) {
      return Boolean.TRUE;
    } else if ("false".equalsIgnoreCase(value)) {
      return Boolean.FALSE;
    } else {
      try {
        return Long.parseLong(value) > 0L;
      } catch (Exception e) {
        // e.printStackTrace();
      }
      try {
        return Float.parseFloat(value) > 0F;
      } catch (Exception e) {
        // e.printStackTrace();
      }
    }
    return Boolean.FALSE;
  }
}

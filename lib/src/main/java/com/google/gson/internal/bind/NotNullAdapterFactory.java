package com.google.gson.internal.bind;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import io.github.xanderwang.notnullgson.NullParser;

public class NotNullAdapterFactory implements TypeAdapterFactory {

  NullParser nullParser;

  public NotNullAdapterFactory(NullParser nullParser) {
    this.nullParser = nullParser;
  }

  @Override
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
    BaseTypeAdapter<T> baseTypeAdapter = null;
    Class<T> rawType = (Class<T>) type.getRawType();
    if (rawType == String.class) {
      baseTypeAdapter = (BaseTypeAdapter<T>) new NotNullStringAdapter();
    } else if (rawType == Integer.class) {
      baseTypeAdapter = (BaseTypeAdapter<T>) new NotNullIntegerAdapter();
    } else if (rawType == Boolean.class) {
      baseTypeAdapter = (BaseTypeAdapter<T>) new NotNullBooleanAdapter();
    } else if (rawType == Long.class) {
      baseTypeAdapter = (BaseTypeAdapter<T>) new NotNullLongAdapter();
    } else if (rawType == Float.class) {
      baseTypeAdapter = (BaseTypeAdapter<T>) new NotNullFloatAdapter();
    }
    if (null != baseTypeAdapter) {
      baseTypeAdapter.nullParser = nullParser;
    }
    return baseTypeAdapter;
  }

}

abstract class BaseTypeAdapter<T> extends TypeAdapter<T> {

  NullParser nullParser;

  public BaseTypeAdapter() {

  }

}

class NotNullStringAdapter extends BaseTypeAdapter<String> {

  @Override
  public void write(JsonWriter out, String value) throws IOException {
    if (value == null) {
      // out.nullValue();
      out.value("");
      return;
    }
    out.value(value);
  }

  @Override
  public String read(JsonReader in) throws IOException {
    if (in.peek() == JsonToken.NULL) {
      in.nextNull();
      if (null != nullParser) {
        nullParser.nullString();
      }
      return "";
    }
    return in.nextString();
  }

}

class NotNullBooleanAdapter extends BaseTypeAdapter<Boolean> {

  @Override
  public void write(JsonWriter out, Boolean value) throws IOException {
    if (value == null) {
      // out.nullValue();
      out.value(false);
      return;
    }
    out.value(value);
  }

  @Override
  public Boolean read(JsonReader in) throws IOException {
    JsonToken next = in.peek();
    if (next == JsonToken.NULL) {
      in.nextNull();
      if (null != nullParser) {
        return nullParser.nullBoolean(null);
      }
      return Boolean.FALSE;
    }
    if (next == JsonToken.NUMBER || next == JsonToken.STRING) {
      String v = in.nextString().trim();
      if (null != nullParser) {
        return nullParser.nullBoolean(v);
      }
      if ("true".equalsIgnoreCase(v)) {
        return true;
      } else if ("false".equalsIgnoreCase(v)) {
        return false;
      }
      try {
        return Long.parseLong(v) > 0L;
      } catch (Exception e) {
        // e.printStackTrace();
      }
      try {
        return Float.parseFloat(v) > 0F;
      } catch (Exception e) {
        // e.printStackTrace();
      }
      return false;
    }
    return in.nextBoolean();
  }

}

class NotNullIntegerAdapter extends BaseTypeAdapter<Integer> {

  @Override
  public void write(JsonWriter out, Integer value) throws IOException {
    if (value == null) {
      out.nullValue();
      return;
    }
    out.value(value);
  }

  @Override
  public Integer read(JsonReader in) throws IOException {
    if (in.peek() == JsonToken.NULL) {
      in.nextNull();
      if (null != nullParser) {
        return nullParser.nullInteger();
      }
      return 0;
    }
    return in.nextInt();
  }

}

class NotNullLongAdapter extends BaseTypeAdapter<Long> {

  @Override
  public void write(JsonWriter out, Long value) throws IOException {
    if (value == null) {
      out.nullValue();
      return;
    }
    out.value(value);
  }

  @Override
  public Long read(JsonReader in) throws IOException {
    if (in.peek() == JsonToken.NULL) {
      in.nextNull();
      if (null != nullParser) {
        return nullParser.nullLong();
      }
      return 0L;
    }
    return in.nextLong();
  }
}

class NotNullFloatAdapter extends BaseTypeAdapter<Float> {

  @Override
  public void write(JsonWriter out, Float value) throws IOException {
    if (value == null) {
      out.nullValue();
      return;
    }
    out.value(value);
  }

  @Override
  public Float read(JsonReader in) throws IOException {
    if (in.peek() == JsonToken.NULL) {
      in.nextNull();
      if (null != nullParser) {
        return nullParser.nullFloat();
      }
      return 0F;
    }
    return Float.parseFloat(in.nextString());
  }
}
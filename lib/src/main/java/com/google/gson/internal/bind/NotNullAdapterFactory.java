package com.google.gson.internal.bind;

import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import io.github.xanderwang.notnullgson.NullListener;

public class NotNullAdapterFactory implements TypeAdapterFactory {

  @Override
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
    Class<T> rawType = (Class<T>) type.getRawType();
    if (rawType == String.class) {
      return (TypeAdapter<T>) new NotNullStringAdapter();
    } else if (rawType == Integer.class) {
      return (TypeAdapter<T>) new NotNullIntegerAdapter();
    } else if (rawType == Boolean.class) {
      return (TypeAdapter<T>) new NotNullBooleanAdapter();
    } else if (rawType == Long.class) {
      return (TypeAdapter<T>) new NotNullLongAdapter();
    }
    return null;
  }

}

abstract class BaseTypeAdapter<T> extends TypeAdapter<T> {

  NullListener nullListener;

  public BaseTypeAdapter() {

  }

}

class NotNullStringAdapter extends BaseTypeAdapter<String> {

  @Override
  public void write(JsonWriter out, String value) throws IOException {
    if (value == null) {
      out.nullValue();
      return;
    }
    out.value(value);
  }

  @Override
  public String read(JsonReader in) throws IOException {
    if (in.peek() == JsonToken.NULL) {
      in.nextNull();
      return "";
    }
    return in.nextString();
  }

}

class NotNullBooleanAdapter extends TypeAdapter<Boolean> {

  @Override
  public void write(JsonWriter out, Boolean value) throws IOException {
    if (value == null) {
      out.nullValue();
      return;
    }
    out.value(value);
  }

  @Override
  public Boolean read(JsonReader in) throws IOException {
    JsonToken next = in.peek();
    if (next == JsonToken.NULL) {
      in.nextNull();
      return Boolean.FALSE;
    }
    if (next == JsonToken.NUMBER) {
      return in.nextDouble() > 0;
    }
    if (next == JsonToken.STRING) {
      return Integer.parseInt(in.nextString()) > 0;
    }
    return in.nextBoolean();
  }

}

class NotNullIntegerAdapter extends TypeAdapter<Integer> {

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
      return 0;
    }
    return in.nextInt();
  }

}

class NotNullLongAdapter extends TypeAdapter<Long> {

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
      return 0L;
    }
    return in.nextLong();
  }
}

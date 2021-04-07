package com.google.gson.internal.bind;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.internal.$Gson$Types;

public class NotNullArrayAdapterFactory implements TypeAdapterFactory {

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
    Type type = typeToken.getType();
    if (!(type instanceof GenericArrayType || type instanceof Class && ((Class<?>) type)
        .isArray())) {
      return null;
    }

    Type componentType = $Gson$Types.getArrayComponentType(type);
    TypeAdapter<?> componentTypeAdapter = gson.getAdapter(TypeToken.get(componentType));
    return new NotNullArrayTypeAdapter(gson, componentTypeAdapter,
        $Gson$Types.getRawType(componentType));
  }
}

/**
 * Adapt an array of objects.
 */
class NotNullArrayTypeAdapter<E> extends TypeAdapter<Object> {

  private final Class<E> componentType;
  private final TypeAdapter<E> componentTypeAdapter;

  public NotNullArrayTypeAdapter(Gson context, TypeAdapter<E> componentTypeAdapter,
      Class<E> componentType) {
    this.componentTypeAdapter = new TypeAdapterRuntimeTypeWrapper<E>(context, componentTypeAdapter,
        componentType);
    this.componentType = componentType;
  }

  @Override
  public Object read(JsonReader in) throws IOException {
    if (in.peek() == JsonToken.NULL) {
      in.nextNull();
      return Array.newInstance(componentType, 0);
    }

    List<E> list = new ArrayList<E>();
    in.beginArray();
    while (in.hasNext()) {
      E instance = componentTypeAdapter.read(in);
      list.add(instance);
    }
    in.endArray();

    int size = list.size();
    Object array = Array.newInstance(componentType, size);
    for (int i = 0; i < size; i++) {
      Array.set(array, i, list.get(i));
    }
    return array;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void write(JsonWriter out, Object array) throws IOException {
    if (array == null) {
      out.nullValue();
      return;
    }
    out.beginArray();
    for (int i = 0, length = Array.getLength(array); i < length; i++) {
      E value = (E) Array.get(array, i);
      componentTypeAdapter.write(out, value);
    }
    out.endArray();
  }
}

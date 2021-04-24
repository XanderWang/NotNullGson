package io.github.xanderwang.notnullgson;

public interface NullListener {

  String nullString();

  Integer nullInteger();

  Integer nullInteger(String value);

  Long nullLong();

  Float nullFloat();

  Boolean nullBoolean();

  Boolean nullBoolean(Double value);

  Boolean nullBoolean(String value);
}

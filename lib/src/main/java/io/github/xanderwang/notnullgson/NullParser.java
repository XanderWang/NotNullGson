package io.github.xanderwang.notnullgson;

public interface NullParser {

  String nullString();

  Integer nullInteger();

  Long nullLong();

  Float nullFloat();

  Boolean nullBoolean(String value);
}

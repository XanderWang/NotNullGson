package io.github.xanderwang.notnullgson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;

/**
 * @author Xander Wang Created on 2021/4/7.
 * @Description //TODO
 */
public class NotNullGsonTest {

  public static void main(String[] args) {
    GsonBuilder builder = NotNullGsonBuilder.builder();
    Gson gson = builder.create();
    String jsonStr = "{\"ii\":1.3,\"ll\":null,\"name\":null,\"addrs\":null}";
    Demo newDemo = gson.fromJson(jsonStr, Demo.class);
    System.out.println(newDemo);
  }

  public static class Demo {

    Boolean ii;
    Long ll = 1L;
    String name;
    List<String> addrs;

    @Override
    public String toString() {
      return "Demo{" +
          "ii=" + ii +
          ", ll=" + ll +
          ", name='" + name + '\'' +
          ", addrs=" + addrs +
          '}';
    }
  }

}

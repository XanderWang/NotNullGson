package io.github.xanderwang.notnullgson;

public class NullValue {
  private String  defStr    = "";
  private Integer defInt    = 0;
  private Long    defLong   = 0L;
  private Float   defFloat  = 0F;
  private Double  defDouble = 0D;
  // private

  public String getDefStr() {
    return defStr;
  }

  public void setDefStr(String defStr) {
    if (null == defStr) {
      return;
    }
    this.defStr = defStr;
  }

  public Integer getDefInt() {
    return defInt;
  }

  public void setDefInt(Integer defInt) {
    if (null == defInt) {
      return;
    }
    this.defInt = defInt;
  }

  public Long getDefLong() {
    return defLong;
  }

  public void setDefLong(Long defLong) {
    if (null == defLong) {
      return;
    }
    this.defLong = defLong;
  }

  public Float getDefFloat() {
    return defFloat;
  }

  public void setDefFloat(Float defFloat) {
    if (null == defFloat) {
      return;
    }
    this.defFloat = defFloat;
  }

  public Double getDefDouble() {
    return defDouble;
  }

  public void setDefDouble(Double defDouble) {
    if (null == defDouble) {
      return;
    }
    this.defDouble = defDouble;
  }
}

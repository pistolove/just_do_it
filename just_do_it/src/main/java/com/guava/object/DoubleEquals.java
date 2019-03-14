package com.guava.object;

import com.google.common.base.Objects;

public class DoubleEquals {

  public static void main(String[] args
  ) {
    Double a = 1d;
    Double b = 1D;
    System.out.println(Objects.equal(a, b));
  }
}

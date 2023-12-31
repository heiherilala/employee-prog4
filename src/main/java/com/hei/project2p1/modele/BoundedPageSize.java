package com.hei.project2p1.modele;

import lombok.EqualsAndHashCode;
import lombok.Getter;
@EqualsAndHashCode
public class BoundedPageSize {

  @Getter
  private final int value;

  private static final int MAX_SIZE = 500;

  public BoundedPageSize(int value) {
    if (value > MAX_SIZE) {
      throw new RuntimeException("page size must be <" + MAX_SIZE);
    }
    this.value = value;
  }
}

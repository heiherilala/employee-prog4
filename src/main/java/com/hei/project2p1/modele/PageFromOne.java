package com.hei.project2p1.modele;

import lombok.Getter;

public class PageFromOne {

  @Getter
  private final int value;

  public PageFromOne(int value) {
    if (value < 1) {
      throw new RuntimeException("page value must be >=1");
    }
    this.value = value;
  }
}

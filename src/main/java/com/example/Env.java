package com.example;

import java.util.Map;

public class Env {
  private final Map<String, Expr> holder;

  public Env(Map<String, Expr> holder) {
    this.holder = holder;
  }

  public Expr get(String name) {
    return holder.get(name);
  }
}

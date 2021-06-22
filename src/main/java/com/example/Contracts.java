package com.example;

public class Contracts {
  public static void requires(boolean truthy, String message) {
    if (truthy) {
      return;
    }
    throw new ContractViolationException(message);
  }
}

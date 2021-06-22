package com.example;

public class ContractViolationException extends RuntimeException {
  public ContractViolationException(String message) {
    super(message);
  }
}

package com.example;

import static com.example.ExprFactory.*;

public interface OperationMixin {
  Expr that();

  default Expr plus(Expr expr) {
    return sum(that(), expr);
  }

  default Expr ne() {
    return ExprFactory.ne(that());
  }

  default Expr pow(long power) {
    return ExprFactory.pow(that(), power);
  }

  default Expr mul(Expr expr) {
    return ExprFactory.mul(that(), expr);
  }
}

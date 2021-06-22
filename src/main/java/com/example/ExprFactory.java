package com.example;

public class ExprFactory {
  public static Expr var(String name) {
    return VarExpr.var(name);
  }

  public static Expr ne(Expr expr) {
    return new NegExpr(expr);
  }

  public static Expr con(long value) {
    return new ConstExpr(value);
  }

  public static Expr sum(Expr left, Expr right) {
    return new SumExpr(left, right);
  }

  public static Expr mul(Expr left, Expr right) {
    return new MulExpr(left, right);
  }

  public static Expr pow(Expr expr, long power) {
    return new PowerExpr(expr, power);
  }
}

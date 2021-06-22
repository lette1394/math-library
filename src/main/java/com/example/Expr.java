package com.example;

import static com.example.Contracts.requires;
import static java.lang.String.format;

public sealed interface Expr extends OperationMixin
  permits ConstExpr,
          SumExpr,
          MulExpr,
          PowerExpr,
          NegExpr,
          VarExpr {
  @Override
  default Expr that() {
    return this;
  }
}



final record ConstExpr(long value) implements Expr {
  @Override
  public String toString() {
    return Long.toString(value);
  }
}

final record SumExpr(Expr left, Expr right) implements Expr {
  @Override
  public String toString() {
    return format("(%s + %s)", left.toString(), right.toString());
  }
}

final record MulExpr(Expr left, Expr right) implements Expr {
  @Override
  public String toString() {
    return format("(%s * %s)", left.toString(), right.toString());
  }
}

final record NegExpr(Expr expr) implements Expr {
  @Override
  public String toString() {
    return format("(-%s)", expr.toString());
  }
}

final record PowerExpr(Expr expr, long power) implements Expr {
  PowerExpr {
    requires(power > 0, "power > 0");
  }

  public PowerExpr(Expr expr) {
    this(expr, 1);
  }

  @Override
  public String toString() {
    if (power == 1) {
      return expr.toString();
    }
    return format("(%s^%s)", expr.toString(), power);
  }
}

final class VarExpr implements Expr {
  private final String name;

  private VarExpr(String name) {
    this.name = name;
  }

  public String varName() {
    return name;
  }

  public static Expr var(String name) {
    return new PowerExpr(new VarExpr(name));
  }

  @Override
  public String toString() {
    return format("%s", name);
  }
}

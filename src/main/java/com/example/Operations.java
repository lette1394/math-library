package com.example;

import static com.example.ExprFactory.*;
import static com.example.ExprFactory.pow;
import static java.lang.Math.negateExact;
import static java.lang.Math.pow;

public class Operations {
  public static long eval(Expr expr, Env env) {
    if (expr instanceof ConstExpr e) {
      return e.value();
    }
    if (expr instanceof SumExpr e) {
      return eval(e.left(), env) + eval(e.right(), env);
    }
    if (expr instanceof MulExpr e) {
      return eval(e.left(), env) * eval(e.right(), env);
    }
    if (expr instanceof NegExpr e) {
      return negateExact(eval(e.expr(), env));
    }
    if (expr instanceof PowerExpr e) {
      return (long) pow(eval(e.expr(), env), e.power());
    }
    if (expr instanceof VarExpr e) {
      final String name = e.varName();
      final Expr var = env.get(name);
      return eval(var, env);
    }
    throw new IllegalArgumentException(expr.toString());
  }

  public static Expr derive(Expr expr) {
    if (expr instanceof ConstExpr) {
      return con(0);
    }
    if (expr instanceof SumExpr e) {
      return sum(derive(e.left()), derive(e.right()));
    }
    if (expr instanceof MulExpr e) {
      final Expr left = mul(derive(e.left()), e.right());
      final Expr right = mul(e.left(), derive(e.right()));
      return sum(left, right);
    }
    if (expr instanceof NegExpr e) {
      return ne(derive(e.expr()));
    }
    if (expr instanceof PowerExpr e) {
      final long power = e.power();
      if (power == 1) {
        return con(1);
      }
      return mul(con(power),
        mul(pow(e.expr(), power - 1),
        derive(e.expr())));
    }
    if (expr instanceof VarExpr e) {
      return e;
    }
    throw new IllegalArgumentException(expr.toString());
  }
}

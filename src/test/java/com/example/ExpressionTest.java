package com.example;

import static com.example.ExprFactory.con;
import static com.example.ExprFactory.mul;
import static com.example.ExprFactory.ne;
import static com.example.ExprFactory.pow;
import static com.example.ExprFactory.sum;
import static com.example.ExprFactory.var;
import static com.example.Operations.derive;
import static com.example.Operations.eval;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.scala.OperationsScala;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ExpressionTest {
  @Test
  void test1() {
    final Map<String, Expr> map = Map.of(
      "x", ne(var("y")),
      "y", con(200)
    );
    final Env env = new Env(map);
    final Expr expression = sum(
      sum(con(1), con(5)),
      var("x"));

    assertEquals(-194, eval(expression, env));
  }

  @Test
  void test2() {
    final Map<String, Expr> map = Map.of(
      "x", con(3)
    );
    final Env env = new Env(map);
    final Expr expression = pow(sum(
      mul(con(2), pow(pow(var("x"), 1), 2)),
      con(1)
    ), 3);

    final Expr x = (con(2).mul(var("x").pow(2)).plus(con(1))).pow(3);
    assertEquals(12996, eval(derive(expression), env));
    assertEquals(12996, eval(derive(x), env));
  }

  @Test
  void test3() {
    final Map<String, Expr> map = Map.of(
      "x", con(3)
    );
    final Env env = new Env(map);

    // (2x^2+1)^3
    final Expr expression = pow(sum(
      mul(con(2), pow(pow(var("x"), 1), 2)),
      con(1)
    ), 3);

    // (2x^2+1)^3
    final var x = (con(2).mul(var("x").pow(2)).plus(con(1))).pow(3);

    assertEquals(12996, eval(derive(expression), env));
    assertEquals(12996, eval(derive(x), env));
  }
}
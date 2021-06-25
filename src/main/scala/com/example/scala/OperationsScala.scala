package com.example.scala

import scala.language.implicitConversions

object OperationsScala {
  def format(expr: Expr)(implicit env: Env): String = {
    expr match {
      case ConstExpr(e) => e.toString

      case SumExpr(VarExpr(left), VarExpr(right)) if left.equals(right) => s"2$left"
      case SumExpr(left, right) =>
        if (eval(left) == 0) {
          return format(right)
        }
        if (eval(right) == 0) {
          return format(left)
        }
        s"${format(left)}+${format(right)}"

      case MulExpr(left: VarExpr, right: ConstExpr) => s"${format(right)}${format(left)}"
      case MulExpr(left: ConstExpr, right: VarExpr) => s"${format(left)}${format(right)}"
      case MulExpr(left, right) =>
        if (eval(left) == 1) {
          return format(right)
        }
        if (eval(right) == 1) {
          return format(left)
        }
        s"${format(left)}*${format(right)}"

      case NegExpr(e) => s"-${format(e)}"
      case PowerExpr(e, power) =>
        if (eval(power) == 1) {
          return format(e)
        }
        s"(${format(e)}^$power)"

      case VarExpr(name) => name
    }
  }

  def eval(expr: Expr)(implicit env: Env): Long = {
    expr match {
      case ConstExpr(e) => e
      case SumExpr(left, right) => eval(left) + eval(right)
      case MulExpr(left, right) => eval(left) * eval(right)
      case NegExpr(e) => -eval(e)
      case PowerExpr(e, power) => Math.pow(eval(e), eval(power)).toLong
      case VarExpr(name) => eval(env.get(name))
    }
  }

  def derive(expr: Expr)(implicit env: Env): Expr = {
    expr match {
      case ConstExpr(_) => ConstExpr(0)
      case SumExpr(l, r) => SumExpr(derive(l), derive(r))
      case MulExpr(l, r) => SumExpr(MulExpr(derive(l), r), MulExpr(l, derive(r)))
      case NegExpr(e) => NegExpr(derive(e))
      case PowerExpr(e, p) =>
        val power = eval(p)
        if (power == 1) {
          return ConstExpr(1)
        }
        MulExpr(
          ConstExpr(power),
          MulExpr(
            PowerExpr(e, ConstExpr(power - 1)),
            derive(e)))
      case VarExpr(_) => expr
    }
  }
}

trait ExprOps {
  this: Expr =>

  def +(that: Expr): Expr = {
    (this, that) match {
      case (_: VarExpr, _: VarExpr) => MulExpr(ConstExpr(2), this)
      case (l, r) => SumExpr(l, r)
    }
  }

  def *(that: Expr): Expr = {
    (this, that) match {
      case (v: VarExpr, c: ConstExpr) => MulExpr(c, v)
      case (l, r) => MulExpr(l, r)
    }
  }

  def ^(that: Expr): Expr = {
    (this, that) match {
      case (l, r) => PowerExpr(l, r)
    }
  }
}

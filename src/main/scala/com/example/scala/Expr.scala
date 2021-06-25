package com.example.scala

import com.example.scala.OperationsScala._

import scala.Console.println
import scala.Predef.{any2stringadd => _}
import scala.collection.immutable.Map
import scala.language.implicitConversions

class Env(private var map: Map[String, Expr]) {
  def get(name: String): Expr = {
    map(name)
  }
  override def toString: String = map.toString
}

sealed class Expr extends ExprOps {

}

case class ConstExpr(value: Long) extends Expr {
  override def toString: String = value.toString
}
case class SumExpr(left: Expr, right: Expr) extends Expr {
  override def toString: String = s"$left+$right"
}
case class MulExpr(left: Expr, right: Expr) extends Expr {
  override def toString: String = s"$left$right"
}
case class NegExpr(expr: Expr) extends Expr {
  override def toString: String = s"-$expr"
}
case class PowerExpr(expr: Expr, power: Expr) extends Expr {
  override def toString: String = s"($expr^$power)"
}
case class VarExpr(name: String) extends Expr {
  // 단독 사용금지. 항상 power로 감싸줘야 하는데.. 이걸 어떻게 하지?
  // 하위클래스인데 음...
  override def toString: String = name
}
case class NilExpr() extends Expr {
  override def +(that: Expr): Expr = that
}

object Main {
  def main(args: Array[String]): Unit = {
    implicit def long2Const(value: Long): Expr = ConstExpr(value)
    implicit def string2Var(value: String): Expr = PowerExpr(VarExpr(value), ConstExpr(1))
    implicit val env: Env = new Env(Map(("x", 24)))

    def `__`: Expr = NilExpr()

    val e: Expr = __ + "x" + "x"
    val e1: Expr = __ + 6 + 2 + "x" * 10
    println(s"env, $env")
    println(s"$e, eval:${eval(e)}, format:${format(e)}")
    println(s"$e1, eval:${eval(e1)}, derive:${derive(e1)}")
    println(s"$e1, eval:${eval(e1)}, formatted derive:${format(derive(e1))}")
  }
}

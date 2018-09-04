package languages.oo.traits

import scala.collection.mutable.ArrayBuffer

abstract class IntQueue {
  def put(x: Int): Unit

  def get(): Int
}

class BasicIntQueue extends IntQueue {
  private val buf = new ArrayBuffer[Int]

  def put(x: Int): Unit = {
    buf += x
  }

  def get(): Int = buf.remove(0)
}

// a trait declaring a superclass can only be mixed into a class that also extends that class
trait Doubling extends IntQueue {
  // abstract override def?????
  println("doubling")
  abstract override def put(x: Int) = {
    println("doubling put")
    super.put(2 * x)
  }
}

trait Incrementing extends IntQueue {
  println("incrementing")
  abstract override def put(x: Int): Unit = {
    println("incrementing put")
    super.put(x + 1)
  }
}

trait Filtering extends IntQueue {
  println("filtering")
  abstract override def put(x: Int): Unit = {
    println("filtering put")
    if (x > 0) {
      super.put(x)
    }
  }
}

object Test extends App {
  val queue1 = new BasicIntQueue with Filtering with Incrementing with Doubling
  println("--------")
  queue1.put(10)
  println("--------")
  val queue2 = new BasicIntQueue with Doubling with Filtering with Incrementing
  println("--------")
  queue2.put(10)
}
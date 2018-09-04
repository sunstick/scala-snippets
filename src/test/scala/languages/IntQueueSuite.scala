package languages

import languages.oo.traits.{BasicIntQueue, Doubling, Filtering, Incrementing}
import org.scalatest.FunSuite

class IntQueueSuite extends FunSuite {
  test("a doubling queue should get doubled results") {
    class MyQueue extends BasicIntQueue with Doubling
    val queue = new MyQueue
    queue.put(10)
    queue.put(0)
    assert(queue.get() == 20)
    assert(queue.get() == 0)
  }

  test("an incrementing queue should get incremental results") {
    class MyQueue extends BasicIntQueue with Incrementing
    val queue = new MyQueue
    queue.put(10)
    queue.put(1)

    assert(queue.get() == 11)
    assert(queue.get() == 2)
  }

  test("the filtering queue should filter all results non-positive numbers") {
    val queue = new BasicIntQueue with Filtering
    queue.put(-1)
    queue.put(0)
    queue.put(1)
    assert(queue.get() == 1)
    intercept[IndexOutOfBoundsException] {
      assert(queue.get() == 0)
    }
  }

  test("mixin order should matter") {
    val queue1 = new BasicIntQueue with Doubling with Incrementing
    queue1.put(10)
    assert(queue1.get() == 22)

    val queue2 = new BasicIntQueue with Incrementing with Doubling
    queue2.put(10)
    assert(queue2.get() == 21)
  }
}

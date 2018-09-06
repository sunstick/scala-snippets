package concurrency

import org.scalatest.FunSuite

class ThreadsVisibilitySuite extends FunSuite {

  case class State(var ready: Boolean, var number: Int)

  test("no visibility suite") {

    val state = State(false, 10)
    val readerThread = new Thread() {
      override def run(): Unit = {
        while (!state.ready) {
          // a hint to the scheduler that the thread is willing to yield its current use of the processor
          // just a hint, the scheduler can choose to ignore
          Thread.`yield`()
        }
        // Question: what would this line print?
        // Options: 1. print 10
        // Options: 2. print 42
        // Options: 3. this function would loop forever
        // This two threads are insufficiently synchronized. The reader thread can get stale data.
        assert(state.number == 42 || state.number == 10)
      }
    }

    readerThread.start()
    state.number = 42
    state.ready = true

  }
}

package concurrency

import java.util.concurrent.Semaphore

import org.scalatest.FunSuite

class SemaphoreSuite extends FunSuite {
  test("test semaphore semantics") {
    val semaphore = new Semaphore(10)

    assert(semaphore.tryAcquire(1))
    assert(semaphore.tryAcquire(2))
    assert(semaphore.tryAcquire(7))
    assert(!semaphore.tryAcquire(1))
    semaphore.release(10)
    assert(semaphore.tryAcquire(10))
  }

  test("main thread waiting for other threads") {
    val semaphore = new Semaphore(0) // initial number of permits is 0
    val numThreads = 10
    for (_ <- 1 to numThreads) {
      new Thread() {
        override def run(): Unit = {
          semaphore.release(1)
        }
      }.run()
    }

    assert(semaphore.tryAcquire(numThreads))
  }
}

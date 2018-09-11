package concurrency

import java.util.concurrent.Semaphore
import java.util.concurrent.locks.ReentrantLock

import org.scalatest.FunSuite

class MutexSuite extends FunSuite {
  test("semaphore as non reentrant mutex") {
    val mutex = new Semaphore(1)

    assert(mutex.tryAcquire())
    assert(!mutex.tryAcquire())
    mutex.release()
  }

  test("Reentrant mutex") {
    val mutex = new ReentrantLock()
    assert(!mutex.isLocked)
    mutex.lock()
    assert(mutex.isLocked)
    assert(mutex.tryLock())
    mutex.unlock()
    assert(mutex.isLocked) // why mutex is still locked here
  }
}

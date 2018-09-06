package concurrency

// a collection of @NotThreadSafe classes
object NotThreadSafeClasses {

  @NotThreadSafe
  class MutableInteger(var value: Int) {
    def setValue(value: Int): Unit = this.value = value

    def getValue: Int = this.value
  }

  @ThreadSafe
  class SynchronizedInteger(@GuardedBy("this") var value: Int) {

    def setValue(value: Int): Unit = synchronized {
      this.value = value
    }

    def getValue: Int = synchronized {
      this.value
    }
  }

  class VolatileInteger(@volatile var value: Int) {
    def setValue(value: Int): Unit = this.value = value

    def getValue: Int = this.value
  }

}

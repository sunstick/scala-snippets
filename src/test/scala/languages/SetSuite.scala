package languages

import org.scalatest.{BeforeAndAfter, FunSuite}

class SetSuite extends FunSuite with BeforeAndAfter {
  test("An empty set should have size of 0") {
    assert(Set.empty.size == 0)
  }
}

package languages

import languages.top.middle.A

// https://alvinalexander.com/scala/how-to-control-scala-method-scope-object-private-package

package top.middle {

  class A {
    private[this] def instanceScope: Unit = {} // most restrictive
    private def classScope: Unit = {}

    private[middle] def middleScope: Unit = {}

    private[top] def topScope: Unit = {}

    def publicScope: Unit = {} // least restrictive

    protected def inheritanceScope: Unit = {} // special, can only referred as super

    def illustrate(other: A): Unit = {
      // other.instanceScope, commented because of compile error
      this.instanceScope
      other.classScope
    }
  }

}

package another {

  object Test {
    val a = new A
    // a.topScope, commented because of compile error
    a.publicScope
  }

  object B extends A {
    val a = new A
    a.publicScope
    //a.inheritanceScope, commented because of compile error
    super.inheritanceScope
  }

}

package top {

  object Test {
    val a = new A
    // a.middleScope
    a.topScope
  }

  package middle {

    object Test {
      val a = new A
      a.middleScope
    }

    package bottom {

      object Test {
        val a = new A
        a.middleScope
      }

    }

  }

}
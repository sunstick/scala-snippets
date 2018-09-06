import scala.annotation.StaticAnnotation

package object concurrency {
  class NotThreadSafe extends StaticAnnotation

  class ThreadSafe extends StaticAnnotation

  class Immutable extends StaticAnnotation

  case class GuardedBy(str: String) extends StaticAnnotation
}

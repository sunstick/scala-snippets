package typesystem

object typeclass {

  trait Functor[F[_]] {
    def map[A, B](fa: F[A])(f: A => B): F[B]
  }

  trait Applicative[F[_]] extends Functor[F] {
    def <*>[A, B](fa: F[A])(f: F[A => B]): F[B]

    def point[A](a: A): F[A]
  }

  trait Monad[F[_]] extends Applicative[F] {
    def >>=[A, B](fa: F[A])(f: A => F[B]): F[B]
  }

  implicit val optionMonad = new Monad[Option] {
    def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa match {
      case Some(a) => Some(f(a))
      case None => None
    } // optionFunctor

    def <*>[A, B](fa: Option[A])(ff: Option[A => B]): Option[B] =
      (fa, ff) match {
        case (Some(a), Some(f)) => Some(f(a))
        case _ => None
      }

    def point[A](a: A): Option[A] = Some(a) // optionApplicative

    def >>=[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa match {
      case Some(a) => f(a)
      case _ => None
    }
  }

  implicit def functionMonad[R] = new Monad[({type F[A] = R => A})#F] {
    def map[A, B](fa: R => A)(f: A => B): R => B =
      f compose fa // functionFunctor

    def <*>[A, B](fa: R => A)(f: R => A => B): R => B = r => f(r)(fa(r))

    def point[A](a: A): R => A = (_ => a) // functionApplicative

    def >>=[A, B](fa: R => A)(f: A => R => B): R => B = r => f(fa(r))(r)
  }

  // functionMonad is actually ReaderMonad

  case class Reader[R, A](run: R => A)

  implicit def readerMonad[R] = new Monad[({type F[A] = Reader[R, A]})#F] {
    def map[A, B](reader: Reader[R, A])(f: A => B): Reader[R, B] =
      Reader(reader.run andThen f)

    def <*>[A, B](reader: Reader[R, A])(f: Reader[R, A => B]): Reader[R, B] =
      Reader { r: R =>
        f.run(r)(reader.run(r))
      }

    def point[A](a: A): Reader[R, A] = Reader(_ => a)

    def >>=[A, B](reader: Reader[R, A])(f: A => Reader[R, B]): Reader[R, B] =
      Reader { r: R =>
        f(reader.run(r)).run(r)
      }
  }
}

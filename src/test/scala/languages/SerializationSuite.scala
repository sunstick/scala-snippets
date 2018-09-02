package languages

import java.io.{File, FileInputStream, FileOutputStream}

import com.esotericsoftware.kryo.io.{Input, Output}
import de.javakaffee.kryoserializers.KryoReflectionFactorySupport
import languages.annotations.People
import org.scalatest.{BeforeAndAfter, FunSuite}

class SerializationSuite extends FunSuite with BeforeAndAfter {
  val siLi = People("Si", "Li", 20, "Address 2", "dummy property 2")
  val kryo = new KryoReflectionFactorySupport
  kryo.register(classOf[People])


  test("transient property should not be serialized") {
    val file = File.createTempFile("serialization", "test")
    file.deleteOnExit()

    val output = new Output(new FileOutputStream(file))
    kryo.writeObject(output, siLi)
    output.close()

    val input = new Input(new FileInputStream(file))
    val sanLiLoaded = kryo.readObject(input, classOf[People])
    input.close()

    assert(sanLiLoaded.age == 20)
    assert(Option(sanLiLoaded.dummyField) == None)
  }
}

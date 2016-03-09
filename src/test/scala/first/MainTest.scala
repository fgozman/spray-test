package first

import scala.collection.mutable.ListBuffer
import spraytest.Address;
import spraytest.Customer;

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.typesafe.config.ConfigFactory
import com.fasterxml.jackson.core.JsonParser.Feature
import com.fasterxml.jackson.annotation.JsonInclude.Include


class MainTest extends AssertionsForJUnit {
  var sb: StringBuilder = _
  var lb: ListBuffer[String] = _
  val jsonMapper:ObjectMapper = new ObjectMapper
  jsonMapper.registerModule(DefaultScalaModule)
  jsonMapper.setSerializationInclusion(Include.NON_NULL)
  @Before def initialize() {
    sb = new StringBuilder("ScalaTest is ")
    lb = new ListBuffer[String]
  }

  @Test def verifyEasy() { // Uses JUnit-style assertions
    sb.append("easy!")
    assertEquals("ScalaTest is easy!", sb.toString)
    assertTrue(lb.isEmpty)
    lb += "sweet"
    try {
      "verbose".charAt(-1)
      fail()
    } catch {
      case e: StringIndexOutOfBoundsException => // Expected
    }
  }

  @Test def verifyFun() { // Uses ScalaTest assertions
    sb.append("fun!")
    assert(sb.toString === "ScalaTest is fun!")
    assert(lb.isEmpty)
    lb += "sweeter"
    intercept[StringIndexOutOfBoundsException] {
      "concise".charAt(-1)
    }
  }
  
  @Test
  def config = {
    val conf = ConfigFactory.load(Thread.currentThread().getContextClassLoader)
    
    println(conf.entrySet())
  }
  
  @Test
  def json = {
    val c = new Customer("cucu",22, new Address("str1",Some("3455")))
    val jsonStr = jsonMapper.writeValueAsString(c)
    println(jsonStr)
    val cBack = jsonMapper.readValue(jsonStr, c.getClass)
    println(cBack.name,cBack.age,cBack.address.street, cBack.address.zipCode)
    val c1 = C1("x",None)
    
     val c1Json = jsonMapper.writeValueAsString(c1)
    println(c1Json)
    val c1Back = jsonMapper.readValue(c1Json, classOf[C1])
    println(c1Back.p1+","+c1Back.p2)
    
  }
}

case class C1(val p1:String, val p2:Option[String])

package com.aibuild.scalatest.fixtures

import org.scalatest.FlatSpec

import scala.collection.mutable.ListBuffer

/**
  * Created by ArnoldHigyed on 5/12/2018
  */
class GetFixtureMethods extends FlatSpec {

  def fixture: Object {
    val buffer: ListBuffer[String]

    val builder: StringBuilder
  } =
    new {
      val builder = new StringBuilder("ScalaTest is ")
      val buffer  = new ListBuffer[String]
    }

  "Testing" should "be easy" in {
    val f = fixture
    f.builder.append("easy!")
    assert(f.builder.toString === "ScalaTest is easy!")
    assert(f.buffer.isEmpty)
    f.buffer += "sweet"
  }

  it should "be fun" in {
    val f = fixture
    f.builder.append("fun!")
    assert(f.builder.toString === "ScalaTest is fun!")
    assert(f.buffer.isEmpty)
  }
}

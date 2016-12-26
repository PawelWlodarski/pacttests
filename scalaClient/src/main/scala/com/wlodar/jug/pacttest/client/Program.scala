package com.wlodar.jug.pacttest.client

object Program {
  def main(args: Array[String]): Unit = {

    val baseUrl="http://localhost:11200"

    println(ServiceClient.simpleCall(baseUrl))
  }
}

package com.github.amura

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

	val httpProtocol = http
		.baseURL("http://localhost:8080")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.8,id;q=0.6")
		.contentTypeHeader("text/plain;charset=UTF-8")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36")

	val headers_0 = Map(
		"Origin" -> "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop",
		"Postman-Token" -> "c9952ab9-fa99-9470-cf2a-09699bc804d0")

	val uri1 = "http://localhost:8080/q"

	val scn = scenario("RecordedSimulation")
		.repeat(1000) {
			exec (http ("request_0")
				.post ("/q")
				.headers (headers_0)
				.body (RawFileBody ("RecordedSimulation_0000_request.txt")))
		}

	setUp(scn.inject(atOnceUsers(10))).protocols(httpProtocol)
}
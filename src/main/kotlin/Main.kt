package org.example

import com.timgroup.statsd.NonBlockingStatsDClientBuilder
import com.timgroup.statsd.StatsDClient
import datadog.trace.api.DDTags
import io.opentracing.Span
import io.opentracing.Tracer
import io.opentracing.util.GlobalTracer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MainApplication {
	companion object {
		val logger: Logger = LoggerFactory.getLogger(MainApplication::class.java)
	}

	fun createLog() {
		logger.info("This is a sample log sent locally.")
	}

	fun createTrace() {
		val tracer: Tracer = GlobalTracer.get()
		// Service and resource name tags are required.
		// You can set them when creating the span:
		val span: Span = tracer.buildSpan("sample.operation")
			.withTag(DDTags.SERVICE_NAME, "datadog-demo")
			.withTag(DDTags.RESOURCE_NAME, "sample.resource")
			.start()
		try {
			tracer.activateSpan(span)
		} catch (e: Exception) {
			println(e)
		} finally {
			// Close span in a finally block
			span.finish()
		}
	}

	fun createMetrics() {
		lateinit var client: StatsDClient
		try {
			client = NonBlockingStatsDClientBuilder()
				.hostname("localhost")
				.port(8125)
				.build()
			for (i in 0..5) {
				client.incrementCounter("custom_metric", i.toDouble(), "service:datadog-demo")
				println("Metric custom_metric incremented to $i.")
				Thread.sleep(1000)
			}
		} finally {
			client.close()
		}
	}
}

fun main() {
	val main = MainApplication()
	main.createMetrics()
}

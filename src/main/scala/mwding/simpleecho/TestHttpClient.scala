package mwding.simpleecho

import com.twitter.finagle.Service
import com.twitter.finagle.http.Http
import com.twitter.finagle.builder.ClientBuilder
import org.jboss.netty.handler.codec.http.{DefaultHttpRequest, HttpRequest, HttpResponse, HttpVersion, HttpMethod}
import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
import java.nio.charset.Charset

object TestHttpClient extends App {
  val client: Service[HttpRequest, HttpResponse] = ClientBuilder()
    .codec(Http())
    .hosts("localhost:8888")
    .hostConnectionLimit(1)
    .build

  val req = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/")
  val msg = "hello world"
  val cb = ChannelBuffers.buffer(msg.length)
  cb.writeBytes(msg.getBytes())
  req.setHeader("Content-Length", msg.length)
  req.setContent(cb)

  println(req)

  val f = client(req)

  f onSuccess { res =>
    println("get response", res)
    println(res.getContent.toString(Charset.defaultCharset()))
  } onFailure { res =>
    println("failed", res)
  }
 }

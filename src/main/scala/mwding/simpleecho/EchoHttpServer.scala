package mwding.simpleecho

import com.twitter.finagle.Service
import com.twitter.finagle.builder.{Server, ServerBuilder}
import com.twitter.finagle.http.Http
import com.twitter.util.{Await, Future}
import java.net.{SocketAddress, InetSocketAddress}
import org.jboss.netty.handler.codec.http._
import java.nio.charset.Charset

object EchoHttpServer extends App {
  val service = new Service[HttpRequest, HttpResponse] {
    def apply(req: HttpRequest) = {
      val r = req.getUri match {
        case "/" => {
          println("get request: " + req.getContent.toString(Charset.defaultCharset()))
          val res = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK)
          res.setContent(req.getContent)
          res
        }
        case _ => new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND)
      }
      Future.value(r)
    }
  }

  val address: SocketAddress = new InetSocketAddress(8888)
  val server: Server = ServerBuilder()
    .codec(Http())
    .bindTo(address)
    .name("HttpServer")
    .build(service)
}

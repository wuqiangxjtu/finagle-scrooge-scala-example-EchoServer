package mwding.simpleecho

import com.twitter.finagle.Service
import com.twitter.finagle.thrift.{ThriftClientFramedCodec, ThriftClientRequest}
import com.twitter.finagle.builder.ClientBuilder
import java.net.InetSocketAddress
import org.apache.thrift.protocol.TBinaryProtocol

object TestRPCClient extends App{
  val service: Service[ThriftClientRequest, Array[Byte]] = ClientBuilder()
    .hosts(new InetSocketAddress(8888))
    .codec(ThriftClientFramedCodec())
    .hostConnectionLimit(1)
    .build

  val client = new EchoService$FinagleClient(service, new TBinaryProtocol.Factory())

  val req = SimpleRequest("hello world")

  val echoFuture = client.echo(req)

  echoFuture onSuccess { res =>
    println("get response: " + res.content)
  } onFailure { res =>
    println("Oops, failed")
  }
}

package mwding.simpleecho

import org.apache.thrift.protocol.TBinaryProtocol
import java.net.{InetSocketAddress, SocketAddress}
import com.twitter.finagle.builder.{ServerBuilder, Server}
import com.twitter.finagle.thrift.ThriftServerFramedCodec

object EchoRPCServer extends App {
  val service = new EchoService$FinagleService(new EchoServiceImpl, new TBinaryProtocol.Factory())

  val address: SocketAddress = new InetSocketAddress("localhost", 8888)
  val server: Server = ServerBuilder()
    .codec(ThriftServerFramedCodec())
    .bindTo(address)
    .name("EchoRPCServer")
    .build(service)
}

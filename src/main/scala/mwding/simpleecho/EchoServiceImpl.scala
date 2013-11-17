package mwding.simpleecho

import com.twitter.util.Future

class EchoServiceImpl extends EchoService[Future] {
  override def echo(req: SimpleRequest): Future[SimpleResponse] = {
    val res = SimpleResponse(req.content)
    return Future.value(res)
  }
}

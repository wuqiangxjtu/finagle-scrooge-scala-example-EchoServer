namespace java mwding.simpleecho

struct SimpleRequest {
	1: string content
}

struct SimpleResponse {
    1: string content
}

service EchoService {
    SimpleResponse echo(1: SimpleRequest req)
}

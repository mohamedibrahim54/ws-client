package wsclient.util;

import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MockServer {

    public static void main(String[] args) throws IOException {
        start();
    }

    public static void start() throws IOException {
        InetSocketAddress localAddress = new InetSocketAddress(9095);
        var httpServer = HttpServer.create();
        httpServer.bind(localAddress, 0);
        httpServer.createContext("/mock/ws", exchange -> {
            try (exchange) {
                var headers = exchange.getRequestHeaders();
                String body;
                try (var input = new InputStreamReader(exchange.getRequestBody(), UTF_8);
                     var reader = new BufferedReader(input)) {
                    body = reader.lines().collect(Collectors.joining());
                }
                var requestInfo = """
                        Headers: %s,
                        Body: %s
                        """.formatted(headers.entrySet(), body);
                System.out.println(requestInfo);

                String response = """
                        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
                            <soapenv:Header/>
                            <soapenv:Body>
                                <ex:order xmlns:ex="https://example.org">
                                    <id>05041995</id>
                                    <item>
                                        <name>Mobile</name>
                                        <price>1000</price>
                                    </item>
                                </ex:order>
                            </soapenv:Body>
                        </soapenv:Envelope>
                        """;
//                var contentType = "application/xml";
                var contentType = headers.get("Accept") != null ? headers.get("Accept").getFirst() : "text/xml";
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(200, response.length());
                try (var writer = new OutputStreamWriter(exchange.getResponseBody(), UTF_8)) {
                    writer.write(response);
                }
            }
        });
        httpServer.start();
        System.out.println("server at http://localhost:" + localAddress.getPort());
    }
}

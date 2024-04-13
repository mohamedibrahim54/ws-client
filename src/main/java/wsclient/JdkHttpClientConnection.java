package wsclient;

import jakarta.xml.soap.SOAPException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class JdkHttpClientConnection implements WsConnection {

    private final HttpClient httpClient;

    private final URI uri;


    public JdkHttpClientConnection(HttpClient httpClient, URI uri) {
        this.httpClient = httpClient;
        this.uri = uri;
    }

    @Override
    public ClientResponse send(Map<String, List<String>> headers, WsMessage message) throws SOAPException, IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        message.writeTo(outputStream);
        byte[] body = outputStream.toByteArray();
        HttpRequest.Builder builder = HttpRequest.newBuilder(uri)
                .POST(HttpRequest.BodyPublishers.ofByteArray(body));
        if (headers != null) {
            headers.forEach((key, value) -> builder.header(key, String.join(", ", value)));
        }
        HttpRequest request = builder
                .build();
        HttpResponse<InputStream> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(ex);
        }
        return new JdkClientHttpResponse(response);
    }
}

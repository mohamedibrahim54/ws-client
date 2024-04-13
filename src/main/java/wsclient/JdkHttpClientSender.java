package wsclient;

import java.net.URI;
import java.net.http.HttpClient;

public class JdkHttpClientSender implements WsSender {

    private final HttpClient httpClient;

    public JdkHttpClientSender() {
        this(HttpClient.newHttpClient());
    }

    public JdkHttpClientSender(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public WsConnection createConnection(URI uri) {
        return new JdkHttpClientConnection(httpClient, uri);
    }
}

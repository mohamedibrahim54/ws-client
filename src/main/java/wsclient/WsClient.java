package wsclient;

import jakarta.xml.soap.SOAPException;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import javax.xml.transform.Source;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class WsClient {

    private final String baseUrl;
    private final WsSender sender;
    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;
    private final WsMessageFactory messageFactory;
    private final List<ClientRequestInterceptor> interceptors;
    private final Map<String, List<String>> defaultHeaders;


    public static WsClientBuilder builder() {
        return new WsClientBuilder();
    }

    public WsClient(String baseUrl, WsSender sender, Marshaller marshaller, Unmarshaller unmarshaller, List<ClientRequestInterceptor> interceptors, Map<String, List<String>> defaultHeaders) throws SOAPException {
        this.baseUrl = baseUrl;
        this.sender = sender;
        this.marshaller = marshaller;
        this.unmarshaller = unmarshaller;
        this.interceptors = interceptors;
        this.defaultHeaders = defaultHeaders;
        messageFactory = new WsSaajMessageFactory();
    }

    public RequestSpec request(Object payload) {
        return new RequestSpec(defaultHeaders, payload);
    }

    public class RequestSpec {

        private final Map<String, List<String>> headers;
        private final Object payload;
        private URI uri;

        public RequestSpec(Map<String, List<String>> headers, Object payload) {
            this.headers = headers;
            this.payload = payload;
        }

        public RequestSpec uri(String uri) {
            requireNonNull(uri, "uri");
            if (baseUrl != null) {
                this.uri = URI.create(baseUrl + uri);
            } else {
                this.uri = URI.create(uri);
            }
            return this;
        }
        
        public RequestSpec header(String name, String value){
            requireNonNull(value, "value");
            return header(name, List.of(value));
        }

        public RequestSpec header(String name, List<String> valueList){
            requireNonNull(name, "name");
            requireNonNull(valueList, "valueList");
            headers.put(name, valueList);
            return this;
        }

        public ResponseSpec send() throws SOAPException, IOException {
            URI requestUri = uri == null ? URI.create(baseUrl) : uri;
            ClientRequest clientRequest = buildRequest(requestUri, WsClient.this.sender, headers, payload, WsClient.this.interceptors);
            return new ResponseSpec(clientRequest.execute());
        }

        private ClientRequest buildRequest(URI uri, WsSender sender, Map<String, List<String>> defaultHeaders, Object payload, List<ClientRequestInterceptor> interceptors) throws SOAPException, IOException {
            WsMessage wsMessage = messageFactory.createMessage();
            marshaller.marshal(payload, wsMessage.getPayloadResult());
            return new InterceptingClientRequest(uri, sender, defaultHeaders, wsMessage, interceptors);
        }
    }

    public class ResponseSpec {
        private final ClientResponse response;

        public ResponseSpec(ClientResponse response) {
            this.response = response;
        }

        public String retrieveAsString() throws IOException {
            String responseString;
            try (response) {
                responseString = new String(response.getBody().readAllBytes());
            }
            return responseString;
        }

        public Object retrieve() throws IOException, SOAPException {
            try (response) {
                WsMessage wsMessage = messageFactory.createMessage(response);
                Source payload = wsMessage.getPayloadSource();

                if (payload == null) {
                    return null;
                } else {
                    return unmarshaller.unmarshal(payload);
                }
            }
        }
    }
}

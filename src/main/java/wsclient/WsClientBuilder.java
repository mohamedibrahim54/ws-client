package wsclient;

import jakarta.xml.soap.SOAPException;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import java.util.*;
import java.util.function.Consumer;

public class WsClientBuilder {
    private String baseUrl;
    private WsSender sender;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    private List<ClientRequestInterceptor> interceptors;
    private Map<String, List<String>> defaultHeaders;

    public WsClientBuilder baseUrl(String baseUrl) {
        Objects.requireNonNull(baseUrl, "baseUrl");
        this.baseUrl = baseUrl;
        return this;
    }

    public WsClientBuilder sender(WsSender sender) {
        Objects.requireNonNull(sender, "sender");
        this.sender = sender;
        return this;
    }

    public WsClientBuilder marshaller(Marshaller marshaller) {
        Objects.requireNonNull(sender, "marshaller");
        this.marshaller = marshaller;
        return this;
    }

    public WsClientBuilder unmarshaller(Unmarshaller unmarshaller) {
        Objects.requireNonNull(sender, "unmarshaller");
        this.unmarshaller = unmarshaller;
        return this;
    }

    public WsClientBuilder requestInterceptor(ClientRequestInterceptor interceptor) {
        Objects.requireNonNull(interceptor, "interceptor");
        initInterceptors().add(interceptor);
        return this;
    }

    public WsClientBuilder requestInterceptors(Consumer<List<ClientRequestInterceptor>> interceptorsConsumer) {
        interceptorsConsumer.accept(initInterceptors());
        return this;
    }

    private List<ClientRequestInterceptor> initInterceptors() {
        if (interceptors == null) {
            interceptors = new ArrayList<>();
        }
        return this.interceptors;
    }

    public WsClientBuilder defaultHeaders(Consumer<Map<String, List<String>>> headersConsumer) {
        headersConsumer.accept(initHeaders());
        return this;
    }

    private Map<String, List<String>> initHeaders() {
        if (this.defaultHeaders == null) {
            this.defaultHeaders = new HashMap<>();
        }
        return this.defaultHeaders;
    }

    public WsClient build() throws SOAPException {
        if (unmarshaller == null) {
            if (marshaller instanceof Unmarshaller unmarshaller1) {
                this.unmarshaller = unmarshaller1;
            } else {
                throw new IllegalStateException("Marshaller [" + marshaller + "] does not implement the Unmarshaller "
                        + "interface. Please set an Unmarshaller explicitly");
            }
        }
        return new WsClient(baseUrl,sender, marshaller, unmarshaller, interceptors, defaultHeaders);
    }

}

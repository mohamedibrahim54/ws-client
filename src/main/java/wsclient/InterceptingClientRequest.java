package wsclient;

import jakarta.xml.soap.SOAPException;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class InterceptingClientRequest implements ClientRequest {

    private final URI uri;
    private final WsSender sender;
    private final Map<String, List<String>> headers;
    private final WsMessage message;
    private final List<ClientRequestInterceptor> interceptors;

    public InterceptingClientRequest(URI uri, WsSender sender, Map<String, List<String>> headers, WsMessage message, List<ClientRequestInterceptor> interceptors) {
        this.uri = uri;
        this.sender = sender;
        this.headers = headers;
        this.message = message;
        this.interceptors = interceptors;
    }


    @Override
    public URI getURI() {
        return uri;
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    @Override
    public WsMessage getMessage() {
        return message;
    }


    @Override
    public WsSender getSender() {
        return sender;
    }

    @Override
    public ClientResponse execute() throws IOException, SOAPException {
        ClientRequestExecution execution = new ClientExecution();
        return execution.execute(this);
    }

    private class ClientExecution implements ClientRequestExecution{

        private final Iterator<ClientRequestInterceptor> iterator;

        public ClientExecution() {
            this.iterator = interceptors != null ? interceptors.iterator() : Collections.emptyIterator();
        }

        @Override
        public ClientResponse execute(WsRequest request) throws SOAPException, IOException {
            if (iterator.hasNext()){
                ClientRequestInterceptor interceptor = iterator.next();
                return interceptor.intercept(request, this);
            } else {
                return sender.createConnection(uri).send(headers, message);
            }
        }
    }
}

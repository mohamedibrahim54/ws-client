package wsclient;

import jakarta.xml.soap.SOAPException;

import java.io.IOException;

public interface ClientRequestInterceptor {
    ClientResponse intercept(WsRequest request, ClientRequestExecution execution) throws SOAPException, IOException;
}

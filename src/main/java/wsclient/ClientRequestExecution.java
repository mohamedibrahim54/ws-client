package wsclient;

import jakarta.xml.soap.SOAPException;

import java.io.IOException;

public interface ClientRequestExecution {
    ClientResponse execute(WsRequest request) throws SOAPException, IOException;
}

package wsclient;

import jakarta.xml.soap.SOAPException;

import java.io.IOException;

public interface WsMessageFactory {
    WsMessage createMessage() throws SOAPException;
    WsMessage createMessage(ClientResponse clientResponse) throws SOAPException, IOException;
}

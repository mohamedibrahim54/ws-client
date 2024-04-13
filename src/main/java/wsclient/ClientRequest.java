package wsclient;

import jakarta.xml.soap.SOAPException;

import java.io.IOException;

public interface ClientRequest extends WsRequest {

    WsSender getSender();

    ClientResponse execute() throws IOException, SOAPException;
}

package wsclient;

import jakarta.xml.soap.SOAPException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface WsConnection {
    ClientResponse send(Map<String, List<String>> headers, WsMessage message) throws SOAPException, IOException;
}

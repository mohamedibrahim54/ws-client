package wsclient;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WsSaajMessageFactory implements WsMessageFactory {

    private final MessageFactory messageFactory;

    public WsSaajMessageFactory() throws SOAPException {
        messageFactory = MessageFactory.newInstance();
    }

    @Override
    public WsMessage createMessage() throws SOAPException {
        SOAPMessage soapMessage = messageFactory.createMessage();
        return new WsMessage(soapMessage);
    }

    @Override
    public WsMessage createMessage(ClientResponse clientResponse) throws SOAPException, IOException {
        MimeHeaders mimeHeaders = parseMimeHeaders(clientResponse);
        SOAPMessage soapMessage = messageFactory.createMessage(mimeHeaders, clientResponse.getBody());
        return new WsMessage(soapMessage);
    }

    private MimeHeaders parseMimeHeaders(ClientResponse clientResponse) {
        MimeHeaders mimeHeaders = new MimeHeaders();
        for (Map.Entry<String, List<String>> entry : clientResponse.getHeaders().entrySet()){
            String headerName = entry.getKey();
            for (String value : entry.getValue()){
                mimeHeaders.addHeader(headerName, value);
            }
        }
        return mimeHeaders;
    }
}

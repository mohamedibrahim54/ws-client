package wsclient;

import jakarta.xml.soap.*;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

public class WsMessage extends SOAPMessage {

    private final SOAPMessage soapMessage;

    public WsMessage(SOAPMessage soapMessage) {
        this.soapMessage = soapMessage;
    }

    public SOAPMessage getSoapMessage() {
        return soapMessage;
    }

    public Result getPayloadResult() throws SOAPException {
        getSOAPBody().removeContents();
        return new DOMResult(getSOAPBody());
    }

    public Source getPayloadSource() throws SOAPException {
        SOAPElement bodyElement = getFirstBodyElement(getSOAPBody());
        return bodyElement != null ? new DOMSource(bodyElement) : null;
    }

    public static SOAPElement getFirstBodyElement(SOAPBody body) {
        for (Iterator<?> iterator = body.getChildElements(); iterator.hasNext();) {
            Object child = iterator.next();
            if (child instanceof SOAPElement element) {
                return element;
            }
        }
        return null;
    }

    @Override
    public SOAPBody getSOAPBody() throws SOAPException {
        return soapMessage.getSOAPBody();
    }

    @Override
    public SOAPHeader getSOAPHeader() throws SOAPException {
        return soapMessage.getSOAPHeader();
    }

    @Override
    public void setProperty(String property, Object value) throws SOAPException {
        soapMessage.setProperty(property, value);
    }

    @Override
    public Object getProperty(String property) throws SOAPException {
        return soapMessage.getProperty(property);
    }

    @Override
    public void setContentDescription(String s) {
        soapMessage.setContentDescription(s);
    }

    @Override
    public String getContentDescription() {
        return soapMessage.getContentDescription();
    }

    @Override
    public SOAPPart getSOAPPart() {
        return soapMessage.getSOAPPart();
    }

    @Override
    public void removeAllAttachments() {
        soapMessage.removeAllAttachments();
    }

    @Override
    public int countAttachments() {
        return soapMessage.countAttachments();
    }

    @Override
    public Iterator<AttachmentPart> getAttachments() {
        return soapMessage.getAttachments();
    }

    @Override
    public Iterator<AttachmentPart> getAttachments(MimeHeaders mimeHeaders) {
        return soapMessage.getAttachments(mimeHeaders);
    }

    @Override
    public void removeAttachments(MimeHeaders mimeHeaders) {
        soapMessage.removeAttachments(mimeHeaders);
    }

    @Override
    public AttachmentPart getAttachment(SOAPElement soapElement) throws SOAPException {
        return soapMessage.getAttachment(soapElement);
    }

    @Override
    public void addAttachmentPart(AttachmentPart attachmentPart) {
        soapMessage.addAttachmentPart(attachmentPart);
    }

    @Override
    public AttachmentPart createAttachmentPart() {
        return soapMessage.createAttachmentPart();
    }

    @Override
    public MimeHeaders getMimeHeaders() {
        return soapMessage.getMimeHeaders();
    }

    @Override
    public void saveChanges() throws SOAPException {
        soapMessage.saveChanges();
    }

    @Override
    public boolean saveRequired() {
        return soapMessage.saveRequired();
    }

    @Override
    public void writeTo(OutputStream outputStream) throws SOAPException, IOException {
        soapMessage.writeTo(outputStream);
    }

}

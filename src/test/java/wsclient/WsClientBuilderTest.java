package wsclient;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WsClientBuilderTest {

    @Test
    void BuilderWithNullSender(){
        WsClientBuilder clientBuilder = new WsClientBuilder();
        assertThrows(NullPointerException.class, () -> clientBuilder.sender(null));
    }

    @Test
    void BuilderWithNullMarshaller(){
        WsClientBuilder clientBuilder = new WsClientBuilder();
        assertThrows(NullPointerException.class, () -> clientBuilder.marshaller(null));
    }

    @Test
    void BuilderWithNullUnmarshaller(){
        WsClientBuilder clientBuilder = new WsClientBuilder();
        assertThrows(NullPointerException.class, () -> clientBuilder.unmarshaller(null));
    }

}
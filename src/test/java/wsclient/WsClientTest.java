package wsclient;

import jakarta.xml.soap.SOAPException;
import wsclient.types.ObjectFactory;
import wsclient.types.QueryRequest;
import wsclient.util.MockServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class WsClientTest {

    @BeforeAll
    public static void init() throws IOException {
        MockServer.start();
    }

    @Test
    void retrieveAsString() throws SOAPException, IOException {
        JdkHttpClientSender jdkHttpClientSender = new JdkHttpClientSender();

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("wsclient.types");

        ObjectFactory factory = new ObjectFactory();
        QueryRequest queryRequest = factory.createQueryRequest();
        queryRequest.setId("05041995");

        WsClient wsClient = WsClient.builder()
                .baseUrl("http://localhost:9095/mock/ws")
                .sender(jdkHttpClientSender)
                .marshaller(marshaller)
                .build();

        String retrieved = wsClient.request(queryRequest).send().retrieveAsString();
        System.out.println(retrieved);

        assertNotNull(retrieved);
    }

    @Test
    void retrieve() throws SOAPException, IOException {
        JdkHttpClientSender jdkHttpClientSender = new JdkHttpClientSender();

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("wsclient.types");

        ObjectFactory factory = new ObjectFactory();
        QueryRequest queryRequest = factory.createQueryRequest();
        queryRequest.setId("05041995");

        WsClient wsClient = WsClient.builder()
                .baseUrl("http://localhost:9095")
                .sender(jdkHttpClientSender)
                .marshaller(marshaller)
                .build();
        Object retrieved = wsClient.request(queryRequest).uri("/mock/ws").send().retrieve();
        System.out.println(retrieved);

        assertNotNull(retrieved);
    }

    @Test
    void retrieveWithInterceptor() throws SOAPException, IOException {
        JdkHttpClientSender jdkHttpClientSender = new JdkHttpClientSender();

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("wsclient.types");

        ObjectFactory factory = new ObjectFactory();
        QueryRequest queryRequest = factory.createQueryRequest();
        queryRequest.setId("05041995");

        WsClient wsClient = WsClient.builder()
                .baseUrl("http://localhost:9095/mock/ws")
                .sender(jdkHttpClientSender)
                .marshaller(marshaller)
                .defaultHeaders(map -> map.put("Accept", List.of("application/xml")))
                .requestInterceptor((request, execution) -> {
                    ClientResponse response = execution.execute(request);
                    System.out.println(response.getHeaders());
                    response.getHeaders().replace("content-type", List.of("text/xml"));
                    return response;
                }).build();

        Object retrieved = wsClient.request(queryRequest).send().retrieve();
        System.out.println(retrieved);

        assertNotNull(retrieved);
    }

    @Test
    void retrieveWithRequestHeaders() throws SOAPException, IOException {
        JdkHttpClientSender jdkHttpClientSender = new JdkHttpClientSender();

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("wsclient.types");

        ObjectFactory factory = new ObjectFactory();
        QueryRequest queryRequest = factory.createQueryRequest();
        queryRequest.setId("05041995");

        WsClient wsClient = WsClient.builder()
                .baseUrl("http://localhost:9095/mock/ws")
                .sender(jdkHttpClientSender)
                .marshaller(marshaller)
                .defaultHeaders(map -> map.put("Accept", List.of("application/xml")))
                .build();

        Object retrieved = wsClient.request(queryRequest).header("Accept", "text/xml").send().retrieve();
        System.out.println(retrieved);

        assertNotNull(retrieved);
    }

}
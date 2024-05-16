# Simple Web Services Client [![.github/workflows/main.yml](https://github.com/mohamedibrahim54/ws-client/actions/workflows/main.yml/badge.svg)](https://github.com/mohamedibrahim54/ws-client/actions/workflows/main.yml)
### Simple, Small and Powerfull Web Services Client features a fluent API
## Features
  - Easy to Exchange SOAP messages
  - Provide Request Interceptors via chain of responsibility pattern to intercept HTTP request including web service message and HTTP response 
  - Fluent API makes the code more readable and expressive by sequentially calling methods
  - Small jar dependency
## Examples
### Basic
```java
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
```
### Advanced
```java
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
```

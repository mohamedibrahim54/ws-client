package wsclient;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ClientResponse extends AutoCloseable {

    InputStream getBody();

    int getStatusCode();

    Map<String, List<String>> getHeaders();

    void close();

}

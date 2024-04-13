package wsclient;

import java.net.URI;
import java.util.List;
import java.util.Map;

public interface WsRequest {

    URI getURI();

    Map<String, List<String>> getHeaders();

    WsMessage getMessage();

}

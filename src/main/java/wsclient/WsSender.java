package wsclient;

import java.net.URI;

public interface WsSender {
    WsConnection createConnection(URI uri);
}

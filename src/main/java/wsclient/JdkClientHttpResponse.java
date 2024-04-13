package wsclient;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdkClientHttpResponse implements ClientResponse{

    private final HttpResponse<InputStream> response;

    private final Map<String, List<String>> headers;

    private final InputStream body;

    public JdkClientHttpResponse(HttpResponse<InputStream> response) {
        this.response = response;
        this.headers = new HashMap<>(response.headers().map());
        InputStream inputStream = response.body();
        this.body = (inputStream != null ? inputStream : InputStream.nullInputStream());
    }

    @Override
    public int getStatusCode() {
        return this.response.statusCode();
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    @Override
    public InputStream getBody() {
        return this.body;
    }


    @Override
    public void close() {
        try {
            try {
                StreamUtils.drain(this.body);
            }
            finally {
                this.body.close();
            }
        }
        catch (IOException ignored) {
            // ignored
        }
    }

}

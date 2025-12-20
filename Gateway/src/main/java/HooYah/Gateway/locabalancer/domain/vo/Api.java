package HooYah.Gateway.locabalancer.domain.vo;

import java.net.URI;

public class Api {

    private Protocol protocol;
    private Url url; // http://localhost:8080
    private Uri uri; // /public/test // except "/user/" !!

    public Api(Url url, Uri uri) {
        this.url = url;
        this.uri = uri;
    }

    public String getHost() {
        return url.getHost().toString();
    }

    public int getPort() {
        return url.getPort().getPort();
    }

    public String toProxyUri() {
        return uri.getUri();
    }

    public Uri getUri() {
        return uri;
    }

    public Url getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Api{" +
                "url=" + url +
                ", uri=" + uri +
                '}';
    }
}

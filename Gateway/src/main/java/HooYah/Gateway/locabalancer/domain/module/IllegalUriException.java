package HooYah.Gateway.locabalancer.domain.module;

import HooYah.Gateway.locabalancer.domain.vo.Uri;

public class IllegalUriException extends RuntimeException {
    public IllegalUriException(Uri requestUri) {
        super("Illegal URI: " + requestUri);
    }
}

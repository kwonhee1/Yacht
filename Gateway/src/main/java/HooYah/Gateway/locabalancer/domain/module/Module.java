package HooYah.Gateway.locabalancer.domain.module;

import HooYah.Gateway.locabalancer.domain.service.Service;
import HooYah.Gateway.locabalancer.domain.vo.Uri;
import java.util.List;

// module: uri
public class Module {

    private final Uri uri;
    private List<Service> services;

    private List<Service> subServices;

    private ModuleStatus moduleStatus; // scale out, scale in을 결정함

    public Module(Uri uri, List<Service> services, List<Service> subServices) {
        this.uri = uri;
        this.services = services;
        this.subServices = subServices;
    }

    public Uri getUri() {
        return uri;
    }

    public boolean matches(Uri requestUri) {
        return uri.isMatch(requestUri);
    }

    public Service matching() {
        // todo : matching logic
        return services.getFirst();
    }

    @Override
    public String toString() {
        return "Module{" +
                "uri=" + uri +
                ", services=" + services +
                ", subServices=" + subServices +
                ", moduleStatus=" + moduleStatus +
                '}';
    }
}

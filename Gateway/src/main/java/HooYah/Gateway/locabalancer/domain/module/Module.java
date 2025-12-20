package HooYah.Gateway.locabalancer.domain.module;

import HooYah.Gateway.locabalancer.domain.service.Service;
import HooYah.Gateway.locabalancer.domain.vo.Uri;
import HooYah.Gateway.locabalancer.domain.vo.UriMatcher;
import java.util.List;

public class Module {

    private UriMatcher matcher;
    private List<Service> services;

    private List<Service> subServices;

    private ModuleStatus moduleStatus; // scale out, scale in을 결정함

    public Module(UriMatcher matcher, List<Service> services, List<Service> subServices) {
        this.matcher = matcher;
        this.services = services;
        this.subServices = subServices;
    }

    public boolean matches(Uri requestUri) {
        return matcher.isMatch(requestUri);
    }

    public Service matching() {
        // todo : matching logic
        return services.getFirst();
    }

    public Uri toProxyUri(Uri requestUri) {
        return matcher.toProxyUri(requestUri);
    }

    @Override
    public String toString() {
        return "Module{" +
                "matcher=" + matcher +
                ", services=" + services +
                ", subServices=" + subServices +
                ", moduleStatus=" + moduleStatus +
                '}';
    }
}

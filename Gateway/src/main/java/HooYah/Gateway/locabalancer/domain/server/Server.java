package HooYah.Gateway.locabalancer.domain.server;

import HooYah.Gateway.locabalancer.domain.service.Service;
import HooYah.Gateway.locabalancer.domain.vo.Host;
import HooYah.Gateway.locabalancer.domain.vo.Protocol;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private String name;
    private Protocol protocol;
    private Host host;

    private int maxCount;

    private List<Service> services = new ArrayList<>();

    public Server(String name, Host host, int maxCount) {
        this.name = name;
        this.host = host;
        this.maxCount = maxCount;
    }

    public void addService(Service service) {
        services.add(service);
    }

    public void deleteService(Service service) {
        services.remove(service);
    }

    public Host getHost() {
        return host;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Server{" +
                "name='" + name + '\'' +
                ", host=" + host +
                ", maxCount=" + maxCount +
                // ", services=" + services +
                '}';
    }
}

package HooYah.Gateway.locabalancer.domain.service;

import HooYah.Gateway.locabalancer.domain.server.Server;
import HooYah.Gateway.locabalancer.domain.vo.Host;
import HooYah.Gateway.locabalancer.domain.vo.Port;
import HooYah.Gateway.locabalancer.domain.vo.Url;

public class Service {

    private final boolean isRunning;

    private String name;

    private Server server;
    private Port port;

    private ServiceStatus lastStatus;

    private Service(String name, Server server, Port port, boolean isRunning) {
        this.name = name;
        this.server = server;
        this.port = port;
        this.isRunning = isRunning;

        if(isRunning)
            server.addService(this);
    }

    public static Service running(String name, Server server, Port port) {
        return new Service(name, server, port, true);
    }

    public static Service sub(String name, Server server, Port port) {
        return new Service(name, server, port, false);
    }

    public String getName() {
        return name;
    }

    public Url getUrl() {
        return new Url(server.getHost(), port);
    }

    public Host getHost() {
        return server.getHost();
    }

    public ServiceStatus getLastStatus() {
        return lastStatus;
    }

    public Port getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "Service{" +
                "isRunning=" + isRunning +
                ", name='" + name + '\'' +
                ", server=" + server +
                ", port=" + port +
                ", lastStatus=" + lastStatus +
                '}';
    }
}

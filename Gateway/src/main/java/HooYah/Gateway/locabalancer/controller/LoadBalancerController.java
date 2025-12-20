package HooYah.Gateway.locabalancer.controller;

import HooYah.Gateway.locabalancer.conf.Config;
import HooYah.Gateway.locabalancer.domain.module.Module;
import HooYah.Gateway.locabalancer.domain.module.Modules;
import HooYah.Gateway.locabalancer.domain.server.Server;
import HooYah.Gateway.locabalancer.domain.service.Service;
import HooYah.Gateway.locabalancer.domain.vo.Api;
import HooYah.Gateway.locabalancer.domain.vo.Uri;
import java.util.List;

public class LoadBalancerController {

    private final List<Server> servers;
    private final Modules modules;

    public LoadBalancerController() {
        Config config = Config.getInstance();

        servers = config.getServerConfig().getServers();
        modules = config.getServerConfig().getModules();
    }

    public Api loadBalance(String uri) {
        Uri requestUri = new Uri(uri);

        Module machtedModule = modules.matching(requestUri);
        Service matchedService = machtedModule.matching();

        return new Api(matchedService.getProtocol(), matchedService.getUrl(), machtedModule.toProxyUri(requestUri));
    }

}

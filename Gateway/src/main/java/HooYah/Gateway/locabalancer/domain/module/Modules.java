package HooYah.Gateway.locabalancer.domain.module;

import HooYah.Gateway.locabalancer.domain.service.Service;
import HooYah.Gateway.locabalancer.domain.vo.Uri;
import java.util.List;

public class Modules {

    private List<Module> modules;

    public Modules(List<Module> modules) {
        this.modules = modules;
    }

    public Module matching(Uri requestUri) {
        for(Module module : modules) {
            if(module.matches(requestUri)) {
                return module;
            }
        }

        throw new IllegalUriException(requestUri);
    }

}

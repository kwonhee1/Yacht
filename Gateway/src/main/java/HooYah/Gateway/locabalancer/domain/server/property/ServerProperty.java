package HooYah.Gateway.locabalancer.domain.server.property;

import HooYah.Gateway.locabalancer.domain.server.Server;
import HooYah.Gateway.locabalancer.domain.vo.Host;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(value = { "docker" })
public class ServerProperty {

    private String name;
    private String host; // protocol, ip
    private int count;

    public Server toServer() {
        return new Server(name, new Host(host), count);
    }

}

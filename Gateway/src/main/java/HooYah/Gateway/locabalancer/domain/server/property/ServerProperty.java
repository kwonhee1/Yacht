package HooYah.Gateway.locabalancer.domain.server.property;

import HooYah.Gateway.locabalancer.domain.server.Server;
import HooYah.Gateway.locabalancer.domain.vo.Host;
import HooYah.Gateway.locabalancer.domain.vo.Protocol;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(value = { "docker" })
public class ServerProperty {

    private String name;
    private String host;
    private String protocol;
    private int count;

    public Server toServer() {
        Protocol protocolEnum = Protocol.getProtocol(protocol);
        return new Server(name, protocolEnum, new Host(host), count);
    }

}

package HooYah.Gateway.locabalancer.conf;

import HooYah.Gateway.locabalancer.domain.module.Modules;
import HooYah.Gateway.locabalancer.domain.module.Module;
import HooYah.Gateway.locabalancer.domain.module.property.ModuleProperty;
import HooYah.Gateway.locabalancer.domain.server.Server;
import HooYah.Gateway.locabalancer.domain.server.property.ServerProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerConfig {

    private final YamlReader yamlReader;

    private final List<Server> servers;
    private final Modules modules;

    private Logger logger = LoggerFactory.getLogger(ServerConfig.class);

    public ServerConfig() throws IOException {
        yamlReader = new YamlReader();
        servers = initServers();
        modules = initModules();
    }

    public List<Server> getServers() {
        return servers;
    }

    public Modules getModules() {
        return modules;
    }

    private List<Server> initServers() {
        List<ServerProperty> serverProperties = yamlReader.getValueList("servers", ServerProperty.class);
        List<Server> serverList = serverProperties.stream()
                .map(ServerProperty::toServer)
                .toList();

        for(Server server : serverList) {
            logger.info(server.toString());
        }

        return serverList;
    }

    private Modules initModules() {
        List<ModuleProperty> moduleProperties = yamlReader.getValueList("modules", ModuleProperty.class);
        List<Module> moduleList = moduleProperties.stream()
                .map(f->f.toModule(servers))
                .toList();

        for(Module module : moduleList) {
            logger.info(module.toString());
        }

        return new Modules(moduleList);
    }

    class YamlReader {
        private static final String APPLICATION_YML = "servers.yml";

        private Map<String, String> ymlValue;
        private ObjectMapper jsonMapper = new ObjectMapper();

        public YamlReader() throws IOException {
            init();
        }

        private void init() throws IOException {
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

            InputStream inputStream = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream(APPLICATION_YML);

            ymlValue = yamlMapper.readValue(inputStream, HashMap.class);
        }

        public <T> T getValue(String propertyPath, Class<T> clazz) {
            return jsonMapper.convertValue(ymlValue.get(propertyPath), clazz);
        }

        public <T> List<T> getValueList(String propertyPath, Class<T> clazz) {
            List<String> valueStr = getValue(propertyPath, List.class); // 이때 이미 List<Map> 형식으로 모두 변환이 되어있는 상태 --> 때문에 애당초 List<String>에 type 오류가 나야하지만 컴파일러가 잡지 못함 (runtime Exception 발생함
            List<T> result = new ArrayList<>();

            for(Object value : valueStr) {
                result.add(jsonMapper.convertValue(value, clazz)); // map to Object
            }

            return result;
        }
    }

}

package HooYah.Gateway.gateway.handler;

import HooYah.Gateway.gateway.AttributeConfig;
import HooYah.Gateway.locabalancer.conf.Config;
import HooYah.Gateway.locabalancer.conf.ServerConfig;
import HooYah.Gateway.locabalancer.controller.LoadBalancerController;
import HooYah.Gateway.locabalancer.domain.vo.Api;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URIHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final LoadBalancerController loadBalancerController;

    public URIHandler(LoadBalancerController loadBalancerController) {
        super(false);
        this.loadBalancerController = loadBalancerController;
    }

    private Logger logger = LoggerFactory.getLogger(URIHandler.class);
    private final static ServerConfig serverConfig = Config.getInstance().getServerConfig();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        String requestUri = msg.uri();

        Api proxy = loadBalancerController.loadBalance(requestUri);

        ctx.channel().attr(AttributeConfig.Host).set(proxy.getHost());
        ctx.channel().attr(AttributeConfig.Port).set(proxy.getPort());
        msg.setUri(proxy.toProxyUri());

        logger.info("proxy " + requestUri + " -> " + proxy.toString());

        ctx.fireChannelRead(msg);
    }
}

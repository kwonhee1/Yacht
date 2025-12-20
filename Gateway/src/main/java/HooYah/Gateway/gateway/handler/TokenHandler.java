package HooYah.Gateway.gateway.handler;

import HooYah.Gateway.util.JWTUtil;
import io.jsonwebtoken.JwtException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private Logger logger = LoggerFactory.getLogger(TokenHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        setUserId(msg);

        ctx.fireChannelRead(msg);
    }

    private void setUserId(FullHttpRequest msg) {
        String authHeaders = msg.headers().get("Authorization");

        if (authHeaders == null || !authHeaders.startsWith("Bearer ")) {
            logger.info("no Authorization header");
            return;
        }

        String token = authHeaders.substring(7);
        Optional<Long> userId = getUserIdFromToken(token);

        if (userId.isEmpty()) {
            logger.info("no userId found from db");
            return;
        }

        logger.info("userId : " + userId.get());
        msg.headers().set("UserId", userId.get());
        msg.headers().set("Authorization", null);
    }

    private Optional<Long> getUserIdFromToken(String token) {
        try {
            Long userId = JWTUtil.decodeToken(token);
            return Optional.of(userId);
        } catch (JwtException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}

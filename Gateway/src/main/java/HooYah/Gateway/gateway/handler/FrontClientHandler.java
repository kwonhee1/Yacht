package HooYah.Gateway.gateway.handler;

import HooYah.Gateway.gateway.AttributeConfig;
import HooYah.Gateway.locabalancer.conf.Config;
import HooYah.Gateway.locabalancer.conf.ServerConfig;
import HooYah.Gateway.locabalancer.domain.vo.Uri;
import HooYah.Gateway.locabalancer.domain.vo.Url;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrontClientHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private Logger log = LoggerFactory.getLogger(FrontClientHandler.class);

    public FrontClientHandler() {
        super(false);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        String toHost = ctx.channel().attr(AttributeConfig.Host).get();
        int toPort = ctx.channel().attr(AttributeConfig.Port).get();

        Bootstrap b = new Bootstrap();
        b.group(ctx.channel().eventLoop())
                .channel(NioSocketChannel.class)
                .remoteAddress(toHost, toPort)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new HttpClientCodec());
                        p.addLast(new HttpObjectAggregator(1048576)); // 최대 1MB
                        p.addLast(new BackendHandler(ctx.channel(), msg));
                    }
                });


        ChannelFuture future = b.connect();
        future.addListener((ChannelFutureListener) f -> {
            if (!f.isSuccess()) {
                closeOnFlush(ctx.channel());
            }
        });
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.read();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        closeOnFlush(ctx.channel());
    }

    static void closeOnFlush(Channel ch) {
        if (ch.isActive()) {
            ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

}
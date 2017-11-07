package study.spring.webflux.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.ipc.netty.http.server.HttpServer;

/**
 * @author: lihong58
 * @date: 2017/11/1
 * description: ${DESCRIPTION}
 */
@Configuration
public class HttpServerConfig {

    private final Environment environment;

    @Autowired
    public HttpServerConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public HttpServer httpServer(RouterFunction<?> routerFunction) {
        // 转化为通用的Reactive HttpHandler
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(routerFunction);
        // 适配成Netty Server所需的Handler
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        // 创建Netty Server
        HttpServer server = HttpServer.create("localhost", Integer.valueOf(environment.getProperty("server.port")));
        // 注册Handler并启动Netty Server
        server.newHandler(adapter);
        return server;
    }
}

package cn.wolfcode.web.commons.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 配置WebSocket消息代理端点，即stomp服务端
 * @author Eastern unbeaten
 * @email chenshiyun2011@163.com
 * @date 2019-07-06 00:49
 */
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * 为了连接安全，setAllowedOrigins设置的允许连接的源地址，如果在非这个配置的地址下发起连接会报403，
         * 进一步还可以使用addInterceptors设置拦截器，来做相关的鉴权操作
         */
        registry.addEndpoint("/websocket")
//                .setAllowedOrigins("http://localhost:8976")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}

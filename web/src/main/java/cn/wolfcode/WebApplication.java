package cn.wolfcode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import java.util.concurrent.Executor;


/**
 * @author Eastern unbeaten
 * @email chenshiyun2011@163.com
 * @data 2019/3/31
 */
@SpringBootApplication
@EnableWebSocketMessageBroker
@EnableCaching
@MapperScan("cn.wolfcode.web.modules.*.mapper")
public class WebApplication extends AsyncConfigurerSupport {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("nebula-web-");
        executor.initialize();
        return executor;
    }


}

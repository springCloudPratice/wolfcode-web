package cn.wolfcode.web.commons.websocket;

import cn.wolfcode.web.commons.config.SystemConfig;
import cn.wolfcode.web.modules.log.LoggerMessage;
import cn.wolfcode.web.modules.log.LoggerQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 通过websocket将日志推送到已订阅的浏览器
 *
 * @author Eastern unbeaten
 * @email chenshiyun2011@163.com
 * @date 2019-07-06 00:49
 */
@Component
@Profile("test")
@Slf4j
public class LogMonitor {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private SystemConfig systemConfig;

    int info = 1;

    @Scheduled(fixedRate = 10000)
    public void outputLogger() {
        System.out.println(systemConfig.getName());
        log.info(",测试日志输出了" + info++);
    }

    /**
     * 推送日志到/topic/pullLogger
     */
    @PostConstruct
    public void pushLogger() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Runnable runnable = () -> {
            while (true) {
                try {
                    LoggerMessage log = LoggerQueue.getInstance().poll();
                    if (log != null) {
                        messagingTemplate.convertAndSend("/topic/pullLogger", log);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        executorService.submit(runnable);
        executorService.submit(runnable);
    }
}

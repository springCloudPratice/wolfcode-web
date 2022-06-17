package cn.wolfcode.web.modules.log;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 创建一个阻塞队列，作为日志系统输出的日志的一个临时载体
 *
 * @author Eastern unbeaten
 * @email chenshiyun2011@163.com
 * @date 2019-07-06 00:49
 */
@Log4j2
public class LoggerQueue {
    /**
     * 队列大小
     */
    private static final int QUEUE_MAX_SIZE = 10000;
    private static LoggerQueue alarmMessageQueue = new LoggerQueue();
    /**
     * 阻塞队列
     */
    private BlockingQueue blockingQueue = new LinkedBlockingQueue<>(QUEUE_MAX_SIZE);

    private LoggerQueue() {
    }

    public static LoggerQueue getInstance() {
        return alarmMessageQueue;
    }

    /**
     * 消息入队
     * @param log
     * @return
     */
    public boolean push(LoggerMessage log) {
        //队列满了就抛出异常，不阻塞
        return this.blockingQueue.add(log);
    }
    /**
     * 消息出队
     * @return
     */
    public LoggerMessage poll() {
        LoggerMessage result = null;
        try {
            result = (LoggerMessage)this.blockingQueue.take();
        } catch (InterruptedException e) {
            log.error("消息队列poll异常");
            e.printStackTrace();
        }
        return result;
    }
}

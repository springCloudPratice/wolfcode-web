package cn.wolfcode.web.modules.log;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 日志消息实体
 *
 * @author Eastern unbeaten
 * @email chenshiyun2011@163.com
 * @date 2019-07-06 00:49
 */
@Getter
@Setter
@ToString
public class LoggerMessage {
    private String body;
    private String timestamp;
    private String threadName;
    private String className;
    private String level;

    public LoggerMessage(String body, String timestamp, String threadName, String className, String level) {
        this.body = body;
        this.timestamp = timestamp;
        this.threadName = threadName;
        this.className = className;
        this.level = level;
    }

    public LoggerMessage() {
    }
}

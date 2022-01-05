package monster.helloworld.gdflbd.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * 日志的格式化器，实现对日志格式的自定义输出
 */
public class AppStartLogFormat extends Formatter {

    Long timeStamp = 0L;

    public AppStartLogFormat() {
    }

    public AppStartLogFormat(Long timeStamp) {
        setTimeStamp(timeStamp);
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String format(LogRecord record) {

        StringBuilder stringBuilder = new StringBuilder(); // 用于存放日志主体

        // 获取时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date logTime = new Date(this.timeStamp);
        String dateTimeStr = sdf.format(logTime);

        // 前两节：日期时间
        stringBuilder.append(dateTimeStr).append(" ");

        // 第三节：方法名
        stringBuilder.append("[").append(record.getSourceMethodName()).append("] ");

        // 第四节：日志级别
        stringBuilder.append(record.getLevel()).append(" ");

        // 第五节：类名
        stringBuilder.append(AppStartLogger.class.toString().replaceAll("class", "")).append(" - ");

        // 第六节：消息本体
        stringBuilder.append(record.getMessage());
        // 换行
        stringBuilder.append("\r\n");

        return stringBuilder.toString();
    }

//    @Override
//    public String getHead(Handler h) {
//        return "\r\n";  // 在日志（文件）头部输出的信息
//    }
//
//    @Override
//    public String getTail(Handler h) {
//        return "\r\n";  // 在日志（文件）尾部输出的信息
//    }


}

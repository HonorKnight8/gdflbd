package monster.helloworld.gdflbd.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * 执行日志输出的类
 */
public class AppStartLogger implements LoggerInterface {
    private final Logger logger = Logger.getLogger(AppStartLogger.class.toString());

    private String threadName;

    public AppStartLogger(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void logToFile(String logFilePath, long timeStamp, String message) {
        Logger loggerToFile = Logger.getLogger(this.threadName);
        // System.out.println(loggerToFile);
        // 不同线程创建的本类的实例是不一样的
        // 但 Logger.getLogger("") 创建的 logger 实例却是同一个
        // 应该是传入的参数一样，就使用同一个实例

//        ConsoleHandler consoleHandler = new ConsoleHandler(); // 创建 ConsoleHandler
//        AppStartLogFormat appStartLogFormat = new AppStartLogFormat(); // 创建 日志格式对象
//        consoleHandler.setFormatter(appStartLogFormat);          // 给 Handler 设置日志格式

        // 第二个参数 ture ，表示追加写入文件
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler(logFilePath, true);
        } catch (IOException e) {
            this.logger.severe("！！！创建写入日志文件的 Handler 出错！");
            e.printStackTrace();
        }
        AppStartLogFormat appStartLogFormat = new AppStartLogFormat(timeStamp); // 创建日志格式化器
        fileHandler.setFormatter(appStartLogFormat);    // 设置自定义的日志格式化器

        loggerToFile.addHandler(fileHandler);           // 添加 Handler
        loggerToFile.setUseParentHandlers(false);       // 去掉继承而来的默认 Handler（控制台不输出）

        loggerToFile.info(message);

        fileHandler.close();
        loggerToFile=null;
    }
}

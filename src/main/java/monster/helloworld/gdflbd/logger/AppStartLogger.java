package monster.helloworld.gdflbd.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class AppStartLogger {
    private static final Logger logger = Logger.getLogger(AppStartLogger.class.toString());

    public static void logToFile(String logFilePath, long timeStamp, String message) {
        Logger loggerToFile = Logger.getLogger(AppStartLogger.class.toString());

//        ConsoleHandler consoleHandler = new ConsoleHandler(); // 创建 ConsoleHandler
//        AppStartLogFormat appStartLogFormat = new AppStartLogFormat(); // 创建 日志格式对象
//        consoleHandler.setFormatter(appStartLogFormat);          // 给 Handler 设置日志格式

        // 第二个参数 ture ，表示追加写入文件
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler(logFilePath, true);
        } catch (IOException e) {
            logger.severe("创建写入日志文件的 Handler 出错！");
            e.printStackTrace();
        }
        AppStartLogFormat appStartLogFormat = new AppStartLogFormat();
        fileHandler.setFormatter(appStartLogFormat);
        loggerToFile.addHandler(fileHandler);
        // 去掉继承而来的默认 Handler（控制台不输出）
        loggerToFile.setUseParentHandlers(false);

        appStartLogFormat.setTimeStamp(timeStamp);
        loggerToFile.info(message);

        fileHandler.close();
    }
}

import monster.helloworld.gdflbd.logger.AppStartLogFormat;
import monster.helloworld.gdflbd.logger.AppStartLogger;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class TestAppStartLogger  {
    @Test
    public void testAppStartLogFormat() {

        // java.util.logging 最简示例
        Logger logger =Logger.getLogger(AppStartLogFormat.class.toString());
        logger.info("test");

        Logger myLogger = Logger.getLogger("myLogger"); // 创建 Logger 对象

        ConsoleHandler consoleHandler = new ConsoleHandler(); // 创建 ConsoleHandler
        AppStartLogFormat appStartLogFormat = new AppStartLogFormat(); // 创建 日志格式对象
        consoleHandler.setFormatter(appStartLogFormat);          // 给 Handler 设置日志格式

        myLogger.addHandler(consoleHandler); //
        // 去掉继承而来的默认 Handler
        //      否则会在控制台重复输出日志，默认的一条未格式化，另一条按照预期使用自定义格式进行格式化
        myLogger.setUseParentHandlers(false);

        // 如果需要将日志文件写到文件系统中，需要创建一个FileHandler对象
        //      第二个参数 ture ，表示追加写入文件
        // FileHandler fileHandler = new FileHandler("D:\\test\\gdflbd\\1222test\\test.log" , true);
        // fileHandler.setFormatter(new MyLoggerFormat());
        // myLogger.addHandler(fileHandler);

        appStartLogFormat.setTimeStamp(1595299643027L);
        myLogger.severe("{SERVER|严重 级别}");
        appStartLogFormat.setTimeStamp(1595309484329L);
        myLogger.warning("{WARN|警告 级别}");
        appStartLogFormat.setTimeStamp(1595299643027L);
        myLogger.info("{INFO|信息 级别}");

        myLogger.config("{默认config级别及以下不显示}");
        // 日志等级：
        //    SEVERE (highest value)
        //    WARNING
        //    INFO
        //    CONFIG
        //    FINE
        //    FINER
        //    FINEST (lowest value)

    }


    @Test
    public void testAppStartLogger() throws IOException {


        File targetFile = new File("D:\\test\\gdflbd\\1223test\\1.log");
        File parentFolder = new File(targetFile.getParent());
        if(!parentFolder.exists()){
            parentFolder.mkdirs();
        }
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }


        AppStartLogger.logToFile("D:\\test\\gdflbd\\1223test\\1.log",1595309484329L,"test");

    }
}

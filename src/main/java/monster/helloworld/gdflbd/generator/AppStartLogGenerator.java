package monster.helloworld.gdflbd.generator;

import monster.helloworld.gdflbd.datatype.AppStartLog;
import monster.helloworld.gdflbd.logger.AppStartLogger;

public class AppStartLogGenerator {

    private static AppStartLogger appStartLogger = new AppStartLogger();

    public static String getLogMessage(long startTime, int lastID, boolean isNewDevice){
        AppStartLog appStartLog = new AppStartLog();

        String logMessage = appStartLog.getLogMessage(startTime, lastID, isNewDevice);

        appStartLog = null;
        return logMessage;
    }

    public static void logToFile(String logFilePath, long timeStamp, String message){
        appStartLogger.logToFile(logFilePath, timeStamp, message);
    }


    public static void close(){
        appStartLogger = null;

    }


}

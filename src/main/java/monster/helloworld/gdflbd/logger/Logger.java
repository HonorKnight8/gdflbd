package monster.helloworld.gdflbd.logger;

public interface Logger {
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AppStartLogger.class.toString());

    void logToFile(String logFilePath, long timeStamp, String message);

}

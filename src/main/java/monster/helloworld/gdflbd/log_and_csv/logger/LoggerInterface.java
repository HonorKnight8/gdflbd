package monster.helloworld.gdflbd.log_and_csv.logger;

public interface LoggerInterface {

    void logToFile(String logFilePath, long timeStamp, String message);

}

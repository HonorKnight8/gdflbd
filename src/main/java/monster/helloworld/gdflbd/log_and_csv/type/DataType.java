package monster.helloworld.gdflbd.log_and_csv.type;

import java.util.Random;

public interface DataType {
    Random random = new Random();
    String getLogMessage(long timeStamp, int lastID, boolean isNewDevice);
}

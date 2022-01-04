package monster.helloworld.gdflbd.datatype;

import java.util.Random;

public interface DataType {
    Random random = new Random();
    String getLogMessage(long timeStamp, int lastID, boolean isNewDevice);
}

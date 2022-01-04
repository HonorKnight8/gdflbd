package monster.helloworld.gdflbd.generator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import monster.helloworld.gdflbd.constants.GdflbdConstant;
import monster.helloworld.gdflbd.datatype.AppStartLog;
import monster.helloworld.gdflbd.logger.AppStartLogger;

import java.io.File;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class AppStartLogOneDayThread implements Runnable {
    private static final Logger logger = Logger.getLogger(AppStartLogOneDayThread.class.toString());

    private String[] params;

    public AppStartLogOneDayThread(String[] params) {
        this.params = params;
    }

    ReentrantLock reentrantLock = new ReentrantLock();  // 用于线程同步锁

    @Override
    public synchronized void run() {
        Thread thread = Thread.currentThread(); // 本线程对象，用于控制台输出提示信息
        thread.setName("Thread:" + params[3]);
        // 线程开始工作
        logger.info("___线程：“" + thread.getName() + "” 开始工作...");

        System.out.println(Arrays.toString(params));
        // [D:\test\gdflbd\0104test\m, AppStartLog, huge, 2022-01-05, 12, 1973703, 2075699]

        String targetPath = params[0];      // 输出路径
        String dateType = params[1];        // 数据集类型
        // String dataScale = params[2];       // 数据集规模
        LocalDate localDate = LocalDate.parse(params[3]);       // “起始日期”
        int lastIDYesterday = Integer.parseInt(params[4]);       // 昨天的最大会员ID
        int lastIDToday = Integer.parseInt(params[5]);           // 今天的最大会员ID
        int totalCount = lastIDToday - lastIDYesterday;
        // System.out.println(" " + lastIDYesterday + " " + lastIDToday + " " + totalCount);
        // System.exit(99);
        // 当天凌晨 0 点
        Long startTime =
                localDate.atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
        // 次日凌晨 0 点
        Long endTime =
                localDate.plusDays(1).atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli();

        // 取得输出文件名的日期部分
        Instant instant = Instant.ofEpochMilli(startTime);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMdd");
        String fileNameDate = dateTimeFormatter.format(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
        // System.out.println(fileNameDate);

        // 创建目标文件（一天一个文件）
        File targetFile = new File(
                targetPath + File.separator + getFileNamePrefix(dateType) + fileNameDate + ".log");
        // System.out.println(targetFile);
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                logger.severe("！！！创建目标文件出错！");
                e.printStackTrace();
            }
        }

        int currentID = lastIDYesterday;
        long currentTime = startTime;
        while (currentTime < endTime) {
            // 执行数据集生成逻辑：每次循环，生成一条数据

            // System.out.println(currentTime + "|" + currentID); //测试
            // lastIDYesterday++; //测试

            String logMessage = "";
            if (currentID < lastIDToday &&
                    simulateNewDeviceOrNot(lastIDToday - currentID, totalCount, endTime - currentTime)
            ) {
                // 模拟新设备
                System.out.println(thread.getName() + "模拟新用户");
                currentID++;
                System.out.println(currentID);
                reentrantLock.lock();
                logMessage = getLogMessage(startTime, currentID, true);
                reentrantLock.unlock();

            } else {
                System.out.println(thread.getName() + "模拟老用户");
                reentrantLock.lock();
                logMessage = getLogMessage(startTime, currentID, false);
                reentrantLock.unlock();
            }

            // System.out.println(logMessage);
            reentrantLock.lock();
            // 输出到目标日志文件
            logToFile(targetFile.getPath(), startTime, logMessage);
            reentrantLock.unlock();
            break; // 开发测试，每天只写一条
            // 执行步进
//            startTime = startTime + DataScaleUtil.stepOn(dataScale.toLowerCase());
        }

//        try {
//        // 延时2秒，改善测试时控制台输出效果
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        // 线程工作完成
        logger.info("___线程：“" + thread.getName() + "” 工作结束。");
    }


    private synchronized String getLogMessage(long startTime, int lastID, boolean isNewDevice) {
        AppStartLog appStartLog = new AppStartLog();

        String logMessage = appStartLog.getLogMessage(startTime, lastID, isNewDevice);

        appStartLog = null; // 释放资源
        return logMessage;
    }

    private synchronized void logToFile(String logFilePath, long timeStamp, String message) {
        AppStartLogger appStartLogger = new AppStartLogger();
        appStartLogger.logToFile(logFilePath, timeStamp, message);
        appStartLogger = null; // 释放资源
    }


    /**
     * 根据不同的数据集类型，获取相应的文件民前缀
     *
     * @param dataType
     * @return
     */
    private synchronized String getFileNamePrefix(String dataType) {
//        System.out.println(GdflbdConstant.FILE_NAME_PREFIX_JSON);
        JSONObject jsonObject = JSON.parseObject(GdflbdConstant.FILE_NAME_PREFIX_JSON);
        Map<String, Object> innerMap = jsonObject.getInnerMap();
//        System.out.println(innerMap);
        return innerMap.get(dataType).toString();
    }

    private synchronized boolean simulateNewDeviceOrNot(int remainingCount, int totalCount, long remainingTime) {

        // 根据 剩余新ID数量 / 当天新ID数 、 剩余时间 / 一天总时间 的比率大小
        // 前者大，则返回真，反之，则返回假
//        System.out.println(remainingCount+"|"+totalCount+"|"+remainingTime);
//        System.out.println( 1.0*(remainingCount-1) / totalCount);
//        System.out.println( 1.0*(remainingTime-1) / 86400000);

        if (1.0 * remainingCount / totalCount >= 1.0 * remainingTime / 86400000) {
            return true;
        } else {
            return false;
        }

    }
}

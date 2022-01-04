package monster.helloworld.gdflbd.generator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import monster.helloworld.gdflbd.constants.GdflbdConstant;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

public class GenerateDatasets2 {
    private static final Logger logger = Logger.getLogger(GenerateDatasets2.class.toString());

    private static final Random random = new Random();

    /**
     * 程序的核心方法
     *
     * @param args
     */
    public static void generateData(String[] args) {
        String targetPath = args[0];    // 输出路径
        String dateType = args[1];      // 数据集类型
        String dataScale = args[2];     // 数据集规模
        LocalDate localDate = LocalDate.parse(args[3]);     // “起始日期”
        Integer daysQuantity = Integer.parseInt(args[4]);   // 天数

        int lastID = getLastID(dataScale.toLowerCase()); // 初始化 lastID 的值

        // 通过反射创建 不同数据集类型 的 Generator
        Class dataTypeClass = null;
        try {
            dataTypeClass = Class.forName("monster.helloworld.gdflbd.generator." + dateType + "Generator");
            // System.out.println(dataTypeClass);
        } catch (ClassNotFoundException e) {
            logger.severe("创建数据集类型对象出错！");
            e.printStackTrace();
            System.exit(99);
        }


        // 每次循环，生成一天的数据集
        for (int i = 0; i < daysQuantity; i++) {
            // 当天凌晨 0 点
            Long startTime =
                    localDate.plusDays(i).atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
            // 次日凌晨 0 点
            Long endTime =
                    localDate.plusDays(i + 1).atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli();

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
                    logger.severe("创建目标文件出错！");
                    e.printStackTrace();
                }
            }


            //int testQuantity = 0; //测试
            while (startTime < endTime) {
                // 执行数据集生成逻辑：每次循环，生成一条数据

                // System.out.println(startTime + "|" + testQuantity); //测试
                // testQuantity++; //测试

                boolean isNewDevice = false; // 用于存放用户是否新设备（新用户）的标记
                if (random.nextInt(100) > 90) {
                    // 10% 模拟新用户启动app逻辑
                    lastID = lastID + 1;
                    isNewDevice = true;
                }
                // 其余（90%）模拟老用户启动app逻辑
                // 这个模拟方式出来的数据结果也不够自然（呈线性减少），需要改进

                String logMessage = "";
                try {
                    // 通过反射调用相应的方法，获取 logMessage（日志主体）
                    Method getLogMessage =
                            dataTypeClass.getDeclaredMethod("getLogMessage", long.class, int.class, boolean.class);
                    logMessage =
                            (String) getLogMessage.invoke(dataTypeClass, startTime, lastID, isNewDevice);
                    System.out.println(logMessage);

                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    logger.severe("获取 logMessage 出错！");
                    e.printStackTrace();
                    System.exit(99);
                }

                // 输出到目标日志文件
                try {
                    // 通过反射调用相应的方法，将 logMessage（日志主体）输出到日志文件
                    Method logToFile =
                            dataTypeClass.getDeclaredMethod("logToFile", String.class, long.class, String.class);
                    logToFile.invoke(dataTypeClass, targetFile.getPath(), startTime, logMessage);

//                    System.exit(99);

                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    logger.severe("输出到目标日志文件出错！");
                    e.printStackTrace();
                    System.exit(99);
                }

//                AppStartLogger.logToFile(targetFile.getPath(), startTime, logMessage);

//                 break; // 开发测试，每天只写一条
                // 执行步进
                startTime = startTime + stepOn(dataScale.toLowerCase());
            }
        }
    }

    /**
     * 根据数据集规模，决定 “步进” ，以不同的生成间隔，实现输出不同规模大小的数据集
     * 五种规模：Tiny Small Medium Large Huge
     * 相应的应用场景：[开发（本项目开发），大数据项目学习和开发，均衡，大数据项目演示，压力测试（开发环境）]
     * 生成日志数量（每日）设定为： [500，5千，2万，20万，100万]（大约）
     * 一天 86400 秒，86400000 毫秒
     * 五种规模依次对应步进时间为：[172800，17280，4320，432，86.4]（平均，单位：豪秒）
     * 反序，换算为倍率，大约是：[stepInterval 步进间隔 i = 81-91 毫秒，5i，50i，200i，2000i]
     *
     * @param dataScale
     * @return
     */
    private static long stepOn(String dataScale) {

        int stepInterval = random.nextInt(10) + 81;

        switch (dataScale) {
            case "huge":
                // nothing;
                break;
            case "large":
                stepInterval = stepInterval * 5;
                break;
            case "medium":
                stepInterval = stepInterval * 50;
                break;
            case "small":
                stepInterval = stepInterval * 200;
                break;
            case "tiny":
                stepInterval = stepInterval * 2000;
                break;
            default:
                logger.info("数据集类型错误");
        }

        return stepInterval;
    }


    /**
     * 根据不同的数据集类型，获取相应的文件民前缀
     *
     * @param dataType
     * @return
     */
    private static String getFileNamePrefix(String dataType) {
//        System.out.println(GdflbdConstant.FILE_NAME_PREFIX_JSON);
        JSONObject jsonObject = JSON.parseObject(GdflbdConstant.FILE_NAME_PREFIX_JSON);
        Map<String, Object> innerMap = jsonObject.getInnerMap();
//        System.out.println(innerMap);
        return innerMap.get(dataType).toString();
    }


    /**
     * 根据数据集规模，生成 lastID（模拟当前系统中已有的用户）
     *
     * @param dataScale
     * @return
     */
    private static int getLastID(String dataScale) {

        int lastID = 1000;
        switch (dataScale) {
            case "tiny":
                // nothing;
                break;
            case "small":
                lastID = lastID * 10;
                break;
            case "medium":
                lastID = lastID * 40;
                break;
            case "large":
                lastID = lastID * 400;
                break;
            case "huge":
                lastID = lastID * 2000;
                break;
            default:
                logger.info("数据集类型错误");
        }

        return lastID;
    }
}

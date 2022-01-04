package monster.helloworld.gdflbd;

import monster.helloworld.gdflbd.utils.CheckArgs;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * 程序入口
 */
public class Entry {
    private static final Logger logger = Logger.getLogger(Entry.class.toString());

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // 检查参数合法性
        if (!CheckArgs.checkArgs(args)) {
            logger.warning("参数不合法，请检查！");
        } else {

            // 准备目录
            File targetFolder = new File(args[0]);
            if (!targetFolder.exists()) {
                targetFolder.mkdirs();
            }

            // 生成数据集的核心方法
//            GenerateDatasets.generateData(args);
            // 通过反射创建 "不同数据集类型" 的 Generator ，并调用其中的 generate 方法（核心方法）
            try {
                Class dataTypeClass = Class.forName("monster.helloworld.gdflbd.generator." + args[1] + "Generator");
                Method generate = dataTypeClass.getDeclaredMethod("generate", String[].class);
                generate.invoke(dataTypeClass, (Object) args);
                // System.out.println(dataTypeClass);
            } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                logger.severe("！！！创建数据集类型对象出错！");
                e.printStackTrace();
                System.exit(99);
            }
        }

//        System.out.println(999);

        // 要等所有线程结束
        long endTime = System.currentTimeMillis();
        logger.info(">>>执行参数：" + Arrays.toString(args));
        logger.info(">>>总耗时：" + (endTime - startTime) + " 毫秒");
    }
}

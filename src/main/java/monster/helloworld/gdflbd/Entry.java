package monster.helloworld.gdflbd;

import monster.helloworld.gdflbd.LogBuilder.GenerateDatasets;
import monster.helloworld.gdflbd.utils.CheckArgs;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * 程序入口
 */
public class Entry {
    private static final Logger logger = Logger.getLogger(GenerateDatasets.class.toString());

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // 检查参数合法性
        if (!CheckArgs.checkArgs(args)) {
            logger.warning("参数不合法，请检查！");
        } else {

            // 生成数据集的核心方法
            GenerateDatasets.generateData(args);
        }

        long endTime = System.currentTimeMillis();
        logger.info(Arrays.toString(args));
        logger.info(">>>总耗时：" + (endTime - startTime) + " 毫秒");
    }
}

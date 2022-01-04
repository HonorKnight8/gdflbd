package monster.helloworld.gdflbd.utils;

import monster.helloworld.gdflbd.generator.GenerateDatasets;

import java.util.*;
import java.util.logging.Logger;

/**
 * 五种规模：Tiny Small Medium Large Huge
 * 相应的应用场景：[开发（本项目开发），大数据项目学习和开发，均衡，大数据项目演示，压力测试（开发环境）]
 * 生成日志数量（每日）设定为： [500，5千，2万，20万，100万]（大约）
 */
public class DataScaleUtil {
    private static final Logger logger = Logger.getLogger(GenerateDatasets.class.toString());
    private static final Random random = new Random();

    // 数据集规模
    public static final String[] DATA_SCALE = new String[]{"Tiny", "Small", "Medium", "Large", "Huge"};

    // 生成日志数量（每日）设定为： [500，5千，2万，20万，100万]（大约）
    // public static final int[] DAILY_LOG_COUNT = {500, 5_000, 20_000, 200_000, 1_000_000};

    public static final Map<String, Integer> SCALE_LOG_COUNT = new HashMap<>();

    static {
        SCALE_LOG_COUNT.put("tiny", 500);
        SCALE_LOG_COUNT.put("small", 5_000);
        SCALE_LOG_COUNT.put("medium", 20_000);
        SCALE_LOG_COUNT.put("large", 200_000);
        SCALE_LOG_COUNT.put("huge", 1_000_000);
    }

    // （预期的）每日会员新增率
    public static final float MEMBER_INCREASE_RATE = 0.1f;


    /**
     * 如果是第一天，直接取规模对应的每日日志数
     * 其他时间，进行递增
     *
     * @return
     */
    public static int[] getLastIDs(String scale, int daysQuantity) {

        int[] lastIDs = new int[daysQuantity + 1]; // 存放每天的 LastID 的数组

//        System.out.println(SCALE_LOG_COUNT.get(scale.toLowerCase()));
//        System.out.println(SCALE_LOG_COUNT.get("tiny"));
//        System.out.println(SCALE_LOG_COUNT.get("small"));
//        System.out.println(SCALE_LOG_COUNT.get("medium"));
//        System.out.println(SCALE_LOG_COUNT.get("large"));
//        System.out.println(SCALE_LOG_COUNT.get("huge"));

        // 起始，直接取规模对应的每日日志数
        Integer baseIncrease = SCALE_LOG_COUNT.get(scale.toLowerCase());

        lastIDs[0] = baseIncrease;
        for (int i = 1; i <= daysQuantity; i++) {

            // 新增数
            int increaseCount = (int) Math.round(
                    baseIncrease *  MEMBER_INCREASE_RATE // 乘以 日增率
                            + baseIncrease * ((random.nextDouble() - 0.5) / 10) // 上下浮动 5%

            );
            // 上一天的 lastID + 新增数
            lastIDs[i] = lastIDs[i - 1] + increaseCount;

        }

        return lastIDs;
    }


    /**
     * 根据数据集规模，决定 “步进” ，以不同的生成间隔，实现输出不同规模大小的数据集
     * 一天 86400 秒，86400000 毫秒
     * 五种规模依次对应步进时间为：[172800，17280，4320，432，86.4]（平均，单位：豪秒）
     * 反序，换算为倍率，大约是：[stepInterval 步进间隔 i = 81-91 毫秒，5i，50i，200i，2000i]
     *
     * @param dataScale
     * @return
     */
    public static int stepOn(String dataScale) {
//        step Interval Proportion

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
}

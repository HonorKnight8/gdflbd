package monster.helloworld.gdflbd.generator;

import monster.helloworld.gdflbd.utils.DataScaleUtil;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AppStartLogGenerator {

    public static void generate(String[] args) {
        // System.out.println(Arrays.toString(args));
        String dataScale = args[2];                         // 数据集规模
        LocalDate localDate = LocalDate.parse(args[3]);     // “起始日期”
        Integer daysQuantity = Integer.parseInt(args[4]);   // 天数

        // 根据 数据集规模、天数 ，给每天生成最大 ID
        int[] lastIDs = DataScaleUtil.getLastIDs(dataScale, daysQuantity);
         System.out.println(Arrays.toString(lastIDs));

        // 创建线程池，最大并发线程数设为 10
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 1 ; i < lastIDs.length; i++){
            // 根据上面得到的 lastIDs ，每天开启一个线程

            // 传入 数据集类型、规模、日期、上一天lastID，今天LastID
            String[] params = new String[args.length + 1];
            System.arraycopy(args, 0, params, 0, args.length - 1); // 天数（最后一个元素）不要
            // [D:\test\gdflbd\0104test\m, AppStartLog, huge, 2021-12-25, null, null]
            params[3] =localDate.plusDays(i-1).toString();
            params[params.length-2] = String.valueOf(lastIDs[i - 1]);
            params[params.length-1] = String.valueOf(lastIDs[i]);
            // System.out.println(Arrays.toString(params));
            // System.exit(99);

            executorService.execute(new AppStartLogOneDayThread(params));
        }

        // 关闭线程池（不再接收新任务）
        executorService.shutdown();
        try {
            // 等待所有线程 结束 或 超时
            executorService.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 立即关闭线程池（不管是否还有未结束的任务）
        executorService.shutdownNow();

    }

}

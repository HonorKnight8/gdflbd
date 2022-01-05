package monster.helloworld.gdflbd.generator;

import monster.helloworld.gdflbd.utils.DataScaleUtil;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppStartLogGenerator {

    public void generate(String[] args) {
        // System.out.println(Arrays.toString(args));
        String dataScale = args[2];                         // 数据集规模
        LocalDate localDate = LocalDate.parse(args[3]);     // “起始日期”
        Integer daysQuantity = Integer.parseInt(args[4]);   // 天数

        // 根据 数据集规模、天数 ，给每天生成最大 ID
        int[] lastIDs = DataScaleUtil.getLastIDs(dataScale, daysQuantity);
        System.out.println(Arrays.toString(lastIDs));

        // 创建线程池，最大并发线程数设为 10
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 1; i < lastIDs.length; i++) {
            // 根据上面得到的 lastIDs ，每天开启一个线程

            // 传入 数据集类型、规模、日期、上一天lastID，今天LastID
            String[] params = new String[args.length + 1];
            System.arraycopy(args, 0, params, 0, args.length - 1); // 天数（最后一个元素）用不到
            // args：    [D:\test\gdflbd\0105test\t1, AppStartLog, tiny, 2021-12-25, 10]
            // params：  [D:\test\gdflbd\0105test\t1, AppStartLog, tiny, 2021-12-25, 500, 558]
            params[3] = localDate.plusDays(i - 1).toString();                              // 修改日期，每个线程执行一天的生成逻辑
            params[params.length - 2] = String.valueOf(lastIDs[i - 1]);                   // 昨天的 lastID
            params[params.length - 1] = String.valueOf(lastIDs[i]);                       // 今天的 lastID
            // System.out.println(Arrays.toString(params));
            // System.exit(99);

            executorService.execute(new AppStartLogOneDayThread(params));
        }

        // 关闭线程池（不再接收新任务）
        executorService.shutdown();
        //try {
        //    // 等待所有线程 结束 或 超时
        //    executorService.awaitTermination(60, TimeUnit.SECONDS);
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
        // 这样会直接在60秒后开始执行后面的代码，达不到等待线程结束的预期

        boolean isDone = false;
        do {
            if (AppStartLogOneDayThread.result.size() >= daysQuantity) {
                // 全部完成
                isDone = true;
            } else {
                // 延时检查
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } while (!isDone);

        // 立即关闭线程池（不管是否还有未结束的任务）
        executorService.shutdownNow();

    }

}

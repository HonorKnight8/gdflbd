package monster.helloworld.gdflbd.generator;

import monster.helloworld.gdflbd.Entry;
import monster.helloworld.gdflbd.dao.OrderDao;
import monster.helloworld.gdflbd.dao.OrderDaoImpl;
import monster.helloworld.gdflbd.utils.ArgsUtil;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

public class OrderDBGenerator {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private OrderDao orderDao = new OrderDaoImpl();


    private Map<String, Boolean> resultMap = new TreeMap<>();   // 结果集，记录进行中的订单

    private final Integer ORDER_COUNT_PER_THREAD = 200;

    public void generate(String[] args) {
        int length = args.length;
        System.out.println(Arrays.toString(ArgsUtil.params));
        // D:\test\gdflbd\0128test\orderdb\tiny OrderDB tiny 2021-12-25 2

        // 新建/重置 数据表
        orderDao.dropTable();
        orderDao.createTable();

        // 根据参数获得 起 止 时间戳（当线程）
        LocalDate localDate = LocalDate.parse(ArgsUtil.params[3]);       // “起始日期”
        // 当天凌晨 0 点
//        Long startTime =
//                localDate.atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
        // 结束日次日凌晨 0 点
        Long allEndTimeStamp =
                localDate.plusDays(Integer.valueOf(ArgsUtil.params[4])).atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli();

        // 每个线程负责 100 个订单，直到所有订单都最终完成或失效

        int threadNameSuffix = 1;
        while (true) {

            int startOrderID = (threadNameSuffix - 1) * ORDER_COUNT_PER_THREAD + 1;
            int endOrderID = threadNameSuffix * ORDER_COUNT_PER_THREAD;
            Long startTimeStamp;

            if (threadNameSuffix == 1) {
                // 第一个线程，从传入参数的 localDate 获取启动时间戳
                startTimeStamp =
                        localDate.plusDays(threadNameSuffix - 1).atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
            } else {
                // 其他线程，从 OrderDBGeneratorThread.lastNewOrderTimeStamp 获取启动时间戳（上一个线程完成新建部分的任务的时间戳）
                startTimeStamp = OrderDBGeneratorThread.lastNewOrderTimeStamp.peek();
            }

            if (startTimeStamp > allEndTimeStamp
                    ||
                (OrderDBGeneratorThread.lastOperateTimeStamp.size() > 0
                        ? (OrderDBGeneratorThread.lastOperateTimeStamp.peek() >= allEndTimeStamp)
                        : false)
            ) {
                threadNameSuffix--; // 退出循环，本循环没创建线程 threadNameSuffix--
                break;
            }

//            System.out.println("___调试_主线程" +
//                    "\n\t变量：threadNameSuffix 的值：" + threadNameSuffix +
//                    "\n\t变量：startOrderID 的值：" + startOrderID +
//                    "\n\t变量：endOrderID 的值：" + endOrderID +
//                    "\n\t变量：startTimeStamp 的值：" + startTimeStamp +
//                    "\n\t变量：allEndTimeStamp 的值：" + allEndTimeStamp);

            OrderDBGeneratorThread orderDBGeneratorThread = new OrderDBGeneratorThread(startOrderID, endOrderID, startTimeStamp, allEndTimeStamp);

            Thread thread = new Thread(orderDBGeneratorThread, "Thread-" + threadNameSuffix);
            thread.start();

            do {
                try {
                    Thread.sleep(2_000);
//                    System.out.println(orderDBGeneratorThread.getCompleteCreateNewOrderFlag());
//                    wait(2_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (!orderDBGeneratorThread.getCompleteCreateNewOrderFlag());
            // 只要没完成新建的部分，就继续等待；完成了新建部分，就继续往下执行（进行下一次循环）

            threadNameSuffix++;

//            System.out.println("___调试_主线程" +
//                    "\n\t" + threadNameSuffix +
//                    "\n\t" + OrderDBGeneratorThread.finishedThread.size() +
//                    "\n\t" + OrderDBGeneratorThread.lastOperateTimeStamp.size() +
//                    "\n\t" + (OrderDBGeneratorThread.lastOperateTimeStamp.size() > 0 ? OrderDBGeneratorThread.lastOperateTimeStamp.peek() : null) +
//                    "\n\t" + allEndTimeStamp);
        }
//        while (
//                OrderDBGeneratorThread.lastOperateTimeStamp.size() == 0
//                        || OrderDBGeneratorThread.lastOperateTimeStamp.peek() < allEndTimeStamp
//        );

//        System.out.println("checkPoint");
        // 退出（创建线程的）循环后，判断是否所有线程都已经完成

        while (true) {
            System.out.println("检查是否结束");

            if (OrderDBGeneratorThread.finishedThread.size() == threadNameSuffix &&
                    OrderDBGeneratorThread.finishedThread.contains("Thread-" + threadNameSuffix)) {
                // 全部完成
                System.out.println("结束");
                break;
            } else {
                // 延时检查
                try {
                    Thread.sleep(2_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        Entry.report(System.currentTimeMillis());
    }
}

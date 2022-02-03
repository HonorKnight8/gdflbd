package monster.helloworld.gdflbd.generator;

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

    public void generate(String[] args) {
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

        // 主线程按照”预估订单数“（非数据库记录数）分配线程
        // 每个线程负责 100 个订单，直到所有订单都最终完成或失效

        int threadNameSuffix = 0;
        while (true) {
            int startOrderID = threadNameSuffix * 100 + 1;
            int endOrderID = (threadNameSuffix + 1) * 100;
            Long startTimeStamp;

            if (threadNameSuffix == 0) {
                // 第一个线程，从传入参数的 localDate 获取启动时间戳
                startTimeStamp =
                        localDate.plusDays(threadNameSuffix).atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
            } else {
                // 其他线程，从 OrderDBGeneratorThread.lastNewOrderTimeStamp 获取启动时间戳（上一个线程完成新建部分的任务的时间戳）
                startTimeStamp = OrderDBGeneratorThread.lastNewOrderTimeStamp.peek();
            }

            OrderDBGeneratorThread orderDBGeneratorThread = new OrderDBGeneratorThread(startOrderID, endOrderID, startTimeStamp, allEndTimeStamp);

            Thread thread = new Thread(orderDBGeneratorThread, "Thread-" + (threadNameSuffix + 1));
            thread.start();

            do {
                try {
                    Thread.sleep(5_000);
//                    wait(5_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (!orderDBGeneratorThread.getCompleteCreateNewOrderFlag());
            // 只要没完成新建的部分，就继续等待；完成了新建部分，就继续往下执行

            // 判断是否再开启一个线程
            if (OrderDBGeneratorThread.lastOperateTimeStamp.peek() > allEndTimeStamp) {
                break;
            }

            // （进入下一轮循环，开启下一个线程）
            threadNameSuffix++;
        }

    }


}

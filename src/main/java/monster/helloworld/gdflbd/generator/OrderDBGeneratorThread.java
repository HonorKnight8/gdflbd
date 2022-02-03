package monster.helloworld.gdflbd.generator;

import monster.helloworld.gdflbd.dao.OrderDao;
import monster.helloworld.gdflbd.dao.OrderDaoImpl;
import monster.helloworld.gdflbd.domain.Order;
import monster.helloworld.gdflbd.utils.ArgsUtil;
import monster.helloworld.gdflbd.utils.DataScaleUtil;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class OrderDBGeneratorThread implements Runnable {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private static final Random random = new Random();
    private OrderDao orderDao = new OrderDaoImpl();

    private Integer startOrderID;
    private Integer endOrderID;
    private Long startTimeStamp;
    private Long allEndTimeStamp;

    ReentrantLock reentrantLock = new ReentrantLock();  // 用于线程同步锁
//    reentrantLock.lock();
//
//    reentrantLock.unlock();

    // 每个线程完成所有新建任务后，设置标记，主线程获取到这个标记为true，才开启下一个线程
    private boolean completeCreateNewOrderFlag = false;                 // 记录本线程是否已完成了全部新订单创建任务的标记（不管是否完成了订单推进的任务）
    public static Stack<Long> lastNewOrderTimeStamp = new Stack<>();    // 记录最后一个新建 order 的时间戳，用于给下一个线程提供开始时间
    public static Stack<Long> lastOperateTimeStamp = new Stack<>();     // 记录线程结束时最后一次操作 order 的时间戳，用于给主线程判断是不是要再开启一个线程
    private Map<String, Boolean> resultMap = new TreeMap<>();           // 结果集，记录进行中的订单

    public boolean getCompleteCreateNewOrderFlag() {
        return completeCreateNewOrderFlag;
    }

    public OrderDBGeneratorThread(Integer startOrderID, Integer endOrderID, Long startTimeStamp, Long allEndTimeStamp) {
        this.startOrderID = startOrderID;
        this.endOrderID = endOrderID;
        this.startTimeStamp = startTimeStamp;
        this.allEndTimeStamp = allEndTimeStamp;
    }

    @Override
    public void run() {
        Thread thread = Thread.currentThread(); // 本线程对象，用于控制台输出提示信息
        logger.info("___线程：“" + thread.getName() + "” 开始工作...");

        int currentID = startOrderID;
        Long currentTimeStamp = startTimeStamp;
        int stepIntervalProportion = DataScaleUtil.stepOn(ArgsUtil.params[2].toLowerCase()); // 步进比例，不同数据集规模不同比例

        do {
            Order order;
            // 执行业务逻辑
            if (currentID > endOrderID || (random.nextInt(100) % 7) == 0) {
                // 执行账单推进，currentID 不变

                // order = orderDao.selectById(currentID); // 不能直接用 currentID
                // 从结果集中，随机取一个订单，对该订单进行推进
                List<String> keyList = new ArrayList(resultMap.keySet());
                System.out.println(keyList.size());
                int index = random.nextInt(keyList.size());
                String targetId = keyList.get(index); // 报错：bound must be positive
                order = orderDao.selectById(Integer.parseInt(targetId));
                // 执行推进
                order = OrderLifeCycleSimulator.pushOnOrder(order, currentTimeStamp);

            } else {
                // 执行新建账单，currentID 递增

                order = OrderLifeCycleSimulator.newOrder(currentID, currentTimeStamp);
                currentID++;
            }
            // 向数据库插入记录
            reentrantLock.lock();
            orderDao.insert(order);
            reentrantLock.unlock();

            // 记录结果
            if (order.getOrderStatus() == 0) {
                // 执行中的订单
                reentrantLock.lock();
                resultMap.put(order.getOrderId().toString(), false);
                reentrantLock.unlock();
            } else {
                // 取消的，或最终完成的订单
                reentrantLock.lock();
                resultMap.remove(order.getOrderId().toString());
                reentrantLock.unlock();
            }

            // 设置完成新订单创建任务的标记
            if ((!completeCreateNewOrderFlag) && currentID > endOrderID) {
                completeCreateNewOrderFlag = true;              // 记录本线程是否已完成了全部新订单创建任务的标记（不管是否完成了订单推进的任务）
                lastNewOrderTimeStamp.push(currentTimeStamp);   // 记录最后一个新建 order 的时间戳，用于给下一个线程提供开始时间
                // 线程完成了新建部分的工作
                logger.info("___线程：“" + thread.getName() + "” 完成了新建部分的工作。");
            }

            // 执行步进
            currentTimeStamp = currentTimeStamp + (long) stepIntervalProportion * (random.nextInt(10) + 81);
        } while ((currentID <= endOrderID || resultMap.containsValue(false)) && currentTimeStamp < allEndTimeStamp);
        reentrantLock.lock();
        lastOperateTimeStamp.push(currentTimeStamp);    // 记录线程结束时最后一次操作 order 的时间戳，用于给主线程判断是不是要再开启一个线程
        reentrantLock.unlock();

        // 线程工作完成
        logger.info("___线程：“" + thread.getName() + "” 工作结束。");
        // notifyAll();

    }
}

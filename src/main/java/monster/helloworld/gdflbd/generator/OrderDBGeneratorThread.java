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

    private final ReentrantLock reentrantLock = new ReentrantLock();  // 用于线程同步锁

    // 每个线程完成所有新建任务后，设置标记，主线程获取到这个标记为true，才开启下一个线程
    private boolean completeCreateNewOrderFlag = false;                 // 记录本线程是否已完成了全部新订单创建任务的标记（不管是否完成了订单推进的任务）
    public static Stack<Long> lastNewOrderTimeStamp = new Stack<>();    // 记录最后一个新建 order 的时间戳，用于给下一个线程提供开始时间
    public static Stack<Long> lastOperateTimeStamp = new Stack<>();     // 记录线程结束时最后一次操作 order 的时间戳，用于给主线程判断是不是要再开启一个线程
    public static ArrayList<String> finishedThread = new ArrayList<>(); // 记录已完成任务的线程
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
            if (resultMap.size() > 0
                    && (currentID > endOrderID || (random.nextInt(100) % 2) == 0)) {
                // 执行账单推进，currentID 不变

                // order = orderDao.selectById(currentID); // 不能直接用 currentID
                // 从结果集中，随机取一个订单，对该订单进行推进
                List<String> keyList = new ArrayList(resultMap.keySet());
//                System.out.println("___调试_线程：" + thread.getName() +
//                        "\n\t变量：keyList.size() 的值：" + keyList.size() +
//                        "\n\t变量：keyList 的值：" + keyList);
                int index = random.nextInt(keyList.size());
                String targetId = keyList.get(index);
//                System.out.println("___调试_线程：" + thread.getName() +
//                        "\n\t变量：index 的值：" + index +
//                        "\n\t变量：targetId 的值：" + targetId);

                order = orderDao.selectByOrderId(Integer.parseInt(targetId));
//                System.out.println("___调试_线程：" + thread.getName() +
//                        "\n\t推进前 order ：" + order);
                // 执行推进
                order = OrderLifeCycleSimulator.pushOnOrder(order, currentTimeStamp);
//                System.out.println("___调试_线程：" + thread.getName() +
//                        "\n\t推进后 order ：" + order);
            } else {
                // 执行新建账单，currentID 递增

                order = OrderLifeCycleSimulator.newOrder(currentID, currentTimeStamp);
                currentID++;
            }

            reentrantLock.lock();
            try {
                // 向数据库插入记录
                orderDao.insert(order);
                // 记录结果
                if (order.getOrderStatus() == 0) {
                    // 进行中的订单，放到 结果集 中
                    resultMap.put(order.getOrderId().toString(), false);
                } else {
                    // 取消的，或最终完成的订单，从 结果集 中移除
                    resultMap.remove(order.getOrderId().toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }

            // 设置完成新订单创建任务的标记
            if ((!completeCreateNewOrderFlag) && currentID > endOrderID) {
                completeCreateNewOrderFlag = true;              // 记录本线程是否已完成了全部新订单创建任务的标记（不管是否完成了订单推进的任务）
                reentrantLock.lock();
                try {

                    lastNewOrderTimeStamp.push(currentTimeStamp);   // 记录最后一个新建 order 的时间戳，用于给下一个线程提供开始时间
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }

                logger.info("___线程：“" + thread.getName() + "” 完成了新建部分的工作。");
            }

            // 执行步进
            currentTimeStamp = currentTimeStamp + (long) stepIntervalProportion * (random.nextInt(10) + 81);

//            System.out.println("___调试_线程：" + thread.getName() +
//                    "\n\t变量：startOrderID 的值：" + startOrderID +
//                    "\n\t变量：endOrderID 的值：" + endOrderID +
//                    "\n\t变量：startTimeStamp 的值：" + startTimeStamp +
//                    "\n\t变量：allEndTimeStamp 的值：" + allEndTimeStamp +
//                    "\n\t变量：order 的值：" + order +
//                    "\n\t变量：resultMap 的值：" + resultMap +
//                    "\n\t变量：completeCreateNewOrderFlag 的值：" + completeCreateNewOrderFlag +
//                    "\n\t变量：lastNewOrderTimeStamp 的值：" + lastNewOrderTimeStamp +
//                    "\n\t变量：currentTimeStamp 的值：" + currentTimeStamp);
//            break;
        } while (currentTimeStamp < allEndTimeStamp
                && (currentID <= endOrderID || resultMap.containsValue(false)));

        completeCreateNewOrderFlag = true; // 最后也要把这个标志置为 true ，否则遇到时间结束了，还没跑完新建账单的部分，会导致主线程死循环

        reentrantLock.lock();
        try {
            lastOperateTimeStamp.push(currentTimeStamp);    // 记录线程结束时最后一次操作 order 的时间戳，用于给主线程判断是不是要再开启一个线程
            finishedThread.add(thread.getName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }

//        System.out.println("___调试_线程：" + thread.getName() +
//                "\n\t变量：lastOperateTimeStamp 的值：" + lastOperateTimeStamp);

        // 线程工作完成
        logger.info("___线程：“" + thread.getName() + "” 工作结束。");
    }
}

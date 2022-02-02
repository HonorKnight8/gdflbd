package monster.helloworld.gdflbd.generator;

import monster.helloworld.gdflbd.dao.OrderDao;
import monster.helloworld.gdflbd.dao.OrderDaoImpl;
import monster.helloworld.gdflbd.domain.Order;
import monster.helloworld.gdflbd.utils.ArgsUtil;
import monster.helloworld.gdflbd.utils.DataScaleUtil;

import java.util.*;
import java.util.logging.Logger;

public class OrderDBGeneratorThread implements Runnable {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private static final Random random = new Random();
    private OrderDao orderDao = new OrderDaoImpl();

    private Integer startOrderID;
    private Integer endOrderID;
    private Long startTimeStamp;
    private Long allEndTimeStamp;

    private Map<String, Boolean> resultMap = new TreeMap<>();   // 结果集，记录进行中的订单

    public OrderDBGeneratorThread(Integer startOrderID, Integer endOrderID, Long startTimeStamp, Long allEndTimeStamp) {
        this.startOrderID = startOrderID;
        this.endOrderID = endOrderID;
        this.startTimeStamp = startTimeStamp;
        this.allEndTimeStamp = allEndTimeStamp;
    }

    @Override
    public void run() {
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
                String targetId = keyList.get(random.nextInt(keyList.size()));
                order = orderDao.selectById(Integer.parseInt(targetId));
                // 执行推进
                order = OrderLifeCycleSimulator.pushOnOrder(order, currentTimeStamp);

            } else {
                // 执行新建账单，currentID 递增
                order = OrderLifeCycleSimulator.newOrder(currentID, currentTimeStamp);
                currentID++;
            }
            // 向数据库插入记录
            orderDao.insert(order);

            // 记录结果
            if(order.getOrderStatus() == 0){
                // 执行中的订单
                resultMap.put(order.getOrderId().toString(),false);
            }else{
                // 取消的，或最终完成的订单
                resultMap.remove(order.getOrderId().toString());
            }

            // 设置用于启动下一个线程的标记？？？

            // 执行步进
            currentTimeStamp = currentTimeStamp + (long) stepIntervalProportion * (random.nextInt(10) + 81);
        } while ((currentID <= endOrderID || resultMap.containsValue(false)) && currentTimeStamp < allEndTimeStamp);


    }
}

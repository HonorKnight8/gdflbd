package monster.helloworld.gdflbd.generator;

import com.sun.org.apache.bcel.internal.generic.TABLESWITCH;
import monster.helloworld.gdflbd.dao.OrderDao;
import monster.helloworld.gdflbd.dao.OrderDaoImpl;
import monster.helloworld.gdflbd.utils.ArgsUtil;
import monster.helloworld.gdflbd.utils.SqliteUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.logging.Logger;

public class OrderDBGenerator {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private OrderDao orderDao = new OrderDaoImpl();

    public void generate(String[] args) {
        System.out.println(Arrays.toString(ArgsUtil.params));

        // 新建/重置 数据表
        orderDao.dropTable();
        orderDao.createTable();

        // 开始生成

        // 根据参数获得 起 止 时间戳（当线程）
        LocalDate localDate = LocalDate.parse(ArgsUtil.params[3]);       // “起始日期”
        // 当天凌晨 0 点
        Long startTime =
                localDate.atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
        // 结束日次日凌晨 0 点
        Long endTime =
                localDate.plusDays(Integer.valueOf(ArgsUtil.params[4])).atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli();

        // 主线程按照”预估订单数“（非数据库记录数）分配线程
        // 每个线程负责 100 个订单，直到所有订单都最终完成或失效

        // 每个线程完成所有新建任务后，设置标记，让下一个线程开始执行任务
        // 除了第一个线程，每个线程都要等上一个线程完成新建之后再开始执行任务
        // 不使用线程池


    }


}

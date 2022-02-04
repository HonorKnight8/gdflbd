package monster.helloworld.gdflbd.dao;

import monster.helloworld.gdflbd.domain.Order;
import monster.helloworld.gdflbd.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class OrderDaoImpl implements OrderDao {
    private final ReentrantLock reentrantLock = new ReentrantLock();  // 用于线程同步锁

    @Override
    public Integer createTable() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            OrderDao mapper = session.getMapper(OrderDao.class);
            Integer result = mapper.createTable();

            reentrantLock.lock();
            try {
                session.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }

            return result;

        }

    }

    @Override
    public Integer dropTable() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            OrderDao mapper = session.getMapper(OrderDao.class);
            Integer result = mapper.dropTable();

            reentrantLock.lock();
            try {
                session.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }

            return result;

        }

    }

    @Override
    public List<Order> selectAll() {
        List<Order> list = null;

        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            OrderDao mapper = session.getMapper(OrderDao.class);
            list = mapper.selectAll();
        }

        return list;
    }

    @Override
    public Order selectById(Integer id) {
        Order order = null;

        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            OrderDao mapper = session.getMapper(OrderDao.class);
            order = mapper.selectById(id);
        }

        return order;
    }

    @Override
    public Integer deleteById(Integer id) {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            OrderDao mapper = session.getMapper(OrderDao.class);
            Integer result = mapper.deleteById(id);

            reentrantLock.lock();
            try {
                session.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }

            return result;

        }
//        return 0;
    }

    @Override
    public Integer insert(Order order) {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            OrderDao mapper = session.getMapper(OrderDao.class);
            Integer result = mapper.insert(order);

            reentrantLock.lock();
            try {
                session.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }

            return result;

        }
//        return 0;
    }

    @Override
    public Order getLastOrderID() {
        Order order = null;

        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            OrderDao mapper = session.getMapper(OrderDao.class);
            order = mapper.getLastOrderID();
        }

        return order;
    }

    @Override
    public Order selectByOrderId(Integer orderId) {
        Order order = null;

        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            OrderDao mapper = session.getMapper(OrderDao.class);
            order = mapper.selectByOrderId(orderId);
        }

        return order;
    }
}

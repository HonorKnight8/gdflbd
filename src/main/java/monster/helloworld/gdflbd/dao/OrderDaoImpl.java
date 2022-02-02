package monster.helloworld.gdflbd.dao;

import monster.helloworld.gdflbd.domain.Order;
import monster.helloworld.gdflbd.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;

public class OrderDaoImpl implements OrderDao{

    @Override
    public Integer createTable() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            OrderDao mapper = session.getMapper(OrderDao.class);
            Integer result = mapper.createTable();
            session.commit();
            return result;

        }

    }

    @Override
    public Integer dropTable() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            OrderDao mapper = session.getMapper(OrderDao.class);
            Integer result = mapper.dropTable();
            session.commit();
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
            session.commit();
            return result;

        }
//        return 0;
    }

    @Override
    public Integer insert(Order order) {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            OrderDao mapper = session.getMapper(OrderDao.class);
            Integer result = mapper.insert(order);
            session.commit();
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
}

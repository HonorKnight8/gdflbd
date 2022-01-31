package monster.helloworld.gdflbd.dao;

import monster.helloworld.gdflbd.domain.Order;

import java.util.List;

public interface OrderDao {
    Integer createTable();
    Integer dropTable();
    List<Order> selectAll();
    Order selectById(Integer id);
    Integer deleteById(Integer id);
    Integer update(Order order);
    Integer insert(Order order);
}

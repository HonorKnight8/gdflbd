//package monster.hellworld;
//
//import monster.helloworld.gdflbd.dao.OrderDao;
//import monster.helloworld.gdflbd.dao.OrderDaoImpl;
//import monster.helloworld.gdflbd.domain.Order;
//import monster.helloworld.gdflbd.utils.ArgsUtil;
//import monster.helloworld.gdflbd.utils.MyBatisUtil;
//import org.apache.ibatis.session.SqlSession;
//import org.junit.jupiter.api.Test;
//
//import java.util.Iterator;
//import java.util.List;
//
//public class MyBatisSqliteOrderTest {
//
//    private OrderDao orderDao = new OrderDaoImpl();
//
//    {
//        String[] args = {"D:\\test\\gdflbd\\orderdb\\devtest", "OrderDB", "tiny", "2021-12-25", "2"};
//        ArgsUtil.checkArgs(args);
//    }
//
//    @Test
//    public void resetTable() {
//        orderDao.dropTable();
//        orderDao.createTable();
//    }
//
//    @Test
//    public void listAll(){
//        System.out.println("__列举全部记录");
//        List<Order> orders = orderDao.selectAll();
//        Iterator<Order> orderIterator = orders.iterator();
//        while(orderIterator.hasNext()){
//            System.out.println(orderIterator.next());
//        }
//    }
//
//    @Test
//    public void insert(){
//        Order order = new Order();
//        order.setOrderId(1);
//        order.setUserId(1);
//        order.setTotalMoney(100.00F);
//        order.setAreaId(1);
//        order.setTradeSrc(0);
//        order.setPayStatus(0);
//        order.setOrderLifeCycle(17);
//        order.setOrderStatus(0);
//        order.setCreateTime("2022-02-02 14:28:000");
//        order.setPayTime("0000-00-00 00:00:000");
//        order.setModifiedTime("2022-02-02 14:28:000");
//
////        System.out.println(order);
//
//        orderDao.insert(order);
//        listAll();
//    }
//
//
//
//
//}

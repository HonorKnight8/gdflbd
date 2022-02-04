package monster.hellworld;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.sql.*;
import java.util.Arrays;
import java.util.Map;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import monster.helloworld.gdflbd.constants.GdflbdConstant;
import monster.helloworld.gdflbd.domain.Order;
import monster.helloworld.gdflbd.generator.OrderLifeCycleSimulator;
import monster.helloworld.gdflbd.utils.DateTimeUtil;
import monster.helloworld.gdflbd.utils.SqliteUtil;
import org.junit.jupiter.api.Test;
import org.sqlite.SQLiteConnection;

public class testClass {

    @Test
    public void test6() {
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE);
    }

    @Test
    public void test5() {
        System.out.println(GdflbdConstant.FILE_NAME_PREFIX_JSON);
        JSONObject jsonObject = JSON.parseObject(GdflbdConstant.FILE_NAME_PREFIX_JSON);
        Map<String, Object> innerMap = jsonObject.getInnerMap();
        System.out.println(innerMap);
        System.out.println(innerMap.get(GdflbdConstant.DATA_TYPE[0]));


//        String[] smartPhoneBrands = {
//                "Alcatel", "Apple", "Honor", "Huawei", "LG", "Lenovo", "Motorola", "Nokia", "OnePlus", "Oppo",
//                "Others", "Realme", "Samsung", "TECNO", "Vivo", "Xiaomi", "ZTE"
//        };
//        Arrays.sort(smartPhoneBrands);
//        System.out.println(Arrays.toString(smartPhoneBrands));

//        String[] cityArray = GdflbdConstant.CITY_ARRAY;
//        System.out.println(cityArray.length);
//        Arrays.sort(cityArray);
//        System.out.println(Arrays.toString(cityArray));

    }

    @Test
    public void testAppStartLog() {
//        AppStartLogOneDayThread appStartLogOneDayThread = new AppStartLogOneDayThread(null);
//
//        System.out.println(appStartLogOneDayThread.getLogMessage(1595309484329L, 10000, true));


//        AppStartLog appStartLog1 = new AppStartLog();
//        System.out.println(appStartLog1.getLogMessage(1595309484329L, 10000, true));
//        System.out.println(appStartLog1.getLogMessage(1595309484329L, 10000, true));
//        System.out.println(appStartLog1.getLogMessage(1595309484329L, 10000, false));
//        System.out.println(appStartLog1.getLogMessage(1595309484329L, 10000, false));


//        System.out.println(AppStartLog.getLogMessage(1595309484329L, 10000, true));
//        System.out.println(AppStartLog.getLogMessage(1595309484329L, 10000, true));
//        System.out.println(AppStartLog.getLogMessage(1595309484329L, 10000, false));
//        System.out.println(AppStartLog.getLogMessage(1595309484329L, 10000, false));
    }


    @Test
    public void test2() {
        Order order = new Order();
        order.setOrderId(1);
        order.setUserId(1);
        order.setTotalMoney(100.00F);
        order.setAreaId(1);
        order.setTradeSrc(0);
        order.setPayStatus(0);
        order.setOrderLifeCycle(1);
        order.setOrderStatus(0);
        order.setCreateTime("2022-02-02 14:28:000");
        order.setPayTime("0000-00-00 00:00:000");
        order.setModifiedTime("2022-02-02 14:28:000");
        System.out.println(order);

        do {
            order = OrderLifeCycleSimulator.pushOnOrder(order, System.currentTimeMillis());
            System.out.println(order);
        } while (order.getOrderStatus() == 0);
    }

    @Test
    public void test1() {
        System.out.println(DateTimeUtil.timeStampToStr_1(1595309484329L));
    }


}


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.sql.*;
import java.util.Arrays;
import java.util.Map;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import monster.helloworld.gdflbd.constants.GdflbdConstant;
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




}

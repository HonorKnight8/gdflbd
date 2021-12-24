
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.time.LocalDateTime;

import java.util.Map;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import monster.helloworld.gdflbd.LogBuilder.GenerateDatasets;
import monster.helloworld.gdflbd.constants.GdflbdConstant;
import monster.helloworld.gdflbd.datatype.AppStartLog;
import org.junit.jupiter.api.Test;

public class testClass {


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
        System.out.println(AppStartLog.getLogMessage(1595309484329L, 10000, true));
        System.out.println(AppStartLog.getLogMessage(1595309484329L, 10000, true));
        System.out.println(AppStartLog.getLogMessage(1595309484329L, 10000, false));
        System.out.println(AppStartLog.getLogMessage(1595309484329L, 10000, false));
    }

    @Test
    public void test4() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        Class dataTypeClass = Class.forName("monster.helloworld.gdflbd.datatype." + "AppStartLog");
        Method getLogMessage = dataTypeClass.getDeclaredMethod(
                "getLogMessage", long.class, int.class, boolean.class);
//        String logMessage = (String) getLogMessage.invoke(dataTypeClass.getDeclaredConstructor().newInstance(),0L);
        String logMessage = (String) getLogMessage.invoke(dataTypeClass, 0L, 1, true);
        System.out.println(logMessage);
    }

    @Test
    public void test3() {
        String[] strings = {"D:\\test\\gdflbd\\1222test", "AppStartLog", "Tiny", "2022-12-03", "2"};
        GenerateDatasets.generateData(strings);
    }


}

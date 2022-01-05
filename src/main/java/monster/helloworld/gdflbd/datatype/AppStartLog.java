package monster.helloworld.gdflbd.datatype;

import com.alibaba.fastjson.JSON;
import monster.helloworld.gdflbd.constants.GdflbdConstant;

import java.util.LinkedHashMap;

/**
 * 日志内容的构造器，模拟生成 App 启动日志
 * 实现自定义时间戳
 * 简单模拟：device_ID、uid、城市、手机品牌 ”绑定“ ，其余随机
 */
public class AppStartLog implements DataType {

    @Override
    public String getLogMessage(long timeStamp, int currentID, boolean isNewDevice) {

        LinkedHashMap<String, Object> appActiveMap = new LinkedHashMap<>();
        appActiveMap.put("name", "app_active");
        LinkedHashMap<String, String> json = new LinkedHashMap<>();
        json.put("entry", getEntry());
        json.put("action", getAction());
        json.put("error_code", "0");
        appActiveMap.put("json", json);
        appActiveMap.put("time", timeStamp);

        LinkedHashMap<String, String> attrMap = new LinkedHashMap<>();
        int id = getID(currentID, isNewDevice);

        attrMap.put("area", randomCity(id));
        attrMap.put("uid", Integer.toString(id));
        attrMap.put("app_v", getAppVersion());
        attrMap.put("event_type", "common");
        attrMap.put("device_id", Integer.toHexString(id));
        attrMap.put("os_type", getOSType());
        attrMap.put("channel", getChannel());
        attrMap.put("language", "chinese");
        attrMap.put("brand", getBrand(id));

        LinkedHashMap<String, Object> messageMap = new LinkedHashMap<>();
        messageMap.put("app_active", appActiveMap);
        messageMap.put("attr", attrMap);

        return JSON.toJSONString(messageMap);
    }

    private String getEntry() {
        return Integer.toString(random.nextInt(2) + 1);
    }

    private String getAction() {
        return Integer.toString(random.nextInt(2));
    }

    private String randomCity(int id) {
        int i = Integer.toHexString(id).hashCode() % GdflbdConstant.CITY_ARRAY.length;
        return GdflbdConstant.CITY_ARRAY[i];
    }

    private int getID(int lastID, boolean isNewDevice) {
        if (isNewDevice) {
            // 新设备（用户），返回 lastID
            return lastID;
        } else {
            // 旧设备（用户），返回 [1, lastID] 范围的随机数
            return random.nextInt(lastID) + 1;
        }
    }

    private String getAppVersion() {
        return "1.1." + random.nextInt(20);
    }

    private String getOSType() {
        if (random.nextInt(100) < 15) {
            // 模拟苹果手机版本
            return random.nextInt(10) + 4
                    + "." + random.nextInt(10)
                    + random.nextInt(10);
        } else {
            // 模拟安装手机版本
            return random.nextInt(9) + 4
                    + "." + random.nextInt(10)
                    + "." + random.nextInt(10);
        }
    }

    private String getChannel() {
        return new StringBuilder()
                .append((char) (random.nextInt(26) + 65))
                .append((char) (random.nextInt(26) + 65))
                .toString();
    }

    private String getBrand(int id) {
        int i = Integer.toHexString(id).hashCode() % GdflbdConstant.SMART_PHONE_BRANDS.length;
        return GdflbdConstant.SMART_PHONE_BRANDS[i];
    }

}

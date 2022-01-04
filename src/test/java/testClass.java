
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.Map;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import monster.helloworld.gdflbd.generator.AppStartLogOneDayThread;
import monster.helloworld.gdflbd.generator.GenerateDatasets;
import monster.helloworld.gdflbd.constants.GdflbdConstant;
import monster.helloworld.gdflbd.generator.AppStartLogGenerator;
import org.junit.jupiter.api.Test;

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
    public void test4() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
//        Class dataTypeClass = Class.forName("monster.helloworld.gdflbd.datatype." + "AppStartLog");
        Class dataTypeClass = Class.forName("monster.helloworld.gdflbd.generator.AppStartLogGenerator");
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

    @Test
    public void test2(){
        String[] cities = {"北京市","天津市","上海市","重庆市","阿坝藏族羌族自治州","阿克苏地区","阿拉善盟","阿勒泰地区","阿里地区","安康市","安庆市","安顺市","安阳市","鞍山市","巴彦淖尔市","巴音郭楞蒙古自治州","巴中市","白城市","白山市","白银市","百色市","蚌埠市","包头市","宝鸡市","保定市","保山市","北海市","本溪市","毕节地区","滨州市","博尔塔拉蒙古自治州","沧州市","昌都地区","昌吉回族自治州","长春市","长沙市","长治市","常德市","常州市","巢湖市","朝阳市","潮州市","郴州市","成都市","承德市","池州市","赤峰市","崇左市","滁州市","楚雄彝族自治州","达州市","大理白族自治州","大连市","大庆市","大同市","大兴安岭地区","丹东市","德宏傣族景颇族自治州","德阳市","德州市","迪庆藏族自治州","定西市","东莞市","东营市","鄂尔多斯市","鄂州市","恩施土家族苗族自治州","防城港市","佛山市","福州市","抚顺市","抚州市","阜新市","阜阳市","甘南州","甘孜藏族自治州","赣州市","固原市","广安市","广元市","广州市","贵港市","贵阳市","桂林市","果洛藏族自治州","哈尔滨市","哈密地区","海北藏族自治州","海东地区","海口市","海南藏族自治州","海西蒙古族藏族自治州","邯郸市","汉中市","杭州市","毫州市","合肥市","和田地区","河池市","河源市","菏泽市","贺州市","鹤壁市","鹤岗市","黑河市","衡水市","衡阳市","红河哈尼族彝族自治州","呼和浩特市","呼伦贝尔市","湖州市","葫芦岛市","怀化市","淮安市","淮北市","淮南市","黄冈市","黄南藏族自治州","黄山市","黄石市","惠州市","鸡西市","吉安市","吉林市","济南市","济宁市","佳木斯市","嘉兴市","嘉峪关市","江门市","焦作市","揭阳市","金昌市","金华市","锦州市","晋城市","晋中市","荆门市","荆州市","景德镇市","九江市","酒泉市","喀什地区","开封市","克拉玛依市","克孜勒苏柯尔克孜自治州","昆明市","拉萨市","来宾市","莱芜市","兰州市","廊坊市","乐山市","丽江市","丽水市","连云港市","凉山彝族自治州","辽阳市","辽源市","聊城市","林芝地区","临沧市","临汾市","临夏州","临沂市","柳州市","六安市","六盘水市","龙岩市","陇南市","娄底市","泸州市","吕梁市","洛阳市","漯河市","马鞍山市","茂名市","眉山市","梅州市","绵阳市","牡丹江市","内江市","那曲地区","南昌市","南充市","南京市","南宁市","南平市","南通市","南阳市","宁波市","宁德市","怒江傈僳族自治州","攀枝花市","盘锦市","平顶山市","平凉市","萍乡市","莆田市","濮阳市","普洱市","七台河市","齐齐哈尔市","黔东南苗族侗族自治州","黔南布依族苗族自治州","黔西南布依族苗族自治州","钦州市","秦皇岛市","青岛市","清远市","庆阳市","曲靖市","衢州市","泉州市","日喀则地区","日照市","三门峡市","三明市","三亚市","山南地区","汕头市","汕尾市","商洛市","商丘市","上饶市","韶关市","邵阳市","绍兴市","深圳市","沈阳市","十堰市","石家庄市","石嘴山市","双鸭山市","朔州市","四平市","松原市","苏州市","宿迁市","宿州市","绥化市","随州市","遂宁市","塔城地区","台州市","太原市","泰安市","泰州市","唐山市","天水市","铁岭市","通化市","通辽市","铜川市","铜陵市","铜仁市","吐鲁番地区","威海市","潍坊市","渭南市","温州市","文山壮族苗族自治州","乌海市","乌兰察布市","乌鲁木齐市","无锡市","吴忠市","芜湖市","梧州市","武汉市","武威市","西安市","西宁市","西双版纳傣族自治州","锡林郭勒盟","厦门市","咸宁市","咸阳市","湘潭市","湘西土家族苗族自治州","襄樊市","孝感市","忻州市","新乡市","新余市","信阳市","兴安盟","邢台市","徐州市","许昌市","宣城市","雅安市","烟台市","延安市","延边朝鲜族自治州","盐城市","扬州市","阳江市","阳泉市","伊春市","伊犁哈萨克自治州","宜宾市","宜昌市","宜春市","益阳市","银川市","鹰潭市","营口市","永州市","榆林市","玉林市","玉树藏族自治州","玉溪市","岳阳市","云浮市","运城市","枣庄市","湛江市","张家界市","张家口市","张掖市","漳州市","昭通市","肇庆市","镇江市","郑州市","中山市","中卫市","舟山市","周口市","株洲市","珠海市","驻马店市","资阳市","淄博市","自贡市","遵义市"};
        System.out.println(cities.length);
        Arrays.sort(cities);
        System.out.println(Arrays.toString(cities));
        System.out.println(cities.length);
    }

}

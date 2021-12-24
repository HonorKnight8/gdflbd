package monster.helloworld.gdflbd.constants;

import java.util.regex.Pattern;

public class GdflbdConstant {
    // 用于验证 LINUX 系统下的路径有效性的正则
    public static final Pattern LINUX_PATH_PATTERN =
            Pattern.compile("(/([a-zA-Z0-9][a-zA-Z0-9_\\-]{0,255}/)*([a-zA-Z0-9][a-zA-Z0-9_\\-]{0,255})|/)");
    // 用于验证 WINDOWS 系统下的路径有效性的正则
    public static final Pattern WINDOWS_PATH_PATTERN =
            Pattern.compile("(^[A-Z]:((\\\\|/)([a-zA-Z0-9\\-_]){1,255}){1,255}|([A-Z]:(\\\\|/)))");

    // 数据集类型
    public static final String[] DATA_TYPE = new String[]{
            "AppStartLog"
//             , "EventLog"
    };

    // 数据集规模
    public static final String[] DATA_SCALE = new String[]{"Tiny", "Small", "Medium", "Large", "Huge"};

    // 城市列表（不全）
    public static final String[] CITY_ARRAY = new String[]{
            "三亚", "三门峡", "上海", "东莞", "东营", "中山", "临安", "临汾", "临沂", "丹东",
            "丽水", "义乌", "乌鲁木齐", "九江", "乳山", "云浮", "佛山", "保定", "克拉玛依", "兰州",
            "包头", "北京", "北海", "南京", "南充", "南宁", "南昌", "南通", "即墨", "厦门",
            "句容", "台州", "合肥", "吉林", "吴江", "呼和浩特", "咸阳", "哈尔滨", "唐山", "嘉兴",
            "嘉峪关", "大同", "大庆", "大连", "天津", "太仓", "太原", "威海", "宁波", "安阳",
            "宜兴", "宜宾", "宜昌", "宝鸡", "宿迁", "富阳", "寿光", "岳阳", "常州", "常德",
            "常熟", "平度", "平顶山", "广州", "库尔勒", "廊坊", "延安", "开封", "张家口", "张家港",
            "张家界", "徐州", "德州", "德阳", "惠州", "成都", "扬州", "承德", "抚顺", "拉萨",
            "招远", "揭阳", "攀枝花", "文登", "无锡", "日照", "昆山", "昆明", "曲靖", "本溪",
            "杭州", "枣庄", "柳州", "株洲", "桂林", "梅州", "武汉", "汕头", "汕尾", "江门",
            "江阴", "沈阳", "沧州", "河源", "泉州", "泰安", "泰州", "泸州", "洛阳", "济南",
            "济宁", "海口", "海门", "淄博", "淮安", "深圳", "清远", "温州", "渭南", "湖州",
            "湘潭", "湛江", "溧阳", "滨州", "潍坊", "潮州", "烟台", "焦作", "牡丹江", "玉溪",
            "珠海", "瓦房店", "盐城", "盘锦", "石嘴山", "石家庄", "福州", "秦皇岛", "章丘", "绍兴",
            "绵阳", "聊城", "肇庆", "胶南", "胶州", "自贡", "舟山", "芜湖", "苏州", "茂名",
            "荆州", "荣成", "莱州", "莱芜", "莱西", "菏泽", "营口", "葫芦岛", "蓬莱", "衡水",
            "衢州", "西宁", "西安", "诸暨", "贵阳", "赤峰", "连云港", "遵义", "邢台", "邯郸",
            "郑州", "鄂尔多斯", "重庆", "金华", "金坛", "金昌", "铜川", "银川", "锦州", "镇江",
            "长春", "长沙", "长治", "阳江", "阳泉", "青岛", "鞍山", "韶关", "马鞍山", "齐齐哈尔"
    };

    // 当前最大 ID ，模拟系统中已有此数量的用户，此处设置为 1 万
//    public static final int LAST_ID = 10_000;

    // 智能手机品牌
    public static final String[] SMART_PHONE_BRANDS = {
            "Alcatel", "Apple", "Honor", "Huawei", "LG", "Lenovo", "Motorola", "Nokia", "OnePlus", "Oppo",
            "Others", "Realme", "Samsung", "TECNO", "Vivo", "Xiaomi", "ZTE"
    };

    // 文件名前缀
    public static final String FILE_NAME_PREFIX_JSON = "{" +
             "\"" + DATA_TYPE[0] + "\"" + ":\"start\"" +
//             ",\"" + DATA_TYPE[1] + "\"" + ":\"event\"" +
            "}";

}

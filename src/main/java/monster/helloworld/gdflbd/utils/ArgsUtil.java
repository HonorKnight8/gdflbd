package monster.helloworld.gdflbd.utils;

import monster.helloworld.gdflbd.constants.GdflbdConstant;
import monster.helloworld.gdflbd.constants.OSType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * 用于检查用户输入参数合法性
 */
public class ArgsUtil {
    private static final Logger logger = Logger.getLogger(ArgsUtil.class.toString());

    public static String[] params = null;

    // 处理传入的参数：
    // AppStartLog 类型的参数：输出路径，生成数据集类型，生成数据集规模：[T,S,M,L,H] ，起始日期，生成天数
    // OrderDB 类型的参数：输出路径，生成数据集类型，生成数据集规模：[T,S,M,L,H] ，起始日期，生成天数
    public static Boolean checkArgs() {

        // System.out.println(Arrays.toString(args));
        Boolean flag = true;

        // 初步检查参数数量
        if (params.length < 2) {
            logger.warning("输入的参数数量过少");
            flag = false;
        }

        // 先检查第一个通用参数
        if (!checkTargetPath(params[0])) {
            logger.warning("目标路径不合法！");
            flag = false;
        }
        // 检查第二个参数
//        if (!checkDataType(args[1])) {
//            logger.warning("输入的数据集类型错误，当前支持的数据集类型有：" + Arrays.toString(GdflbdConstant.DATA_TYPE));
//            flag = false;
//        }

        // 再根据不同的数据集类型（同时检查了第二个参数），调用不同的方法，检查后面的参数
        switch (params[1].toLowerCase()) {
            case "appstartlog":
                flag = appStartLogCheckArgs();
                break;
            case "orderdb":
                flag = orderDBCheckArgs();
                // System.out.println("orderdb");
                // System.exit(99);
                break;
            default:
                logger.warning("输入的数据集类型错误，当前支持的数据集类型有：" +
                        "\n\t" + Arrays.toString(GdflbdConstant.DATA_TYPE));
                flag = false;
        }

        return flag;
    }

    private static Boolean orderDBCheckArgs() {
//        System.out.println(Arrays.toString(args));

        // 检查参数数量
        if (params.length != 5) {
            logger.warning("输入的参数数量不正确，OrderDB 数据集类型需要 5 个参数："
                    + "\n\t ”输出路径“ "
                    + "\n\t ”生成数据集类型“ ：OrderDB"
                    + "\n\t ”生成数据集规模“ ，当前支持的数据集规模有：" + Arrays.toString(DataScaleUtil.DATA_SCALE)
                    + "\n\t ”起始日期“ ，格式： ”1999-01-31“"
                    + "\n\t ”生成数据集的天数“ ，范围：[2 - 2,147,483,647]"

            );
            return false;
        }


        if (!checkDataScale(params[2])) {
            logger.warning("输入的数据集规模错误，当前支持的数据集规模有：" + Arrays.toString(DataScaleUtil.DATA_SCALE));
            return false;
        }
        if (!checkStartDate(params[3])) {
            logger.warning("输入的起始日期格式错误，请以 ”1999-01-31“ 的格式输入起始日期");
            return false;
        }
        if (!checkDaysQuantity(params[4])) {
            logger.warning("输入的生成数据集天数错误，正确范围是 [2 - 2,147,483,647]");
            return false;
        }

        return true;
    }


    public static Boolean appStartLogCheckArgs() {
//        System.out.println(Arrays.toString(args));

        // 检查参数数量
        if (params.length != 5) {
            logger.warning("输入的参数数量不正确，AppStartLog 数据集类型需要 5 个参数："
                    + "\n\t ”输出路径“ "
                    + "\n\t ”生成数据集类型“ ：AppStartLog"
                    + "\n\t ”生成数据集规模“ ，当前支持的数据集规模有：" + Arrays.toString(DataScaleUtil.DATA_SCALE)
                    + "\n\t ”起始日期“ ，格式： ”1999-01-31“"
                    + "\n\t ”生成数据集的天数“ ，范围：[2 - 2,147,483,647]"

            );
            return false;
        }


        if (!checkDataScale(params[2])) {
            logger.warning("输入的数据集规模错误，当前支持的数据集规模有：" + Arrays.toString(DataScaleUtil.DATA_SCALE));
            return false;
        }
        if (!checkStartDate(params[3])) {
            logger.warning("输入的起始日期格式错误，请以 ”1999-01-31“ 的格式输入起始日期");
            return false;
        }
        if (!checkDaysQuantity(params[4])) {
            logger.warning("输入的生成数据集天数错误，正确范围是 [2 - 2,147,483,647]");
            return false;
        }

        return true;
    }


    /**
     * 使用正则表达式，验证用户输入的路径的合法性
     *
     * @param path
     * @return
     */
    private static Boolean checkTargetPath(String path) {
        if (System.getProperty("os.name").toLowerCase().contains(OSType.WINDOWS.name().toLowerCase())) {
            // windows 系统
            return GdflbdConstant.WINDOWS_PATH_PATTERN.matcher(path).matches();
        } else if (System.getProperty("os.name").toLowerCase().contains(OSType.LINUX.name().toLowerCase())) {
            return GdflbdConstant.LINUX_PATH_PATTERN.matcher(path).matches();
        } else {
            logger.warning("判断操作系统类型失败");
            return true;
        }
    }

    private static Boolean checkDataType(String dataType) {
        for (String str : GdflbdConstant.DATA_TYPE) {
            if (str.equalsIgnoreCase(dataType)) {
                return true;
            }
        }
        return false;
    }

    private static Boolean checkDataScale(String dataScale) {
        for (String str : DataScaleUtil.DATA_SCALE) {
            if (str.equalsIgnoreCase(dataScale)) {
                return true;
            }
        }
        return false;
    }

    private static Boolean checkStartDate(String startDate) {

        // 指定日期格式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置 lenient 为 false
            // 否则 SimpleDateFormat 会比较宽松地验证日期，比如 2007-02-29 会被接受，并转换成 2007-03-01
            format.setLenient(false);
            format.parse(startDate);
            return true;
        } catch (ParseException e) {
            // e.printStackTrace();
            return false;
        }

    }

    private static Boolean checkDaysQuantity(String dayQuantity) {
        int i = Integer.parseInt(dayQuantity);
        if (i >= 2 && i <= Integer.MAX_VALUE) {
            return true;
        }
        return false;
    }

}

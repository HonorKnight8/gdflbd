package monster.helloworld.gdflbd.utils;

import monster.helloworld.gdflbd.generator.GenerateDatasets;
import monster.helloworld.gdflbd.constants.GdflbdConstant;
import monster.helloworld.gdflbd.constants.OSType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * 用于检查用户输入参数合法性
 */
public class CheckArgs {
    private static final Logger logger = Logger.getLogger(GenerateDatasets.class.toString());

    // 处理传入的参数：输出路径，生成数据集类型，生成数据集规模：[T,S,M,L,H] ，起始日期，生成天数
    public static Boolean checkArgs(String[] args) {
//        System.out.println(Arrays.toString(args));
        Boolean flag = true;

        // 检查参数数量
        if (args.length != 5) {
            logger.warning("输入的参数数量不正确，请依次输入："
                    + "\n\t ”输出路径“ "
                    + "\n\t ”生成数据集类型“ ，当前支持的数据集类型有：" + Arrays.toString(GdflbdConstant.DATA_TYPE)
                    + "\n\t ”生成数据集规模“ ，当前支持的数据集规模有：" + Arrays.toString(DataScaleUtil.DATA_SCALE)
                    + "\n\t ”起始日期“ ，格式： ”1999-01-31“"
                    + "\n\t ”生成数据集的天数“ ，范围：[2 - 2,147,483,647]"

            );
            flag = false;
        }

        if (!checkTargetPath(args[0])) {
            logger.warning("目标路径不合法！");
            flag = false;
        }
        if (!checkDatatype(args[1])) {
            logger.warning("输入的数据集类型错误，当前支持的数据集类型有：" + Arrays.toString(GdflbdConstant.DATA_TYPE));
            flag = false;
        }
        if (!checkDataScale(args[2])) {
            logger.warning("输入的数据集规模错误，当前支持的数据集规模有：" + Arrays.toString(DataScaleUtil.DATA_SCALE));
            flag = false;
        }
        if (!checkStartDate(args[3])) {
            logger.warning("输入的起始日期格式错误，请以 ”1999-01-31“ 的格式输入起始日期");
            flag = false;
        }
        if (!checkDaysQuantity(args[4])) {
            logger.warning("输入的生成数据集天数错误，正确范围是 [2 - 2,147,483,647]");
            flag = false;
        }

        return flag;
    }


    /**
     * 使用正则表达式，验证用户输入的路径的合法性
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

    private static Boolean checkDatatype(String dataType) {
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

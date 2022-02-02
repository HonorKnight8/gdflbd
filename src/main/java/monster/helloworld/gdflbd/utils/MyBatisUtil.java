package monster.helloworld.gdflbd.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class MyBatisUtil {
    private static final Logger logger = Logger.getLogger(SqliteUtil.class.toString());

    public static final String DB_FILE_NAME = "order.db";
    // public static final String PASSWORD = "pass_the_world";

    private static SqlSessionFactory factory = null;

    // 使用static静态代码块，随着类的加载而加载，只执行一次
    static {
        try {
            String resource = "MybatisConfig.xml";
            // 加载MyBatis的主配置文件
            InputStream inputStream = Resources.getResourceAsStream(resource);

            // 通过构建器（SqlSessionFactoryBuilder）构建一个SqlSessionFactory工厂对象
            // 方式一：使用 jdbc.properties 配置文件中的属性
            // factory = new SqlSessionFactoryBuilder().build(inputStream);

            // 方式二：指定属性值，覆盖 jdbc.properties 配置文件中的属性（可以动态指定属性值）
            Properties properties1 = new Properties();
            properties1.setProperty("jdbc.url", "jdbc:sqlite:" + ArgsUtil.params[0] + File.separator + DB_FILE_NAME);
            factory = new SqlSessionFactoryBuilder().build(inputStream, properties1);
            // factory = new SqlSessionFactoryBuilder().build(inputStream, null, properties1);

        } catch (Exception e) {
            logger.severe("！！！创建 SqlSessionFactory 失败");
            e.printStackTrace();
        }
    }

    public static SqlSession getSqlSession() {
        return factory.openSession();
    }
}


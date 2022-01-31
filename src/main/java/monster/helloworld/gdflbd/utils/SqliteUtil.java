package monster.helloworld.gdflbd.utils;

import java.io.File;
import java.sql.*;
import java.util.Arrays;
import java.util.logging.Logger;

public class SqliteUtil {
    private static final Logger logger = Logger.getLogger(SqliteUtil.class.toString());

    public static final String DB_FILE_NAME = "order.db";
    // public static final String PASSWORD = "pass_the_world";

    /**
     * 获取链接的方法
     *
     * @param targetPath
     * @return
     */
    public static Connection getConnection(String targetPath) {
        // System.out.println(Arrays.toString(ArgsUtil.params));

        String url = "jdbc:sqlite:" + targetPath + File.separator + DB_FILE_NAME;

        Connection connection = null;
        try {
            // Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);

            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
                logger.info("创建数据库连接成功（若数据库文件不存在会自动创建），驱动名：" + meta.getDriverName());
            }
        } catch (Exception e) {
            logger.severe("！！！创建 Sqlite 数据库连接失败");
            logger.severe("！！！" + e.getClass().getName() + ": " + e.getMessage());
            System.exit(99);
        }

        return connection;
    }


    /**
     * 关闭资源的方法
     *
     * @param connection
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection connection, Statement statement) {
        if (connection != null && statement != null) {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        if (connection != null && statement != null) {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

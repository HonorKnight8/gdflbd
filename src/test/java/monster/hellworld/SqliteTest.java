package monster.hellworld;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.*;
import java.util.logging.Logger;

public class SqliteTest {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final String DB_FILE_PATH = "D:\\test\\gdflbd\\orderdb\\devtest";
    private final String DB_FILE_NAME = "sqlite.db";

    private Connection getConnection(){
        // System.out.println(Arrays.toString(ArgsUtil.params));

        String url = "jdbc:sqlite:" + DB_FILE_PATH + File.separator + DB_FILE_NAME;

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
    private void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void close(Connection connection, Statement statement) {
        if (connection != null && statement != null) {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void close(Connection connection, Statement statement, ResultSet resultSet) {
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

    /**
     * 删表
     * @throws SQLException
     */
    @Test
    public void test0() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        connection.setAutoCommit(false);
        String sql = "DROP TABLE IF EXISTS COMPANY;";
        statement.executeUpdate(sql);
        connection.commit();

        close(connection,statement);
    }

    /**
     * 建表
     * @throws SQLException
     */
    @Test
    public void test1() throws SQLException {

        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        String sql = "CREATE TABLE COMPANY "
                + "(ID INT PRIMARY KEY NOT NULL,"
                + " NAME TEXT NOT NULL,"
                + " AGE INT NOT NULL,"
                + " ADDRESS CHAR(50),"
                + " SALARY REAL)";
        statement.executeUpdate(sql);

        close(connection,statement);
    }

    /**
     * 查询
     * @throws SQLException
     */
    @Test
    public void test2() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM COMPANY;");

        while (resultSet.next()) {
            System.out.println();
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            String address = resultSet.getString("address");
            float salary = resultSet.getFloat("salary");
            System.out.println("ID = " + id);
            System.out.print("\tNAME = " + name);
            System.out.print("\tAGE = " + age);
            System.out.print("\tADDRESS = " + address);
            System.out.print("\tSALARY = " + salary);
        }
        resultSet.close();

        close(connection,statement,resultSet);
    }

    /**
     * 插值
     * @throws SQLException
     */
    @Test
    public void test3() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        connection.setAutoCommit(false);
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                + "VALUES (1, 'Paul', 32, 'California', 20000.00 );\n");
        sb.append("INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                + "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );\n");
        sb.append("INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                + "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );\n");
        sb.append("INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                + "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );");
        statement.executeUpdate(sb.toString());
        connection.commit();

        test2();
        close(connection,statement);
    }

    /**
     * 修改
     * @throws SQLException
     */
    @Test
    public void test4() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        connection.setAutoCommit(false);
        String sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
        statement.executeUpdate(sql);
        connection.commit();

        test2();
        close(connection,statement);
    }

    /**
     * 删除
     * @throws SQLException
     */
    @Test
    public void test5() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        connection.setAutoCommit(false);
        String sql = "DELETE from COMPANY where ID=2;";
        statement.executeUpdate(sql);
        connection.commit();

        test2();
        close(connection,statement);
    }

}

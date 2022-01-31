package monster.helloworld.gdflbd.generator;

import com.sun.org.apache.bcel.internal.generic.TABLESWITCH;
import monster.helloworld.gdflbd.utils.ArgsUtil;
import monster.helloworld.gdflbd.utils.SqliteUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Logger;

public class OrderDBGenerator {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final String TABLE_NAME = "orders";

    public void generate(String[] args) {
        System.out.println(Arrays.toString(ArgsUtil.params));

        // 第一天，新建表
        // 后续的每一天，复制这个表，并进行修改和新增


        // 初始化，建表
        createTable();

        // 执行生成逻辑


    }

    private void createTable() {
        Connection connection = SqliteUtil.getConnection(ArgsUtil.params[0]);
        Statement statement = null;
        try {
            statement = connection.createStatement();

            String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";" +
                    "CREATE TABLE " + TABLE_NAME + "(" +
                    "    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, -- 记录id\n" +
                    "    `orderId` bigint(11) NOT NULL, --  订单id\n" +
                    "    `userId` bigint(11) NOT NULL, -- 用户id\n" +
                    "    `billId` bigint(11) NOT NULL, -- 账单id（支付信息）\n" +
                    "    `totalMoney` decimal(11,2) NOT NULL, -- 订单总金额（实付）\n" +
                    "    `areaId` int(11) NOT NULL, -- 区域最低一级id\n" +
                    "    `tradeSrc` tinyint(4) NOT NULL DEFAULT 0, -- 订单来源,0:网页商城,1:微信小程序,2:安卓App,3:苹果App\n" +
                    "    `payStatus` tinyint(4) NOT NULL DEFAULT 0, -- 字符状态,0:未支付;1:已支付，2:支付后取消;3:退款\n" +
                    "    `orderStatus` tinyint(4) NOT NULL DEFAULT 0, -- 订单状态,0:下单,1:下单后取消,2:支付,3:支付后取消,4:发货,5:发货后取消,6:拒收,7:收货,8:收货后取消,9:确认收货,10;确认后取消\n" +
                    "    `createTime` varchar(25) NOT NULL, -- 下单时间\n" +
                    "    `payTime` varchar(25) NOT NULL DEFAULT '0000-00-00 00:00:00:000', -- 支付时间\n" +
                    "    `modifiedTime` varchar(25) NOT NULL DEFAULT '0000-00-00 00:00:00:000' -- 订单更新时间\n" +
                    ");";
            statement.executeUpdate(sql);
            logger.info("Sqlite 建表成功");
        } catch (SQLException e) {
            logger.severe("！！！Sqlite 建表失败");
            logger.severe("！！！" + e.getClass().getName() + ": " + e.getMessage());
            System.exit(99);
            // e.printStackTrace();
        }

        SqliteUtil.close(connection, statement);
    }
}

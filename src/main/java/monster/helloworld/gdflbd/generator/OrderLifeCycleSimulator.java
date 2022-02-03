package monster.helloworld.gdflbd.generator;

import monster.helloworld.gdflbd.domain.Order;
import monster.helloworld.gdflbd.utils.DateTimeUtil;

import java.util.Random;

public class OrderLifeCycleSimulator {
    //    CREATE TABLE IF NOT EXISTS orders(
//            `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, -- 记录id
//            `orderId` bigint(11) NOT NULL, --  订单id
//            `userId` bigint(11) NOT NULL, -- 用户id
//            `totalMoney` decimal(11,2) NOT NULL, -- 订单总金额（实付）
//            `areaId` int(11) NOT NULL, -- 区域最低一级id
//            `tradeSrc` tinyint(4) NOT NULL DEFAULT 0, -- 订单来源,0:网页商城,1:微信小程序,2:安卓App,3:苹果App
//            `payStatus` tinyint(4) NOT NULL DEFAULT 0, -- 支付状态,0:未支付;1:已支付，2:已退款
//            `orderLifeCycle` tinyint(4) NOT NULL DEFAULT 1,
//            -- 订单生命周期, 1:下单,2:下单后取消,4:超时取消,5:支付,6:支付后取消,7:发货,8:发货后取消,10:投递失败退回,12:拒收，13:收货,
//            -- 14:收货后取消,15:确认收货,16:退货,17:过了退货期(最终完成)
//            -- 偶数是取消或退货（对应下面的无效订单），17 是最终完成的订单，其他是执行中的订单
//            `orderStatus` tinyint(4) NOT NULL DEFAULT 0 , -- 订单状态，0:执行中（有效），1:完成（有效）,2:取消或退货（无效）
//            `createTime` varchar(25) NOT NULL, -- 下单时间
//            `payTime` varchar(25) DEFAULT '0000-00-00 00:00:000' NOT NULL, -- 支付时间
//            `modifiedTime` varchar(25) DEFAULT '0000-00-00 00:00:000' NOT NULL -- 订单更新时间
//        );
    private static final Random random = new Random();

    private static final Integer[] TRADE_SRC_ARRAY = {0, 1, 2, 3};

    private static final Integer[] PAY_STATUS_ARRAY = {0, 1, 2};
    private static final Integer[] ORDER_LIFE_CYCLE_ARRAY = {1, 2, 4, 5, 6, 7, 8, 10, 12, 13, 14, 15, 16, 17};
    private static final Integer[] ORDER_STATUS_ARRAY = {0, 1, 2};

    // 新订单
    public static Order newOrder(Integer currentID, Long createTime) {
        Order order = new Order();

        order.setOrderId(currentID);

        order.setUserId(getUserId());
        order.setTotalMoney(getTotalMoney());
        order.setAreaId(getAreaId());
        order.setTradeSrc(TRADE_SRC_ARRAY[random.nextInt(TRADE_SRC_ARRAY.length)]); // 从 TRADE_SRC_ARRAY 随机获取

        order.setPayStatus(0); // 新建订单，未支付，固定值 0
        order.setOrderLifeCycle(1); // 新建订单，下单阶段，固定值 1
        order.setOrderStatus(0); // 新建订单，执行中，固定值 0

//        System.out.println(createTime);
        String dateTimeStr = DateTimeUtil.timeStampToStr_1(createTime);
        order.setCreateTime(dateTimeStr); // 新建订单，使用传入的时间戳作为创建订单的时间
        order.setPayTime("0000-00-00 00:00:00:000"); // 新建订单，未支付，固定值 "0000-00-00 00:00:000"
        order.setModifiedTime(dateTimeStr); // 新建订单，与 createTime 相同

        return order;
    }


    // 推进订单
    public static Order pushOnOrder(Order order, Long modifyTime) {
        // `payStatus` tinyint(4) NOT NULL DEFAULT 0, -- 支付状态,0:未支付;1:已支付，2:已退款
        // `orderLifeCycle` tinyint(4) NOT NULL DEFAULT 1,
        // -- 订单生命周期, 1:下单,2:下单后取消,4:超时取消,5:支付,6:支付后取消,7:发货,8:发货后取消,10:投递失败退回,12:拒收，13:收货,
        // -- 14:收货后取消,15:确认收货,16:退货,17:过了退货期(最终完成)
        // -- 偶数是取消或退货（对应下面的无效订单），17 是最终完成的订单，其他是执行中的订单
        // `orderStatus` tinyint(4) NOT NULL DEFAULT 0 , -- 订单状态，0:执行中（有效），1:完成（有效）,2:取消或退货（无效）
        Integer originalLifeCycle = order.getOrderLifeCycle();

        // 执行 订单推进 的逻辑（订单生命周期发生改变）
        switch (originalLifeCycle) {
            case 1:
                int i = random.nextInt(1000) + 1;
                if (i % 71 == 0) {
                    order.setOrderLifeCycle(2); // 下单后取消
                } else if (i % 73 == 0) {
                    order.setOrderLifeCycle(4); // 超时取消
                } else {
                    order.setOrderLifeCycle(5); //支付
                }
                break;
            case 5:
                i = random.nextInt(1000) + 1;
                if (i % 79 == 0) {
                    order.setOrderLifeCycle(6); // 支付后取消
                } else {
                    order.setOrderLifeCycle(7); // 发货
                }
                break;
            case 7:
                i = random.nextInt(1000) + 1;
                if (i % 131 == 0) {
                    order.setOrderLifeCycle(8); // 发货后取消
                } else if (i % 137 == 0) {
                    order.setOrderLifeCycle(10); // 投递失败退回
                } else if (i % 139 == 0) {
                    order.setOrderLifeCycle(12); // 拒收
                } else {
                    order.setOrderLifeCycle(13); // 收货
                }
                break;
            case 13:
                i = random.nextInt(1000) + 1;
                if (i % 83 == 0) {
                    order.setOrderLifeCycle(14); // 收货后取消
                } else {
                    order.setOrderLifeCycle(15); // 确认收货
                }
                break;
            case 15:
                i = random.nextInt(1000) + 1;
                if (i % 89 == 0) {
                    order.setOrderLifeCycle(16); // 退货
                } else {
                    order.setOrderLifeCycle(17); // 过了退货期(最终完成)
                }
                break;
//            default:
        }

        // 根据新的订单什么周期，修改可能会发生变化的属性
        Integer newLifeCycle = order.getOrderLifeCycle();
        String dateTimeStr = DateTimeUtil.timeStampToStr_1(modifyTime);
        if ((newLifeCycle % 2) == 0) {
            // 偶数是取消或退货（无效订单）
            order.setOrderStatus(2);
        } else if (newLifeCycle == 5) {
            // 已支付的订单
            order.setPayTime(dateTimeStr);
        } else if (newLifeCycle == 17) {
            // 最终完成的订单（有效）
            order.setOrderStatus(1);
        }
//        else {
//            // 执行中的订单（有效）
//            order.setOrderStatus(0);
//        }

        // 必然会更新的值
        order.setModifiedTime(dateTimeStr);

        return order;
    }


    private static Float getTotalMoney() {
        // 应该从 订单-商品表（含商品、商家优惠信息） 和 bill表（含平台活动优惠信息）中获取，暂且使用随机数模拟
        return (random.nextInt(99) + 1) / 100.00F + random.nextInt(1_000_000_000);
    }

    private static Integer getAreaId() {
        // 应该从 Area 表随机获取，暂且使用随机数模拟
        return random.nextInt(10000);
    }

    private static Integer getUserId() {
        // 应该从 user 表随机获取，暂且使用随机数模拟
        return random.nextInt(10000) + 10000;
    }
}

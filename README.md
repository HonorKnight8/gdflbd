## gdflbd

gdflbd，generate datasets for learning big data，生成用于学习大数据的数据集。



## 使用说明

使用环境：支持 Linux 和 Windows，java 1.8 及以上（ v0.1.0 的 jar 是 JDK11 编译的，后面改用 JDK1.8）

命令示例：

- ```shell
  # win
  java -jar gdflbd_0.1.03-jar-with-dependencies.jar D:\test\gdflbd\0212\tiny AppStartLog tiny 2020-08-20 14
  
  # Linux
  java -jar gdflbd_0.1.03-jar-with-dependencies.jar /root/test/gdflbd/0212/tiny AppStartLog tiny 2020-08-20 8
  
  # OrderDB 类型
  java -jar gdflbd_0.1.03-jar-with-dependencies.jar D:\test\gdflbd\0212\orderdb_tiny OrderDB tiny 2020-09-21 8
  ```



## 命令参数说明：

- param 1：
  - 目标文件夹
- param 2：
  - 要生成的数据集类型
  - 当前支持的数据集类型有：[AppStartLog, OrderDB]
- param 3：
  - 要生成的数据集规模
  - 当前支持的数据集规模有：[Tiny, Small, Medium, Large, Huge]
- param 4：
  - 要生成的数据集（日志）的起始日期
  - 格式： ”1999-01-31“
- param 5：
  - 要生成的数据集（日志）的天数
  - 范围：大于等于 2 



## 数据集类型说明：

- AppStartLog 类型

  -  模拟 APP 启动日志，样本如下：
  
  - ```log
    2021-12-25 00:08:28.000 [logToFile] INFO  monster.helloworld.gdflbd.logger.AppStartLogger - {"app_active":{"name":"app_active","json":{"entry":"1","action":"0","error_code":"0"},"time":1640362108000},"attr":{"area":"大同市","uid":"478","app_v":"1.1.5","event_type":"common","device_id":"1de","os_type":"13.05","channel":"KA","language":"chinese","brand":"LG"}}
    ```
  
- OrderDB 类型

  - 模拟 “电商平台” 订单数据库

  - 目前仅实现了订单表，表结构如下

  - ```mysql
    CREATE TABLE IF NOT EXISTS orders(
        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, -- 记录id
        `orderId` bigint(11) NOT NULL, --  订单id
        `userId` bigint(11) NOT NULL, -- 用户id
        `totalMoney` decimal(11,2) NOT NULL, -- 订单总金额（实付）
        `areaId` int(11) NOT NULL, -- 区域最低一级id
        `tradeSrc` tinyint(4) NOT NULL DEFAULT 0, -- 订单来源,0:网页商城,1:微信小程序,2:安卓App,3:苹果App
        `payStatus` tinyint(4) NOT NULL DEFAULT 0, -- 支付状态,0:未支付;1:已支付，2:已退款
        `orderLifeCycle` tinyint(4) NOT NULL DEFAULT 1, -- 订单生命周期：
        -- 1:下单,2:下单后取消,4:超时取消,
        -- 5:支付,6:支付后取消,
        -- 7:发货,8:发货后取消,10:投递失败退回,12:拒收，
        -- 13:收货,14:收货后取消,
        -- 15:确认收货,16:退货,
        -- 17:过了退货期(最终完成)
        -- 偶数是取消或退货（对应下面的无效订单），17 是最终完成的订单，其他是执行中的订单
        `orderStatus` tinyint(4) NOT NULL DEFAULT 0 ,
        -- 订单状态，0:执行中（有效），1:完成（有效）,2:取消或退货（无效）
        `createTime` varchar(25) NOT NULL, -- 下单时间
        `payTime` varchar(25), -- 支付时间
        `modifiedTime` varchar(25) DEFAULT '0000-00-00 00:00:00:000' NOT NULL -- 订单更新时间
    );
    ```

  - 生成的是一个 SQLite 的数据库文件，可以直接使用 sqlite-tools 命令行工具直接导出为 csv 文件，比如：

    - ```shell
      sqlite3 -noheader -csv D:\test\order.db "select * from orders;" > data.csv
      ```

    - 然后再加载进 hive 表




## 其他

### 测试效果及执行效率：

- AppStartLog 类型生成的文件行数、大小：

  - ```bash
    wc -lc ~/gdflbd0105/tiny/*
        505  187315 /root/gdflbd0105/tiny/start0101.log
        506  187709 /root/gdflbd0105/tiny/start0102.log
        506  187678 /root/gdflbd0105/tiny/start0103.log
        505  187597 /root/gdflbd0105/tiny/start0104.log
        504  186978 /root/gdflbd0105/tiny/start0105.log
        506  187572 /root/gdflbd0105/tiny/start0106.log
        504  186979 /root/gdflbd0105/tiny/start0107.log
        507  187848 /root/gdflbd0105/tiny/start1225.log
        506  187742 /root/gdflbd0105/tiny/start1226.log
        505  187071 /root/gdflbd0105/tiny/start1227.log
        506  187588 /root/gdflbd0105/tiny/start1228.log
        506  187622 /root/gdflbd0105/tiny/start1229.log
        504  187099 /root/gdflbd0105/tiny/start1230.log
        507  188137 /root/gdflbd0105/tiny/start1231.log
       7077 2624935 总用量
    
    wc -lc ~/gdflbd0105/large/*
        202121   75900402 /root/gdflbd0105/large/start0101.log
        202101   75904330 /root/gdflbd0105/large/start0102.log
        202087   75905992 /root/gdflbd0105/large/start0103.log
        202119   75919179 /root/gdflbd0105/large/start0104.log
        202118   75925288 /root/gdflbd0105/large/start0105.log
        202098   75914628 /root/gdflbd0105/large/start0106.log
        202083   75916515 /root/gdflbd0105/large/start0107.log
        202100   75836177 /root/gdflbd0105/large/start1225.log
        202092   75854828 /root/gdflbd0105/large/start1226.log
        202107   75862066 /root/gdflbd0105/large/start1227.log
        202092   75879602 /root/gdflbd0105/large/start1228.log
        202102   75881012 /root/gdflbd0105/large/start1229.log
        202086   75878282 /root/gdflbd0105/large/start1230.log
        202118   75900144 /root/gdflbd0105/large/start1231.log
       2829424 1062478445 总用量
    
    ```

- AppStartLog 类型的执行效率

  - CentOS7，OpenJDK1.8 环境执行效率：

    - ```log
      一月 05, 2022 8:09:14 下午 monster.helloworld.gdflbd.Entry main
      信息: >>>执行参数：[/root/gdflbd0105/tiny, AppStartLog, tiny, 2021-12-25, 14]
      一月 05, 2022 8:09:14 下午 monster.helloworld.gdflbd.Entry main
      信息: >>>总耗时：2,197 毫秒
      ```

    - ```log
      一月 05, 2022 8:11:18 下午 monster.helloworld.gdflbd.Entry main
      信息: >>>执行参数：[/root/gdflbd0105/small, AppStartLog, small, 2021-12-25, 14]
      一月 05, 2022 8:11:18 下午 monster.helloworld.gdflbd.Entry main
      信息: >>>总耗时：16,131 毫秒
      ```

    - ```log
      一月 05, 2022 8:12:36 下午 monster.helloworld.gdflbd.Entry main
      信息: >>>执行参数：[/root/gdflbd0105/medium, AppStartLog, medium, 2021-12-25, 14]
      一月 05, 2022 8:12:36 下午 monster.helloworld.gdflbd.Entry main
      信息: >>>总耗时：58,321 毫秒
      ```

    - ```log
      一月 05, 2022 8:31:21 下午 monster.helloworld.gdflbd.Entry main
      信息: >>>执行参数：[/root/gdflbd0105/large, AppStartLog, large, 2021-12-25, 14]
      一月 05, 2022 8:31:21 下午 monster.helloworld.gdflbd.Entry main
      信息: >>>总耗时：1,104,863 毫秒
      ```

  - Win10，OracleJDK1.8，IDEA 环境下执行效率：

    - ```log
      一月 05, 2022 8:38:28 下午 monster.helloworld.gdflbd.Entry main
      信息: >>>执行参数：[D:\test\gdflbd\0105test\medium, AppStartLog, medium, 2021-12-25, 14]
      一月 05, 2022 8:38:28 下午 monster.helloworld.gdflbd.Entry main
      信息: >>>总耗时：75,773 毫秒
      ```

  - [参考](http://helloworld.monster/a_notice_of_using_java.util.logging.logger_class_in_multi_threaded/#%E8%83%8C%E6%99%AF)

- OrderDB 类型的执行效率

  - ```log
    信息: >>>程序运行报告：
            执行参数：[D:\test\gdflbd\0212\orderdb_tiny, OrderDB, tiny, 2020-09-21, 8]
            总耗时：65224 毫秒
    ```




## 日志

- 2022-02-04
  - 初步完成 OrderDB 类型
  - 实现 orders（订单）表
  - release 0.1.03

- 2022-01-05
  - 使用多线程，大大提高执行效率
  - release 0.1.02
- 2022-01-04
  - 减少 static 方法使用，改善内存占用
  - 更新城市列表
- 2021-12-24
  - 创建项目
  - release 0.1




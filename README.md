## gdflbd

gdflbd，generate datasets for learning big data，生成用于学习大数据的数据集。



## 使用说明

使用环境：支持 Linux 和 Windows，java 11 及以上（上传的 jar 是 v11 编译的，没测试使用 v1.8 编译的兼容性）

命令示例：

- `java -jar gdflbd-jar-with-dependencies.jar D:\test\gdflbd\1224test\t AppStartLog tiny 2020-08-20 14`
- `java -jar gdflbd-jar-with-dependencies.jar /root/gdflbd1224/test_tiny AppStartLog tiny 2020-08-20 8`



## 命令参数说明：

- param 1：
  - 目标文件夹
- param 2：
  - 要生成的数据集类型
  - 当前支持的数据集类型有：[AppStartLog]
- param 3：
  - 要生成的数据集规模
  - 当前支持的数据集规模有：[Tiny, Small, Medium, Large, Huge]
- param 4：
  - 要生成的数据集（日志）的起始日期
  - 格式： ”1999-01-31“
- param 5：
  - 要生成的数据集（日志）的天数
  - 范围：大于等于 2 



## 其他

### 测试效果及执行效率：

- 检查生成的文件行数、大小：

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

  - huge 还没跑

- Win10，OracleJDK1.8，IDEA 环境下执行效率：

  - ```log
    一月 05, 2022 8:38:28 下午 monster.helloworld.gdflbd.Entry main
    信息: >>>执行参数：[D:\test\gdflbd\0105test\medium, AppStartLog, medium, 2021-12-25, 14]
    一月 05, 2022 8:38:28 下午 monster.helloworld.gdflbd.Entry main
    信息: >>>总耗时：75,773 毫秒
    ```




### 数据集类型样本：

- AppStartLog 类型

  - ```log
    2020-08-20 23:57:40.000 [logToFile] INFO  monster.helloworld.gdflbd.logger.AppStartLogger - {"app_active":{"name":"app_active","json":{"entry":"1","action":"1","error_code":"0"},"time":1597939060000},"attr":{"area":"廊坊","uid":"382","app_v":"1.1.11","event_type":"common","device_id":"17e","os_type":"5.3.6","channel":"IG","language":"chinese","brand":"Huawei"}}
    ```



## 日志

- 2022-01-05
  - 使用多线程，大大提高执行效率
  - release 0.1.02
- 2022-01-04
  - 减少 static 方法使用，改善内存占用
  - 更新城市列表
- 2021-12-24
  - 创建项目
  - release 0.1




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

- CentOS7，OpenJDK11 环境下：

  - ```log
    12月 24, 2021 8:40:29 下午 monster.helloworld.gdflbd.Entry main
    信息: [/root/gdflbd1224/test_tiny, AppStartLog, tiny, 2020-08-20, 8]
    12月 24, 2021 8:40:29 下午 monster.helloworld.gdflbd.Entry main
    信息: >>>总耗时：1881 毫秒
    ```

- Win10，OracleJDK11 环境下：

  - ```log
    12月 24, 2021 6:40:29 下午 monster.helloworld.gdflbd.Entry main
    信息:[D:\test\gdflbd\1223test\t，AppStartLog,tiny，2020-08-20，14]
    12月 24, 2021 6:40:29 下午 monster.helloworld.gdflbd.Entry main
    信息: >>>总耗时：4950 豪秒
    ```

  - ```log
    12月 24, 2021 7:40:29 下午 monster.helloworld.gdflbd.Entry main
    信息:[D:\test\gdflbd\1223test\m，AppStartLog,medium，2020-08-20，14]
    12月 24, 2021 7:40:29 下午 monster.helloworld.gdflbd.Entry main
    信息: >>>总耗时：1970142豪秒
    ```

- large、huge 还没测试



### 数据集类型举例：

- AppStartLog 类型

  - ```log
    2020-08-20 23:57:40.000 [logToFile] INFO  monster.helloworld.gdflbd.logger.AppStartLogger - {"app_active":{"name":"app_active","json":{"entry":"1","action":"1","error_code":"0"},"time":1597939060000},"attr":{"area":"廊坊","uid":"382","app_v":"1.1.11","event_type":"common","device_id":"17e","os_type":"5.3.6","channel":"IG","language":"chinese","brand":"Huawei"}}
    ```










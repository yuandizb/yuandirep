# mybatis-generator-extend-maven-plugin项目说明
classpath下放置mybatisStart.properties文件。配置如下参数
mybatisStart.properties
- `db.driverLocation`=`/root/mysql-connector-java-5.1.40-bin.jar`
- `db.driverClassName`=`com.mysql.jdbc.Driver`
- `db.url`=`jdbc:mysql://127.0.0.1:3317/micro?useUncoide=true&chracterEncoding=utf-8&useSSL=false`
- `db.username`=`root`
- `db.password`=`studyj2e`
- `mybatisGenerator.pojoPackage`=`pojoPackage`
- `mybatisGenerator.daoPackage`=`daoPackage`
- `mybatisGenerator.tablesPrefix`=`upms`

安装
```bash
mvn install -Dmaven.plugin.skip=true
```
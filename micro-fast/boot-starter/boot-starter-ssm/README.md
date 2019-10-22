# 欢迎查看boot-starter-ssm项目说明
## 项目介绍
    `boot-starter-ssm`项目用于快速开启spring集成mybatis的项目,将本项目使用maven发布到本地仓库,在其他项
    目中引入该依赖进行相关配置即可使用.
### boot-starter-ssm项目的参数配置
得益于springboot的自动配置以及条件化装配的机制．我们可以扩展封装自己的ssm-boot,也是就是本项目中的boot-starter-ssm，本项目的自定义扩展配置如下：
- 参考[druid官网的配置说明](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter)
- `mybatis.typeAliasesPackage`　pojo包所在的位置(必要参数),如果要指定多个包的话使用逗号分割
- `mybatis.mappers`　mapper映射文件所在的路径(必要参数)
- `mybatis.pageHelperProperties`　pagehelper分页插件的properties配置形式是key:value数组的形式，默认方言是mysql,如果需要自定义可以传入自己的配置，使用key:value的数
     组的形式.
- 除此之外`mybatis`的`dao`层扫描的配置需要在项目中自行配置
- 需要注意的是:当使用多数据源的时候,需要在使用的项目之中去除默认的数据源自动装配`@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)`
- 对于数据源的切换方法是使用aop的方式对`@SwitchDataSource('数据源的名字')`进行拦截动态切换数据源，主数据源的名字是`master`,从数据源的名字是`slave`.对于没有注解默认的数据源是`master`.
- PageHelper只对紧跟着的第一个SQL语句起作用
- 如果向定义n个数据源，在自己项目使用中定义bean即可，但是数据源的名字请不要使用`salve` `master` `masterDataSource` `dataSource` `dynamicDataSource`
### mybatis中传入参数的注意事项,通过反射是无法取到参数的名字的,知道这个很重要
- 如果传入的是单参数且参数类型是一个List的时候，collection属性值为list .
- 如果传入的是单参数且参数类型是一个array数组的时候，collection的属性值为array .
- 如果传入的参数是多个的时候，我们就需要把它们封装成一个Map了，当然单参数也可以封装成map，实际上如果你在传入参数的时候，在MyBatis里面也是会把它封装成一个Map的，map的key就是参数名，所以这个时候collection属性值就是传入的List或array对象在自己封装的map里面的key.

### boot-starter-ssm模块提供了增强的程序运行时运行初始化数据库脚本功能
spring-boot提供了默认的启动时执行数据库脚本功能，指定`spring.datasource.schema`属性即可。boot-starter-ssm对此功能进行了增强，
默认提供了主从两个数据源的配置bean.如果想要某个数据源程序启动时执行sql脚本。只需在druid数据源配置增加`sql-path`属性，并且保证
配置`username,password,url,driver-class-name`.如果这仍然不满足你的需求，你想要配置更多的数据源，并且需要启动时各自执行各自的sql
脚本。对于以上需求本项目也是满足的。你只需参照[druid官网的配置说明](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter)配置
更多的数据源，像增加多个druid数据源一样增加`com.micro.fast.common.init.properties.SqlInitProperties`类的配置bean,然后满足上述
两个数据源时同样要求即可。

### 启用基于shardbatis的分表功能
- 通过设置shardbatis.configPath(shardbatis的xml配置文件的classpath路径)属性来激活分表功能．使用`@IdShard`来在mapper接口参数中指定分表策略字段
或者`@IdShard('filedName')`来指定参数对象中哪个字段是分表策略所需的字段.
- shardbatis.tableCount 指定分表的数量
- 注意,使用默认的分表策略的时候mapper接口中不允许使用只有一个map传递参数.

### 乐观锁功能

### 分布式主键生成
- snowflake.use 是否使用分布式主键生成系统，不设置不启用，设置为true的时候启用。
- snowflake.workId 机器id，值可以是0-1023

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

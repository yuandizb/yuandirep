#欢迎查看boot-starter-security-oauth项目说明
##本项目的参数配置说明
    - 用户登录接口url`/user/login` 
- 图片验证码获取接口url`/code/image` ,在进行图片验证码校验时传递参数为`imageCode`
- 使用redis存储验证码，key是根据发起请求的`deviceId`生成的。所以生成和校验验证码的时候必须传入`deviceId`
- `ms.security.code.image.width` 图片验证码宽度，默认值是`67` 可以在请求中使用`width`参数设置
- `ms.security.code.image.height`图片验证码高度，默认值是`23` 可以在请求中使用`height`参数设置
- `ms.security.code.image.length`图片验证码的code长度，默认是`4` 不能在请求中设置
- `ms.security.code.image.expireIn` 图片验证码的有效时间`60`秒 不能在请求中设置
- `ms.security.code.image.url` 需要进行验证码校验的地址，默认值是`/user/login` 可以是`/user/*`这种格式，多个使用,号分割 
   不能在请求中设置
-  本着增量开发的原则，可以实现`imageCodeUtil`生成验证码的方法，并定义名称为`imageCodeUtil`的bean便可替换原有的图片验证码
   的生成逻辑
-  短信验证码获取接口url`/code/sms`　传递参数为`mobile`
- `ms.security.code.sms.length` 短信验证码code的长度，默认值是`6`,不能在请求中设置
- `ms.security.code.sms.expireIn` 短信验证码的默认有效时间是`60`秒,不能在请求中设置
- `ms.security.code.sms.url`短信验证码进行校验的地址，默认值是`/user/login`,可以是`/user/*`这种格式，多个适用,号分割,不
能在请求中设置
- 同样的,可以实现`validateCode`生成验证码的方法，并定义名称为`smsCodeUtil`的bean便可替换原有的短信验证码的生成逻辑
- 短信登录的接口为`/authentication/mobile`,传递参数为`smsCode`和`mobile`
- oauth2发起的请求的用户需要有一个`ROLE_USER`的用户权限
- oauth2中相同的用户会得到相同的token
- `ms.security.oauth2.storeType`token的策略，不设置这个值的是时候默认生成`jwt`的token,如果设置值为`redis`的时候生成的默认的token将会存储在redis中
- `ms.security.oauth2.jwtSigningKey` 生成jwt的token的秘钥
- `ms.security.oauth2.clientStoreType` oauth2服务商client详情的存储方式,默认mysql.填写其他值会存储在内存中
- `ms.security.oauth2.providerLoginPage`oauth2服务提供商提供用户登录的页面
- `ms.security.oauth2.providerLoginProcessUrl`oauth2服务提供商处理用户登录进程的url
- `ms.security.oauth2.clients[i].clientId` 配置oauth2授权的clientId
- `ms.security.oauth2.clients[i].clientSecret` 配置oauth2授权的clientId对应的clientSecret
- `ms.security.oauth2.clients[i].accessTokenValiditySeconds` 数组token过期时间，单位是秒
- `ms.security.oauth2.clients[i].authorizedGrantTypes[i]` 数组，授权类型默认是"refresh_token","password","custom"
- `ms.security.oauth2.clients[i].scopes[i]`  作用范围默认是"all","read","write"

# ms-web

> A Vue.js project

## Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report

# run unit tests
npm run unit

# run e2e tests
npm run e2e

# run all tests
npm test
```
# the project develop
```bash
# 安装element-ui
npm i element-ui -S 
后续配置请查看element-ui官网
# 安装less
npm install less less-loader --save-dev 
安装成功后，打开 build/webpack.base.conf.js ，在 module.exports = 的对象的 module.rules 后面添加一段：
module.exports = {
    //  此处省略无数行，已有的的其他的内容
    module: {
        rules: [
          //  此处省略无数行，已有的的其他的规则
          {
            test: /\.less$/,
            loader: "style-loader!css-loader!less-loader",
          }
        ]
    }
}
最后，在代码中的 style 标签中 加上 lang="less" 属性即可~
<style scoped lang="less">
</style>

#安装vuex

#安装axios
npm install axios -S
修改原型链
import axios from 'axios'
Vue.prototype.$http = axios
#安装vue-devtools

#关于部署使用nodejs的http-server模块进行部署
npm install http-server 
cd dist 
http-server -p 80
```

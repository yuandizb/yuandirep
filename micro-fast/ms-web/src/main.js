// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
//  引入element-ui依赖
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
//  引入axios用于发起请求
import axios from 'axios'
//  引入vuex进行数据管理
import Vuex from 'vuex'
//  引入icon图标
require('./assets/icon/iconfont.css')

Vue.use(ElementUI)
Vue.use(Vuex)
Vue.prototype.$http = axios
// 用于转换json数据
let querystring = require('querystring')
Vue.prototype.$querystring = querystring
// 全局参数配置
Vue.prototype.$msConfig = {
  // ms系统的请求的基础路径
  reqBaseUrl: 'http://fthipw.natappfree.cc',
  // 权限管理系统请求的基础路径
  upms: ':10006/upms'
}
//  admin模块的state和action
const admin = new Vuex.Store({
  //  模块的state
  state: {
    username: ''
  },
  //  根据特定的规则获取state中的数据
  getters: {
    getUsername: (state, getters) => {
      return state.username
    }
  },
  //  改变模块的状态的函数，只能是同步的
  mutations: {
    setUsername (state) {
      state.username = ''
    }
  },
  //  模块的action是对mutations的封装，在action中可以进行异步的操作
  actions: {
    increment (context) {
      context.commit('setUsername')
    }
  }
})

// 把全局的state进行拆分
const store = new Vuex.Store({
  state: {
    admin: admin
  }
})

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})

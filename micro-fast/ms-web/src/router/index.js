import Vue from 'vue'
import Router from 'vue-router'
import SignIn from '@/components/SignIn'
import Register from '@/components/Register'
import Index from '@/components/Index'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'SignIn',
      component: SignIn
    }, {
      path: '/register',
      name: 'Register',
      component: Register
    }, {
      path: '/index',
      name: 'Index',
      component: Index
    }
  ]
})

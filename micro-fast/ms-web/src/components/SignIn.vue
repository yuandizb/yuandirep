<template>
  <el-container class="page-container">
    <el-main class="sign-in-container">
      <el-row type="flex" justify="center">
        <span class="title">ms 分布式系统后台管理</span>
      </el-row>
      <el-row type="flex" justify="center">
        <el-col :sm="5" :md="7" :lg="5" :xl="4">
          <el-input v-model="username" placeholder="用户名" prefixIcon="ms-web-username" size="large" clearable></el-input>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center" class="row-line">
        <el-col :sm="5" :md="7" :lg="5" :xl="4">
          <el-input v-model="password" placeholder="密码" prefixIcon="ms-web-password" size="large"  clearable type="password"></el-input>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center" class="row-line">
        <el-col :sm="2" :md="2" :lg="2" :xl="2">
          <el-input v-model="imageCode" size="small" placeholder="验证码"></el-input>
        </el-col>
        <el-col :sm="3" :md="5" :lg="3" :xl="2">
          <img src="http://fthipw.natappfree.cc:10006/upms/code/image?width=67&height=30" class="image-code" alt="图片验证码"/>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center" class="row-line">
        <el-button type="success" size="medium" class="button-margin" @click="signIn">登录</el-button>
        <el-button type="primary" size="medium" @click="signUp">注册</el-button>
      </el-row>
    </el-main>
  </el-container>
</template>
<script>
  let signIn = {
    username: '',
    password: '',
    imageCode: '',
    rememberMe: false
  }
  export default {
    name: 'SignIn',
    data () {
      return signIn
    },
    methods: {
      signIn () {
        let that = this
        that.$http.post(that.$msConfig.reqBaseUrl + that.$msConfig.upms + '/user/login', that.$querystring.stringify(signIn))
          .then(response => {
            if (response.status === 200 && response.data.code === 0) {
              // 进入首页
              that.$router.push('/index')
            }
          })
          .catch(onError => {
            that.$message({
              message: '登录失败',
              center: true
            })
          })
      },
      signUp () {
        this.$router.push('/register')
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="less">
  .page-container {
    background-color: #2c4557;
    height: 100%;
    .sign-in-container {
      padding-top: 15%;
      .title {
        color: #909399;
        font-size: 2rem;
        margin-bottom: 100px;
      }
      .row-line {
        margin-top: 10px;
        .button-margin {
          margin-right: 70px;
        }
        .image-code {
          margin-left: 30px;
        }
      }
    }
  }
</style>

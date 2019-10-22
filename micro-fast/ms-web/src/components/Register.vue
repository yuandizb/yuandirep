<template>
  <el-container class="page-container">
    <el-main class="sign-up-container">
      <el-row type="flex" justify="center">
        <span class="title">用户注册</span>
      </el-row>
      <el-row type="flex" justify="center" class="row-line">
        <el-col :lg="2" class="left-font" >用户名:</el-col>
        <el-col :lg="4" >
          <el-tooltip class="item" effect="dark" content="请输入6-16位字母或数字" placement="right">
            <el-input v-model="username" placeholder="用户名" suffixIcon="ms-web-need" 　clearable></el-input>
          </el-tooltip>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center" class="row-line">
        <el-col :lg="2" class="left-font" >密码:</el-col>
        <el-col :lg="4">
          <el-tooltip class="item" effect="dark" content="请输入6-16位字母或数字" placement="right">
            <el-input type="password" v-model="password" placeholder="密码"  suffixIcon="ms-web-need" clearable></el-input>
          </el-tooltip>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center" class="row-line">
        <el-col :lg="2" class="left-font" >确认密码:</el-col>
        <el-col :lg="4">
          <el-tooltip class="item" effect="dark" content="再次输入密码" placement="right">
            <el-input type="password" v-model="validatePassword" placeholder="确认密码"  suffixIcon="ms-web-need" clearable></el-input>
          </el-tooltip>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center" class="row-line">
        <el-col :lg="2" class="left-font" >邮箱:</el-col>
        <el-col :lg="4">
          <el-tooltip class="item" effect="dark"  placement="right">
            <div slot="content">请填写您经常使用的有效邮箱<br/>此邮箱将用于您密码找回</div>
            <el-input v-model="email" placeholder="邮箱" 　suffixIcon="ms-web-need"　clearable></el-input>
          </el-tooltip>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center" class="row-line">
          <el-col :lg="2" :offset="1">
            <el-button type="success" size="medium" @click="signUp">提交</el-button>
          </el-col>
          <el-col :lg="2" :offset="2">
            <el-button type="primary" size="medium" @click="reset">重置</el-button>
          </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
  let signUp = {
    username: '',
    password: '',
    validatePassword: '',
    email: ''
  }
  export default {
    name: 'Register',
    data () {
      return signUp
    },
    methods: {
      signUp () {
        let that = this
        let baseConfig = this.$msConfig
        this.$http.post(baseConfig.reqBaseUrl + baseConfig.upms + '/upmsUser', that.$querystring.stringify(signUp))
          .then(response => {
            if (response.status === 200 && response.data.code === 0) {
              this.$message({
                message: '恭喜您,注册成功!',
                type: 'success',
                center: true
              })
            }
          })
          .catch(onError => {
            let response = onError.response
            if (!response) {
              that.$message({
                message: '注册失败',
                center: true
              })
            }
            if (response.status === 400) {
              // es6中对象的遍历
              for (let [key, value] of Object.entries(response.data)) {
                console.log(key)
                this.$message({
                  message: value,
                  type: 'warning',
                  center: true
                })
              }
            }
            if (response.status >= 500) {
              this.$message.error('系统异常请稍后再试')
            }
          })
      },
      reset () {
        signUp.username = ''
        signUp.password = ''
        signUp.email = ''
        signUp.validatePassword = ''
      }
    }
  }
</script>

<style lang="less" scoped>
  .page-container{
    color: #909399;
    background-color: #2c4557;
    height: 100%;
    .sign-up-container{
      padding-top: 15%;
      .title {
        font-size: 2rem;
      }
      .row-line{
        margin-top: 10px;
        .left-font{
          line-height: 40px;
        }
      }
    }
  }
</style>

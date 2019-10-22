<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
  export default {
    name: 'app'
  }
</script>

<style>
  #app {
    height: 100%;
  }
  body,html{
    height: 100%;
    margin: 0;
    padding: 0;
  }
  * {
    margin: 0;
    padding: 0;
    font-size: 14px;
    font-family: Microsoft YaHei;
  }
</style>


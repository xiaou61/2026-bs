<template>
  <div class="home-container">
    <el-card>
      <h2>欢迎使用智慧社区综合服务管理平台</h2>
      <p>当前用户：{{ user.username }}</p>
      <p>角色：{{ user.role }}</p>
    </el-card>
  </div>
</template>

<script setup>
const user = JSON.parse(localStorage.getItem('user') || '{}')
</script>

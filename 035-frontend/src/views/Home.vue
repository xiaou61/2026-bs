<template>
  <el-card shadow="hover">
    <h2>欢迎使用水稻收割预约系统</h2>
    <p>本系统提供预约下单、机手调度、作业跟踪与结算等功能。</p>
    <div class="row">
      <el-button type="primary" @click="$router.push('/health')">后端健康检查</el-button>
      <el-button v-if="!store.isAuthed" @click="$router.push('/login')">去登录</el-button>
      <el-button v-else type="success" plain>已登录：{{ store.user?.username }}</el-button>
    </div>
  </el-card>
</template>

<script setup>
import { useUserStore } from '../stores/user'
const store = useUserStore()
</script>

<style scoped>
h2 { margin-bottom: 12px; }
p { color: #666; margin-bottom: 16px; }
.row { margin-top: 12px; display: flex; gap: 8px; }
</style>

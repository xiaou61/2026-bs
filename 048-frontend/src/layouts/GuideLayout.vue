<template>
  <el-container style="height:100vh">
    <el-aside width="200px" style="background:#2E8B57">
      <div style="color:#fff;text-align:center;padding:20px;font-size:16px;font-weight:bold">讲解员工作台</div>
      <el-menu :default-active="route.path" router background-color="#2E8B57" text-color="#fff" active-text-color="#ffd04b">
        <el-menu-item index="/guide/booking"><el-icon><Tickets /></el-icon>讲解预约</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="display:flex;justify-content:flex-end;align-items:center;background:#fff;box-shadow:0 1px 4px rgba(0,0,0,.1)">
        <span style="margin-right:20px">{{ userStore.username }}</span>
        <el-button @click="handleLogout">退出</el-button>
      </el-header>
      <el-main style="background:#f5f5f5"><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { Tickets } from '@element-plus/icons-vue'
const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const handleLogout = () => { userStore.logout(); router.push('/login') }
</script>

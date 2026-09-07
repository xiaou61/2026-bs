<template>
  <el-container class="layout">
    <el-aside width="200px">
      <div class="logo">店家中心</div>
      <el-menu :default-active="route.path" router background-color="#2d3a4b" text-color="#bfcbd9" active-text-color="#ffd04b">
        <el-menu-item index="/owner/shop"><el-icon><Shop /></el-icon>店铺信息</el-menu-item>
        <el-menu-item index="/owner/room"><el-icon><House /></el-icon>房间管理</el-menu-item>
        <el-menu-item index="/owner/script"><el-icon><Document /></el-icon>剧本管理</el-menu-item>
        <el-menu-item index="/owner/reservation"><el-icon><Calendar /></el-icon>预约管理</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header><span>店家: {{ userStore.userInfo.realName }}</span><el-button type="danger" link @click="handleLogout">退出</el-button></el-header>
      <el-main><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const handleLogout = () => { userStore.logout(); router.push('/login') }
</script>

<style scoped>
.layout { min-height: 100vh; }
.el-aside { background: #2d3a4b; }
.logo { height: 60px; line-height: 60px; text-align: center; color: #ffd04b; font-size: 18px; font-weight: bold; }
.el-header { display: flex; justify-content: space-between; align-items: center; background: #fff; box-shadow: 0 1px 4px rgba(0,0,0,0.1); }
</style>

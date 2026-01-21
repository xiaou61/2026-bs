<template>
  <el-container class="layout">
    <el-aside width="200px">
      <div class="logo">剧本杀平台</div>
      <el-menu :default-active="route.path" router background-color="#16213e" text-color="#bfcbd9" active-text-color="#0f4c75">
        <el-menu-item index="/user/script"><el-icon><Document /></el-icon>剧本大厅</el-menu-item>
        <el-menu-item index="/user/shop"><el-icon><Shop /></el-icon>店铺列表</el-menu-item>
        <el-menu-item index="/user/reservation"><el-icon><Calendar /></el-icon>我的预约</el-menu-item>
        <el-menu-item index="/user/favorite"><el-icon><Star /></el-icon>我的收藏</el-menu-item>
        <el-menu-item index="/user/notice"><el-icon><Bell /></el-icon>系统公告</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header><span>玩家: {{ userStore.userInfo.realName }}</span><el-button type="danger" link @click="handleLogout">退出</el-button></el-header>
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
.el-aside { background: #16213e; }
.logo { height: 60px; line-height: 60px; text-align: center; color: #3282b8; font-size: 18px; font-weight: bold; }
.el-header { display: flex; justify-content: space-between; align-items: center; background: #fff; box-shadow: 0 1px 4px rgba(0,0,0,0.1); }
</style>

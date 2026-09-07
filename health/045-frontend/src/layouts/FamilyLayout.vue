<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <div class="logo">家属服务</div>
      <el-menu :default-active="route.path" router background-color="#304156" text-color="#bfcbd9" active-text-color="#409eff">
        <el-menu-item index="/family/elder"><el-icon><User /></el-icon>老人信息</el-menu-item>
        <el-menu-item index="/family/bill"><el-icon><Wallet /></el-icon>费用查询</el-menu-item>
        <el-menu-item index="/family/visit"><el-icon><Calendar /></el-icon>探访预约</el-menu-item>
        <el-menu-item index="/family/notice"><el-icon><Bell /></el-icon>通知公告</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <span>欢迎您，{{ userStore.userInfo.realName }}</span>
        <el-button type="text" @click="userStore.logout">退出登录</el-button>
      </el-header>
      <el-main><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const userStore = useUserStore()
</script>

<style scoped>
.layout-container { height: 100vh; }
.el-aside { background-color: #304156; }
.logo { height: 60px; line-height: 60px; text-align: center; color: #fff; font-size: 18px; font-weight: bold; }
.el-header { display: flex; justify-content: space-between; align-items: center; background: #fff; box-shadow: 0 1px 4px rgba(0,21,41,.08); }
.el-main { background: #f0f2f5; }
</style>

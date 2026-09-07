<template>
  <el-container class="layout-container">
    <el-header class="header">
      <div class="logo">宠物寄养服务系统</div>
      <el-menu mode="horizontal" :default-active="route.path" router :ellipsis="false">
        <el-menu-item index="/home"><el-icon><HomeFilled /></el-icon>首页</el-menu-item>
        <el-menu-item index="/pets"><el-icon><Cat /></el-icon>我的宠物</el-menu-item>
        <el-menu-item index="/providers"><el-icon><Shop /></el-icon>寄养服务</el-menu-item>
        <el-menu-item index="/bookings"><el-icon><Calendar /></el-icon>我的预约</el-menu-item>
      </el-menu>
      <div class="user-info">
        <el-dropdown>
          <span class="user-name">
            <el-avatar :size="32">{{ userStore.user.nickname?.charAt(0) || 'U' }}</el-avatar>
            <span style="margin-left: 8px">{{ userStore.user.nickname }}</span>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="router.push('/profile')">个人中心</el-dropdown-item>
              <el-dropdown-item divided @click="handleLogout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-header>
    <el-main class="main">
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import { ElMessage } from 'element-plus'
import { HomeFilled, Shop, Calendar } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const handleLogout = () => {
  userStore.logout()
  ElMessage.success('已退出登录')
  router.push('/login')
}
</script>

<style scoped>
.layout-container {
  min-height: 100vh;
}
.header {
  display: flex;
  align-items: center;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  padding: 0 20px;
}
.logo {
  font-size: 20px;
  font-weight: bold;
  color: #409eff;
  margin-right: 40px;
}
.el-menu {
  flex: 1;
  border-bottom: none;
}
.user-info {
  display: flex;
  align-items: center;
}
.user-name {
  display: flex;
  align-items: center;
  cursor: pointer;
}
.main {
  background: #f5f7fa;
  padding: 20px;
}
</style>

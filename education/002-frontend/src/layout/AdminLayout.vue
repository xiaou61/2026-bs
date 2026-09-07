<template>
  <el-container class="layout-container">
    <!-- 顶部导航 -->
    <el-header class="header">
      <div class="header-left">
        <div class="logo">
          <el-icon :size="24"><Reading /></el-icon>
          <span class="logo-text">选课系统管理后台</span>
        </div>
      </div>
      <div class="header-right">
        <div class="user-info">
          <el-avatar :size="36" class="user-avatar">
            {{ userStore.userInfo?.name?.charAt(0) }}
          </el-avatar>
          <div class="user-detail">
            <span class="username">{{ userStore.userInfo?.name }}</span>
            <span class="role">管理员</span>
          </div>
        </div>
        <el-button class="logout-btn" @click="handleLogout">
          <el-icon><SwitchButton /></el-icon>
          退出
        </el-button>
      </div>
    </el-header>
    
    <el-container class="main-container">
      <!-- 侧边栏 -->
      <el-aside class="aside">
        <el-menu 
          :default-active="route.path" 
          router
          class="sidebar-menu"
        >
          <el-menu-item index="/admin/students" class="menu-item">
            <el-icon><User /></el-icon>
            <template #title>学生管理</template>
          </el-menu-item>
          <el-menu-item index="/admin/teachers" class="menu-item">
            <el-icon><Avatar /></el-icon>
            <template #title>教师管理</template>
          </el-menu-item>
          <el-menu-item index="/admin/courses" class="menu-item">
            <el-icon><Reading /></el-icon>
            <template #title>课程管理</template>
          </el-menu-item>
          <el-menu-item index="/admin/notices" class="menu-item">
            <el-icon><Bell /></el-icon>
            <template #title>公告管理</template>
          </el-menu-item>
          <el-menu-item index="/admin/config" class="menu-item">
            <el-icon><Setting /></el-icon>
            <template #title>系统配置</template>
          </el-menu-item>
        </el-menu>
      </el-aside>
      
      <!-- 主内容区 -->
      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const userStore = useUserStore()

const handleLogout = () => {
  userStore.logout()
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

/* 顶部导航 */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: linear-gradient(135deg, #1A237E 0%, #283593 100%);
  padding: 0 24px;
  height: 70px;
  box-shadow: 0 4px 20px rgba(26, 35, 126, 0.3);
}

.header-left {
  display: flex;
  align-items: center;
}

.logo {
  display: flex;
  align-items: center;
  gap: 12px;
  color: white;
}

.logo-icon {
  width: 40px;
  height: 40px;
  background: rgba(249, 168, 37, 0.2);
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.logo-text {
  font-size: 20px;
  font-weight: 700;
  font-family: var(--font-heading);
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-avatar {
  background: linear-gradient(135deg, #F9A825 0%, #FDD835 100%);
  color: #1A237E;
  font-weight: 700;
}

.user-detail {
  display: flex;
  flex-direction: column;
}

.username {
  color: white;
  font-weight: 600;
  font-size: 14px;
}

.role {
  color: rgba(255, 255, 255, 0.7);
  font-size: 12px;
}

.logout-btn {
  background: rgba(255, 255, 255, 0.15) !important;
  border: none !important;
  color: white !important;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.logout-btn:hover {
  background: rgba(255, 255, 255, 0.25) !important;
}

/* 主容器 */
.main-container {
  height: calc(100vh - 70px);
}

/* 侧边栏 */
.aside {
  width: 220px;
  background: white;
  box-shadow: 4px 0 20px rgba(0, 0, 0, 0.05);
  overflow-y: auto;
}

.sidebar-menu {
  border-right: none !important;
  padding: 16px 0;
}

.menu-item {
  height: 50px !important;
  line-height: 50px !important;
  margin: 4px 12px;
  border-radius: 10px !important;
  color: #616161 !important;
  transition: all 0.3s ease !important;
}

.menu-item:hover {
  background: rgba(26, 35, 126, 0.08) !important;
  color: #1A237E !important;
}

.menu-item.is-active {
  background: linear-gradient(135deg, #1A237E 0%, #3949AB 100%) !important;
  color: white !important;
  font-weight: 600;
}

/* 主内容区 */
.main-content {
  background: #F5F7FA;
  padding: 24px;
  overflow-y: auto;
}
</style>


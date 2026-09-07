<template>
  <el-container class="layout-container">
    <el-aside :width="isCollapse ? '64px' : '220px'" class="aside-menu">
      <!-- Logo区域 -->
      <div class="logo" :class="{ 'collapsed': isCollapse }">
        <div class="logo-icon">
          <el-icon :size="isCollapse ? 24 : 32"><School /></el-icon>
        </div>
        <transition name="fade">
          <div v-if="!isCollapse" class="logo-text">
            <span class="title">校园事务管理</span>
            <span class="subtitle">Campus Affairs</span>
          </div>
        </transition>
      </div>
      
      <!-- 菜单 -->
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :collapse-transition="false"
        class="sidebar-menu"
        router
      >
        <template v-for="route in menuRoutes" :key="route.path">
          <el-menu-item 
            v-if="!route.meta?.hidden && hasPermission(route)" 
            :index="route.path"
            class="menu-item"
          >
            <el-icon class="menu-icon"><component :is="route.meta.icon" /></el-icon>
            <template #title>
              <span class="menu-title">{{ route.meta.title }}</span>
            </template>
          </el-menu-item>
        </template>
      </el-menu>
      
      <!-- 底部折叠按钮 -->
      <div class="sidebar-footer">
        <el-button 
          class="collapse-btn"
          @click="toggleCollapse"
          :icon="isCollapse ? 'Expand' : 'Fold'"
          circle
        />
      </div>
    </el-aside>
    
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <div class="breadcrumb">
            <el-breadcrumb separator="/">
              <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
              <el-breadcrumb-item v-if="route.meta?.title">
                {{ route.meta.title }}
              </el-breadcrumb-item>
            </el-breadcrumb>
          </div>
        </div>
        
        <div class="header-right">
          <!-- 通知图标 -->
          <el-badge :value="3" :max="99" class="notification-badge">
            <el-button class="notification-btn" circle>
              <el-icon :size="18"><Bell /></el-icon>
            </el-button>
          </el-badge>
          
          <!-- 用户信息 -->
          <el-dropdown @command="handleCommand" class="user-dropdown">
            <div class="user-info">
              <el-avatar :size="36" class="user-avatar">
                {{ userInfo?.username?.charAt(0) }}
              </el-avatar>
              <div class="user-detail">
                <span class="username">{{ userInfo?.username }}</span>
                <span class="role">{{ getRoleName(userInfo?.role) }}</span>
              </div>
              <el-icon class="dropdown-icon"><ArrowDown /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">
                  <el-icon><User /></el-icon>个人中心
                </el-dropdown-item>
                <el-dropdown-item command="settings">
                  <el-icon><Setting /></el-icon>系统设置
                </el-dropdown-item>
                <el-dropdown-item command="logout" divided>
                  <el-icon><SwitchButton /></el-icon>退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      
      <el-main class="main-content">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessageBox } from 'element-plus'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const isCollapse = ref(false)
const userInfo = computed(() => userStore.userInfo)

// 获取菜单路由
const menuRoutes = computed(() => {
  return router.getRoutes().find(r => r.path === '/')?.children || []
})

// 当前激活的菜单
const activeMenu = computed(() => {
  const { path } = route
  // 如果是详情页，高亮对应的列表页
  if (path.includes('/notice/')) return '/notice'
  if (path.includes('/activity/')) return '/activity'
  return path
})

// 检查权限
const hasPermission = (route) => {
  if (!route.meta?.roles) return true
  return route.meta.roles.includes(userInfo.value?.role)
}

// 获取角色名称
const getRoleName = (role) => {
  const roleMap = {
    'admin': '管理员',
    'teacher': '教师',
    'student': '学生'
  }
  return roleMap[role] || '用户'
}

// 切换侧边栏折叠
const toggleCollapse = () => {
  isCollapse.value = !isCollapse.value
}

// 处理下拉菜单命令
const handleCommand = (command) => {
  if (command === 'profile') {
    router.push('/profile')
  } else if (command === 'settings') {
    router.push('/settings')
  } else if (command === 'logout') {
    ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      userStore.logout()
    }).catch(() => {})
  }
}

onMounted(() => {
  userStore.initUserInfo()
})
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

/* 侧边栏 */
.aside-menu {
  background: linear-gradient(180deg, #1B5E20 0%, #2E7D32 50%, #1565C0 100%);
  transition: width 0.3s ease;
  overflow-x: hidden;
  display: flex;
  flex-direction: column;
  box-shadow: 4px 0 20px rgba(0, 0, 0, 0.1);
}

/* Logo区域 */
.logo {
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 16px;
  background: rgba(0, 0, 0, 0.2);
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  transition: all 0.3s ease;
}

.logo.collapsed {
  height: 64px;
  padding: 0 8px;
}

.logo-icon {
  width: 40px;
  height: 40px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  flex-shrink: 0;
}

.logo-text {
  margin-left: 12px;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.logo-text .title {
  font-size: 16px;
  font-weight: 700;
  color: white;
  white-space: nowrap;
  font-family: var(--font-heading);
}

.logo-text .subtitle {
  font-size: 10px;
  color: rgba(255, 255, 255, 0.7);
  white-space: nowrap;
  letter-spacing: 1px;
}

/* 菜单样式 */
.sidebar-menu {
  flex: 1;
  border-right: none !important;
  background: transparent !important;
  padding: 10px 0;
}

.sidebar-menu:not(.el-menu--collapse) {
  width: 100%;
}

.menu-item {
  height: 50px !important;
  line-height: 50px !important;
  margin: 4px 8px;
  border-radius: 10px !important;
  color: rgba(255, 255, 255, 0.8) !important;
  transition: all 0.3s ease !important;
}

.menu-item:hover {
  background: rgba(255, 255, 255, 0.15) !important;
  color: white !important;
}

.menu-item.is-active {
  background: rgba(255, 255, 255, 0.25) !important;
  color: white !important;
  font-weight: 600;
}

.menu-icon {
  font-size: 18px;
  margin-right: 8px;
}

.menu-title {
  font-size: 14px;
}

/* 侧边栏底部 */
.sidebar-footer {
  padding: 16px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  display: flex;
  justify-content: center;
}

.collapse-btn {
  background: rgba(255, 255, 255, 0.15) !important;
  border: none !important;
  color: white !important;
  width: 36px;
  height: 36px;
}

.collapse-btn:hover {
  background: rgba(255, 255, 255, 0.25) !important;
}

/* 头部导航 */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: white;
  border-bottom: 1px solid #e6e6e6;
  padding: 0 24px;
  height: 70px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.header-left {
  display: flex;
  align-items: center;
}

.breadcrumb {
  font-size: 14px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

/* 通知图标 */
.notification-badge {
  margin-right: 8px;
}

.notification-btn {
  background: transparent !important;
  border: none !important;
  color: #606266 !important;
  width: 40px;
  height: 40px;
}

.notification-btn:hover {
  background: #f5f7fa !important;
  color: #2E7D32 !important;
}

/* 用户信息 */
.user-dropdown {
  cursor: pointer;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 6px 12px;
  border-radius: 10px;
  transition: all 0.3s ease;
}

.user-info:hover {
  background: #f5f7fa;
}

.user-avatar {
  background: linear-gradient(135deg, #2E7D32 0%, #1565C0 100%);
  color: white;
  font-weight: 600;
}

.user-detail {
  display: flex;
  flex-direction: column;
}

.username {
  font-size: 14px;
  font-weight: 600;
  color: #2C3E50;
}

.role {
  font-size: 11px;
  color: #909399;
}

.dropdown-icon {
  color: #C0C4CC;
  font-size: 12px;
}

/* 主内容区 */
.main-content {
  background: #f5f7fa;
  padding: 24px;
  overflow-y: auto;
}

/* 动画效果 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease, transform 0.3s ease;
}

.fade-enter-from {
  opacity: 0;
  transform: translateY(10px);
}

.fade-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}

/* 响应式 */
@media (max-width: 768px) {
  .aside-menu {
    position: fixed;
    z-index: 1000;
    height: 100vh;
  }
  
  .header {
    padding: 0 16px;
  }
  
  .main-content {
    padding: 16px;
  }
}
</style>


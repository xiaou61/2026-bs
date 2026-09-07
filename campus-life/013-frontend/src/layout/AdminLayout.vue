<template>
  <div class="admin-layout">
    <el-container>
      <el-aside width="200px">
        <div class="logo">
          <el-icon><Setting /></el-icon>
          <span>管理后台</span>
        </div>
        
        <el-menu
          :default-active="activeMenu"
          class="sidebar-menu"
          @select="handleMenuSelect"
        >
          <el-menu-item v-for="menu in visibleMenus" :key="menu.path" :index="menu.path">
            <el-icon><component :is="menu.icon" /></el-icon>
            <span>{{ menu.title }}</span>
          </el-menu-item>
        </el-menu>

        <div class="back-btn">
          <el-button type="primary" link @click="$router.push('/')">
            <el-icon><Back /></el-icon>
            返回前台
          </el-button>
        </div>
      </el-aside>

      <el-container>
        <el-header class="admin-header">
          <div class="header-content">
            <h3>{{ pageTitle }}</h3>
            <div class="user-info">
              <el-avatar :size="32">{{ userStore.userInfo?.nickname?.charAt(0) }}</el-avatar>
              <span>{{ userStore.userInfo?.nickname }}</span>
            </div>
          </div>
        </el-header>

        <el-main class="admin-main">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { DataAnalysis, User, Checked, Box, Bicycle } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)

const menus = [
  { path: '/admin/dashboard', title: '数据概览', icon: DataAnalysis, roles: ['ADMIN', 'OPERATOR'] },
  { path: '/admin/users', title: '用户管理', icon: User, roles: ['ADMIN'] },
  { path: '/admin/auth-list', title: '实名认证审核', icon: Checked, roles: ['ADMIN'] },
  { path: '/admin/idle-audit', title: '物品审核', icon: Box, roles: ['ADMIN'] },
  { path: '/admin/shared-items', title: '共享物品管理', icon: Bicycle, roles: ['ADMIN', 'OPERATOR'] }
]

const visibleMenus = computed(() => {
  const role = userStore.userInfo?.role
  return menus.filter(menu => menu.roles.includes(role))
})

const pageTitle = computed(() => {
  const titles = {
    '/admin/dashboard': '数据概览',
    '/admin/users': '用户管理',
    '/admin/auth-list': '实名认证审核',
    '/admin/idle-audit': '物品审核',
    '/admin/shared-items': '共享物品管理'
  }
  return titles[route.path] || '管理后台'
})

const handleMenuSelect = (index) => {
  router.push(index)
}
</script>

<style scoped>
.admin-layout {
  min-height: 100vh;
  background: #f5f7fa;
}

.el-aside {
  background: #fff;
  border-right: 1px solid #e4e7ed;
  display: flex;
  flex-direction: column;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  font-size: 18px;
  font-weight: bold;
  color: #409eff;
  border-bottom: 1px solid #e4e7ed;
}

.sidebar-menu {
  flex: 1;
  border: none;
}

.back-btn {
  padding: 20px;
  border-top: 1px solid #e4e7ed;
}

.admin-header {
  background: #fff;
  border-bottom: 1px solid #e4e7ed;
  display: flex;
  align-items: center;
  height: 60px;
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.admin-main {
  padding: 20px;
}
</style>

